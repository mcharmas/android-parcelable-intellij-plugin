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
import pl.charmas.parcelablegenerator.util.PsiUtils;

import java.util.List;


/**
 * Quite a few changes here by Dallas Gutauckis [dallas@gutauckis.com]
 */
public class CodeGenerator {
    public static final String CREATOR_NAME = "CREATOR";
    public static final String TYPE_PARCEL = "android.os.Parcel";

    private final PsiClass mClass;
    private final List<PsiField> mFields;
    private final TypeSerializerFactory mTypeSerializerFactory;

    public CodeGenerator(PsiClass psiClass, List<PsiField> fields) {
        mClass = psiClass;
        mFields = fields;

        ChainSerializerFactory baseChain = new ChainSerializerFactory(
                new BundleSerializerFactory(),
                new DateSerializerFactory(),
                new EnumerationSerializerFactory(),
                new PrimitiveTypeSerializerFactory(),
                new PrimitiveArraySerializerFactory(),
                new PrimitiveTypeArraySerializerFactory(),
                new ParcelableSerializerFactory(),
                new ListSerializerFactory(),
                new SerializableSerializerFactory(),
                new SparseArraySerializerFactory()
        );
        this.mTypeSerializerFactory = baseChain.extend(new MapSerializerFactory(baseChain));
    }

    private String generateStaticCreator(PsiClass psiClass) {
        StringBuilder sb = new StringBuilder("public static final android.os.Parcelable.Creator<");

        String className = psiClass.getName();

        sb.append(className).append("> CREATOR = new android.os.Parcelable.Creator<").append(className).append(">(){")
                .append("@Override ")
                .append("public ").append(className).append(" createFromParcel(android.os.Parcel source) {")
                .append("return new ").append(className).append("(source);}")
                .append("@Override ")
                .append("public ").append(className).append("[] newArray(int size) {")
                .append("return new ").append(className).append("[size];}")
                .append("};");
        return sb.toString();
    }

    private String generateConstructor(List<PsiField> fields, PsiClass psiClass) {
        String className = psiClass.getName();

        StringBuilder sb = new StringBuilder("protected ");

        // Create the Parcelable-required constructor
        sb.append(className).append("(android.os.Parcel in) {");

        if (hasParcelableSuperclass() && hasParcelableSuperConstructor()) {
            sb.append("super(in);");
        }

        // Creates all of the deserialization methods for the given fields
        for (PsiField field : fields) {
            sb.append(getSerializerForType(field).readValue(SerializableValue.member(field), "in"));
        }

        sb.append("}");
        return sb.toString();
    }

    private boolean hasParcelableSuperConstructor() {
        PsiMethod[] constructors = mClass.getSuperClass() != null ? mClass.getSuperClass().getConstructors() : new PsiMethod[0];
        for (PsiMethod constructor : constructors) {
            PsiParameterList parameterList = constructor.getParameterList();
            if (parameterList.getParametersCount() == 1
                    && parameterList.getParameters()[0].getType().getCanonicalText().equals(TYPE_PARCEL)) {
                return true;
            }
        }
        return false;
    }

    private String generateWriteToParcel(List<PsiField> fields) {
        StringBuilder sb = new StringBuilder("@Override public void writeToParcel(android.os.Parcel dest, int flags) {");
        if (hasParcelableSuperclass() && hasSuperMethod("writeToParcel")) {
            sb.append("super.writeToParcel(dest, flags);");
        }
        for (PsiField field : fields) {
            sb.append(getSerializerForType(field).writeValue(SerializableValue.member(field), "dest", "flags"));
        }

        sb.append("}");

        return sb.toString();
    }

    private boolean hasSuperMethod(String methodName) {
        if (methodName == null) return false;

        PsiMethod[] superclassMethods = mClass.getSuperClass() != null ? mClass.getAllMethods() : new PsiMethod[0];
        for (PsiMethod superclassMethod : superclassMethods) {
            if (superclassMethod.getBody() == null) continue;

            String name = superclassMethod.getName();
            if (name != null && name.equals(methodName)) {
                return true;
            }
        }
        return false;
    }

    private TypeSerializer getSerializerForType(PsiField field) {
        return mTypeSerializerFactory.getSerializer(field.getType());
    }

