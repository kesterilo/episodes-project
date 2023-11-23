package com.tasks.episodesproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tasks.episodesproject.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

  Location findByName(String location);
  
}
