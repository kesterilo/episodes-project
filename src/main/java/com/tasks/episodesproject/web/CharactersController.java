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
import com.tasks.episodesproject.filter.queryFilter;
import com.tasks.episodesproject.service.CharactersService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/character")
public class CharactersController {
  
  CharactersService characterService;
  queryFilter characterFilter;
  
  @GetMapping("/all")
  public ResponseEntity<List<Characters>> getAllCharacters(
      @RequestParam(required = false) String gender,
      @RequestParam(required = false) String status,
      @RequestParam(required = false) String location,
      @RequestParam(required = false) SortFieldEnum sortField,
      @RequestParam(required = false) Sort.Direction sortDirection) {
    Sort sort = Sort.by(sortDirection == null ? Sort.DEFAULT_DIRECTION : sortDirection, sortField == null ? "firstName" : sortField.getSortParameter());
    characterFilter.setGender(gender);
    characterFilter.setStatus(status);
    characterFilter.setLocation(location);
    return new ResponseEntity<>(characterService.getAllCharacters(characterFilter, sort), HttpStatus.OK);
  }
  
  @GetMapping("/{characterId}")
  public ResponseEntity<Characters> getCharacter(@PathVariable Long characterId) {
    return new ResponseEntity<>(characterService.getCharacter(characterId), HttpStatus.OK);
  }
  
  @PostMapping
  public ResponseEntity<Characters> createCharacter(@Valid @RequestBody Characters character) {
    return new ResponseEntity<>(characterService.createCharacter(character), HttpStatus.CREATED);
  }
  
  @PutMapping("/{characterId}")
  public ResponseEntity<Characters> updateCharacter(@Valid @RequestBody Characters character, @PathVariable Long characterId) {
    return new ResponseEntity<>(characterService.updateCharacter(character, characterId), HttpStatus.OK);
  }
  
  @DeleteMapping("/{characterId}")
  public ResponseEntity<HttpStatus> deleteCharacter(@PathVariable Long characterId) {
    characterService.deleteCharacter(characterId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
  @GetMapping("/{characterId}/location")
  public ResponseEntity<Location> getCharacterLocation(@PathVariable Long characterId) {
    return new ResponseEntity<>(characterService.getCharacterLocation(characterId), HttpStatus.OK);
  }
  
  @PutMapping("/{characterId}/location")
  public ResponseEntity<Characters> addCharacterLocation(@PathVariable Long characterId, @Valid @RequestBody Location location) {
    return new ResponseEntity<>(characterService.addCharacterLocation(characterId, location), HttpStatus.OK);
  }
  
  @DeleteMapping("/{characterId}/location/{locationId}")
  public ResponseEntity<HttpStatus> removeCharacterLocation(@PathVariable Long characterId, @PathVariable Long locationId) {
    characterService.removeCharacterlocation(characterId, locationId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
  
  @GetMapping("/{characterId}/episode/all")
  public ResponseEntity<List<EpisodeDTO>> getCharactersEpisodes(@PathVariable Long characterId) {
    return new ResponseEntity<>(characterService.getCharacterEpisodes(characterId), HttpStatus.OK);
  }
  
  @PutMapping("/{characterId}/episode")
  public ResponseEntity<Characters> addEpisode(@PathVariable Long characterId, @Valid @RequestBody Episode episode) {
    return new ResponseEntity<>(characterService.addEpisode(episode, characterId), HttpStatus.OK);
  }
  
  @DeleteMapping("/{characterId}/episode/{episodeId}")
  public ResponseEntity<HttpStatus> removeEpisode(@PathVariable Long characterId, @PathVariable Long episodeId) {
    characterService.removeEpisode(characterId, episodeId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
