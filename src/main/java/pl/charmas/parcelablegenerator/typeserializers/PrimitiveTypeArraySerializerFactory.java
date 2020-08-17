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

import pl.charmas.parcelablegenerator.typeserializers.serializers.NullablePrimitivesArraySerializer;

public class PrimitiveTypeArraySerializerFactory implements TypeSerializerFactory {

    private final Map<String, TypeSerializer> writeMethodsForTypes = new HashMap<String, TypeSerializer>();

    public PrimitiveTypeArraySerializerFactory() {
        initPrimitives();
    }

    private void initPrimitives() {
        writeMethodsForTypes.put("java.lang.Byte[]", new NullablePrimitivesArraySerializer("java.lang.Byte[]"));
        writeMethodsForTypes.put("java.lang.Double[]", new NullablePrimitivesArraySerializer("java.lang.Double[]"));
        writeMethodsForTypes.put("java.lang.Float[]", new NullablePrimitivesArraySerializer("java.lang.Float[]"));
        writeMethodsForTypes.put("java.lang.Integer[]", new NullablePrimitivesArraySerializer("java.lang.Integer[]"));
        writeMethodsForTypes.put("java.lang.Long[]", new NullablePrimitivesArraySerializer("java.lang.Long[]"));
        writeMethodsForTypes.put("java.lang.Boolean[]", new NullablePrimitivesArraySerializer("java.lang.Boolean[]"));
        writeMethodsForTypes.put("java.lang.Char[]", new NullablePrimitivesArraySerializer("java.lang.Char[]"));
    }

    @Override
    public TypeSerializer getSerializer(PsiType psiType) {
        return writeMethodsForTypes.get(psiType.getCanonicalText());
    }
}
