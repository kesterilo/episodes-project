package com.tasks.episodesproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tasks.episodesproject.entity.Episode;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {

  Episode findByEpisodeCode(String episodeCode);

}