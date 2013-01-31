package pl.charmas.parcelablegenerator;

import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGenerator {

    private final PsiClass psiClass;
    private final List<PsiField> fields;
    private final Map<String, MethodForType> writeMethodsForTypes = new HashMap<String, MethodForType>();

    public CodeGenerator(PsiClass psiClass, List<PsiField> fields) {
        this.psiClass = psiClass;
        this.fields = fields;

        writeMethodsForTypes.put("byte", new MethodForType("writeByte", "readByte"));
        writeMethodsForTypes.put("double", new MethodForType("writeDouble", "readDouble"));
        writeMethodsForTypes.put("float", new MethodForType("writeFloat", "readFloat"));
        writeMethodsForTypes.put("int", new MethodForType("writeInt", "readInt"));
        writeMethodsForTypes.put("long", new MethodForType("writeLong", "readLong"));
        writeMethodsForTypes.put("java.lang.String", new MethodForType("writeString", "readString"));
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
            sb.append("this.").append(field.getName()).append(" = ").append(getMethodBasedOnType(field.getType()).getReadMethod()).append("();\n");
        }
        sb.append("}");
        return sb.toString();
    }


    private String generateWriteToParcel(List<PsiField> fields) {
        StringBuilder sb = new StringBuilder("@Override public void writeToParcel(android.os.Parcel dest, int flags) {");
        for (PsiField field : fields) {
            sb.append("dest.").append(getMethodBasedOnType(field.getType()).getWriteMethod()).append("(").append(field.getName()).append(");\n");
        }
        sb.append("}");
        return sb.toString();
    }

    private MethodForType getMethodBasedOnType(PsiType type) {
        String canonicalText = type.getCanonicalText();
        System.out.println(canonicalText);
        return writeMethodsForTypes.get(canonicalText);
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
    }

    private static class MethodForType {
        private String readMethod;
        private String writeMethod;

        private MethodForType(String writeMethod, String readMethod) {
            this.readMethod = readMethod;
            this.writeMethod = writeMethod;
        }

        public String getReadMethod() {
            return readMethod;
        }

        public String getWriteMethod() {
            return writeMethod;
        }
    }
}
