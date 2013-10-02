package pl.charmas.parcelablegenerator.typeserializers.serializers;

import com.intellij.psi.PsiField;
import pl.charmas.parcelablegenerator.typeserializers.TypeSerializer;

public class SerializableObjectSerializer implements TypeSerializer {
    private final String castType;

    public SerializableObjectSerializer(String castType) {
        this.castType = castType;
    }

    @Override
    public String writeValue(PsiField field, String parcel, String flags) {
        return parcel + ".writeSerializable(this." + field.getName() + ");";
    }

    @Override
    public String readValue(PsiField field, String parcel) {
        return "this." + field.getName() + " = (" + castType + ") " + parcel + ".readSerializable();";
    }
}
