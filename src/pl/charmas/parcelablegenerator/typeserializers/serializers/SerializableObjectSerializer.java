package pl.charmas.parcelablegenerator.typeserializers.serializers;

import pl.charmas.parcelablegenerator.typeserializers.SerializableValue;
import pl.charmas.parcelablegenerator.typeserializers.TypeSerializer;

/**
 * Modified by Dallas Gutauckis [dallas@gutauckis.com]
 */
public class SerializableObjectSerializer implements TypeSerializer {
    @Override
    public String writeValue(SerializableValue field, String parcel, String flags) {
        return parcel + ".writeSerializable(" + field.getName() + ");";
    }

    @Override
    public String readValue(SerializableValue field, String parcel) {
        return field.getName() + " = (" + field.getType().getCanonicalText() + ") " + parcel + ".readSerializable();";
    }
}
