package com.tasks.episodesproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tasks.episodesproject.entity.Location;
import com.tasks.episodesproject.exceptions.EntityNotFoundException;
import com.tasks.episodesproject.repository.LocationRepository;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class LocationServiceImplementation implements LocationService {

  LocationRepository locationRepository;

  @Override
  public List<Location> getAllLocations() {
    return locationRepository.findAll();
  }

  @Override
  public Location getLocation(Long id) {
    Optional<Location> location = locationRepository.findById(id);
    return unwrapLocation(location, id); 
  }
  

  private Location unwrapLocation(Optional<Location> location, Long id) {
    if (location.isPresent()) {
      return location.get();
    } else {
      throw new EntityNotFoundException(id, Location.class);
    }
  }
  
}

