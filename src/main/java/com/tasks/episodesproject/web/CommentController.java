package com.tasks.episodesproject.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tasks.episodesproject.dto.CommentDTO;
import com.tasks.episodesproject.exceptions.ErrorResponse;
import com.tasks.episodesproject.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;


@Tag(name = "Comment Controller", description = "Retrieve Comments")
@AllArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {
  
  CommentService commentService;
  
  
  // Get Request. Get all Comments
  @Operation(
      summary = "Get all Comments from the database.",
      description = "Get all Comments from the database. The Comments returned are sorted by 'created-date' in descending order. The request returns a list of DTO Objects.",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Successfull Request",
              content = {
                  @Content(
                      mediaType="application/json",
                      array=@ArraySchema(schema=@Schema(implementation=CommentDTO.class))
                      )  
      }
      )
    }
  )
  @GetMapping("/all")
  public ResponseEntity<List<CommentDTO>> getAllComments() {
    return new ResponseEntity<>(commentService.getAllComments(), HttpStatus.OK);
  }
  
  
  // Get Request. Get single Comment
  @Operation(
      summary = "Get a specified Comment from the data base. Specify by ID.",
      description = "Get a single Comment. The request must contain an id as a parameter-path-variable. The request returns the respective Comment object.",
      responses = {
          @ApiResponse(
              responseCode="200",
              description = "Successful Request. Comment exists.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=CommentDTO.class)
                      )
      }
          ),
          @ApiResponse(
              responseCode="404",
              description = "Unsuccessful Request. Comment does not exist.",
              content = {
                  @Content(
                      mediaType="application/json",
                      schema=@Schema(implementation=ErrorResponse.class)
                      )
      }
      )
    }
  )
  @GetMapping("/{commentId}")
  public ResponseEntity<CommentDTO> getComment(@PathVariable Long commentId) {
    return new ResponseEntity<>(commentService.getComment(commentId), HttpStatus.OK);
  }
  
}
