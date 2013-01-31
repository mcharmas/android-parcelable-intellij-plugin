package pl.charmas.parcelablegenerator;

import com.intellij.psi.PsiField;
import com.intellij.psi.impl.java.stubs.PsiFieldStub;
import com.intellij.psi.impl.java.stubs.impl.PsiFieldStubImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class CodeGeneratorTest {

    @Test
    public void testShouldCreateDescribeContentMethodString() throws Exception {
        CodeGenerator codeGenerator = new CodeGenerator(null, null);
        String describeContentMethod = codeGenerator.generateDescribeContents();
        Assert.assertEquals("public int describeContents() { return 0; }",describeContentMethod);
    }

}
