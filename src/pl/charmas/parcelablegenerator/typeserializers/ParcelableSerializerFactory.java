package pl.charmas.parcelablegenerator.typeserializers;

import com.intellij.psi.PsiType;
import pl.charmas.parcelablegenerator.typeserializers.serializers.ParcelableObjectSerializer;

/**
 * Serializer factory for Parcelable objects
 *
 * @author Dallas Gutauckis [dallas@gutauckis.com]
 */
public class ParcelableSerializerFactory implements TypeSerializerFactory {

    @Override
    public TypeSerializer getSerializer(PsiType psiType) {
        PsiType[] superTypes = psiType.getSuperTypes();

        for (PsiType superType : superTypes) {
            String canonicalText = superType.getCanonicalText();

            if ("android.os.Parcelable".equals(canonicalText)) {
                return new ParcelableObjectSerializer();
            }
        }

        return null;
    }
}
