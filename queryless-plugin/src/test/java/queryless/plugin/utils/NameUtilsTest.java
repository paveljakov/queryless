package queryless.plugin.utils;

import org.junit.Assert;
import org.junit.Test;

public class NameUtilsTest {

    @Test
    public void toClassNameTest() {
        Assert.assertEquals("SparePartsQueries", NameUtils.toClassName("spare-parts-queries"));
        Assert.assertEquals("$1SparePartsQueries", NameUtils.toClassName("1-spare-parts-queries"));
        Assert.assertEquals("$999sparePartsQueries", NameUtils.toClassName("999spare-parts-queries"));
    }

    @Test
    public void toConstantNameTest() {
        Assert.assertEquals("SPARE_PARTS_MERGE", NameUtils.toConstantName("spare-parts.merge"));
        Assert.assertEquals("$1_SPARE_PARTS_MERGE", NameUtils.toConstantName("1-spare-parts.merge"));
        Assert.assertEquals("$999SPARE_PARTS_MERGE", NameUtils.toConstantName("999spare-parts.merge"));
    }

}