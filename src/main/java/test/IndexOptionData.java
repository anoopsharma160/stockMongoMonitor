//package test;
//
//import io.restassured.RestAssured;
//import io.restassured.response.Response;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//
//public class IndexOptionData {
//    @Test(invocationCount = 100000)
//    void callIndexDataMC() throws InterruptedException {
//        Thread.sleep(100000);
//        Response response= RestAssured.given().port(9000).get("/dashboard/indexOption/0");
//        System.out.println(response.prettyPrint());
//        Assert.assertEquals(response.getStatusCode(),200);
//    }
//}
