package pl.charmas.parcelablegenerator.typeserializers;

import com.intellij.psi.PsiType;
import pl.charmas.parcelablegenerator.typeserializers.serializers.SerializableObjectSerializer;

import java.util.HashSet;

/**
 * Modified by Dallas Gutauckis [dallas@gutauckis.com]
 */
public class SerializableSerializerFactory implements TypeSerializerFactory {
    private TypeSerializer mSerializer;

    public SerializableSerializerFactory() {
        mSerializer = new SerializableObjectSerializer();
    }

    @Override
    public TypeSerializer getSerializer(PsiType psiType) {
        PsiType[] superTypes = psiType.getSuperTypes();

        for (PsiType superType : superTypes) {
            String canonicalText = superType.getCanonicalText();

            if ("java.io.Serializable".equals(canonicalText)) {
                return mSerializer;
            }
        }

        return null;
    }
}
