package pl.charmas.parcelablegenerator.typeserializers.serializers;

import org.apache.xmlbeans.impl.common.NameUtil;
import pl.charmas.parcelablegenerator.typeserializers.SerializableValue;
import pl.charmas.parcelablegenerator.typeserializers.TypeSerializer;

/**
 * Modified by Dallas Gutauckis [dallas@gutauckis.com]
 */
public class EnumerationSerializer implements TypeSerializer {
    @Override
    public String writeValue(SerializableValue field, String parcel, String flags) {
        String fieldName = field.getName();
        return String.format("%s.writeInt(%s == null ? -1 : %s.ordinal());", parcel, fieldName, fieldName);
    }

    @Override
    public String readValue(SerializableValue field, String parcel) {
        String tmpFieldName = NameUtil.upperCaseFirstLetter(field.getSimpleName());
        String format = "int tmp%s = %s.readInt();"
                + "%s = tmp%s == -1 ? null : %s.values()[tmp%s];";
        return String.format(format, tmpFieldName, parcel, field.getName(), tmpFieldName, field.getType().getCanonicalText(), tmpFieldName);
    }
}
