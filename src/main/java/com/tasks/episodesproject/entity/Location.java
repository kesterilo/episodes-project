package com.tasks.episodesproject.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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
@Table(name = "locations")
public class Location {
  
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;
  
  @NonNull
  @Column(nullable = false)
  private String name;
  
  @NonNull
  @Column(nullable = false)
  private double latitude;
  
  @NonNull
  @Column(nullable = false)
  private double longitude;
  
  @NonNull
  @Column(nullable = false)
  private LocalDateTime created;
  
  @JsonIgnore
  @OneToOne(mappedBy = "location", cascade = CascadeType.ALL)
  private Character character;
}
