import Controller.DBController;
import NSEData.JSONReader;
import NSEData.NseChartController;

import java.io.IOException;
import java.text.ParseException;

public class Executor {

    public static void main(String[] args) throws IOException, InterruptedException, ParseException {



//            Thread.sleep(180000);
            new DBController().execute();

    }
}
