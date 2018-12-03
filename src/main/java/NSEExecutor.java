import NSEData.JSONReader;
import NSEData.NseChartController;

public class NSEExecutor {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i <100000000 ; i++) {
            Thread.sleep(90000);
            new JSONReader().execute();

        }
    }
}
