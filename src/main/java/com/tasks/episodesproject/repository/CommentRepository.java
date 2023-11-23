package com.tasks.episodesproject.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tasks.episodesproject.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
  List<Comment> findByEpisodeId(Long episodeId, Sort sort);
}
