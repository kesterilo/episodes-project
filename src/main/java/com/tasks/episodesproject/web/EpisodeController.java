package com.tasks.episodesproject.web;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tasks.episodesproject.dto.CommentDTO;
import com.tasks.episodesproject.dto.EpisodeDTO;
import com.tasks.episodesproject.entity.Characters;
import com.tasks.episodesproject.entity.Comment;
import com.tasks.episodesproject.entity.Episode;
import com.tasks.episodesproject.exceptions.ErrorResponse;
import com.tasks.episodesproject.service.EpisodeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@Tag(name = "Episode Controller", description = "Create, Retrieve and modify Episodes")
@AllArgsConstructor
@RestController
@RequestMapping("/episode")
public class EpisodeController {
  
  EpisodeService episodeService;
  
  
  // Get Request. Get all Episodes
  @Operation(
      summary = "Get all Episodes from the database.",
      description = "Get all Episodes from the database. The Episodes returned are sorted by 'release-date' in ascending order. The request returns a list of DTO Objects each containing an Episode and its comment counts.",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Successfull Request",
              content = {
                  @Content(
                      mediaType="application/json",
                      array=@ArraySchema(schema=@Schema(implementation=EpisodeDTO.class))
                      )  
      }
      )
    }
  )
  @GetMapping("/all")
  public ResponseEntity<List<EpisodeDTO>> getAllEpisodes() {
    return new ResponseEntity<>(episodeService.getAllEpisodes(), HttpStatus.OK);
  }
  
  
  // Get Request. Get single Episode
  @Operation(
      summary = "Get a specified Episode from the data base. Specify by ID.",
      description = "Get a single Episode. The request must contain an id as a parameter-path-variable. The request returns the respective Episode object.",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Successful Request. Episode exists.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=EpisodeDTO.class)
                      )
      }
          ),
          @ApiResponse(
              responseCode="404",
              description = "Unsuccessful Request. Episode does not exist.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=ErrorResponse.class)
                      )
      }
      )
    }
  )
  @GetMapping("/{episodeId}")
  public ResponseEntity<EpisodeDTO> getEpisode(@PathVariable Long episodeId) {
    return new ResponseEntity<>(episodeService.getEpisode(episodeId), HttpStatus.OK);
  }
  
  
  // Post Request. Create a new Episode in the database
  @Operation(
      summary = "Create a new Episode in the database.",
      description = "Create a new Episode. The request must contain mandatory fields in JSON as request body (Mandatory fields are: name, releaseDate, episodeCode). The request returns the created Episode object.",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Episode successfully created.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=EpisodeDTO.class)
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
  public ResponseEntity<EpisodeDTO> createEpisode(@Valid @RequestBody Episode episode) {
    return new ResponseEntity<>(episodeService.createEpisode(episode), HttpStatus.CREATED);
  }
  
  
  // Put Request. Edit an existing Episode
  @Operation(
      summary = "Edit an existing Episode in the database.",
      description = "Edit the properties of an existing Episode. The request must contain mandatory fields in JSON as request body. The request returns the edited Episode object.",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Episode successfully edited.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=EpisodeDTO.class)
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
              description = "The Episode with the passed in ID does not exist.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=ErrorResponse.class)
                      )
      }
      )
    }
  )
  @PutMapping("/{episodeId}")
  public ResponseEntity<EpisodeDTO> updateEpisode(@Valid @RequestBody Episode episode, @PathVariable Long episodeId) {
    return new ResponseEntity<>(episodeService.updateEpisode(episode, episodeId), HttpStatus.CREATED);
  }
  
  
  // Delete Request. Delete an existing Episode
  @Operation(
      summary = "Delete an existing Episode in the database.",
      description = "Delete an existing Episode specified by the passed-in ID. The request must have an Episode ID.",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Episode successfully deleted",
              content = {
                  @Content(
                      mediaType="application/json"
                      )
      }
          ),
          @ApiResponse(
              responseCode="404",
              description = "The Episode with the passed in ID does not exist.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=ErrorResponse.class)
                      )
      }
      )
    }
  )
  @DeleteMapping("/{episodeId}")
  public ResponseEntity<HttpStatus> deleteEpisode(@PathVariable Long episodeId) {
    episodeService.deleteEpisode(episodeId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
  
  // Get Request. Get Characters in an Episode.
  @Operation(
      summary = "Get the Characters in an Episode.",
      description = "Get the Characters in the Episode specified by the passed-in ID. The request must have an Episode ID. Returns a set of Characters featured in the specified Episode.",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Episode exists.",
              content = {
                  @Content(
                      mediaType="application/json",
                      array=@ArraySchema(schema=@Schema(implementation=Characters.class))
                      )
      }
          ),
          @ApiResponse(
              responseCode="404",
              description = "The Episode with the passed in ID does not exist.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=ErrorResponse.class)
                      )
      }
      )
    }
  )
  @GetMapping("/{episodeId}/character/all")
  public ResponseEntity<Set<Characters>> getEpisodeCharacters(@PathVariable Long episodeId) {
    return new ResponseEntity<>(episodeService.getEpisodeCharacters(episodeId), HttpStatus.OK);
  }
  
  
  // Put Request. Add a Character to an existing Episode
  @Operation(
      summary = "Add a Character to an Episode.",
      description = "Add a Character to an Episode that already exists. The request must have an Episode ID and a Character in JSON in its request body. Returns a DTO Object containing the specified Episode and its number of comments",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Character successfully added",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=EpisodeDTO.class)
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
              description = "The Episode with the passed in ID does not exist.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=ErrorResponse.class)
                      )
      }
      )
    }
  )
  @PutMapping("/{episodeId}/character")
  public ResponseEntity<EpisodeDTO> addCharacter(@PathVariable Long episodeId, @Valid @RequestBody Characters character) {
    return new ResponseEntity<>(episodeService.addCharacter(episodeId, character), HttpStatus.OK);
  }
  
  
  // Delete Request. Delete an Episode's Character
  @Operation(
      summary = "Delete an Episode's Character.",
      description = "Delete an existing Episode's Character specified by the passed-in ID. The request must have an Episode ID and the Character ID.",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Episode exists. Character successfully deleted",
              content = {
                  @Content(
                      mediaType="application/json"
                      )
      }
          ),
          @ApiResponse(
              responseCode="404",
              description = "The Episode with the passed in ID does not exist. OR The Character with the passed in ID does not exist",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=ErrorResponse.class)
                      )
      }
      )
    }
  )
  @DeleteMapping("/{episodeId}/character/{characterId}")
  public ResponseEntity<HttpStatus> removeCharacter(@PathVariable Long episodeId, @PathVariable Long characterId) {
    episodeService.removeCharacter(episodeId, characterId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
  
  
  // Get Request. Get Comments in an Episode.
  @Operation(
      summary = "Get the Comments in an Episode.",
      description = "Get the Comments in the Episode specified by the passed-in ID. The request must have an Episode ID. Returns a List of Comments. The Comments are returned in a descending sort order by 'date created'.",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Episode exists.",
              content = {
                  @Content(
                      mediaType="application/json",
                      array=@ArraySchema(schema=@Schema(implementation=CommentDTO.class))
                      )
      }
          ),
          @ApiResponse(
              responseCode="404",
              description = "The Episode with the passed in ID does not exist.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=ErrorResponse.class)
                      )
      }
      )
    }
  )
  @GetMapping("{episodeId}/comment/all")
  public ResponseEntity<List<CommentDTO>> getEpisodeComments(@PathVariable Long episodeId) {
    return new ResponseEntity<>(episodeService.getEpisodeComments(episodeId), HttpStatus.OK);
  }
  
  
  // Put Request. Add a Comment to an existing Episode
  @Operation(
      summary = "Add a Comment to an Episode.", 
      description = "Add a Comment to an Episode that already exists. The request must have an Episode ID and a Comment in JSON in its request body. Returns a DTO Object.",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Comment successfully added",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=EpisodeDTO.class)
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
              description = "The Episode with the passed in ID does not exist.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=ErrorResponse.class)
                      )
      }
      )
    }
  )
  @PutMapping("/{episodeId}/comment")
  public ResponseEntity<EpisodeDTO> addComment(@PathVariable Long episodeId, @Valid @RequestBody Comment comment) {
    return new ResponseEntity<>(episodeService.addComment(episodeId, comment), HttpStatus.OK);
  }
  
  
// Delete Request. Delete an Episode's Comment
  @Operation(
      summary = "Delete an Episode's Comment.",
      description = "Delete an existing Episode's Comment specified by the passed-in ID. The request must have an Episode ID and the Comment ID.",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Episode exists. Comment successfully deleted",
              content = {
                  @Content(
                      mediaType="application/json"
                      )
      }
          ),
          @ApiResponse(
              responseCode="404",
              description = "The Episode with the passed in ID does not exist. OR The Comment with the passed in ID does not exist",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=ErrorResponse.class)
                      )
      }
      )
    }
  )
  @DeleteMapping("/{episodeId}/comment/{commentId}")
  public ResponseEntity<HttpStatus> removeComment(@PathVariable Long episodeId, @PathVariable Long commentId) {
    episodeService.removeComment(episodeId, commentId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
