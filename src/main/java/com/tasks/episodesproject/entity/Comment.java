package com.tasks.episodesproject.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment implements Entities{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;
  
  @Size(max = 250)
  @NotBlank(message = "Add Comment")
  @NonNull
  @Column(nullable = false)
  private String comment;
  
  @NotBlank(message = "Add Location")
  @NonNull
  @Column(nullable = false)
  private String ipAddressLocation;
  
  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime created;
  
  @JsonIgnore
  @ManyToOne(optional = false)
  @JoinColumn(name = "episode_id", referencedColumnName = "id")
  private Episode episode;
 
}
