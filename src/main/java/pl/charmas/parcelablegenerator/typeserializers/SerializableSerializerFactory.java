package pl.charmas.parcelablegenerator.typeserializers;

import com.intellij.psi.PsiType;

import pl.charmas.parcelablegenerator.typeserializers.serializers.SerializableObjectSerializer;
import pl.charmas.parcelablegenerator.util.PsiUtils;

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
        if (PsiUtils.isOfType(psiType, "java.io.Serializable")) {
            return mSerializer;
        }

        return null;
    }
}
