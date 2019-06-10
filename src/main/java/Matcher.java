import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Matcher {
    private Map<String, ArrayList<Integer[]>> result = new HashMap<>();

    Map<String, ArrayList<Integer[]>> find(String batch, String[] setOfStrings, int lineOffset, int charOffset) {
        try (BufferedReader reader = new BufferedReader((new StringReader(batch)))) {
            String line = reader.readLine();
            while (null != line) {
                for (String word : setOfStrings) {
                    for (Integer localCharOffset : allIndicesOf(word, line)) {
                        populateResult(word, lineOffset, localCharOffset + charOffset);
                    }
                }
                lineOffset++;
                charOffset += line.length();
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        return result;
    }

    private void populateResult(String word, Integer lineOffset, Integer charOffset) {
        result.computeIfAbsent(word, k -> new ArrayList<>());
        result.get(word).add(new Integer[]{lineOffset, charOffset});
    }

    private ArrayList<Integer> allIndicesOf(String word, String line) {
        ArrayList<Integer> result = new ArrayList<>();
        int charOffset = 0;
        int cursorIndex = 0;

        while (charOffset != -1) {
            charOffset = line.indexOf(word, cursorIndex);
            if (charOffset != -1) {
                result.add(charOffset);
                cursorIndex += charOffset + word.length();
            }
        }

        return  result;
    }
}
