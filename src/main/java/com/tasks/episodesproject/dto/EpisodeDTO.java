package com.tasks.episodesproject.dto;

import com.tasks.episodesproject.entity.Episode;

public record EpisodeDTO(
  Episode episode,
  int commentsCount
) {
  
}
