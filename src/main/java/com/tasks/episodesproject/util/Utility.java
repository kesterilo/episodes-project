package com.tasks.episodesproject.util;

import java.util.Optional;

import com.tasks.episodesproject.entity.Entities;
import com.tasks.episodesproject.exceptions.EntityNotFoundException;

public class Utility {
  
  public static Entities unwrapEntity(Optional<? extends Entities> entity, long id, Class<?> objectType) {
    if (entity.isPresent()) {
      return entity.get();
    } else {
      throw new EntityNotFoundException(id, objectType);
    }
  }
}
