package com.tasks.episodesproject.validation;

import java.util.Arrays;
import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<Gender, String> {
  
  List<String> genders = Arrays.asList(
    "MALE", "FEMALE"
  );

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null)
      return false;
    for (String gender : genders) {
      if (value.equals(gender))
        return true;
    }
    return false;
  }
  
}
