import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

class Aggregator {
    private Integer charOffset = 0;
    private String[] setOfStrings;
    private Map<String, ArrayList<Integer[]>> aggregatedMap;
    private Map<String, ArrayList<Integer[]>> batchMap;

    Aggregator(String[] setOfStrings) {
        this.setOfStrings = setOfStrings;
        aggregatedMap = new HashMap<>();
        batchMap = new HashMap<>();
    }

    void processBatch(String batch, Integer lineOffset) {
        final Integer localCharOffset = charOffset;
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            Matcher matcher = new Matcher();
            batchMap = matcher.find(batch, setOfStrings, lineOffset, localCharOffset);
        });
        completableFuture.whenCompleteAsync((thread, action) -> {
            batchMap.forEach((word, matches) -> {
                if (null == aggregatedMap.get(word))
                    aggregatedMap.put(word, matches);
                else {
                    aggregatedMap.get(word).addAll(matches);
                }
            });
        });
        completableFuture.exceptionally(exception -> {
            System.err.println(exception);
            return null;
        });
        charOffset += batch.length();
    }

    void print() {
        aggregatedMap.forEach((word, occurrences) -> {
            String output = word + "--> [";
            Integer[] firstOffset = occurrences.remove(0);
            output += "[lineOffset=" + firstOffset[0] + ", charOffset=" + firstOffset[1] + "]";
            for (Integer[] offsetGroup : occurrences) {
                output += ", [lineOffset=" + offsetGroup[0] + ", charOffset=" + offsetGroup[1] + "]";
            }
            output += "]";
            System.out.println(output);
        });
    }
}
