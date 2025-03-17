package org.api.music.service;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import org.api.music.models.Photo;

public class PhotoStatistics {

  public CompletableFuture<Map<Integer, Long>> contarFotosPorAlbumParalelo(List<Photo> photos,
      String tituloPhoto, ExecutorService executorService) {

    CompletableFuture<Map<Integer, Long>> futureCountByAlbum = CompletableFuture.supplyAsync(
        () -> photos.parallelStream()
            .collect(Collectors.groupingBy(Photo::getAlbumId, Collectors.counting())),
        executorService);

    CompletableFuture<List<Photo>> futureTitleSearch = CompletableFuture.supplyAsync(
        () -> photos.parallelStream()
            .filter(photo -> photo.getTitle().contains(tituloPhoto))
            .collect(Collectors.toList()), executorService);

    return futureCountByAlbum.thenCombine(futureTitleSearch, (countByAlbum, titleSearchResults) -> {
      System.out.println(
          "Fotos con '" + tituloPhoto + "' en el título: " + titleSearchResults.size());

      // Retornar el conteo de fotos por álbum
      return countByAlbum;
    });
  }


}
