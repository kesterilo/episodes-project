package com.tasks.episodesproject.enums;

public enum SortFieldEnum {
  NAME("firstName"),
  GENDER("gender");
  
  private String sortParameter;
  
  private SortFieldEnum(String sortParameter) {
    this.sortParameter = sortParameter;
  }
  
  public String getSortParameter() {
    return sortParameter;
  }
}
