package com.tasks.episodesproject.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tasks.episodesproject.dto.CommentDTO;
import com.tasks.episodesproject.service.CommentService;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {
  
  CommentService commentService;
  
  
  @GetMapping("/all")
  public ResponseEntity<List<CommentDTO>> getAllComments() {
    return new ResponseEntity<>(commentService.getAllComments(), HttpStatus.OK);
  }
  
  @GetMapping("/{commentId}")
  public ResponseEntity<CommentDTO> getComment(@PathVariable Long commentId) {
    return new ResponseEntity<>(commentService.getComment(commentId), HttpStatus.OK);
  }
  
}
