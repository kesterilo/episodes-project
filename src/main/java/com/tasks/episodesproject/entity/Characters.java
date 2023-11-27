package com.tasks.episodesproject.entity;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tasks.episodesproject.validation.Gender;
import com.tasks.episodesproject.validation.Status;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "characters", uniqueConstraints = 
  @UniqueConstraint(columnNames = {"last_name", "first_name"})
)
public class Characters implements Entities{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false)
  private long characterId;
  
  @NotBlank(message = "First name cannot be blank")
  @NonNull
  @Column(name = "first_name", nullable = false)
  private String firstName;
    
  @NotBlank(message = "Last name cannot be blank")
  @NonNull
  @Column(name = "last_name", nullable = false)
  private String lastName;
  
  @Status(message = "Status must be ACTIVE, DEAD OR UNKNOWN. In capital letters.")
  @NotBlank(message = "Character must have Status")
  @NonNull
  @Column(nullable = false)
  private String status;
  
  @Column
  private String stateOfOrigin;
  
  @Gender(message = "Gender must be MALE OR FEMALE. In capital letters.")
  @NotBlank(message = "Character must have Gender")
  @NonNull
  @Column(nullable = false)
  private String gender;
  
  @OneToOne(mappedBy = "character", cascade = CascadeType.ALL) 
  private Location location;
  
  @JsonIgnore
  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(
    name = "characters_episodes",
    joinColumns = @JoinColumn(name = "character_id",
      referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "episode_id",
      referencedColumnName = "id")
  )
  private Set<Episode> episodes;
  
  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime created;
  
  
  @Override
  public int hashCode() {
    return (getFirstName() + getLastName() + getGender() + getStateOfOrigin()).hashCode();
  }
  
  @Override
  public boolean equals(Object object) {
    return getFirstName().equals(((Characters) object).getFirstName()) && getLastName().equals(((Characters) object).getLastName());
  }
  
}
