package pl.charmas.parcelablegenerator.typeserializers.serializers;

import com.intellij.psi.PsiField;
import pl.charmas.parcelablegenerator.typeserializers.TypeSerializer;

public class NullablePrimitivesSerializer implements TypeSerializer {

    private final String typeName;

    public NullablePrimitivesSerializer(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String writeValue(PsiField field, String parcel, String flags) {
        return parcel + ".writeValue(this." + field.getName() + ");";
    }

    @Override
    public String readValue(PsiField field, String parcel) {
        return "this." + field.getName() + " = (" + typeName + ")" + parcel + ".readValue(" + typeName + ".class.getClassLoader());";
    }
}
