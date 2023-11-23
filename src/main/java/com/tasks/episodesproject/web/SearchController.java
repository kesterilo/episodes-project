package com.tasks.episodesproject.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tasks.episodesproject.dto.EpisodeDTO;
import com.tasks.episodesproject.service.CharactersService;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Controller
@RequestMapping("/search")
public class SearchController {
  
  private CharactersService characterService;
  
  @GetMapping("/name")
  public ResponseEntity<List<List<EpisodeDTO>>> searchCharacterNames(@RequestParam String name) {
    return new ResponseEntity<>(characterService.findEpisodesByCharacterName(name), HttpStatus.OK);
  }
}
