package befaster.solutions.CHL;

import befaster.runner.SolutionNotImplementedException;
import com.google.common.collect.ImmutableMap;

import javax.print.DocFlavor;
import java.util.List;
import java.util.Map;

public class CheckliteSolution {

    private final static ImmutableMap<String,Integer> prices = ImmutableMap.<String, Integer>builder()
                                                        .put("A",50)
                                                        .put("B",30)
                                                        .put("C",20)
                                                        .put("D",15)
                                                        .build();


//    private List<String> parse (String input){
//
//    }


    public Integer checklite(String skus) {
        String item = skus.replaceAll("^[0-9]","").trim();
        String trim = skus.replaceAll("^[a-z]", "").trim();
        System.out.printf(item+" "+trim);
        Integer quantity =Integer.parseInt(trim);

        int price = 0;

        if(prices.containsKey(item)){
            price =  quantity*prices.get(item);
        }
        return price;


        //separate number and item name

        //offer count%total number = original item to price
        //offer count/total number = offer price

        // get value of item from map
    }
}

