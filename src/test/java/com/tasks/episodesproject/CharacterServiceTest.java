package com.tasks.episodesproject;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.tasks.episodesproject.dto.EpisodeDTO;
import com.tasks.episodesproject.entity.Characters;
import com.tasks.episodesproject.entity.Episode;
import com.tasks.episodesproject.repository.CharactersRepository;
import com.tasks.episodesproject.repository.EpisodeRepository;
import com.tasks.episodesproject.service.CharactersServiceImplementation;

@RunWith(MockitoJUnitRunner.class)
public class CharacterServiceTest {
  
  @Mock
  private EpisodeRepository episodeRepository;
  @Mock
  private CharactersRepository characterRepository;
  
  @InjectMocks
  private CharactersServiceImplementation characterService;
  
  
  @Test
  public void findCharacterByFirstNameOrLastName() {
    List<Characters> characters = Arrays.asList(new Characters("mike", "banning", "ACTIVE", "MALE"));
    
    when(characterRepository.findByFirstName("mike")).thenReturn(null);
    when(characterRepository.findByLastName("mike")).thenReturn(characters);
    
    List<Characters> returnedCharacters = characterService.findCharacterByFirstnameOrLastname("mike");
    
    assertEquals("mike", returnedCharacters.get(0).getFirstName());
    
  }
  
  @Test
  public void addEpisodeTest() {
    Characters character = new Characters("mike", "banning", "ACTIVE", "MALE");
    character.setEpisodes(new HashSet<>());
    Episode episode = new Episode("episode1", LocalDate.of(2015, 02, 02), "ep01");

    when(characterRepository.findById(1L)).thenReturn(Optional.of(character));
    when(episodeRepository.findByEpisodeCode("ep01")).thenReturn(episode);
    // when(episodeRepository.findByEpisodeCode("ep01")).thenReturn(null);
    
    characterService.addEpisode(episode, 1L);
    
    // verify(episodeRepository, times(1)).save(episode);
    verify(characterRepository, times(1)).save(character);
  }
  
  @Test
  public void findEpisodesByCharacterNameTest() {
    
    Characters character = new Characters("mike", "banning", "ACTIVE", "MALE");
    Episode episode1 = new Episode("episode1", LocalDate.of(2015, 02, 02), "ep01");
    Episode episode2 = new Episode("episode2", LocalDate.of(2017, 03, 03), "ep02");
    Set<Episode> episodeSet = new HashSet<>();
    episodeSet.add(episode1);
    episodeSet.add(episode2);
    character.setEpisodes(episodeSet);
    
    when(characterRepository.findByFirstNameAndLastName("mike", "banning")).thenReturn(character);
    
    when(characterRepository.findByFirstName("mike")).thenReturn(Arrays.asList(character));
    
    List<List<EpisodeDTO>> episodesFirstOrLastName = characterService.findEpisodesByCharacterName("mike");
    List<List<EpisodeDTO>> episodesBothNames = characterService.findEpisodesByCharacterName("mike banning");
    
    assertEquals(2, episodesFirstOrLastName.get(0).size());
    assertEquals(2, episodesBothNames.get(0).size());
  }
}
