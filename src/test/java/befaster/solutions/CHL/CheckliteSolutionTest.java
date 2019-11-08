package befaster.solutions.CHL;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CheckliteSolutionTest {

    CheckliteSolution solution = new CheckliteSolution();

    @Test
    public void checklite() {

        Integer price = solution.checklite("2A");
        Assert.assertEquals(new Integer(100),price);
    }

    @Test
    public void checklite() {

        Integer price = solution.checklite("3A");
        Assert.assertEquals(new Integer(100),price);
    }
}