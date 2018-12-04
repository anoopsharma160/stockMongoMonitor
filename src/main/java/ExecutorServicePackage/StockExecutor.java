package ExecutorServicePackage;

import IndexNSELive.NSEBNDController;
import MoneyControlDataFetcher.DBController;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StockExecutor {

    public static void main(String[] args) {
        ExecutorService executorService= Executors.newFixedThreadPool(2);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <100000000; i++) {
                    try {
                        System.out.println("Executed BNOCData Count: "+i);
                        Thread.sleep(30000);
                        new NSEBNDController().execute(0);
                    } catch (InterruptedException | IOException | ParseException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Task 1 "+i);
                }

            }
        });

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <10000000 ; i++) {
                    System.out.println("Task 2 " + i);
                    System.out.println("Executed IncOI Inc Pri: Count "+i);
                    try {
                        Thread.sleep(100000);
                        new DBController().execute(0);
                    } catch (InterruptedException | IOException | ParseException e) {
                        e.printStackTrace();
                    }

                }
                }

        });

        executorService.shutdown();
    }
}
