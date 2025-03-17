package org.api.music;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import org.api.music.config.ApiConfig;
import org.api.music.service.ApiService;
import org.api.music.service.PhotoStatistics;

public class Main {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ApiConfig apiConfig = new ApiConfig();
    PhotoStatistics photoStatistics = new PhotoStatistics();
    ApiService apiService = new ApiService(photoStatistics, apiConfig, "accusamus");
    apiService.ejecutarTareas();
  }

}
