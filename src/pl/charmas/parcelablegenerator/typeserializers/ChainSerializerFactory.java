package pl.charmas.parcelablegenerator.typeserializers;

import com.intellij.psi.PsiType;
import pl.charmas.parcelablegenerator.typeserializers.serializers.UnknownTypeSerializer;

import java.util.Arrays;
import java.util.List;

public class ChainSerializerFactory implements TypeSerializerFactory {

    private final List<TypeSerializerFactory> factories;

    public ChainSerializerFactory(TypeSerializerFactory... factories) {
        this.factories = Arrays.asList(factories);
    }

    @Override
    public TypeSerializer getSerializer(PsiType psiType) {
        for (TypeSerializerFactory factory : factories) {
            TypeSerializer serializer = factory.getSerializer(psiType);
            if (serializer != null) {
                return serializer;
            }
        }
        return new UnknownTypeSerializer(psiType.getCanonicalText());
    }
}
