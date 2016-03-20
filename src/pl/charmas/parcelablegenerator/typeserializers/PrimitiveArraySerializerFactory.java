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

import pl.charmas.parcelablegenerator.typeserializers.serializers.BooleanSparseArraySerializer;
import pl.charmas.parcelablegenerator.typeserializers.serializers.PrimitiveArraySerializer;

public class PrimitiveArraySerializerFactory implements TypeSerializerFactory {
    private final HashMap<String, TypeSerializer> handledTypes;

    public PrimitiveArraySerializerFactory() {
        handledTypes = new HashMap<String, TypeSerializer>();
        handledTypes.put("boolean[]", new PrimitiveArraySerializer("Boolean"));
        handledTypes.put("byte[]", new PrimitiveArraySerializer("Byte"));
        handledTypes.put("char[]", new PrimitiveArraySerializer("Char"));
        handledTypes.put("double[]", new PrimitiveArraySerializer("Double"));
        handledTypes.put("float[]", new PrimitiveArraySerializer("Float"));
        handledTypes.put("int[]", new PrimitiveArraySerializer("Int"));
        handledTypes.put("long[]", new PrimitiveArraySerializer("Long"));
        handledTypes.put("java.lang.String[]", new PrimitiveArraySerializer("String"));
        handledTypes.put("android.util.SparseBooleanArray", new BooleanSparseArraySerializer());
    }

    @Override
    public TypeSerializer getSerializer(PsiType psiType) {
        return handledTypes.get(psiType.getCanonicalText());
    }
}
