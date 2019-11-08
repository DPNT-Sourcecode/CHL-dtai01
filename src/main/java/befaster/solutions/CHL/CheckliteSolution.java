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
            .put("G", 20)
            .put("H", 10)
            .put("I", 35)
            .put("J", 60)
            .put("K", 80)
            .put("L", 90)
            .put("M", 15)
            .put("N", 40)
            .put("O", 10)
            .put("P", 50)
            .put("Q", 30)
            .put("R", 50)
            .put("S", 30)
            .put("T", 20)
            .put("U", 40)
            .put("V", 50)
            .put("W", 20)
            .put("X", 90)
            .put("Y", 10)
            .put("Z", 50)
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
                if (someItemIsFree(skuMap, offer)) {
                    selfFree = keepOnlyPayableItems(skuMap, finalItemVsQty, sku, offer);
                }
            }
            if (!selfFree) {
                finalItemVsQty.put(sku.getIetm(), sku);
            }
        }
        return newArrayList(finalItemVsQty.values());
    }

    private boolean someItemIsFree(Map<String, SKU> skuMap, Offer offer) {
        return offer.getFreeItem() != null && skuMap.containsKey(offer.getFreeItem());
    }

    private boolean keepOnlyPayableItems(Map<String, SKU> skuMap, Map<String, SKU> finalItemVsQty, SKU sku, Offer offer) {
        boolean selfFree;
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
        return selfFree;
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
            price = applyLessPriceOffer(offerMap, sku, price);
        }
        if (price == 0 && prices.containsKey(sku.getIetm())) {
            price = sku.getQty() * prices.get(sku.getIetm());
        }

        if (price == 0 && !prices.containsKey(sku.getIetm())) {
            return -1;
        }

        return price;
    }

    private int applyLessPriceOffer(ImmutableMap<String, Offer> offerMap, SKU sku, int price) {
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
        return price;
    }

    private ImmutableMap<String, Offer> initOfferMap() {
        Offer o1 = Offer.builder().forItem("A").offer(3, 130).offer(5, 200).build();
        Offer o2 = new Offer(2, "B", 45);
        Offer o3 = Offer.builder().forItem("E").offer(2, 80).freeItem("B").build();
        Offer o4 = Offer.builder().forItem("F").offer(2, 20).freeItem("F").build();
        Offer o5 = Offer.builder().forItem("F").offer(2, 20).freeItem("F").build();//5H for 45, 10H for 80

        return ImmutableMap.<String, Offer>builder()
                .put("A", o1)
                .put("B", o2)
                .put("E", o3)
                .put("F", o4)
                .build();
    }
}




