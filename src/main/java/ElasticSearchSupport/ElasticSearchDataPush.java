package ElasticSearchSupport;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.io.IOException;

public class ElasticSearchDataPush {
    public static void main(String[] args) throws IOException {

        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost", 9200, "http"),
                new HttpHost("localhost", 9201, "http")
        ));

// Create Index


        IndexRequest request = new IndexRequest(
                "nseindex",
                "doc");
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2018-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        request.source(jsonString, XContentType.JSON);
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);

        System.out.println(indexResponse.status().getStatus());


// Check the existing source


        GetRequest getRequest = new GetRequest(
                "nseindex",
                "doc","1"
        );
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");

        System.out.println(client.get(getRequest, RequestOptions.DEFAULT));


        client.close();

    }
}