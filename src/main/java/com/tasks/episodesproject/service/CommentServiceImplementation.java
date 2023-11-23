package com.tasks.episodesproject.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tasks.episodesproject.dto.CommentDTO;
import com.tasks.episodesproject.dto.CommentDTOMapper;
import com.tasks.episodesproject.entity.Comment;
import com.tasks.episodesproject.repository.CommentRepository;
import com.tasks.episodesproject.util.Utility;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CommentServiceImplementation implements CommentService {
  
  CommentRepository commentRepository;
  
  // Returns a Comment DTO-Mapper class
  public CommentDTOMapper getCommentDTOMapper() {
    return new CommentDTOMapper();
  }
  
  
  @Override
  public List<CommentDTO> getAllComments() {
    return commentRepository.findAll(Sort.by(Sort.Direction.DESC, "created"))
        .stream()
        .map(getCommentDTOMapper())
        .collect(Collectors.toList());
  }
  
  @Override
  public CommentDTO getComment(Long id) {
    Optional<Comment> comment = commentRepository.findById(id);
    Comment unwrappedComment = (Comment) Utility.unwrapEntity(comment, id, Comment.class);
    return getCommentDTOMapper().apply(unwrappedComment);
  }
}
