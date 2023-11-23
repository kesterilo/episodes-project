package com.tasks.episodesproject.dto;

import java.util.function.Function;

import com.tasks.episodesproject.entity.Comment;

public class CommentDTOMapper implements Function<Comment, CommentDTO>{

  @Override
  public CommentDTO apply(Comment comment) {
    return new CommentDTO(comment.getComment(), comment.getIpAddressLocation(), comment.getCreated());
  }
  
}
