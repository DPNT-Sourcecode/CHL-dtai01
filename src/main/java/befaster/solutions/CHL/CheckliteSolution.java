package befaster.solutions.CHL;

import befaster.runner.SolutionNotImplementedException;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class CheckliteSolution {

    private final static ImmutableMap<String,Integer> prices = ImmutableMap.<String, Integer>builder()
                                                        .put("A",50)
                                                        .put("B",30)
                                                        .put("C",20)
                                                        .put("D",15)
                                                        .build();


    public Integer checklite(String skus) {
        String item = "";
                Integer quantity =0;

        //separate number and item name

        //offer count%total number = original item to price
        //offer count/total number = offer price

        // get value of item from map
        return 0;
    }
}




