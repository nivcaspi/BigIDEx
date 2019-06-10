import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

class Reader {
    private static int BATCH_SIZE;
    private String batch;
    private int lineOffset;

    Reader(Integer batchSize) {
        BATCH_SIZE = batchSize;
        batch = "";
        lineOffset = 0;
    }

    void urlToAggregator(String urlString, Aggregator aggregator) {
        try {
            URL url = new URL(urlString);

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;
            while ((line = in.readLine()) != null) {
                push(line, aggregator);
            }
            if (!"".equals(batch))
                aggregator.processBatch(batch, lineOffset);
            in.close();

        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }
    }

    private void push(String line, Aggregator aggregator) {
       if (lineOffset > 0 && lineOffset % BATCH_SIZE == 0) {
           aggregator.processBatch(batch, lineOffset);
           batch = "";
       }
        batch += line;
        lineOffset++;
    }
}
