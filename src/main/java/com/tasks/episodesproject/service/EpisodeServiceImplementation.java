package com.tasks.episodesproject.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tasks.episodesproject.dto.CommentDTO;
import com.tasks.episodesproject.dto.CommentDTOMapper;
import com.tasks.episodesproject.dto.EpisodeDTO;
import com.tasks.episodesproject.dto.EpisodeDTOMapper;
import com.tasks.episodesproject.entity.Characters;
import com.tasks.episodesproject.entity.Comment;
import com.tasks.episodesproject.entity.Episode;
import com.tasks.episodesproject.repository.CharactersRepository;
import com.tasks.episodesproject.repository.CommentRepository;
import com.tasks.episodesproject.repository.EpisodeRepository;
import com.tasks.episodesproject.util.Utility;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EpisodeServiceImplementation implements EpisodeService {
  
  EpisodeRepository episodeRepository;
  CharactersRepository characterRepository;
  CommentRepository commentRepository;
  
  
  // Returns a Comment DTO-Mapper class
  public CommentDTOMapper getCommentDTOMapper() {
    return new CommentDTOMapper();
  }
  
  // Returns an Episode DTO-Mapper class
  public EpisodeDTOMapper getEpisodeDTOMapper() {
    return new EpisodeDTOMapper();
  }
  
  // Helper method
  public List<Episode> getRawEpisodes() {
    return episodeRepository.findAll(Sort.by(Sort.Direction.ASC, "releaseDate"));
  }
  
  // Helper method
  public List<EpisodeDTO> transformEpisodes(List<Episode> episodes) {
    return episodes
        .stream()
        .map(getEpisodeDTOMapper())
        .collect(Collectors.toList());
  }
  
  // Helper method
  private Episode getEpisodeById(Long id) {
    Optional<Episode> episode = episodeRepository.findById(id);
    return (Episode) Utility.unwrapEntity(episode, id, Episode.class);
  }
  
  // Helper method
  public EpisodeDTO transformEpisode(Episode episode) {
    return getEpisodeDTOMapper().apply(episode);
  }
  
  // Helper method
  public Set<Characters> sortCharacters(Set<Characters> characters) {
    Set<Characters> sortedCharacters = new TreeSet<Characters>((characterA, characterB) -> characterA.getFirstName().compareTo(characterB.getFirstName()));
    sortedCharacters.addAll(characters);
    return sortedCharacters;
  }
  
  // Helper method
  public EpisodeDTO saveEpisodeCharacter(Characters character, Episode episode, Long id) {
    if (character.getEpisodes() == null)
      character.setEpisodes(new HashSet<Episode>());
    character.getEpisodes().add(episode);
    characterRepository.save(character);
    episode = getEpisodeById(id);
    return transformEpisode(episode);
  }
  
  // Helper method
  public List<CommentDTO> transformComments(List<Comment> comments) {
    return comments
        .stream()
        .map(getCommentDTOMapper())
        .collect(Collectors.toList());
  }
  
  // Helper method
  public List<Comment> getRawComments(Long episodeId) {
    return commentRepository.findByEpisodeId(episodeId, Sort.by(Sort.Direction.DESC, "created"));
  }
  
  @Override
  public List<EpisodeDTO> getAllEpisodes() {
    return transformEpisodes(getRawEpisodes());
  }
  

  @Override
  public EpisodeDTO getEpisode(Long id) {
    return transformEpisode(getEpisodeById(id));
  }
  
  @Override
  public EpisodeDTO createEpisode(Episode episode) {
    Episode savedEpisode = episodeRepository.save(episode);
    return transformEpisode(savedEpisode);
  }
  
  @Override
  public EpisodeDTO updateEpisode(Episode episode, Long episodeId) {
    getEpisodeById(episodeId); // Checks if the entity exists
    episode.setId(episodeId); 
    return createEpisode(episode);
  }
  
  @Override
  public void deleteEpisode(Long id) {
    getEpisodeById(id); // Checks if the entity exists
    episodeRepository.deleteById(id);
  }
  
  @Override
  public Set<Characters> getEpisodeCharacters(Long id) {
    Episode episode = getEpisodeById(id);
    Set<Characters> characters = episode.getCharacters();
    return sortCharacters(characters);
  }
  
  @Override
  public EpisodeDTO addCharacter(Long id, Characters character) {
    Episode episode = getEpisodeById(id);
    Characters checkCharacter = characterRepository.findByFirstNameAndLastName(character.getFirstName(),character.getLastName());
    if (checkCharacter == null) {
      Characters newCharacter = characterRepository.save(character);
      return saveEpisodeCharacter(newCharacter, episode, id);
    } else {
      return saveEpisodeCharacter(checkCharacter, episode, id);
    }
  }
  
  @Override
  public void removeCharacter(Long episodeId, Long characterId) {
    Characters character = (Characters)Utility.unwrapEntity(characterRepository.findById(characterId), characterId, Character.class);
    Episode episode = getEpisodeById(episodeId);
    character.getEpisodes().remove(episode);
    characterRepository.save(character);
  }
  
  @Override
  public List<CommentDTO> getEpisodeComments(Long episodeId) {
    getEpisodeById(episodeId); // Checks if the entity exists
    return transformComments(getRawComments(episodeId));
  }
  
  @Override
  public EpisodeDTO addComment(Long episodeId, Comment comment) {
    Episode episode = getEpisodeById(episodeId);
    comment.setEpisode(episode);
    episode.getEpisodeComments().add(comment);
    return createEpisode(episode);
  }

  @Override
  public void removeComment(Long episodeId, Long commentId) {
    Episode episode = getEpisodeById(episodeId);
    Comment comment = (Comment)Utility.unwrapEntity(commentRepository.findById(commentId), commentId, Comment.class);
    commentRepository.deleteById(commentId);
    episode.getEpisodeComments().remove(comment);
    episodeRepository.save(episode);
  }
}
