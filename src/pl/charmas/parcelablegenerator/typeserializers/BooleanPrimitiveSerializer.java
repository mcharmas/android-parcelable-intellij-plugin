package pl.charmas.parcelablegenerator.typeserializers;

import com.intellij.psi.PsiField;

public class BooleanPrimitiveSerializer implements TypeSerializer {

    @Override
    public String writeValue(PsiField field, String parcel, String flags) {
        return parcel + ".writeByte(" + field.getName() + " ? (byte) 1 : (byte) 0);";
    }

    @Override
    public String readValue(PsiField field, String parcel) {
        return "this." + field.getName() + " = " + parcel + ".readByte() != 0;";
    }
}
