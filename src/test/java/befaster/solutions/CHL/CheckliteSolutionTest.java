package befaster.solutions.CHL;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CheckliteSolutionTest {

    CheckliteSolution solution = new CheckliteSolution();

    @Test
    public void checkliteSimpleSingleItem() {

        Integer price = solution.checklite("2A");
        Assert.assertEquals(new Integer(100),price);
    }

    @Test
    public void checkliteOfferAndNormalPriceSingleItem() {

        Integer price = solution.checklite("5A");
        Assert.assertEquals(new Integer(230),price);
    }

    @Test
    public void checkliteForOfferPrice() {

        Integer price = solution.checklite("3A");
        Assert.assertEquals(new Integer(130),price);
    }

    @Test
    public void checkliteMultipleProductForNormalPrice() {

        Integer price = solution.checklite("2A1B");
        Assert.assertEquals(new Integer(130),price);
    }

    @Test
    public void checkliteMultipleProductForNormalAndOfferPrice() {

        Integer price = solution.checklite("5A1B");
        Assert.assertEquals(new Integer(260),price);
    }
    @Test
    public void checkliteInvalidSKU() {

        Integer price = solution.checklite("dfsd1A");
        Assert.assertEquals(new Integer(50),price);
    }

    @Test
    public void checkliteNonPresentItem() {

        Integer price = solution.checklite("78G");
        Assert.assertEquals(new Integer(-1),price);
    }




}
