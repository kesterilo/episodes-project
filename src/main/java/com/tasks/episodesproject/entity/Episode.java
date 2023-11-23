package com.tasks.episodesproject.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "episodes")
public class Episode implements Entities{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false)
  private long id;
  
  @NotBlank(message = "Add Episode name")
  @NonNull
  @Column(nullable = false)
  private String name;
  
  @NotNull(message = "Add Release date")
  @NonNull
  @Column(nullable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
  private LocalDate releaseDate;
  
  @NotBlank(message = "Add Episode Code")
  @NonNull
  @Column(nullable = false, unique = true)
  private String episodeCode;
  
  @ManyToMany(mappedBy = "episodes", cascade = CascadeType.PERSIST)
  private Set<Characters> characters;
  
  @OneToMany(mappedBy = "episode", cascade = CascadeType.ALL) 
  private List<Comment> episodeComments;
  
  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime created;
  
  
  @Override
  public int hashCode() {
    return (getEpisodeCode() + getName()).hashCode();
  }
  
  @Override
  public boolean equals(Object episode) {
    return getEpisodeCode().equals(((Episode) episode).getEpisodeCode());
  }
  
}
