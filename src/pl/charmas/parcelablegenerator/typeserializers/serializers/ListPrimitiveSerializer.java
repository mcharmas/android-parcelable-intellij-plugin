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
package pl.charmas.parcelablegenerator.typeserializers.serializers;

import com.intellij.psi.PsiField;
import com.intellij.psi.PsiType;

import java.util.List;

import pl.charmas.parcelablegenerator.typeserializers.TypeSerializer;
import pl.charmas.parcelablegenerator.util.PsiUtils;

public class ListPrimitiveSerializer implements TypeSerializer {

    public String writeValue(String parcel, String assignTo) {
        return parcel + ".writeList(" + assignTo + ");";
    }

    public String readValue(String genericType, String assignTo) {
        return assignTo + " = new java.util.ArrayList<" + genericType + ">();" +
                       "in.readList(" + assignTo + " ," + genericType + ".class.getClassLoader());";
    }

    @Override
    public String writeValue(PsiField field, String parcel, String flags) {
        final String fieldName = field.getName();
        return writeValue(parcel, "this." + fieldName);
    }

    @Override
    public String readValue(PsiField field, String parcel) {
        final List<PsiType> resolvedGenerics = PsiUtils.getResolvedGenerics(field.getType());

        // Assumes a List never has more than one generic type
        String genericType = resolvedGenerics.get(0).getCanonicalText();

        final String fieldName = field.getName();

        return readValue(genericType, "this." + fieldName);
    }
}
