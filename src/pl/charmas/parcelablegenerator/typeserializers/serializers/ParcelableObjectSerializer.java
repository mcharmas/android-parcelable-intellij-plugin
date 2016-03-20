package pl.charmas.parcelablegenerator.typeserializers.serializers;

import pl.charmas.parcelablegenerator.typeserializers.SerializableValue;
import pl.charmas.parcelablegenerator.typeserializers.TypeSerializer;

/**
 * Serializer for types implementing Parcelable
 *
 * @author Dallas Gutauckis [dallas@gutauckis.com]
 */
public class ParcelableObjectSerializer implements TypeSerializer {
    @Override
    public String writeValue(SerializableValue field, String parcel, String flags) {
        return parcel + ".writeParcelable(" + field.getName() + ", " + flags + ");";
    }

    @Override
    public String readValue(SerializableValue field, String parcel) {
        return field.getName() + " = " + parcel + ".readParcelable(" + field.getType().getCanonicalText() + ".class.getClassLoader());";
    }
}
