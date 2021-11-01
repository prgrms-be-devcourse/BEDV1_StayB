package org.programmers.staybb.dto.room.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.programmers.staybb.domain.room.Option;

public class OptionValidator implements ConstraintValidator<OptionValid, Option> {

    @Override
    public boolean isValid(Option option, ConstraintValidatorContext constraintValidatorContext) {

        if (option.getBedroomNum() < 1) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    "침실은 최소 1개 이상이어야 합니다.")
                .addConstraintViolation();
            return false;
        }

        if (option.getBedNum() < 1) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    "침실은 최소 1개 이상이어야 합니다.")
                .addConstraintViolation();
            return false;
        }

        if (option.getBathroomNum() < 0.5) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                "욕실 개수는 최소 0.5개 이상이어야 합니다.").addConstraintViolation();
            return false;
        }

        return true;
    }

}
