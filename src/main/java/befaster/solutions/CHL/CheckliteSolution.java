package befaster.solutions.CHL;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.collect.Lists.newArrayList;

public class CheckliteSolution {


    private final static ImmutableMap<String, Integer> prices = ImmutableMap.<String, Integer>builder()
            .put("A", 50)
            .put("B", 30)
            .put("C", 20)
            .put("D", 15)
            .build();

    private List<SKU> parse(String input) {
        List<SKU> skus = newArrayList();
        List<String> chunks = separateValidChunsfItemNameAndQantity(input);
        int i = 0;
        while (i <= chunks.size() - 1) {
            if (chunks.get(i).matches("[0-9]")) { //2A
                skus.add(new SKU(chunks.get(i + 1), Integer.parseInt(chunks.get(i))));
                i = i + 1;
            } else {//A
                if(prices.containsKey(chunks.get(i))){
                    skus.add(new SKU(chunks.get(i), 1));
                }else {
                    throw new InvalidInputException("invalid input "+chunks.get(i));
                }
            }
        }
        return skus;
    }

    private List<String> separateValidChunsfItemNameAndQantity(String input) {
        Pattern VALID_PATTERN = Pattern.compile("[0-9]+|[A-Z]+");
        List<String> chunks = newArrayList();
        Matcher matcher = VALID_PATTERN.matcher(input);
        while (matcher.find()) {
            chunks.add(matcher.group());
        }

//        long quantityCount = chunks.stream().filter(c -> c.matches("[0-9]")).count();
//
//        if (chunks.size() != quantityCount * 2) {
//            throw new InvalidInputException("Invalid Input - " + chunks);
//        }
        return chunks;
    }

    public Integer checklite(String input) {
        ImmutableMap<String, Offer> offerMap = initOfferMap();

        if (Strings.isNullOrEmpty(input)) {
            return 0;
        }
        System.out.println(input);
        try {
            List<SKU> skuList = parse(input);
            return skuList.stream().mapToInt(sku -> calculatePriceForOneSKU(offerMap, sku)).sum();
        } catch (InvalidInputException e) {
            return -1;
        }
    }

    private int calculatePriceForOneSKU(ImmutableMap<String, Offer> offerMap, SKU sku) {
        int price = 0;

        if (offerMap.containsKey(sku.getIetm())) {
            Offer offerForItem = offerMap.get(sku.getIetm());
            if (offerForItem.getQty() <= sku.getQty()) {
                price += offerForItem.getPrice() * (sku.getQty() / offerForItem.getQty());
                price += prices.get(sku.getIetm()) * (sku.getQty() % offerForItem.getQty());
            }
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
        Offer o1 = new Offer(3, "A", 130);
        Offer o2 = new Offer(2, "B", 45);

        return ImmutableMap.<String, Offer>builder()
                .put("A", o1)
                .put("B", o2)
                .build();
    }
}




