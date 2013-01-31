package pl.charmas.parcelablegenerator.typeserializers;

import com.intellij.psi.PsiField;

public class UnknownTypeSerializer implements TypeSerializer {
    @Override
    public String writeValue(PsiField field, String parcel, String flags) {
        return "";
    }

    @Override
    public String readValue(PsiField field, String parcel) {
        return "";
    }
}
