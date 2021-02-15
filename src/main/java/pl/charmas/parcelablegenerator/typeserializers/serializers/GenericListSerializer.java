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

import com.intellij.psi.PsiType;
import com.intellij.psi.impl.source.PsiClassReferenceType;

import org.jetbrains.annotations.NotNull;

import pl.charmas.parcelablegenerator.typeserializers.SerializableValue;
import pl.charmas.parcelablegenerator.typeserializers.TypeSerializer;

public class GenericListSerializer implements TypeSerializer {

    public static final String STRING_TYPE_NAME = "java.lang.String";

    public String writeValue(String parcel, String valueType, String valueVarName) {
        String method = "writeList";
        if (valueType.equals(STRING_TYPE_NAME)) {
            method = "writeStringList";
        }
        return parcel + "." + method + "(" + valueVarName + ");";
    }

    public String readValue(String parcel, String valueType, String valueVarName) {
        StringBuilder statement = new StringBuilder();
        if (valueType.equals(STRING_TYPE_NAME)) {
            statement.append(valueVarName).append("=").append(parcel).append(".createStringArrayList();");
        } else {
            String listConstructor = !valueType.isEmpty()
                    ? "new java.util.ArrayList<" + valueType + ">();"
                    : "new java.util.ArrayList();";

            statement.append(valueVarName).append(" = ").append(listConstructor);
            statement.append(parcel).append(".readList(").append(valueVarName).append(", ").append(valueType).append(".class.getClassLoader());");
        }

        return statement.toString();
    }

    @Override
    public String writeValue(SerializableValue field, String parcel, String flags) {
        return writeValue(parcel, getGenericType(field), field.getName());
    }

    @Override
    public String readValue(SerializableValue field, String parcel) {
        final String valueType = getGenericType(field);
        final String valueVarName = field.getName();
        return readValue(parcel, valueType, valueVarName);
    }

    @NotNull
    private String getGenericType(SerializableValue field) {
        String genericType = "";
        try {
            PsiType[] parameters = ((PsiClassReferenceType) field.getType()).getParameters();
            if (parameters.length > 0) {
                genericType = parameters[0].getCanonicalText();
            }
        } catch (Exception ignored) {
        }
        return genericType;
    }
}
