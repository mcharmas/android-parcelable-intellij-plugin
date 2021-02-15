package pl.charmas.parcelablegenerator.typeserializers;

import com.intellij.psi.PsiType;

import java.util.List;

import pl.charmas.parcelablegenerator.typeserializers.serializers.ParcelableArraySerializer;
import pl.charmas.parcelablegenerator.typeserializers.serializers.ParcelableListSerializer;
import pl.charmas.parcelablegenerator.typeserializers.serializers.ParcelableObjectSerializer;
import pl.charmas.parcelablegenerator.util.PsiUtils;

/**
 * Serializer factory for Parcelable objects
 *
 * @author Dallas Gutauckis [dallas@gutauckis.com]
 * @author Michał Charmas [micha@charmas.pl]
 */
public class ParcelableSerializerFactory implements TypeSerializerFactory {
    private TypeSerializer mSerializer = new ParcelableObjectSerializer();
    private TypeSerializer listSerializer = new ParcelableListSerializer();
    private TypeSerializer arraySerializer = new ParcelableArraySerializer();

    @Override
    public TypeSerializer getSerializer(PsiType psiType) {
        if (PsiUtils.isOfType(psiType, "android.os.Parcelable[]")) {
            return arraySerializer;
        }

        if (PsiUtils.isOfType(psiType, "android.os.Parcelable")) {
            return mSerializer;
        }

        if (PsiUtils.isOfType(psiType, "java.util.List")) {
            List<PsiType> resolvedGenerics = PsiUtils.getResolvedGenerics(psiType);
            for (PsiType resolvedGeneric : resolvedGenerics) {
                if (PsiUtils.isOfType(resolvedGeneric, "android.os.Parcelable")) {
                    return listSerializer;
                }
            }
        }

        return null;
    }

}
