package befaster.solutions.CHL;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

public class CheckliteSolution {

    public final static ImmutableMap<String, Integer> prices = ImmutableMap.<String, Integer>builder()
            .put("A", 50)
            .put("B", 30)
            .put("C", 20)
            .put("D", 15)
            .put("E", 40)
            .put("F", 10)
            .build();

    public Integer checklite(String input) {
        ImmutableMap<String, Offer> offerMap = initOfferMap();

        System.out.println(input);
        try {
            List<SKU> skuList = InputParser.parse(input);
            Map<String, SKU> skuMap = skuList.stream().collect(Collectors.toMap(SKU::getIetm, sku -> sku));
            List<SKU> finalItemsToCalculate = filterFreeItems(skuMap, offerMap);
            return finalItemsToCalculate.stream().mapToInt(sku -> calculatePriceForOneSKU(offerMap, sku)).sum();
        } catch (InvalidInputException e) {
            return -1;
        }
    }

    private List<SKU> filterFreeItems(Map<String, SKU> skuMap, ImmutableMap<String, Offer> offerMap) {
        Map<String, SKU> finalItemVsQty = newHashMap();
        boolean selfFree = false;

        for (SKU sku : skuMap.values()) {
            if (offerMap.containsKey(sku.getIetm())) {
                Offer offer = offerMap.get(sku.getIetm());
                if (offer.getFreeItem() != null && skuMap.containsKey(offer.getFreeItem())) {
                    SKU freeItem = skuMap.get(offer.getFreeItem());//F
                    selfFree = freeItem.getIetm().equals(sku.getIetm());
                    Integer freeItems = calculateFreeItems(sku, offer);
                    int finalQuantityToPay;

                    if (selfFree) {
                        int remainingQty = sku.getQty();
                        freeItems = 0;
                        while (remainingQty > offer.getSingleOfferQty()) { //4
                            remainingQty -= offer.getSingleOfferQty();//2
                            remainingQty -= 1;//1
                            freeItems++;//1
                        }
                        finalQuantityToPay = freeItem.getQty() - freeItems;
                    } else {
                        finalQuantityToPay = freeItem.getQty() - freeItems;
                    }
                    int qty = finalQuantityToPay <= 0 ? 0 : finalQuantityToPay;
                    finalItemVsQty.put(freeItem.getIetm(), new SKU(freeItem.getIetm(), qty));
                }
            }
            if (!selfFree) {
                finalItemVsQty.put(sku.getIetm(), sku);
            }
        }
        return newArrayList(finalItemVsQty.values());
    }

    private Integer calculateFreeItems(SKU sku, Offer offer) {
        int remainingQty = sku.getQty();
        if (offer.getSingleOfferQty() <= remainingQty) {
            return remainingQty / offer.getSingleOfferQty();
        } else return 0;
    }

    private int calculatePriceForOneSKU(ImmutableMap<String, Offer> offerMap, SKU sku) {
        int price = 0;

        if (offerMap.containsKey(sku.getIetm())) {
            Offer offerForItem = offerMap.get(sku.getIetm());
            int remainingQty = sku.getQty();//4
            for (Map.Entry<Integer, Integer> offer : offerForItem.getQuantityVsPrice().entrySet()) {
                if (offer.getKey() <= remainingQty) {
                    int noOfTimeOfferCaqnBeApplied = remainingQty / offer.getKey();
                    price += offer.getValue() * noOfTimeOfferCaqnBeApplied;
                    remainingQty -= offer.getKey() * noOfTimeOfferCaqnBeApplied;
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
        Offer o4 = Offer.builder().forItem("F").offer(2, 20).freeItem("F").build();

        return ImmutableMap.<String, Offer>builder()
                .put("A", o1)
                .put("B", o2)
                .put("E", o3)
                .put("F", o4)
                .build();
    }
}

