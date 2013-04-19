package pl.charmas.parcelablegenerator.typeserializers;

import com.intellij.psi.PsiType;
import pl.charmas.parcelablegenerator.typeserializers.serializers.ListPrimitiveSerializer;

public class ListSerializerFactory implements TypeSerializerFactory {
    @Override
    public TypeSerializer getSerializer(PsiType psiType) {
        String typeName = psiType.getCanonicalText();
        if (typeName.startsWith("java.util.List")) {
            String genericTypeName = typeName.replaceAll("java.util.List<", "");
            genericTypeName = genericTypeName.replaceAll(">", "");
            return new ListPrimitiveSerializer(genericTypeName);
        }
        return null;
    }
}
