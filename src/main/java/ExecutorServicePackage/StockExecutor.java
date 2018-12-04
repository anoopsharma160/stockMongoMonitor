package ExecutorServicePackage;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

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
                        Thread.sleep(1000);
                        System.out.println("Executed BNOCData Count: "+i);
                        Thread.sleep(30000);
                        Response response= RestAssured.given().port(9000).get("/dashboard/nsebnoi");
                        System.out.println(response.prettyPrint());
                        Assert.assertEquals(response.getStatusCode(),200);

                    } catch (InterruptedException e) {
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
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Response response= RestAssured.given().port(9000).get("/dashboard/IncPriIncOi/0");
                    Assert.assertEquals(response.getStatusCode(),200);
                }
                }

        });

        executorService.shutdown();
    }
}
