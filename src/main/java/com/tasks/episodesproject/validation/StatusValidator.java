package com.tasks.episodesproject.validation;

import java.util.Arrays;
import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StatusValidator implements ConstraintValidator<Status, String>{

  List<String> status = Arrays.asList(
    "ACTIVE", "DEAD", "UNKNOWN"
  );
  
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null)
      return false;
    for (String string : status) {
      if (value.equals(string))
        return true;
    }
    return false;
  }
  
}
