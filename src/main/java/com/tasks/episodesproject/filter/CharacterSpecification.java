package com.tasks.episodesproject.filter;

import org.springframework.data.jpa.domain.Specification;

import com.tasks.episodesproject.entity.Characters;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CharacterSpecification {
  
  public static Specification<Characters> filterBy(CharacterFilter characterFilter) {
    return Specification
        .where(hasGender(characterFilter.getGender()))
        .and(hasStatus(characterFilter.getStatus()));
  }
  
  
  private static Specification<Characters> hasGender(String gender) {
    return ((root, query, cb) -> gender == null || gender.isEmpty() ? cb.conjunction()
        : cb.equal(root.get("gender"), gender));
  }
  
  private static Specification<Characters> hasStatus(String status) {
    return ((root, query, cb) -> status == null || status.isEmpty() ? cb.conjunction()
        : cb.equal(root.get("status"), status));
  }
}
