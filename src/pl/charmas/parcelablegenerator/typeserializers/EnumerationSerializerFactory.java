package pl.charmas.parcelablegenerator.typeserializers;

import com.intellij.psi.PsiType;
import pl.charmas.parcelablegenerator.typeserializers.serializers.EnumerationSerializer;
import pl.charmas.parcelablegenerator.util.PsiUtils;

/**
 * Modified by Dallas Gutauckis [dallas@gutauckis.com]
 */
public class EnumerationSerializerFactory implements TypeSerializerFactory {
    private TypeSerializer mSerializer = new EnumerationSerializer();

    @Override
    public TypeSerializer getSerializer(PsiType psiType) {
        if (PsiUtils.isOfType(psiType, "java.lang.Enum")) {
            return mSerializer;
        }

        return null;
    }
}
