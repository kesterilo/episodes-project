package com.tasks.episodesproject.service;

import java.util.List;

import com.tasks.episodesproject.dto.CommentDTO;

public interface CommentService {

  List<CommentDTO> getAllComments();

  CommentDTO getComment(Long id);
  
}
