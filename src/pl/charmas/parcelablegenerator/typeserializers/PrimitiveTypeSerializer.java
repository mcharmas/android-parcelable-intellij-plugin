package pl.charmas.parcelablegenerator.typeserializers;

import com.intellij.psi.PsiField;

public class PrimitiveTypeSerializer implements TypeSerializer {

    private final String typeName;

    public PrimitiveTypeSerializer(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String writeValue(PsiField field, String parcel, String flags) {
        return parcel + ".write" + typeName + "(this." + field.getName() + ");";
    }

    @Override
    public String readValue(PsiField field, String parcel) {
        return "this." + field.getName() + " = " + parcel + ".read" + typeName + "();";
    }
}
