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
import pl.charmas.parcelablegenerator.typeserializers.serializers.ListPrimitiveSerializer;

public class ListSerializerFactory implements TypeSerializerFactory {
    @Override
    public TypeSerializer getSerializer(PsiType psiType) {
        String typeName = psiType.getCanonicalText();
        if (typeName.startsWith("java.util.List")) {
            String genericTypeName = typeName.replaceAll("java.util.List<", "");
            genericTypeName = genericTypeName.replaceAll(">", "");
            return new ListPrimitiveSerializer(genericTypeName);
        }
        return null;
    }
}
