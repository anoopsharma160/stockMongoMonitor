package test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;


public class NSeData {
    @Test(invocationCount = 1000000)
    void callNseData() throws InterruptedException {
        Thread.sleep(80000);
        Response response= RestAssured.given().port(9000).get("/dashboard/nse/0");
        System.out.println(response.prettyPrint());
        Assert.assertEquals(response.getStatusCode(),200);
    }
}
