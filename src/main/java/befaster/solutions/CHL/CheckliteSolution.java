package befaster.solutions.CHL;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

public class CheckliteSolution {

    public final static ImmutableMap<String, Integer> prices = ImmutableMap.<String, Integer>builder()
            .put("A", 50)
            .put("B", 30)
            .put("C", 20)
            .put("D", 15)
            .put("E", 40)
            .build();

    public Integer checklite(String input) {
        ImmutableMap<String, Offer> offerMap = initOfferMap();

        System.out.println(input);
        try {
            List<SKU> skuList = InputParser.parse(input);
            Map<String, SKU> skuMap = skuList.stream().collect(Collectors.toMap(SKU::getIetm, sku -> sku));
            Set duplicateSkuListForFreeItems = newHashSet(skuList);
            return skuList.stream().mapToInt(sku -> calculatePriceForOneSKU(offerMap, sku, skuMap)).sum();
        } catch (InvalidInputException e) {
            return -1;
        }
    }

    private int calculatePriceForOneSKU(ImmutableMap<String, Offer> offerMap, SKU sku, Map<String, SKU> duplicateSkuMapForFreeItems) {
        int price = 0;

        if (offerMap.containsKey(sku.getIetm())) {
            Offer offerForItem = offerMap.get(sku.getIetm());
            int remainingQty = sku.getQty();//4
            for (Map.Entry<Integer, Integer> offer : offerForItem.getQuantityVsPrice().entrySet()) {
                if (offer.getKey() <= remainingQty) {
                    int noOfTimeOfferCaqnBeApplied = remainingQty / offer.getKey();
                    price += offer.getValue() * noOfTimeOfferCaqnBeApplied;
                    remainingQty -= offer.getKey() * noOfTimeOfferCaqnBeApplied;
                    if (offerForItem.getFreeItem() != null && duplicateSkuMapForFreeItems.containsKey(offerForItem.getFreeItem())) {
                        SKU freeItem = duplicateSkuMapForFreeItems.get(offerForItem.getFreeItem());
                        if (noOfTimeOfferCaqnBeApplied > freeItem.getQty()){
                            price-= freeItem.getQty()*prices.get(freeItem.getIetm());
                        }else {
                            price-=noOfTimeOfferCaqnBeApplied*prices.get(freeItem.getIetm());
                        }
                    }
                }
            }
            price += prices.get(sku.getIetm()) * (remainingQty);
        }
        if (price == 0 && prices.containsKey(sku.getIetm())) {
            price = sku.getQty() * prices.get(sku.getIetm());
        }

        if (price == 0 && !prices.containsKey(sku.getIetm())) {
            return -1;
        }

        return price;
    }

    private ImmutableMap<String, Offer> initOfferMap() {
        Offer o1 = Offer.builder().forItem("A").offer(3, 130).offer(5, 200).build();
        Offer o2 = new Offer(2, "B", 45);
        Offer o3 = Offer.builder().forItem("E").offer(2, 80).freeItem("B").build();

        return ImmutableMap.<String, Offer>builder()
                .put("A", o1)
                .put("B", o2)
                .put("E", o3)
                .build();
    }
}
