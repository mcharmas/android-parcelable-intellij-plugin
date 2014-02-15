package pl.charmas.parcelablegenerator.typeserializers.serializers;

import com.intellij.psi.PsiField;
import org.apache.xmlbeans.impl.common.NameUtil;
import pl.charmas.parcelablegenerator.typeserializers.TypeSerializer;

/**
 * Modified by Dallas Gutauckis [dallas@gutauckis.com]
 */
public class EnumerationSerializer implements TypeSerializer {
    @Override
    public String writeValue(PsiField field, String parcel, String flags) {
        String fieldName = field.getName();
        return String.format("%s.writeInt(this.%s == null ? -1 : this.%s.ordinal());", parcel, fieldName, fieldName);
    }

    @Override
    public String readValue(PsiField field, String parcel) {
        String fieldName = field.getName();
        String tmpFieldName = NameUtil.upperCaseFirstLetter(fieldName);
        String format = "int tmp%s = %s.readInt();"
                + "this.%s = tmp%s == -1 ? null : %s.values()[tmp%s];";
        return String.format(format, tmpFieldName, parcel, fieldName, tmpFieldName, field.getType().getCanonicalText(), tmpFieldName);
    }
}
