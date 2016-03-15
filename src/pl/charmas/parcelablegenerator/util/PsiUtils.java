package pl.charmas.parcelablegenerator.util;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiType;
import com.intellij.psi.util.PsiTypesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Utils for introspecting Psi* stuff
 *
 * @author Dallas Gutauckis [dallas@gutauckis.com]
 */
final public class PsiUtils {
    private PsiUtils() {
    }

    /**
     * Resolves generics on the given type and returns them (if any) or null if there are none
     *
     * @param type
     * @return
     */
    public static List<PsiType> getResolvedGenerics(PsiType type) {
        List<PsiType> psiTypes = null;

        if (type instanceof PsiClassType) {
            PsiClassType pct = (PsiClassType) type;
            psiTypes = new ArrayList<PsiType>(pct.resolveGenerics().getSubstitutor().getSubstitutionMap().values());
        }

        return psiTypes;
    }

    public static boolean isOfType(PsiType type, String canonicalName) {
        if (type.getCanonicalText().equals(canonicalName)) {
            return true;
        }

        String nonGenericType = getNonGenericType(type);
        if (nonGenericType != null && nonGenericType.equals(canonicalName)) {
            return true;
        }

        for (PsiType iterType : type.getSuperTypes()) {
            if (isOfType(iterType, canonicalName)) {
                return true;
            }
        }

        return false;
    }

    public static String getNonGenericType(PsiType type) {
        if (type instanceof PsiClassType) {
            PsiClassType pct = (PsiClassType) type;
            PsiClass psiClass = pct.resolve();
            return psiClass != null ? psiClass.getQualifiedName() : null;
        }

        return type.getCanonicalText();
    }
}
