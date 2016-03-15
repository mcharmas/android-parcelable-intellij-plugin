package pl.charmas.parcelablegenerator.typeserializers.serializers;

import com.intellij.psi.PsiField;

import pl.charmas.parcelablegenerator.typeserializers.TypeSerializer;

public class ShortPrimitiveSerializer implements TypeSerializer {

    @Override
    public String writeValue(PsiField field, String parcel, String flags) {
        return parcel + ".writeInt(this." + field.getName() + ");";
    }

    @Override
    public String readValue(PsiField field, String parcel) {
        return "this." + field.getName() + " = (short) " + parcel + ".readInt();";
    }
}