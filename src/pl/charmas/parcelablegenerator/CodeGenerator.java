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
package pl.charmas.parcelablegenerator;

import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import pl.charmas.parcelablegenerator.typeserializers.*;

import java.util.List;


/**
 * Quite a few changes here by Dallas Gutauckis [dallas@gutauckis.com]
 */
public class CodeGenerator {

    public static final String CREATOR_NAME = "CREATOR";
    private final PsiClass psiClass;
    private final List<PsiField> fields;
    private final TypeSerializerFactory typeSerializerFactory;

    public CodeGenerator(PsiClass psiClass, List<PsiField> fields) {
        this.psiClass = psiClass;
        this.fields = fields;
        this.typeSerializerFactory = new ChainSerializerFactory(
                new BundleSerializerFactory(),
                new DateSerializerFactory(),
                new EnumerationSerializerFactory(),
                new ParcelableListSerializerFactory(),
                new PrimitiveTypeSerializerFactory(),
                new PrimitiveArraySerializerFactory(),
                new ListSerializerFactory(),
                new ParcelableSerializerFactory(),
                new SerializableSerializerFactory()
        );
    }

    private String generateStaticCreator(PsiClass psiClass) {
        PsiField[] allFields = psiClass.getAllFields();

        // Look for an existing CREATOR and remove it
        for (PsiField field : allFields) {
            if (field.getName().equals(CREATOR_NAME)) {
                // Creator already exists, need to remove/replace it
                field.delete();
            }
        }

        StringBuilder sb = new StringBuilder("public static android.os.Parcelable.Creator<");
        String name = psiClass.getName();
        sb.append(name).append("> CREATOR = new android.os.Parcelable.Creator<").append(name).append(">(){")
                .append("public ").append(name).append(" createFromParcel(android.os.Parcel source) {")
                .append("return new ").append(name).append("(source);}")
                .append("public ").append(name).append("[] newArray(int size) {")
                .append("return new ").append(name).append("[size];}")
                .append("};");
        return sb.toString();
    }

    private String generateConstructor(List<PsiField> fields, PsiClass psiClass) {
        findAndRemoveMethod(psiClass.getName(), "android.os.Parcel");

        StringBuilder sb = new StringBuilder("private ").append(psiClass.getName()).append("(android.os.Parcel in) {");

        for (PsiField field : fields) {
            sb.append(getSerializerForType(field).readValue(field, "in"));
        }

        sb.append("}");
        return sb.toString();
    }

    private String generateWriteToParcel(List<PsiField> fields) {
        // Remove existing method
        findAndRemoveMethod("writeToParcel", "android.os.Parcel", "int");

        StringBuilder sb = new StringBuilder("@Override public void writeToParcel(android.os.Parcel dest, int flags) {");
        for (PsiField field : fields) {
            sb.append(getSerializerForType(field).writeValue(field, "dest", "flags"));
        }
        sb.append("}");
        return sb.toString();
    }

    private TypeSerializer getSerializerForType(PsiField field) {
        return typeSerializerFactory.getSerializer(field.getType());
    }

    private String generateDescribeContents() {
        findAndRemoveMethod("describeContents");

        return "@Override public int describeContents() { return 0; }";
    }

    public void generate() {
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiClass.getProject());

        // Describe contents method
        PsiMethod describeContentsMethod = elementFactory.createMethodFromText(generateDescribeContents(), psiClass);
        // Method for writing to the parcel
        PsiMethod writeToParcelMethod = elementFactory.createMethodFromText(generateWriteToParcel(fields), psiClass);
        // Constructor
        PsiMethod constructor = elementFactory.createMethodFromText(generateConstructor(fields, psiClass), psiClass);
        // CREATOR
        PsiField creatorField = elementFactory.createFieldFromText(generateStaticCreator(psiClass), psiClass);

        JavaCodeStyleManager styleManager = JavaCodeStyleManager.getInstance(psiClass.getProject());

        // Shorten all class references
        styleManager.shortenClassReferences(psiClass.addBefore(describeContentsMethod, psiClass.getLastChild()));
        styleManager.shortenClassReferences(psiClass.addBefore(writeToParcelMethod, psiClass.getLastChild()));
        styleManager.shortenClassReferences(psiClass.addBefore(constructor, psiClass.getLastChild()));
        styleManager.shortenClassReferences(psiClass.addBefore(creatorField, psiClass.getLastChild()));

        makeClassImplementParcelable(elementFactory);
    }

    private void makeClassImplementParcelable(PsiElementFactory elementFactory) {
        final PsiClassType[] implementsListTypes = psiClass.getImplementsListTypes();
        final String implementsType = "android.os.Parcelable";

        for (PsiClassType implementsListType : implementsListTypes) {
            PsiClass resolved = implementsListType.resolve();

            // Already implements Parcelable, no need to add it
            if (resolved != null && implementsType.equals(resolved.getQualifiedName())) {
                return;
            }
        }

        PsiJavaCodeReferenceElement implementsReference = elementFactory.createReferenceFromText(implementsType, psiClass);
        PsiReferenceList implementsList = psiClass.getImplementsList();

        if (implementsList != null) {
            implementsList.add(implementsReference);
        }
    }



    private void findAndRemoveMethod(String methodName, String... arguments) {
        // Maybe there's an easier way to do this with psiClass.findMethodBySignature(), but I'm not an expert on Psi*
        PsiMethod[] methods = psiClass.findMethodsByName(methodName, false);

        for (PsiMethod method : methods) {
            PsiParameterList parameterList = method.getParameterList();

            if (parameterList.getParametersCount() == arguments.length) {
                boolean shouldDelete = true;

                PsiParameter[] parameters = parameterList.getParameters();

                for (int i = 0; i < arguments.length; i++) {
                    if (!parameters[i].getType().getCanonicalText().equals(arguments[i])) {
                        shouldDelete = false;
                    }
                }

                if (shouldDelete) {
                    method.delete();
                }
            }
        }
    }
}
