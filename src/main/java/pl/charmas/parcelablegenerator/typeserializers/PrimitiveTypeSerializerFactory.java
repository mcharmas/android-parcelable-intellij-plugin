/*
 * Copyright (C) 2013 Michał Charmas (http://blog.charmas.pl)
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
package pl.charmas.parcelablegenerator.typeserializers;

import com.intellij.psi.PsiType;

import java.util.HashMap;
import java.util.Map;

import pl.charmas.parcelablegenerator.typeserializers.serializers.BooleanPrimitiveSerializer;
import pl.charmas.parcelablegenerator.typeserializers.serializers.CharPrimitiveSerializer;
import pl.charmas.parcelablegenerator.typeserializers.serializers.NullablePrimitivesSerializer;
import pl.charmas.parcelablegenerator.typeserializers.serializers.PrimitiveTypeSerializer;
import pl.charmas.parcelablegenerator.typeserializers.serializers.ShortPrimitiveSerializer;

public class PrimitiveTypeSerializerFactory implements TypeSerializerFactory {

    private final Map<String, TypeSerializer> writeMethodsForTypes = new HashMap<String, TypeSerializer>();

    public PrimitiveTypeSerializerFactory() {
        initPrimitives();
        initNullablePrimitives();
    }

    private void initNullablePrimitives() {
        writeMethodsForTypes.put("java.lang.Byte", new NullablePrimitivesSerializer("java.lang.Byte"));
        writeMethodsForTypes.put("java.lang.Double", new NullablePrimitivesSerializer("java.lang.Double"));
        writeMethodsForTypes.put("java.lang.Float", new NullablePrimitivesSerializer("java.lang.Float"));
        writeMethodsForTypes.put("java.lang.Short", new NullablePrimitivesSerializer("java.lang.Short"));
        writeMethodsForTypes.put("java.lang.Integer", new NullablePrimitivesSerializer("java.lang.Integer"));
        writeMethodsForTypes.put("java.lang.Long", new NullablePrimitivesSerializer("java.lang.Long"));
        writeMethodsForTypes.put("java.lang.Boolean", new NullablePrimitivesSerializer("java.lang.Boolean"));
        writeMethodsForTypes.put("java.lang.Char", new NullablePrimitivesSerializer("java.lang.Char"));
    }

    private void initPrimitives() {
        writeMethodsForTypes.put("byte", new PrimitiveTypeSerializer("Byte"));
        writeMethodsForTypes.put("double", new PrimitiveTypeSerializer("Double"));
        writeMethodsForTypes.put("float", new PrimitiveTypeSerializer("Float"));
        writeMethodsForTypes.put("short", new ShortPrimitiveSerializer());
        writeMethodsForTypes.put("int", new PrimitiveTypeSerializer("Int"));
        writeMethodsForTypes.put("long", new PrimitiveTypeSerializer("Long"));
        writeMethodsForTypes.put("java.lang.String", new PrimitiveTypeSerializer("String"));
        writeMethodsForTypes.put("boolean", new BooleanPrimitiveSerializer());
        writeMethodsForTypes.put("char", new CharPrimitiveSerializer());
    }

    @Override
    public TypeSerializer getSerializer(PsiType psiType) {
        return writeMethodsForTypes.get(psiType.getCanonicalText());
    }
}
