package com.tasks.episodesproject.web;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tasks.episodesproject.dto.EpisodeDTO;
import com.tasks.episodesproject.entity.Characters;
import com.tasks.episodesproject.entity.Episode;
import com.tasks.episodesproject.entity.Location;
import com.tasks.episodesproject.enums.SortFieldEnum;
import com.tasks.episodesproject.exceptions.ErrorResponse;
import com.tasks.episodesproject.filter.CharacterFilter;
import com.tasks.episodesproject.service.CharactersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Characters Controller", description = "Create, Retrieve and modify Characters")
@AllArgsConstructor
@RestController
@RequestMapping("/character")
public class CharactersController {
  
  
  CharactersService characterService;
  
  
  public CharacterFilter getCharacterFilter() {
    return new CharacterFilter();
  }
  
  
  // Get Request. Get all Characters
  @Operation(
      summary = "Get all Characters from the database. Also get a specific list of Characters by adding filters and sort parameters to the request.",
      description = "Get all Characters from the database. Optionally, you can filter through the Characters by adding filters to the parameters as query parameters. Also optionally, you can sort in ascending or descending order by adding ASC or DESC as query parameters. The request returns an array of Characters.",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Successfull Request",
              content = {
                  @Content(
                      mediaType="application/json",
                      array=@ArraySchema(schema=@Schema(implementation=Characters.class))
                      )  
      }
      )
    }
  )
  @GetMapping("/all")
  public ResponseEntity<List<Characters>> getAllCharacters(
      @RequestParam(required = false) String gender,
      @RequestParam(required = false) String status,
      @RequestParam(required = false) String location,
      @RequestParam(required = false) SortFieldEnum sortField,
      @RequestParam(required = false) Sort.Direction sortDirection) {
        
    Sort sort = Sort.by(sortDirection == null ? Sort.DEFAULT_DIRECTION : sortDirection,
        sortField == null ? "firstName" : sortField.getSortParameter());
    CharacterFilter characterFilter = getCharacterFilter();
    characterFilter.setGender(gender);
    characterFilter.setStatus(status);
    characterFilter.setLocation(location);
    return new ResponseEntity<>(characterService.getAllCharacters(characterFilter, sort), HttpStatus.OK);
    
  }
  
  
  // Get Request. Get single Character
  @Operation(
      summary = "Get a specified Character from the data base. Specify by ID.",
      description = "Get a single Character. The request must contain an id as a parameter-path-variable. The request returns the respective Character object.",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Successful Request. Character exists.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=Characters.class)
                      )
      }
          ),
          @ApiResponse(
              responseCode="404",
              description = "Unsuccessful Request. Character does not exist.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=ErrorResponse.class)
                      )
      }
      )
    }
  )
  @GetMapping("/{characterId}")
  public ResponseEntity<Characters> getCharacter(@PathVariable Long characterId) {
    return new ResponseEntity<>(characterService.getCharacter(characterId), HttpStatus.OK);
  }
  
  
  // Post Request. Create a new Character in the database
  @Operation(
      summary = "Create a new Character in the database.",
      description = "Create a new Character. The request must contain mandatory fields in JSON as request body (Mandatory fields are: Firstname, Lastname, Status(must be either of ACTIVE, DEAD, UNKNOWN) and Gender(must be either MALE OR FEMALE)). The request returns the created Character object.",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Character successfully created.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=Characters.class)
                      )
      }
          ),
          @ApiResponse(
              responseCode="400",
              description = "Bad Request.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=ErrorResponse.class)
                      )
      }
      )
    }
  )
  @PostMapping
  public ResponseEntity<Characters> createCharacter(@Valid @RequestBody Characters character) {
    return new ResponseEntity<>(characterService.createCharacter(character), HttpStatus.CREATED);
  }
  
  
  // Put Request. Edit an existing Character
  @Operation(
      summary = "Edit an existing Character in the database.",
      description = "Modify the properties of an existing Character. The request must contain mandatory fields in JSON as request body. The request returns the edited Character object.",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Character successfully edited.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=Characters.class)
                      )
      }
          ),
          @ApiResponse(
              responseCode="400",
              description = "Bad Request.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=ErrorResponse.class)
                      )
      }
          ),
          @ApiResponse(
              responseCode="404",
              description = "The Character with the passed in ID does not exist.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=ErrorResponse.class)
                      )
      }
      )
    }
  )
  @PutMapping("/{characterId}")
  public ResponseEntity<Characters> updateCharacter(@Valid @RequestBody Characters character, @PathVariable Long characterId) {
    return new ResponseEntity<>(characterService.updateCharacter(character, characterId), HttpStatus.OK);
  }
  
  
  // Delete Request. Delete an existing Character
  @Operation(
      summary = "Delete an existing Character in the database.",
      description = "Delete an existing Character specified by the passed-in ID. The request must have a Character ID.",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Character successfully deleted",
              content = {
                  @Content(
                      mediaType="application/json"
                      )
      }
          ),
          @ApiResponse(
              responseCode="404",
              description = "The Character with the passed in ID does not exist.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=ErrorResponse.class)
                      )
      }
      )
    }
  )
  @DeleteMapping("/{characterId}")
  public ResponseEntity<HttpStatus> deleteCharacter(@PathVariable Long characterId) {
    characterService.deleteCharacter(characterId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
  
  // Get Request. Get the Location of a Character
    @Operation(
      summary = "Get the Location of a specified Character.",
      description = "Get the Location of the Character specified by the passed-in ID. The request must have a Character ID. Returns the specified Character containing its Location.",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Character exists.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=Characters.class)
                      )
      }
          ),
          @ApiResponse(
              responseCode="404",
              description = "The Character with the passed in ID does not exist.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=ErrorResponse.class)
                      )
      }
      )
    }
  )
  @GetMapping("/{characterId}/location")
  public ResponseEntity<Location> getCharacterLocation(@PathVariable Long characterId) {
    return new ResponseEntity<>(characterService.getCharacterLocation(characterId), HttpStatus.OK);
  }
  
  
  // Put Request. Add Location to an existing Character
  @Operation(
      summary = "Add Character Location.",
      description = "Add Location to a Character that already exists. The request must have a Character ID and a Location in JSON in its request body. Returns the Location Object created",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Location successfully added",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=Characters.class)
                      )
      }
          ),
          @ApiResponse(
              responseCode="400",
              description = "Bad Request.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=ErrorResponse.class)
                      )
      }
          ),
          @ApiResponse(
              responseCode="404",
              description = "The Character with the passed in ID does not exist.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=ErrorResponse.class)
                      )
      }
      )
    }
  )
  @PutMapping("/{characterId}/location")
  public ResponseEntity<Characters> addCharacterLocation(@PathVariable Long characterId, @Valid @RequestBody Location location) {
    return new ResponseEntity<>(characterService.addCharacterLocation(characterId, location), HttpStatus.OK);
  }
  
  
  // Delete Request. Delete a Character's Location
  @Operation(
      summary = "Delete a Character's Location.",
      description = "Delete an existing Character's Location specified by the passed-in ID. The request must have a Character ID and the Location ID.",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Character exists. Location successfully deleted",
              content = {
                  @Content(
                      mediaType="application/json"
                      )
      }
          ),
          @ApiResponse(
              responseCode="404",
              description = "The Character with the passed in ID does not exist. OR The Location with the passed in ID does not exist",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=ErrorResponse.class)
                      )
      }
      )
    }
  )
  @DeleteMapping("/{characterId}/location/{locationId}")
  public ResponseEntity<HttpStatus> removeCharacterLocation(@PathVariable Long characterId, @PathVariable Long locationId) {
    characterService.removeCharacterlocation(characterId, locationId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
  
  
  // Get Request. Get the Episodes of a Character
  @Operation(
      summary = "Get the Episodes of a specified Character.",
      description = "Get the Episodes of the Character specified by the passed-in ID. The request must have a Character ID. The request returns a list of Episodes that Character featured in.",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Character exists.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=Characters.class)
                      )
      }
          ),
          @ApiResponse(
              responseCode="404",
              description = "The Character with the passed in ID does not exist.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=ErrorResponse.class)
                      )
      }
      )
    }
  )
  @GetMapping("/{characterId}/episode/all")
  public ResponseEntity<List<EpisodeDTO>> getCharactersEpisodes(@PathVariable Long characterId) {
    return new ResponseEntity<>(characterService.getCharacterEpisodes(characterId), HttpStatus.OK);
  }
  
  
  // Put Request. Add an Episode to an existing Character.
  @Operation(
      summary = "Add Character Episode.",
      description = "Add Episode to a Character that already exists. The request must have a Character ID and an Episode in JSON in its request body.",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Episode successfully added",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=Characters.class)
                      )
      }
          ),
          @ApiResponse(
              responseCode="400",
              description = "Bad Request.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=ErrorResponse.class)
                      )
      }
          ),
          @ApiResponse(
              responseCode="404",
              description = "The Character with the passed in ID does not exist.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=ErrorResponse.class)
                      )
      }
      )
    }
  )
  @PutMapping("/{characterId}/episode")
  public ResponseEntity<Characters> addEpisode(@PathVariable Long characterId, @Valid @RequestBody Episode episode) {
    return new ResponseEntity<>(characterService.addEpisode(episode, characterId), HttpStatus.OK);
  }
  
  
  // Delete Request. Delete a Character's Episode
  @Operation(
      summary = "Delete a Character's Episode.",
      description = "Delete an existing Character's Episode specified by the passed-in ID. The request must have a Character ID and the Episode ID.",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Character exists. Episode successfully deleted",
              content = {
                  @Content(
                      mediaType="application/json"
                      )
      }
          ),
          @ApiResponse(
              responseCode="404",
              description = "The Character with the passed in ID does not exist. OR The Episode with the passed in ID does not exist",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=ErrorResponse.class)
                      )
      }
      )
    }
  )
  @DeleteMapping("/{characterId}/episode/{episodeId}")
  public ResponseEntity<HttpStatus> removeEpisode(@PathVariable Long characterId, @PathVariable Long episodeId) {
    characterService.removeEpisode(characterId, episodeId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
