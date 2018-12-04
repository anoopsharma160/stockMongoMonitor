package ExecutorServicePackage;

import IndexNSELive.NSEBNDController;
import MoneyControlDataFetcher.DBController;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StockExecutor {
    public static void enableSSLSocket() throws KeyManagementException, NoSuchAlgorithmException {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, new X509TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }}, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
    }
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException {

enableSSLSocket();

        ExecutorService executorService= Executors.newFixedThreadPool(2);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <100000000; i++) {
                    try {
                        Thread.sleep(30000);
                        new NSEBNDController().execute(0);
                        System.out.println("Executed BNOCData Count: "+i);
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
                    try {
                        Thread.sleep(100000);
                        new DBController().execute(0);
                        System.out.println("Executed IncOI Inc Pri: Count "+i);
                    } catch (InterruptedException | IOException | ParseException e) {
                        e.printStackTrace();
                    }

                }
                }

        });

        executorService.shutdown();
    }
}
