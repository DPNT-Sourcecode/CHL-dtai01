package befaster.solutions.CHL;

import befaster.runner.SolutionNotImplementedException;
import com.google.common.collect.ImmutableMap;

import javax.print.DocFlavor;
import java.util.List;
import java.util.Map;

public class CheckliteSolution {

    private final static ImmutableMap<String, Integer> prices = ImmutableMap.<String, Integer>builder()
            .put("A", 50)
            .put("B", 30)
            .put("C", 20)
            .put("D", 15)
            .build();

//    private List<String> parse (String input){
//
//    }

    class Offer {
        private int qty;
        private String item;
        private Integer price;

        public Offer(int qty, String item, Integer price) {
            this.qty = qty;
            this.item = item;
            this.price = price;
        }

        public int getQty() {
            return qty;
        }

        public String getItem() {
            return item;
        }

        public Integer getPrice() {
            return price;
        }
    }


    public Integer checklite(String skus) {
        Offer o1 = new Offer(3, "A", 130);
        Offer o2 = new Offer(2, "B", 45);

        ImmutableMap<String, Offer> offerMap = ImmutableMap.<String, Offer>builder()
                .put("A", o1)
                .put("B", o2)
                .build();

        String item = skus.replaceAll("^[0-9]", "").trim();
        Integer quantity = Integer.parseInt(skus.replaceAll("[A-Z]", "").trim());

        int price = 0;

        if (offerMap.containsKey(item)) {
            Offer offerForItem = offerMap.get(item);
            if (offerForItem.getQty() >= quantity) {
                price += offerForItem.getPrice() * (quantity / offerForItem.getQty());
                price += offerForItem.getPrice() * (quantity % offerForItem.getQty());
            }
        } else if (prices.containsKey(item)) {
            price = quantity * prices.get(item);
        }
        return price;

    }
}

