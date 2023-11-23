package com.tasks.episodesproject.service;

import java.util.List;
import java.util.Set;

import com.tasks.episodesproject.dto.CommentDTO;
import com.tasks.episodesproject.dto.EpisodeDTO;
import com.tasks.episodesproject.entity.Characters;
import com.tasks.episodesproject.entity.Comment;
import com.tasks.episodesproject.entity.Episode;

public interface EpisodeService {
  
  List<EpisodeDTO> getAllEpisodes();

  EpisodeDTO getEpisode(Long id);

  EpisodeDTO createEpisode(Episode episode);

  EpisodeDTO updateEpisode(Episode episode, Long episodeId);

  void deleteEpisode(Long id);

  Set<Characters> getEpisodeCharacters(Long id);
  
  EpisodeDTO addCharacter(Long id, Characters character);
  
  void removeCharacter(Long episodeId, Long characterId);
  
  List<CommentDTO> getEpisodeComments(Long id);
  
  EpisodeDTO addComment(Long id, Comment comment);

  void removeComment(Long episodeId, Long commentId);
  
}
