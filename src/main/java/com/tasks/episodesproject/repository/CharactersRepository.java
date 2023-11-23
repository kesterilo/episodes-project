package com.tasks.episodesproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tasks.episodesproject.entity.Characters;

public interface CharactersRepository extends JpaRepository<Characters, Long>, JpaSpecificationExecutor<Characters> {
  
  @Override
  List<Characters> findAll();

  Characters findByFirstNameAndLastName(String firstName, String lastName);

  List<Characters> findByFirstName(String firstName);

  List<Characters> findByLastName(String lastName);
}
