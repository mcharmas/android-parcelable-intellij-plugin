package pl.charmas.parcelablegenerator.typeserializers;

import com.intellij.psi.PsiField;
import com.intellij.psi.PsiType;

public abstract class SerializableValue {
    public abstract PsiType getType();

    public abstract String getName();

    public static SerializableValue member(PsiField field) {
        return new MemberSerializableValue(field);
    }

    public static SerializableValue statement(String statement, PsiType type) {
        return new StatementSerializableValue(statement, type);
    }

    public static SerializableValue variable(String name, PsiType type) {
        return new VariableSerializableValue(name, type);
    }

    public abstract String getSimpleName();

    private static class MemberSerializableValue extends SerializableValue {
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

        @Override
        public String getSimpleName() {
            return field.getName();
        }
    }

    private static class StatementSerializableValue extends SerializableValue {
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

        @Override
        public String getSimpleName() {
            return statement;
        }
    }

    private static class VariableSerializableValue extends SerializableValue {
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

        @Override
        public String getSimpleName() {
            return variableName;
        }
    }
}
