package befaster.solutions.CHL;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CheckliteSolutionTest {

    CheckliteSolution solution = new CheckliteSolution();


    @Test
    public void checkliteEmptyInput() {

        Integer price = solution.checklite("");
        Assert.assertEquals(new Integer(0), price);
    }

    @Test
    public void checkliteSimpleSingleItem() {

        Integer price = solution.checklite("2A");
        Assert.assertEquals(new Integer(100), price);
    }

    @Test
    public void checkliteOfferAndNormalPriceSingleItem() {

        Integer price = solution.checklite("5A");
        Assert.assertEquals(new Integer(200), price);
    }

    @Test
    public void checkliteNewItemOffer() {

        Integer price = solution.checklite("2E");
        Assert.assertEquals(new Integer(80), price);
    }

    @Test
    public void checkliteNewItemOfferWithFreeItem() {

        Integer price = solution.checklite("EEB");
        Assert.assertEquals(new Integer(80), price);
    }

    @Test
    public void checkliteNewItemOfferWithFreeItem2() {

        Integer price = solution.checklite("EEEB");
        Assert.assertEquals(new Integer(120), price);
    }

    @Test
    public void checkliteNewItemOfferWithFreeItem3() {

        Integer price = solution.checklite("EEEEBB");
        Assert.assertEquals(new Integer(160), price);
    }

    @Test
    public void checkliteNewItemOfferWithFreeItem4() {

        Integer price = solution.checklite("EEEEBB");
        Assert.assertEquals(new Integer(160), price);
    }

    @Test
    public void checkliteNewItemOfferWithFreeItem5() {

        Integer price = solution.checklite("BEBEEE");
        Assert.assertEquals(new Integer(160), price);
    }

    @Test
    public void checkliteNewItemOfferWithFreeItem6() {

        Integer price = solution.checklite("ABCDEABCDE");
        Assert.assertEquals(new Integer(280), price);
    }

    @Test
    public void checkliteForOfferPrice() {

        Integer price = solution.checklite("3A");
        Assert.assertEquals(new Integer(130), price);
    }

    @Test
    public void checkliteMultipleProductForNormalPrice() {

        Integer price = solution.checklite("2A1B");
        Assert.assertEquals(new Integer(130), price);
    }

    @Test
    public void checkliteMultipleProductForNormalAndOfferPrice() {

        Integer price = solution.checklite("5A1B");
        Assert.assertEquals(new Integer(230), price);
    }

    @Test
    public void checkliteInvalidSKU() {

        Integer price = solution.checklite("dfsd1A");
        Assert.assertEquals(new Integer(-1), price);
    }

    @Test
    public void checkliteNonPresentItem() {
        Integer price = solution.checklite("78G");
        Assert.assertEquals(new Integer(-1), price);
    }

    @Test
    public void checkliteForInputWithoutQuantity() {
        Integer price = solution.checklite("A");
        Assert.assertEquals(new Integer(50), price);
    }

    @Test
    public void checkliteForOnlyCharInputMultipleSKU() {
        Integer price = solution.checklite("AA");
        Assert.assertEquals(new Integer(100), price);
    }

    @Test
    public void checkliteForOnlyCharInputMultipleSKU2() {
        Integer price = solution.checklite("AAA");
        Assert.assertEquals(new Integer(130), price);
    }

    @Test
    public void checkliteForOnlyCharInputMultipleSKU3() {
        Integer price = solution.checklite("AAAA");
        Assert.assertEquals(new Integer(180), price);
    }

    @Test
    public void checkliteForMultipleSingleSKU() {
        Integer price = solution.checklite("ABCD");
        Assert.assertEquals(new Integer(115), price);
    }

    @Test
    public void checkliteForIlleagalInput2() {
        Integer price = solution.checklite("AxA");
        Assert.assertEquals(new Integer(-1), price);
    }

    @Test
    public void checkliteForIlleagalInput3() {
        Integer price = solution.checklite("a");
        Assert.assertEquals(new Integer(-1), price);
    }

    @Test
    public void checkliteForIlleagalInput4() {
        Integer price = solution.checklite("-");
        Assert.assertEquals(new Integer(-1), price);
    }

    //ch_3
    @Test
    public void checkliteForOfferSelfFree() {
        Integer price = solution.checklite("FF");
        Assert.assertEquals(new Integer(20), price);
    }

    @Test
    public void checkliteForOfferSelfFree2() {
        Integer price = solution.checklite("FFF");
        Assert.assertEquals(new Integer(20), price);
    }

    @Test
    public void checkliteForOfferSelfFree3() {
        Integer price = solution.checklite("FFFF");
        Assert.assertEquals(new Integer(30), price);
    }

    @Test
    public void checkliteForOfferSelfFree4() {
        Integer price = solution.checklite("FFFFF");
        Assert.assertEquals(new Integer(40), price);
    }
    @Test
    public void checkliteForOfferSelfFree5() {
        Integer price = solution.checklite("FFFFFF");
        Assert.assertEquals(new Integer(40), price);
    }
    //ch_4
    @Test
    public void checkliteForAllAtoZ() {
        Integer price = solution.checklite("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        Assert.assertEquals(new Integer(965), price);
    }

}