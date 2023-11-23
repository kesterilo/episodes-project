package com.tasks.episodesproject.service;

import java.util.List;

import com.tasks.episodesproject.entity.Location;

public interface LocationService {

  List<Location> getAllLocations();

  Location getLocation(Long id);
  
}
