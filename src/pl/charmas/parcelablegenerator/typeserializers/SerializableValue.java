package pl.charmas.parcelablegenerator.typeserializers;

import com.intellij.psi.PsiField;
import com.intellij.psi.PsiType;

public interface SerializableValue {
    PsiType getType();

    String getName();

    class MemberSerializableValue implements SerializableValue {
        private final PsiField field;

        public MemberSerializableValue(PsiField field) {
            this.field = field;
        }

        @Override
        public PsiType getType() {
            return field.getType();
        }

        @Override
        public String getName() {
            return "this." + field.getName();
        }
    }
}
