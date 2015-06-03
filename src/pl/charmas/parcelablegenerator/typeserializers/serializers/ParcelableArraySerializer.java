package pl.charmas.parcelablegenerator.typeserializers.serializers;

import com.intellij.psi.PsiField;
import pl.charmas.parcelablegenerator.typeserializers.TypeSerializer;

public class ParcelableArraySerializer implements TypeSerializer {
    @Override
    public String writeValue(PsiField field, String parcel, String flags) {
        return parcel + ".writeParcelableArray(this." + field.getName() + ", 0);";
    }

    @Override
    public String readValue(PsiField field, String parcel) {
        return "this." + field.getName() + " = (" + field.getType().getCanonicalText() + ")" + parcel + ".readParcelableArray(" + field.getType().getDeepComponentType().getCanonicalText() + ".class.getClassLoader());";
    }
}
