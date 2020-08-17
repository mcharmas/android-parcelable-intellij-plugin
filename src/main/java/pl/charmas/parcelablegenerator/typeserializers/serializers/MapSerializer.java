/*
 * Copyright (C) 2014 Dallas Gutauckis (http://dallasgutauckis.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.charmas.parcelablegenerator.typeserializers.serializers;

import com.intellij.psi.PsiType;
import pl.charmas.parcelablegenerator.typeserializers.SerializableValue;
import pl.charmas.parcelablegenerator.typeserializers.TypeSerializer;
import pl.charmas.parcelablegenerator.typeserializers.TypeSerializerFactory;
import pl.charmas.parcelablegenerator.util.PsiUtils;

import java.util.List;

@SuppressWarnings("StringBufferReplaceableByString")
public class MapSerializer implements TypeSerializer {
    private final TypeSerializerFactory typeSerializerFactory;

    public MapSerializer(TypeSerializerFactory typeSerializerFactory) {
        this.typeSerializerFactory = typeSerializerFactory;
    }


    @Override
    public String writeValue(SerializableValue field, String parcel, String flags) {
        final List<PsiType> resolvedGenerics = PsiUtils.getResolvedGenerics(field.getType());
        PsiType keyType = resolvedGenerics.get(1);
        PsiType valueType = resolvedGenerics.get(0);

        final String fieldName = field.getName();

        return new StringBuilder()
                .append(parcel).append(".writeInt(").append(fieldName).append(".size());")
                .append(String.format("for(Map.Entry<%s,%s> entry : %s.entrySet()) {", keyType.getCanonicalText(), valueType.getCanonicalText(), field.getName()))
                .append(typeSerializerFactory
                        .getSerializer(keyType)
                        .writeValue(SerializableValue.statement("entry.getKey()", keyType), parcel, flags)
                )
                .append(typeSerializerFactory
                        .getSerializer(valueType)
                        .writeValue(SerializableValue.statement("entry.getValue()", valueType), parcel, flags)
                )
                .append("}")
                .toString();
    }

    @Override
    public String readValue(SerializableValue field, String parcel) {
        final List<PsiType> resolvedGenerics = PsiUtils.getResolvedGenerics(field.getType());
        PsiType keyType = resolvedGenerics.get(1);
        PsiType valueType = resolvedGenerics.get(0);

        String sizeVariableName = field.getSimpleName() + "Size";
        return new StringBuilder()
                .append(String.format("int %s = %s.readInt();", sizeVariableName, parcel))
                .append(field.getName()).append(String.format(" = new java.util.HashMap<%s, %s>(%s);", keyType.getCanonicalText(), valueType.getCanonicalText(), sizeVariableName))
                .append(String.format("for(int i = 0; i < %s; i++) {", sizeVariableName))
                .append(typeSerializerFactory.getSerializer(keyType).readValue(SerializableValue.variable("key", keyType), parcel))
                .append(typeSerializerFactory.getSerializer(valueType).readValue(SerializableValue.variable("value", valueType), parcel))
                .append(field.getName()).append(".put(key, value);")
                .append("}")
                .toString();
    }
}