    private String generateDescribeContents() {
        return "@Override public int describeContents() { return 0; }";
    }

    public void generate() {
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(mClass.getProject());

        removeExistingParcelableImplementation(mClass);

        // Describe contents method
        PsiMethod describeContentsMethod = elementFactory.createMethodFromText(generateDescribeContents(), mClass);
        // Method for writing to the parcel
        PsiMethod writeToParcelMethod = elementFactory.createMethodFromText(generateWriteToParcel(mFields), mClass);

        // Default constructor if needed
        String defaultConstructorString = generateDefaultConstructor(mClass);
        PsiMethod defaultConstructor = null;

        if (defaultConstructorString != null) {
            defaultConstructor = elementFactory.createMethodFromText(defaultConstructorString, mClass);
        }

        // Constructor
        PsiMethod constructor = elementFactory.createMethodFromText(generateConstructor(mFields, mClass), mClass);
        // CREATOR
        PsiField creatorField = elementFactory.createFieldFromText(generateStaticCreator(mClass), mClass);

        JavaCodeStyleManager styleManager = JavaCodeStyleManager.getInstance(mClass.getProject());

        // Shorten all class references
        styleManager.shortenClassReferences(mClass.addBefore(describeContentsMethod, mClass.getLastChild()));
        styleManager.shortenClassReferences(mClass.addBefore(writeToParcelMethod, mClass.getLastChild()));

        // Only adds if available
        if (defaultConstructor != null) {
            styleManager.shortenClassReferences(mClass.addBefore(defaultConstructor, mClass.getLastChild()));
        }

        styleManager.shortenClassReferences(mClass.addBefore(constructor, mClass.getLastChild()));
        styleManager.shortenClassReferences(mClass.addBefore(creatorField, mClass.getLastChild()));

        makeClassImplementParcelable(elementFactory);
    }

    private boolean hasParcelableSuperclass() {
        PsiClassType[] superTypes = mClass.getSuperTypes();
        for (PsiClassType superType : superTypes) {
            if (PsiUtils.isOfType(superType, "android.os.Parcelable")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Strips the
     *
     * @param psiClass
     */
    private void removeExistingParcelableImplementation(PsiClass psiClass) {
        PsiField[] allFields = psiClass.getAllFields();

        // Look for an existing CREATOR and remove it
        for (PsiField field : allFields) {
            if (field.getName().equals(CREATOR_NAME)) {
                // Creator already exists, need to remove/replace it
                field.delete();
            }
        }

        findAndRemoveMethod(psiClass, psiClass.getName(), TYPE_PARCEL);
        findAndRemoveMethod(psiClass, "describeContents");
        findAndRemoveMethod(psiClass, "writeToParcel", TYPE_PARCEL, "int");
    }

    private String generateDefaultConstructor(PsiClass clazz) {
        // Check for any constructors; if none exist, we'll make a default one
        if (clazz.getConstructors().length == 0) {
            // No constructors exist, make a default one for convenience
            return "public " + clazz.getName() + "(){}" + '\n';
        } else {
            return null;
        }
    }

    private void makeClassImplementParcelable(PsiElementFactory elementFactory) {
        if (hasParcelableSuperclass()) return;

        final PsiClassType[] implementsListTypes = mClass.getImplementsListTypes();
        final String implementsType = "android.os.Parcelable";

        for (PsiClassType implementsListType : implementsListTypes) {
            PsiClass resolved = implementsListType.resolve();

            // Already implements Parcelable, no need to add it
            if (resolved != null && implementsType.equals(resolved.getQualifiedName())) {
                return;
            }
        }

        PsiJavaCodeReferenceElement implementsReference = elementFactory.createReferenceFromText(implementsType, mClass);
        PsiReferenceList implementsList = mClass.getImplementsList();

        if (implementsList != null) {
            implementsList.add(implementsReference);
        }
    }


    private static void findAndRemoveMethod(PsiClass clazz, String methodName, String... arguments) {
        // Maybe there's an easier way to do this with mClass.findMethodBySignature(), but I'm not an expert on Psi*
        PsiMethod[] methods = clazz.findMethodsByName(methodName, false);

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
