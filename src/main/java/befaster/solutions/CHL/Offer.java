package befaster.solutions.CHL;

import com.google.common.collect.Iterables;

import java.util.Map;
import java.util.TreeMap;

import static com.google.common.collect.Maps.newHashMap;

public class Offer {
    private String item;
    Map<Integer, Integer> quantityVsPrice = new TreeMap<>();

    private Offer() {
    }

    public Map<Integer, Integer> getQuantityVsPrice() {
        return quantityVsPrice;
    }


    Offer(int qty, String item, Integer price) {
        this.item = item;
        quantityVsPrice.put(qty, price);
    }

    public int getSingleOfferQty() {
        return Iterables.getOnlyElement(quantityVsPrice.keySet());
    }

    public String getItem() {
        return item;
    }

    public Integer getSingleOfferPrice() {
        return Iterables.getOnlyElement(quantityVsPrice.values());
    }

    static Builder builder() {
        return new Builder();
    }

    static class Builder {

        Offer offer;

        public Builder() {
            this.offer = new Offer();
        }

        public Builder forItem(String item) {
            offer.item = item;
            return this;
        }

        public Builder offer(int qty, int prize) {
            offer.quantityVsPrice.put(qty, prize);
            return this;
        }

        public Offer build() {
            return offer;
        }
    }
}

