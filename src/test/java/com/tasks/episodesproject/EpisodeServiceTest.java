package com.tasks.episodesproject;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.tasks.episodesproject.entity.Characters;
import com.tasks.episodesproject.entity.Episode;
import com.tasks.episodesproject.repository.CharactersRepository;
import com.tasks.episodesproject.repository.EpisodeRepository;
import com.tasks.episodesproject.service.EpisodeServiceImplementation;

@RunWith(MockitoJUnitRunner.class)
public class EpisodeServiceTest {
    
  @Mock
  private EpisodeRepository episodeRepository;
  @Mock
  private CharactersRepository characterRepository;
  
  @InjectMocks
  private EpisodeServiceImplementation episodeService;
  
  
  @Test
  public void addCharacterTest() {
    Characters character = new Characters("mike", "banning", "ACTIVE", "MALE");
    
    Episode episode = new Episode("episode1", LocalDate.of(2015, 02, 02), "ep01");
    
    when(episodeRepository.findById(1L)).thenReturn(Optional.of(episode));
    when(characterRepository.findByFirstNameAndLastName("mike", "banning")).thenReturn(character);
    
    episodeService.addCharacter(1L, character);
    
    verify(characterRepository, times(1)).save(character);
  }
}
