package queryless.plugin.utils;

import org.junit.Assert;
import org.junit.Test;

public class TextUtilsTest {

    @Test
    public void toClassNameTest() {
        Assert.assertEquals("SparePartsQueries", TextUtils.toClassName("spare-parts-queries"));
        Assert.assertEquals("$1SparePartsQueries", TextUtils.toClassName("1-spare-parts-queries"));
        Assert.assertEquals("$999sparePartsQueries", TextUtils.toClassName("999spare-parts-queries"));
        Assert.assertEquals("Class", TextUtils.toClassName("class"));
        Assert.assertEquals("Public", TextUtils.toClassName("public"));
        Assert.assertEquals("PrivateStaticFinalMethodname", TextUtils.toClassName("private static final methodName()"));
        Assert.assertEquals("OneTwoName", TextUtils.toClassName("ONE_TWO_NAME"));
        Assert.assertEquals("OneTwoName", TextUtils.toClassName("ONE.TWO.NAME"));
    }

    @Test
    public void toConstantNameTest() {
        Assert.assertEquals("SPARE_PARTS_MERGE", TextUtils.toConstantName("spare-parts.merge"));
        Assert.assertEquals("$1_SPARE_PARTS_MERGE", TextUtils.toConstantName("1-spare-parts.merge"));
        Assert.assertEquals("$999SPARE_PARTS_MERGE", TextUtils.toConstantName("999spare-parts.merge"));
        Assert.assertEquals("CLASS", TextUtils.toConstantName("class"));
        Assert.assertEquals("PUBLIC", TextUtils.toConstantName("public"));
        Assert.assertEquals("PRIVATE_STATIC_FINAL_METHODNAME__", TextUtils.toConstantName("private static final methodName()"));
        Assert.assertEquals("ONE_TWO_NAME", TextUtils.toConstantName("ONE_TWO_NAME"));
        Assert.assertEquals("ONE_TWO_NAME", TextUtils.toConstantName("ONE.TWO.NAME"));
    }

    @Test
    public void removeSpacesIndentationTest() {
        final String indentedQuery = "\n"
                + "        INSERT INTO\n"
                + "            EMPLOYEE(\n"
                + "                ID,\n"
                + "                NAME,\n"
                + "                AGE,\n"
                + "                DEPARTMENT\n"
                + "            )\n"
                + "        VALUES(\n"
                + "            EMPLOYEE_SEQ.NEXTVAL,\n"
                + "            ?,\n"
                + "            ?,\n"
                + "            ?\n"
                + "        )\n"
                + "    ";

        final String unindentedQuery =
                "INSERT INTO\n"
                + "    EMPLOYEE(\n"
                + "        ID,\n"
                + "        NAME,\n"
                + "        AGE,\n"
                + "        DEPARTMENT\n"
                + "    )\n"
                + "VALUES(\n"
                + "    EMPLOYEE_SEQ.NEXTVAL,\n"
                + "    ?,\n"
                + "    ?,\n"
                + "    ?\n"
                + ")";

        Assert.assertEquals(unindentedQuery, TextUtils.removeIndentation(indentedQuery));
    }

    @Test
    public void removeTabsIndentationTest() {
        final String indentedQuery = "\n"
                + "\t\tINSERT INTO\n"
                + "\t\t\tEMPLOYEE(\n"
                + "\t\t\t\tID,\n"
                + "\t\t\t\tNAME,\n"
                + "\t\t\t\tAGE,\n"
                + "\t\t\t\tDEPARTMENT\n"
                + "\t\t\t)\n"
                + "\t\tVALUES(\n"
                + "\t\t\tEMPLOYEE_SEQ.NEXTVAL,\n"
                + "\t\t\t?,\n"
                + "\t\t\t?,\n"
                + "\t\t\t?\n"
                + "\t\t)\n"
                + "\t";

        final String unindentedQuery =
                "INSERT INTO\n"
                + "\tEMPLOYEE(\n"
                + "\t\tID,\n"
                + "\t\tNAME,\n"
                + "\t\tAGE,\n"
                + "\t\tDEPARTMENT\n"
                + "\t)\n"
                + "VALUES(\n"
                + "\tEMPLOYEE_SEQ.NEXTVAL,\n"
                + "\t?,\n"
                + "\t?,\n"
                + "\t?\n"
                + ")";

        Assert.assertEquals(unindentedQuery, TextUtils.removeIndentation(indentedQuery));
    }

}