package pl.charmas.parcelablegenerator.typeserializers;

import com.intellij.psi.PsiField;

public interface TypeSerializer {

    String writeValue(PsiField field, String parcel, String flags);

    String readValue(PsiField field, String parcel);
}
