package org.api.music.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.api.music.models.Photo;

public class ApiConfig {

  private static final String PHOTOS_URL = "https://jsonplaceholder.typicode.com/photos";

  public List<Photo> getPhotos() throws IOException {
    try (CloseableHttpClient client = HttpClients.createDefault()) {
      HttpGet request = new HttpGet(PHOTOS_URL);
      try (CloseableHttpResponse response = client.execute(request)) {
        String jsonResponse = EntityUtils.toString(response.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });
      }
    }
  }

}
