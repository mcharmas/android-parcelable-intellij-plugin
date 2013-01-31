package pl.charmas.parcelablegenerator;

import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import pl.charmas.parcelablegenerator.typeserializers.BooleanPrimitiveSerializer;
import pl.charmas.parcelablegenerator.typeserializers.PrimitiveTypeSerializer;
import pl.charmas.parcelablegenerator.typeserializers.TypeSerializer;
import pl.charmas.parcelablegenerator.typeserializers.UnknownTypeSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGenerator {

    private final PsiClass psiClass;
    private final List<PsiField> fields;
    private final Map<String, TypeSerializer> writeMethodsForTypes = new HashMap<String, TypeSerializer>();

    public CodeGenerator(PsiClass psiClass, List<PsiField> fields) {
        this.psiClass = psiClass;
        this.fields = fields;

        writeMethodsForTypes.put("byte", new PrimitiveTypeSerializer("Byte"));
        writeMethodsForTypes.put("double", new PrimitiveTypeSerializer("Double"));
        writeMethodsForTypes.put("float", new PrimitiveTypeSerializer("Float"));
        writeMethodsForTypes.put("int", new PrimitiveTypeSerializer("Int"));
        writeMethodsForTypes.put("long", new PrimitiveTypeSerializer("Long"));
        writeMethodsForTypes.put("java.lang.String", new PrimitiveTypeSerializer("String"));
        writeMethodsForTypes.put("boolean", new BooleanPrimitiveSerializer());
    }

    private String generateStaticCreator(PsiClass psiClass) {
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
        StringBuilder sb = new StringBuilder("private ").append(psiClass.getName()).append("(android.os.Parcel in) {");
        for (PsiField field : fields) {
            sb.append(getMethodBasedOnType(field.getType()).readValue(field, "in"));
        }
        sb.append("}");
        return sb.toString();
    }


    private String generateWriteToParcel(List<PsiField> fields) {
        StringBuilder sb = new StringBuilder("@Override public void writeToParcel(android.os.Parcel dest, int flags) {");
        for (PsiField field : fields) {
            sb.append(getMethodBasedOnType(field.getType()).writeValue(field, "dest", "flags"));
        }
        sb.append("}");
        return sb.toString();
    }

    private TypeSerializer getMethodBasedOnType(PsiType type) {
        String canonicalText = type.getCanonicalText();
        System.out.println(canonicalText);
        TypeSerializer typeSerializer = writeMethodsForTypes.get(canonicalText);
        return typeSerializer == null ? new UnknownTypeSerializer() : typeSerializer;
    }

    protected String generateDescribeContents() {
        return "@Override public int describeContents() { return 0; }";
    }

    public void generate() {
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiClass.getProject());
        PsiMethod describeContentsMethod = elementFactory.createMethodFromText(generateDescribeContents(), psiClass);
        PsiMethod writeToParcelMethod = elementFactory.createMethodFromText(generateWriteToParcel(fields), psiClass);
        PsiMethod constructor = elementFactory.createMethodFromText(generateConstructor(fields, psiClass), psiClass);
        PsiField creatorField = elementFactory.createFieldFromText(generateStaticCreator(psiClass), psiClass);


        JavaCodeStyleManager styleManager = JavaCodeStyleManager.getInstance(psiClass.getProject());
        styleManager.shortenClassReferences(psiClass.add(describeContentsMethod));
        styleManager.shortenClassReferences(psiClass.add(writeToParcelMethod));
        styleManager.shortenClassReferences(psiClass.add(constructor));
        styleManager.shortenClassReferences(psiClass.add(creatorField));
        makeClassImplementParcelable(elementFactory);
    }

    private void makeClassImplementParcelable(PsiElementFactory elementFactory) {
        PsiClassType[] implementsListTypes = psiClass.getImplementsListTypes();
        for (PsiClassType implementsListType : implementsListTypes) {
            PsiClass resolved = implementsListType.resolve();
            if (resolved != null && "android.os.Parcelable".equals(resolved.getQualifiedName())) {
                return;
            }
        }

        String implementsType = "android.os.Parcelable";
        PsiJavaCodeReferenceElement implementsReference = elementFactory.createReferenceFromText(implementsType, psiClass);
        PsiReferenceList implementsList = psiClass.getImplementsList();
        if (implementsList != null) {
            implementsList.add(implementsReference);
        }
    }

}
