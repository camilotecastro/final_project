package org.api.music.service;

import java.util.List;
import java.util.concurrent.*;
import org.api.music.config.ApiConfig;
import org.api.music.models.Photo;

public class ApiService {

  private final PhotoStatistics photoStatistics;
  private final ApiConfig apiConfig;
  private final String photoTittle;

  public ApiService(PhotoStatistics photoStatistics, ApiConfig apiConfig, String photoTittle) {
    this.photoStatistics = photoStatistics;
    this.apiConfig = apiConfig;
    this.photoTittle = photoTittle;
  }

  public void ejecutarTareas() throws ExecutionException, InterruptedException {

    ExecutorService executorService = Executors.newFixedThreadPool(
        Runtime.getRuntime().availableProcessors());

    // Crear una tarea que devuelve la lista de fotos
    Callable<List<Photo>> photoTask = apiConfig::getPhotos;

    // Enviar la tarea al executor
    Future<List<Photo>> photoFuture = executorService.submit(photoTask);

    // Obtener las fotos
    List<Photo> photos = photoFuture.get();

    // Procesar las estadísticas
    photoStatistics.contarFotosPorAlbumParalelo(photos, photoTittle, executorService)
        .thenAccept(statistics -> {
          statistics.forEach((albumId, count) -> {
            System.out.println("Álbum " + albumId + " tiene " + count + " fotos");
          });
        }).get();

    executorService.shutdown();
  }


}
