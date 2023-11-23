package com.tasks.episodesproject.dto;

import java.util.function.Function;

import com.tasks.episodesproject.entity.Episode;

public class EpisodeDTOMapper implements Function<Episode, EpisodeDTO> {

  @Override
  public EpisodeDTO apply(Episode episode) {
    return new EpisodeDTO(episode, episode.getEpisodeComments() == null ? 0 : episode.getEpisodeComments().size());
  }
  
}
