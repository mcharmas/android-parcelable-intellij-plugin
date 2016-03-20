package pl.charmas.parcelablegenerator.typeserializers.serializers;

import pl.charmas.parcelablegenerator.typeserializers.SerializableValue;
import pl.charmas.parcelablegenerator.typeserializers.TypeSerializer;

public class ParcelableArraySerializer implements TypeSerializer {
    @Override
    public String writeValue(SerializableValue field, String parcel, String flags) {
        return parcel + ".writeTypedArray(" + field.getName() + ", flags);";
    }

    @Override
    public String readValue(SerializableValue field, String parcel) {
        return field.getName() + " = " + parcel + ".createTypedArray(" + field.getType().getDeepComponentType().getCanonicalText() + ".CREATOR);";
    }
}
