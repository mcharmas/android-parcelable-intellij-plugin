package pl.charmas.parcelablegenerator.typeserializers.serializers;

import com.intellij.psi.PsiField;
import pl.charmas.parcelablegenerator.typeserializers.TypeSerializer;

public class UnknownTypeSerializer implements TypeSerializer {

    private final String typeName;

    public UnknownTypeSerializer(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String writeValue(PsiField field, String parcel, String flags) {
        return parcel + ".writeParcelable(this." + field.getName() + ", " + flags + ");";
    }

    @Override
    public String readValue(PsiField field, String parcel) {
        return "this." + field.getName() + " = " + parcel + ".readParcelable(" + this.typeName + ".class.getClassLoader());";
    }
}
