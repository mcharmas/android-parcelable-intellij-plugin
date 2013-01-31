package pl.charmas.parcelablegenerator;

import org.junit.Assert;
import org.junit.Test;

public class CodeGeneratorTest {

    @Test
    public void testShouldCreateDescribeContentMethodString() throws Exception {
        CodeGenerator codeGenerator = new CodeGenerator(null, null);
        String describeContentMethod = codeGenerator.generateDescribeContents();
        Assert.assertEquals("public int describeContents() { return 0; }", describeContentMethod);
    }

}
