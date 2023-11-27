package com.tasks.episodesproject.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tasks.episodesproject.entity.Location;
import com.tasks.episodesproject.exceptions.ErrorResponse;
import com.tasks.episodesproject.service.LocationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;


@Tag(name = "Location Controller", description = "Retrieve Locations")
@AllArgsConstructor
@RestController
@RequestMapping("/location")
public class LocationController {
  
  LocationService locationService;
  
  
  // Get Request. Get all Location
  @Operation(
      summary = "Get all Locations from the database.",
      description = "Get all Locations from the database. The request returns a list of Location Objects.",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Successfull Request",
              content = {
                  @Content(
                      mediaType="application/json",
                      array=@ArraySchema(schema=@Schema(implementation=Location.class))
                      )  
      }
      )
    }
  )
  @GetMapping("/all")
  public ResponseEntity<List<Location>> getAllLocations() {
    return new ResponseEntity<>(locationService.getAllLocations(), HttpStatus.OK);
  }
  
  
  // Get Request. Get single Location
  @Operation(
      summary = "Get a specified Location from the data base. Specify by ID.",
      description = "Get a single Location. The request must contain an id as a parameter-path-variable. The request returns the respective Location object.",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Successful Request. Location exists.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=Location.class)
                      )
      }
          ),
          @ApiResponse(
              responseCode="404",
              description = "Unsuccessful Request. Location does not exist.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=ErrorResponse.class)
                      )
      }
      )
    }
  )
  @GetMapping("/{locationId}")
  public ResponseEntity<Location> getLocation(@PathVariable Long locationId) {
    return new ResponseEntity<>(locationService.getLocation(locationId), HttpStatus.OK);
  }

}
