package pl.charmas.parcelablegenerator.typeserializers.serializers;

import pl.charmas.parcelablegenerator.typeserializers.SerializableValue;
import pl.charmas.parcelablegenerator.typeserializers.TypeSerializer;
import pl.charmas.parcelablegenerator.util.PsiUtils;

/**
 * @author Dallas Gutauckis [dallas@gutauckis.com]
 * @author Michał Charmas [michal@charmas.pl]
 */
public class ParcelableListSerializer implements TypeSerializer {
    @Override
    public String writeValue(SerializableValue field, String parcel, String flags) {
        return String.format("%s.writeTypedList(%s);", parcel, field.getName());
    }

    @Override
    public String readValue(SerializableValue field, String parcel) {
        String paramType = PsiUtils.getResolvedGenerics(field.getType()).get(0).getCanonicalText();
        return String.format("%s = %s.createTypedArrayList(%s.CREATOR);", field.getName(), parcel, paramType);
    }
}
