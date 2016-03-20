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

    class StatementSerializableValue implements SerializableValue {
        private final String statement;
        private final PsiType type;

        public StatementSerializableValue(String statement, PsiType type) {
            this.statement = statement;
            this.type = type;
        }

        @Override
        public PsiType getType() {
            return type;
        }

        @Override
        public String getName() {
            return statement;
        }
    }

    class VariableSerializableValue implements SerializableValue {
        private final String variableName;
        private final PsiType type;

        public VariableSerializableValue(String variableName, PsiType type) {
            this.variableName = variableName;
            this.type = type;
        }

        @Override
        public PsiType getType() {
            return type;
        }

        @Override
        public String getName() {
            return type.getCanonicalText() + " " + variableName;
        }
    }
}
