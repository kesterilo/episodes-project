package com.tasks.episodesproject.entity;

import java.time.LocalDateTime;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "characters")
public class Character {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;
  
  @NonNull
  @Column(nullable = false)
  private String firstName;
  
  @NonNull
  @Column(nullable = false)
  private String lastName;
  
  @NonNull
  @Column(nullable = false)
  private String status;
  
  @Column
  private String stateOfOrigin;
  
  @NonNull
  @Column(nullable = false)
  private String gender;
  
  @OneToOne
  @JoinColumn(name = "location_id", referencedColumnName = "id")
  private Location location;
  
  @JsonIgnore
  @ManyToMany
  @JoinTable(
    name = "episodes_characters",
    joinColumns = @JoinColumn(name = "character_id",
      referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "episode_id",
      referencedColumnName = "id")  
  )
  private Set<Episode> episodes;
  
  @NonNull
  @Column(nullable = false)
  private LocalDateTime created;
  
  
}
