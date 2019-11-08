package befaster.solutions.CHL;

import com.google.common.collect.Iterables;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class Offer {
    private String item;
    Map<Integer, Integer> quantityVsPrice = newHashMap();

    Offer() {
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
        Iterables.getOnlyElement(quantityVsPrice.values());
    }

    Builder builder() {
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
