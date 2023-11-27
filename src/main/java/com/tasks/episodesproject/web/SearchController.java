package com.tasks.episodesproject.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tasks.episodesproject.dto.EpisodeDTO;
import com.tasks.episodesproject.entity.Characters;
import com.tasks.episodesproject.exceptions.ErrorResponse;
import com.tasks.episodesproject.service.CharactersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;


@Tag(name = "Search Controller", description = "Search the Database")
@AllArgsConstructor
@RestController
@RequestMapping("/search")
public class SearchController {
  
  private CharactersService characterService;
  
  // Search Request. Search for episodes
  @Operation(
      summary = "Search for Characters",
      description = "Search the database for Characters. The request takes in the search-word as a parameter query. Search-word could be Firstname, Lastname or both names. The request returns an array of arrays of Episodes featuring Characters whose names contain the search-word. Each array of Episodes belong to a Character",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Character Found.",
              content = {
                  @Content(
                      mediaType="application/json",
                      array=@ArraySchema(schema=@Schema(implementation=Characters.class))
                      )
      }
          ),
          @ApiResponse(
              responseCode="404",
              description = "Character not Found.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=ErrorResponse.class)
                      )
      }
      )
    }
  )
  @GetMapping("/name")
  public ResponseEntity<List<List<EpisodeDTO>>> searchCharacterNames(@RequestParam String name) {
    return new ResponseEntity<>(characterService.findEpisodesByCharacterName(name), HttpStatus.OK);
  }
}
