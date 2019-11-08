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

        for (SKU sku : skuMap.values()) {
            boolean selfFree = false;
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
        Offer oa = Offer.builder().forItem("A").offer(3, 130).offer(5, 200).build();
        Offer ob = new Offer(2, "B", 45);
        Offer oe = Offer.builder().forItem("E").offer(2, 80).freeItem("B").build();
        Offer of = Offer.builder().forItem("F").offer(2, 20).freeItem("F").build();
        Offer oh = Offer.builder().forItem("H").offer(5, 45).offer(10,80).build();//5H for 45, 10H for 80
        Offer ok = Offer.builder().forItem("K").offer(2, 150).build();//2K for 150
        Offer ov = Offer.builder().forItem("V").offer(2, 90).offer(3,130).build();       //  2V for 90, 3V for 130
        Offer on = Offer.builder().forItem("N").offer(3, 120).freeItem("M").build();       //  3N get one M free
        Offer op = Offer.builder().forItem("P").offer(5, 200).build();       //  5P for 200
        Offer oq = Offer.builder().forItem("Q").offer(3, 80).build();       // 3Q for 80
        Offer or = Offer.builder().forItem("R").offer(3, 150).freeItem("Q").build();       // 3R get one Q free
        Offer ou = Offer.builder().forItem("U").offer(3, 120).freeItem("U").build();       // 3U get one U free


        return ImmutableMap.<String, Offer>builder()
                .put("A", oa)
                .put("B", ob)
                .put("E", oe)
                .put("F", of)
                .put("H", oh)
                .put("K", ok)
                .put("V", ov)
                .put("N", on)
                .put("P", op)
                .put("Q", oq)
                .put("R", or)
                .put("U", ou)
                .build();
    }
}
