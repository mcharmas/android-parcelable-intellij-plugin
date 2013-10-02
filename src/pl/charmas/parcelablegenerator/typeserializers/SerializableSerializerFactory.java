package pl.charmas.parcelablegenerator.typeserializers;

import com.intellij.psi.PsiType;
import pl.charmas.parcelablegenerator.typeserializers.serializers.SerializableObjectSerializer;

import java.util.HashSet;

public class SerializableSerializerFactory implements TypeSerializerFactory {

    private final HashSet<String> handledTypes = new HashSet<String>();

    public SerializableSerializerFactory() {
        handledTypes.add("java.util.Date");
        handledTypes.add("java.math.BigDecimal");
    }

    @Override
    public TypeSerializer getSerializer(PsiType psiType) {
        if (handledTypes.contains(psiType.getCanonicalText())) {
            return new SerializableObjectSerializer(psiType.getCanonicalText());
        }
        return null;
    }
}
