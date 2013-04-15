package pl.charmas.parcelablegenerator.typeserializers;

import com.intellij.psi.PsiType;

public interface TypeSerializerFactory {
    public TypeSerializer getSerializer(PsiType psiType);
}
