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
import com.tasks.episodesproject.service.EpisodeService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
@RequestMapping("/episode")
public class EpisodeController {
  
  EpisodeService episodeService;
  
  
  @GetMapping("/all")
  public ResponseEntity<List<EpisodeDTO>> getAllEpisodes() {
    return new ResponseEntity<>(episodeService.getAllEpisodes(), HttpStatus.OK);
  }
  
  @GetMapping("/{episodeId}")
  public ResponseEntity<EpisodeDTO> getEpisode(@PathVariable Long episodeId) {
    return new ResponseEntity<>(episodeService.getEpisode(episodeId), HttpStatus.OK);
  }
  
  @PostMapping
  public ResponseEntity<EpisodeDTO> createEpisode(@Valid @RequestBody Episode episode) {
    return new ResponseEntity<>(episodeService.createEpisode(episode), HttpStatus.CREATED);
  }
  
  @PutMapping("/{episodeId}")
  public ResponseEntity<EpisodeDTO> updateEpisode(@Valid @RequestBody Episode episode, @PathVariable Long episodeId) {
    return new ResponseEntity<>(episodeService.updateEpisode(episode, episodeId), HttpStatus.CREATED);
  }
  
  @DeleteMapping("/{episodeId}")
  public ResponseEntity<HttpStatus> deleteEpisode(@PathVariable Long episodeId) {
    episodeService.deleteEpisode(episodeId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
  @GetMapping("/{episodeId}/character/all")
  public ResponseEntity<Set<Characters>> getEpisodeCharacters(@PathVariable Long episodeId) {
    return new ResponseEntity<>(episodeService.getEpisodeCharacters(episodeId), HttpStatus.OK);
  }
  
  @PutMapping("/{episodeId}/character")
  public ResponseEntity<EpisodeDTO> addCharacter(@PathVariable Long episodeId, @Valid @RequestBody Characters character) {
    return new ResponseEntity<>(episodeService.addCharacter(episodeId, character), HttpStatus.OK);
  }
  
  @DeleteMapping("/{episodeId}/character/{characterId}")
  public ResponseEntity<HttpStatus> removeCharacter(@PathVariable Long episodeId, @PathVariable Long characterId) {
    episodeService.removeCharacter(episodeId, characterId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
  
  @GetMapping("{episodeId}/comment/all")
  public ResponseEntity<List<CommentDTO>> getEpisodeComments(@PathVariable Long episodeId) {
    return new ResponseEntity<>(episodeService.getEpisodeComments(episodeId), HttpStatus.OK);
  }
  
  @PutMapping("/{episodeId}/comment")
  public ResponseEntity<EpisodeDTO> addComment(@PathVariable Long episodeId, @Valid @RequestBody Comment comment) {
    return new ResponseEntity<>(episodeService.addComment(episodeId, comment), HttpStatus.OK);
  }
  
  @DeleteMapping("/{episodeId}/comment/{commentId}")
  public ResponseEntity<HttpStatus> removeComment(@PathVariable Long episodeId, @PathVariable Long commentId) {
    episodeService.removeComment(episodeId, commentId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
