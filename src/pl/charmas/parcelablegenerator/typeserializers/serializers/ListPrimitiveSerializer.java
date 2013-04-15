package pl.charmas.parcelablegenerator.typeserializers.serializers;

import com.intellij.psi.PsiField;
import pl.charmas.parcelablegenerator.typeserializers.TypeSerializer;

/**
 * User: GoogolMo
 * Date: 13-4-3
 * Time: 下午12:06
 */
public class ListPrimitiveSerializer implements TypeSerializer {

    private final String typeName;

    public ListPrimitiveSerializer(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String writeValue(PsiField field, String parcel, String flags) {
        return parcel + ".writeList(this." + field.getName() + ");";
    }

    @Override
    public String readValue(PsiField field, String parcel) {
        return "this." + field.getName() + " = new ArrayList<" + typeName + ">(); \n in.readList(this." + field.getName()
                + " ," + typeName + ".class.getClassLoader());";
    }
}
