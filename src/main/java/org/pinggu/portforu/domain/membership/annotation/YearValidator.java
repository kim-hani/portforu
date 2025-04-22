package org.pinggu.portforu.domain.membership.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Instant;
import java.time.ZoneId;

public class YearValidator implements ConstraintValidator<YearMinNow, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) return true;

        int currentYear = Instant.now()
                .atZone(ZoneId.of("Asia/Seoul"))
                .getYear();

        return value >= currentYear;
    }
}
