package com.tasks.episodesproject.filter;

import org.springframework.stereotype.Component;

import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class queryFilter {
  private String gender;
  private String status;
  private String location;
  
}
