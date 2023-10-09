package com.tasks.episodesproject.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "episodes")
public class Episode {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;
  
  @NonNull
  @Column(nullable = false)
  private String name;
  
  @NonNull
  @Column(nullable = false)
  private LocalDateTime releaseDate;
  
  @NonNull
  @Column(nullable = false)
  private String episodeCode;
  
  @JsonIgnore
  @ManyToMany
  @JoinTable(
    name = "character_episode",
    joinColumns = @JoinColumn(name = "episode_id",
      referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "character_id",
      referencedColumnName = "id")  
  )
  private Set<Character> characters;
  
  @Column
  @OneToMany(mappedBy = "episode", cascade = CascadeType.ALL)
  private Set<Comment> episodeComments;
  
  @NonNull
  @Column(nullable = false)
  private LocalDateTime created;
}
