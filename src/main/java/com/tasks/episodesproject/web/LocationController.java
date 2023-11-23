package com.tasks.episodesproject.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tasks.episodesproject.entity.Location;
import com.tasks.episodesproject.service.LocationService;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
@RequestMapping("/location")
public class LocationController {
  
  LocationService locationService;
  
  
  @GetMapping("/all")
  public ResponseEntity<List<Location>> getAllLocations() {
    return new ResponseEntity<>(locationService.getAllLocations(), HttpStatus.OK);
  }
  
  @GetMapping("/{locationId}")
  public ResponseEntity<Location> getLocation(@PathVariable Long locationId) {
    return new ResponseEntity<>(locationService.getLocation(locationId), HttpStatus.OK);
  }

}
