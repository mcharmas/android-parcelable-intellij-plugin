package pl.charmas.parcelablegenerator.typeserializers.serializers;

import com.intellij.psi.PsiField;
import pl.charmas.parcelablegenerator.typeserializers.TypeSerializer;

public class PrimitiveArraySerializer implements TypeSerializer {

    private final String type;

    public PrimitiveArraySerializer(String type) {
        this.type = type;
    }

    @Override
    public String writeValue(PsiField field, String parcel, String flags) {
        return parcel + ".write" + type + "Array(this." + field.getName() + ");";
    }

    @Override
    public String readValue(PsiField field, String parcel) {
        return "this." + field.getName() + " = " + parcel + ".create" + type + "Array();";
    }
}
