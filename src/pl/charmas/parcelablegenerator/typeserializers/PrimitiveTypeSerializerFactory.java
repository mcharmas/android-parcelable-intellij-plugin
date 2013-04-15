package pl.charmas.parcelablegenerator.typeserializers;

import com.intellij.psi.PsiType;
import pl.charmas.parcelablegenerator.typeserializers.serializers.BooleanPrimitiveSerializer;
import pl.charmas.parcelablegenerator.typeserializers.serializers.NullablePrimitivesSerializer;
import pl.charmas.parcelablegenerator.typeserializers.serializers.PrimitiveTypeSerializer;

import java.util.HashMap;
import java.util.Map;

public class PrimitiveTypeSerializerFactory implements TypeSerializerFactory {

    private final Map<String, TypeSerializer> writeMethodsForTypes = new HashMap<String, TypeSerializer>();

    public PrimitiveTypeSerializerFactory() {
        initPrimitives();
        initNullablePrimitives();
    }

    private void initNullablePrimitives() {
        writeMethodsForTypes.put("byte", new PrimitiveTypeSerializer("Byte"));
        writeMethodsForTypes.put("double", new PrimitiveTypeSerializer("Double"));
        writeMethodsForTypes.put("float", new PrimitiveTypeSerializer("Float"));
        writeMethodsForTypes.put("int", new PrimitiveTypeSerializer("Int"));
        writeMethodsForTypes.put("long", new PrimitiveTypeSerializer("Long"));
        writeMethodsForTypes.put("java.lang.String", new PrimitiveTypeSerializer("String"));
        writeMethodsForTypes.put("boolean", new BooleanPrimitiveSerializer());
    }

    private void initPrimitives() {
        writeMethodsForTypes.put("java.lang.Byte", new NullablePrimitivesSerializer("java.lang.Byte"));
        writeMethodsForTypes.put("java.lang.Double", new NullablePrimitivesSerializer("java.lang.Double"));
        writeMethodsForTypes.put("java.lang.Float", new NullablePrimitivesSerializer("java.lang.Float"));
        writeMethodsForTypes.put("java.lang.Integer", new NullablePrimitivesSerializer("java.lang.Integer"));
        writeMethodsForTypes.put("java.lang.Long", new NullablePrimitivesSerializer("java.lang.Long"));
        writeMethodsForTypes.put("java.lang.Boolean", new NullablePrimitivesSerializer("java.lang.Boolean"));
    }

    @Override
    public TypeSerializer getSerializer(PsiType psiType) {
        return writeMethodsForTypes.get(psiType.getCanonicalText());
    }
}
