package befaster.solutions.CHL;

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
        Pattern VALID_PATTERN = Pattern.compile("[0-9]+|[A-Z]+");
        List<String> chunks = newArrayList();
        Matcher matcher = VALID_PATTERN.matcher(input);
        while (matcher.find()) {
            chunks.add( matcher.group() );
        }
        for (int i = 0;i<=chunks.size()-1;i++) {
            skus.add(new SKU(chunks.get(i+1), Integer.parseInt(chunks.get(i))));
            i = i+1;
        }
        return skus;
    }

    public Integer checklite(String skus) {
        ImmutableMap<String, Offer> offerMap = initOfferMap();

        List<SKU> skuList = parse(skus);
        return skuList.stream().mapToInt(sku -> calculatePriceForOneSKU(offerMap, sku)).sum();
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
        if (price==0 && prices.containsKey(sku.getIetm()) ) {
            price = sku.getQty() * prices.get(sku.getIetm());
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


