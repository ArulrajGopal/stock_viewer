package pack1;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class fetchstockdetails {

    public static void main(String[] args) {
        fetchstockdetails obj = new fetchstockdetails();
        String result = obj.fetchstockDetails_direct();
        System.out.println(result);

    }

    public String fetchstockDetails_direct () {
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://latest-stock-price.p.rapidapi.com/any"))
                        .header("X-RapidAPI-Key", "23a945bde0msha8c00c2faa561f5p119d77jsnea9895e0d88d")
                        .header("X-RapidAPI-Host", "latest-stock-price.p.rapidapi.com")
                        .method("GET", HttpRequest.BodyPublishers.noBody())
                        .build();

                    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                     return response.body(); 
                }

                catch (IOException | InterruptedException e) {
                    e.printStackTrace(); 
                    return null; 
                }                
        }




    public String fetchStockDetails_through_aws_api_gateway () {
                try {
                    OkHttpClient client = new OkHttpClient();

                    String url = "https://qmxqg0gnxh.execute-api.us-east-1.amazonaws.com/prod/myresource";
                    Request request = new Request.Builder()
                                            .url(url)
                                            .build();
                    Response response = client.newCall(request).execute();
                    String result = response.body().string()
                                    .replace("\"[","[")
                                    .replace("]\"","]")
                                    .replace("\\\"", "\"");
                    return result;
                }

                catch (IOException e) {
                    e.printStackTrace(); 
                    return null; 
                    
                }

    }
    
}
