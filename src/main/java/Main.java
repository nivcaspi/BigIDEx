public class Main {

    private static final String urlString = "http://norvig.com/big.txt";
    private static final String[] setOfStrings = ("James,John,Robert,Michael,William,David,Richard,Charles,Joseph,Thomas,Christopher,Daniel,Paul,Mark,Donal" +
            "d,George,Kenneth,Steven,Edward,Brian,Ronald,Anthony,Kevin,Jason,Matthew,Gary,Timothy,Jose,Larry,Jeffrey," +
            "Frank,Scott,Eric,Stephen,Andrew,Raymond,Gregory,Joshua,Jerry,Dennis,Walter,Patrick,Peter,Harold,Douglas,H" +
            "enry,Carl,Arthur,Ryan,Roger").split(",");
    private static final Integer BATCH_SIZE = 1000;

    public static void main(String[] args) {
        Reader textReader = new Reader(BATCH_SIZE);
        Aggregator aggregator = new Aggregator(setOfStrings);
        textReader.urlToAggregator(urlString, aggregator);
        aggregator.print();
        System.out.println("bye");
    }
}
