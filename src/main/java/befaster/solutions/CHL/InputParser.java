package befaster.solutions.CHL;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

public class InputParser {

    static List<SKU> parse(String input) {
        List<SKU> skus = newArrayList();
        Map<String, SKU> skuMap = newHashMap();
        List<String> chunks = separateValidChunsfItemNameAndQantity(input);
        int i = 0;
        while (i <= chunks.size() - 1) {
            if (chunks.get(i).matches("[0-9]")) { //2A
                addToSKUMap(skuMap, chunks.get(i + 1), Integer.parseInt(chunks.get(i)));
                i = i + 2;
            } else {//A
                if (CheckliteSolution.prices.containsKey(chunks.get(i))) {
                    addToSKUMap(skuMap, chunks.get(i), 1);
                    i++;
                } else {//AxA ABCD
                    throw new InvalidInputException("invalid input " + chunks.get(i));
                }
            }
        }
        return newArrayList(skuMap.values());
    }

    private static void addToSKUMap(Map<String, SKU> skus, String item, int qty) {
        if (skus.containsKey(item)) {
            SKU sku = skus.get(item);
            skus.put(item, new SKU(item, qty + sku.getQty()));
        } else {
            skus.put(item, new SKU(item, qty));
        }
    }

    private static List<String> separateValidChunsfItemNameAndQantity(String input) {
        Pattern VALID_PATTERN = Pattern.compile("[0-9]+|[ABCDE]");
        Pattern invalidPattern = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        Pattern invalidChars = Pattern.compile("[a-z]");
        if (invalidPattern.matcher(input).find() || invalidChars.matcher(input).find()) {
            throw new InvalidInputException("symbol in input" + input);
        }
        List<String> chunks = newArrayList();
        Matcher matcher = VALID_PATTERN.matcher(input);
        while (matcher.find()) {
            chunks.add(matcher.group());
        }
        return chunks;
    }
}
