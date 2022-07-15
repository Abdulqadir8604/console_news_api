package main.java;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String api_key = "bb35820342c54275bea094772d3b2c78";
        String type = "everything";
        String type2 = "";

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        System.out.println("Enter the topic you want to search (if not any particular then type \"all\": ");
        Scanner sc = new Scanner(System.in);
        String topic = sc.nextLine();

        if (topic.equals("all")) {
            type = "top-headlines";
            type2 = "country=in";
        } else {
            type = "top-headlines";
            type2 = "q=" + topic;
        }

        String url = "https://newsapi.org/v2/" + type + "?" + type2 + "&apiKey=" + api_key;

        //http client
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //printing the response
        System.out.println(url);
        System.out.println(response.body());

        //parsing the response
        JSONObject jsonObject = new JSONObject(response.body());
        JSONArray jsonArray = jsonObject.getJSONArray("articles");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            System.out.println("Title: " + jsonObject1.getString("title"));
            if (jsonObject1.getJSONObject("source").isNull("name")) {
                System.out.println("Source: " + "Unknown");
            }else if (jsonObject1.isNull("content")) {
                System.out.println("Content: " + "Unknown");
            } else {
                System.out.println("Source: " + jsonObject1.getJSONObject("source").getString("name"));
                System.out.println("Description: " + jsonObject1.getString("content"));
            }
            System.out.println("URL: " + jsonObject1.getString("url"));
            System.out.println("Published At: " + jsonObject1.getString("publishedAt"));
            System.out.println("---------------------------------------------------------------------------------------------------------------------");
        }
    }
}
