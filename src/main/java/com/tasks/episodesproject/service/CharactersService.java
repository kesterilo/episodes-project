package com.tasks.episodesproject.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.tasks.episodesproject.dto.EpisodeDTO;
import com.tasks.episodesproject.entity.Characters;
import com.tasks.episodesproject.entity.Episode;
import com.tasks.episodesproject.entity.Location;
import com.tasks.episodesproject.filter.CharacterFilter;

public interface CharactersService {

  List<Characters> getAllCharacters(CharacterFilter filter, Sort sort);

  Characters getCharacter(Long id);

  Characters createCharacter(Characters character);

  Characters updateCharacter(Characters character, Long characterId);

  void deleteCharacter(Long id);

  Location getCharacterLocation(Long id);
  
  Characters addCharacterLocation(Long id, Location location);

  void removeCharacterlocation(Long characterId, Long locationId);
  
  List<EpisodeDTO> getCharacterEpisodes(Long id);

  Characters addEpisode(Episode episode, Long id);

  void removeEpisode(Long charactersId, Long episodeId);

  List<List<EpisodeDTO>> findEpisodesByCharacterName(String name);
  
}
