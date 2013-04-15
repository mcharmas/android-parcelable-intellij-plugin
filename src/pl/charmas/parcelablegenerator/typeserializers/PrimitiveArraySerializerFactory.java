package pl.charmas.parcelablegenerator.typeserializers;

import com.intellij.psi.PsiType;
import pl.charmas.parcelablegenerator.typeserializers.serializers.BooleanSparseArraySerializer;
import pl.charmas.parcelablegenerator.typeserializers.serializers.PrimitiveArraySerializer;

import java.util.HashMap;

public class PrimitiveArraySerializerFactory implements TypeSerializerFactory {
    private final HashMap<String, TypeSerializer> handledTypes;

    public PrimitiveArraySerializerFactory() {
        handledTypes = new HashMap<String, TypeSerializer>();
        handledTypes.put("boolean[]", new PrimitiveArraySerializer("Boolean"));
        handledTypes.put("byte[]", new PrimitiveArraySerializer("Byte"));
        handledTypes.put("char[]", new PrimitiveArraySerializer("Char"));
        handledTypes.put("double[]", new PrimitiveArraySerializer("Double"));
        handledTypes.put("float[]", new PrimitiveArraySerializer("Float"));
        handledTypes.put("int[]", new PrimitiveArraySerializer("Int"));
        handledTypes.put("long[]", new PrimitiveArraySerializer("Long"));
        handledTypes.put("java.lang.String[]", new PrimitiveArraySerializer("String"));
        handledTypes.put("android.util.SparseBooleanArray", new BooleanSparseArraySerializer());
    }

    @Override
    public TypeSerializer getSerializer(PsiType psiType) {
        return handledTypes.get(psiType.getCanonicalText());
    }
}
