package com.doggyApp.registry.validation;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {

    private String region;

    private final PhoneNumberUtil phoneUtil =
            PhoneNumberUtil.getInstance();

    @Override
    public void initialize(ValidPhone constraintAnnotation) {
        this.region = constraintAnnotation.region();
    }

    @Override
    public boolean isValid(String value,
                           ConstraintValidatorContext context) {

        if (value == null || value.isBlank()) {
            return false;
        }

        try {
            var number = phoneUtil.parse(value, region);
            return phoneUtil.isValidNumber(number);

        } catch (NumberParseException e) {
            return false;
        }
    }
}