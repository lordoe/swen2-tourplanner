package at.fhtw.swen2.tutorial.service.impl;

import at.fhtw.swen2.tutorial.service.MapService;
import at.fhtw.swen2.tutorial.service.utils.MapData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Transactional
@Slf4j
public class MapServiceImpl implements MapService {

    @Value("${mapquest.apikey}")
    private String apiKey;

    private ObjectMapper objectMapper = new ObjectMapper();

    private MapData requestMapData(String from, String to, String transportType) throws IOException, InterruptedException, JSONException {
        // get route information from MapQuest API
        /* routeType:
            fastest - Quickest drive time route.
            shortest - Shortest driving distance route.
            pedestrian - Walking route; Avoids limited access roads; Ignores turn restrictions.
            bicycle - Will only use roads on which bicycling is appropriate.
         */
        // create directory if not exists
        Path dirPath = Paths.get("src/main/resources/maps");
        if(!Files.exists(dirPath)){
            Files.createDirectory(dirPath);
        }
        // check if mapdata already exists at Path
        String metaDataPath = "src/main/resources/maps/." + from + "_" + to + "_" + transportType + ".json";
        Path path = Paths.get(metaDataPath);
        if(Files.exists(path)){
            String json = Files.readString(path);
            MapData mapData = objectMapper.readValue(json, MapData.class);
            return mapData;
        }

        // build request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.mapquestapi.com/directions/v2/route?key=" + apiKey + "&from=" + from + "&to=" + to + "&unit=k" + "&routeType=" + transportType))
                .GET()
                .build();

        // get information from MapQuest API
        log.info("Requesting MapQuest API: " + request.uri().toString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject jsonpObject = new JSONObject(response.body());
        double distance = jsonpObject.getJSONObject("route").getDouble("distance");
        double time = jsonpObject.getJSONObject("route").getDouble("time")/3600;
        MapData mapData = new MapData();
        mapData.setDistance(distance);
        mapData.setDuration(time);

        // save mapdata to Path
        objectMapper.writeValue(path.toFile(), mapData);
        return mapData;
    }
    private String requestStaticMap(String from, String to, String transportType) throws IOException {
        // check if file exists at Path:
        String savePath = "src/main/resources/maps/" + from + "_" + to + ".png";
        if(Files.exists(Paths.get(savePath))) {
            return savePath;
        }

        // get image from MapQuest API
        String size = "600,400@2x";
        String strUrl = "https://www.mapquestapi.com/staticmap/v5/map?start=" + from + "&end=" + to + "&size=" + size + "&key=" + apiKey;
        URL url = new URL(strUrl);
        log.info("Requesting MapQuest API: " + url.toString());
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setRequestMethod("GET");

        // save image to Path
        int responseCode = httpCon.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = httpCon.getInputStream();
            OutputStream outputStream = new FileOutputStream(savePath);

            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();
            return savePath;
        }
        else{
            return null;
        }
    }

    @Override
    public MapData getMap(String from, String to, String transportType) {
        try {
            MapData mapData = requestMapData(from, to, transportType);
            String imagePath = requestStaticMap(from, to, transportType);
            mapData.setImagePath(imagePath);
            return mapData;

        } catch (IOException | InterruptedException | JSONException e) {
            return null;
        } catch (Exception e) {
            System.err.println("unknown exception: " + e.getMessage());
            return null;
        }
    }
}
