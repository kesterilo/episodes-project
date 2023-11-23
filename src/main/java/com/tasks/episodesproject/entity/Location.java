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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "locations")
public class Location implements Entities{
  
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;
  
  @NotBlank(message = "Add Location name")
  @NonNull
  @Column(nullable = false)
  private String name;
  
  @NotNull(message = "Add Latitude")
  @NonNull
  @Column(nullable = false)
  private double latitude;
  
  @NotNull(message = "Add Longitude")
  @NonNull
  @Column(nullable = false)
  private double longitude;
  
  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime created;
  
  @JsonIgnore
  @OneToOne(optional = false)
  @JoinColumn(name = "characters_id", referencedColumnName = "id")
  private Characters character;

}
