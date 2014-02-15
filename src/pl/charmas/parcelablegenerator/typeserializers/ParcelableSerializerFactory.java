package pl.charmas.parcelablegenerator.typeserializers;

import com.intellij.psi.PsiType;
import pl.charmas.parcelablegenerator.typeserializers.serializers.ParcelableObjectSerializer;
import pl.charmas.parcelablegenerator.util.PsiUtils;

/**
 * Serializer factory for Parcelable objects
 *
 * @author Dallas Gutauckis [dallas@gutauckis.com]
 */
public class ParcelableSerializerFactory implements TypeSerializerFactory {

    private ParcelableObjectSerializer mSerializer = new ParcelableObjectSerializer();

    @Override
    public TypeSerializer getSerializer(PsiType psiType) {
        if (PsiUtils.isOfType(psiType, "android.os.Parcelable")) {
            return mSerializer;
        }

        return null;
    }
}
