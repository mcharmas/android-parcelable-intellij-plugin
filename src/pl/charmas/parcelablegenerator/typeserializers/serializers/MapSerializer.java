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

import com.intellij.psi.PsiField;
import com.intellij.psi.PsiType;

import java.util.List;

import pl.charmas.parcelablegenerator.typeserializers.TypeSerializer;
import pl.charmas.parcelablegenerator.util.PsiUtils;

public class MapSerializer implements TypeSerializer {

    ListPrimitiveSerializer mListSerializer = new ListPrimitiveSerializer();

    @Override
    public String writeValue(PsiField field, String parcel, String flags) {
        final List<PsiType> resolvedGenerics = PsiUtils.getResolvedGenerics(field.getType());

        // Assumes a Map never has more than two generic types — also this is completely anecdotal as I found that the 0th item is the last generic
        String keyGenericType = resolvedGenerics.get(1).getCanonicalText();
        String valueGenericType = resolvedGenerics.get(0).getCanonicalText();

        final String fieldName = field.getName();
        final String keysVarName = fieldName + "Keys";
        final String valuesVarName = fieldName + "Values";

        return "java.util.ArrayList<" + keyGenericType + "> " + keysVarName + " = new java.util.ArrayList();" +
                       keysVarName + ".addAll(this." + fieldName + ".keySet());" +
                       "java.util.ArrayList<" + valueGenericType + "> " + valuesVarName + " = new java.util.ArrayList();" +
                       valuesVarName + ".addAll(this." + fieldName + ".values());" +
                       mListSerializer.writeValue(parcel, keysVarName) +
                       mListSerializer.writeValue(parcel, valuesVarName);
    }

    @Override
    public String readValue(PsiField field, String parcel) {
        final List<PsiType> resolvedGenerics = PsiUtils.getResolvedGenerics(field.getType());

        // Assumes a Map never has more than two generic types — also this is completely anecdotal as I found that the 0th item is the last generic
        String keyGenericType = resolvedGenerics.get(1).getCanonicalText();
        String valueGenericType = resolvedGenerics.get(0).getCanonicalText();

        final String fieldName = field.getName();
        final String keysVarName = fieldName + "Keys";
        final String valuesVarName = fieldName + "Values";

        return "java.util.ArrayList<" + keyGenericType + "> " + keysVarName + ";" +
                       "java.util.ArrayList<" + valueGenericType + "> " + valuesVarName + ";" +
                       mListSerializer.readValue(keyGenericType, keysVarName) +
                       mListSerializer.readValue(valueGenericType, valuesVarName)
                       + fieldName + " = new HashMap<" + keyGenericType + ", " + valueGenericType + ">();"
                       + "for (int i = 0; i < " + keysVarName + ".size(); i++) {"
                       + "    " + fieldName + ".put(" + keysVarName + ".get(i), " + valuesVarName + ".get(i));"
                       + "}";
    }
}
