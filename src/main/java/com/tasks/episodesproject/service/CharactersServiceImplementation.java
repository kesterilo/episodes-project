package com.tasks.episodesproject.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.tasks.episodesproject.dto.EpisodeDTO;
import com.tasks.episodesproject.dto.EpisodeDTOMapper;
import com.tasks.episodesproject.entity.Characters;
import com.tasks.episodesproject.entity.Episode;
import com.tasks.episodesproject.entity.Location;
import com.tasks.episodesproject.filter.queryFilter;
import com.tasks.episodesproject.filter.CharacterSpecification;
import com.tasks.episodesproject.repository.CharactersRepository;
import com.tasks.episodesproject.repository.EpisodeRepository;
import com.tasks.episodesproject.repository.LocationRepository;
import com.tasks.episodesproject.util.Utility;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CharactersServiceImplementation implements CharactersService {
  
  private CharactersRepository characterRepository;
  private EpisodeRepository episodeRepository;
  private LocationRepository locationRepository;
  
  
  
  // Returns an Episode DTO-Mapper class
  public EpisodeDTOMapper getEpisodeDTOMapper() {
    return new EpisodeDTOMapper();
  }
  
  // Helper method
  public List<EpisodeDTO> transformEpisodes(Set<Episode> episodes) {
    return episodes
    .stream()
    .map(getEpisodeDTOMapper())
    .collect(Collectors.toList());
  }
  
  // Helper method
  public List<EpisodeDTO> sortEpisodes(List<EpisodeDTO> episodes) {
    Collections.sort(episodes, (episodeA, episodeB) -> episodeA.episode().getReleaseDate().compareTo(episodeB.episode().getReleaseDate()));
    return episodes;
  }
  
  // Helper method
  public Characters saveCharacterEpisode(Characters character, Episode episode) {
    character.getEpisodes().add(episode);
    return createCharacter(character);
  }
  
  // Helper method
  public Characters findCharacterByFirstNameAndLastName(String firstname, String lastname) {
    Characters character = characterRepository.findByFirstNameAndLastName(firstname, lastname);
    if (character == null) throw new RuntimeException("No Character with such names exists");
    return character;
  }
  
  // Helper method
  public Set<Episode> searchForEpisodesByCharacterFirstnameAndLastname(String firstname, String lastname) {
    Characters character = findCharacterByFirstNameAndLastName(firstname, lastname);
    return character.getEpisodes();
  }
  
  // Helper method
  public List<Characters> findCharacterByFirstnameOrLastname(String name) {
    List<Characters> characters = characterRepository.findByFirstName(name);
    if (characters == null || characters.size() == 0) characters = characterRepository.findByLastName(name);
    if (characters == null || characters.size() == 0) throw new RuntimeException("Character does not exist");
    return characters;
  }
  
  
  @Override
  public List<Characters> getAllCharacters(queryFilter characterFilter, Sort sort) {
    if (!(characterFilter.getLocation() == null)) {
      Location location = locationRepository.findByName(characterFilter.getLocation());
      return Arrays.asList(location.getCharacter());
    }
    Specification<Characters> characterSpec = CharacterSpecification.filterBy(characterFilter);
    return characterRepository.findAll(characterSpec, sort);
  }
  
  @Override
  public Characters getCharacter(Long id) {
    Optional<Characters> character = characterRepository.findById(id);
    return (Characters)Utility.unwrapEntity(character, id, Characters.class);
  }
  
  @Override
  public Characters createCharacter(Characters character) {
    return characterRepository.save(character);
  }

  @Override
  public Characters updateCharacter(Characters character, Long characterId) {
    getCharacter(characterId); // Checks if Entity exists
    character.setCharacterId(characterId);
    return createCharacter(character);
  }

  @Override
  public void deleteCharacter(Long id) {
    getCharacter(id); // Checks if Entity exists
    characterRepository.deleteById(id);
  }

  @Override
  public Location getCharacterLocation(Long id) {
    Characters character = getCharacter(id);
    return character.getLocation();
  }

  @Override
  public Characters addCharacterLocation(Long id, Location location) {
    Characters character = getCharacter(id);
    location.setCharacter(character);
    character.setLocation(location);
    return createCharacter(character);
  }

  @Override
  public void removeCharacterlocation(Long characterId, Long locationId) {
    Characters character = getCharacter(characterId);
    Utility.unwrapEntity(locationRepository.findById(locationId), locationId, Location.class); // Checks if Entity exists
    character.setLocation(null);
    characterRepository.save(character);
    locationRepository.deleteById(locationId);
  }

  @Override
  public List<EpisodeDTO> getCharacterEpisodes(Long id) {
    Characters character = getCharacter(id);
    List<EpisodeDTO> episodes = transformEpisodes(character.getEpisodes());
    return sortEpisodes(episodes);
  }

  @Override
  public Characters addEpisode(Episode episode, Long id) {
    Characters character = getCharacter(id);
    Episode checkEpisode = episodeRepository.findByEpisodeCode(episode.getEpisodeCode());
    if (checkEpisode == null) {
      Episode newEpisode = episodeRepository.save(episode);
      return saveCharacterEpisode(character, newEpisode);
    }
    return saveCharacterEpisode(character, checkEpisode);
  }

  @Override
  public void removeEpisode(Long charactersId, Long episodeId) {
    Characters characters = getCharacter(charactersId);
    Episode episode = (Episode) Utility.unwrapEntity(episodeRepository.findById(episodeId), episodeId, Episode.class);
    characters.getEpisodes().remove(episode);
    characterRepository.save(characters);
  }
  
  //Search: Method for searching the Characters table by First Name, Last Name or both Names and returning the Episodes the Character featured in
  @Override
  public List<List<EpisodeDTO>> findEpisodesByCharacterName(String name) {
    String[] names = name.split(" ");
    String firstName = names[0];
    String lastName = (names.length > 1 ? names[1] : null);   
    // Searching by full name  (firstname and lastname)
    if (!(firstName == null) && !(lastName == null)) {
      List<EpisodeDTO> episodes = transformEpisodes(searchForEpisodesByCharacterFirstnameAndLastname(firstName, lastName));
      sortEpisodes(episodes); // Sort episodes in ascending order by release-dates
      List<List<EpisodeDTO>> episodesList = new ArrayList<>();
      episodesList.add(episodes);
      return episodesList;
      }
    // Searching by firstname or lastname (not both)
    List<Characters> characters = findCharacterByFirstnameOrLastname(firstName);
    List<List<EpisodeDTO>> episodesList = new ArrayList<>();
    for (Characters character : characters) {
      List<EpisodeDTO> episodes = transformEpisodes(character.getEpisodes());
      sortEpisodes(episodes); // Sort episodes in ascending order by release-dates
      episodesList.add(episodes);
    }
    return episodesList;
  }
}