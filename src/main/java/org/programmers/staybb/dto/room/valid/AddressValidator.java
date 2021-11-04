package org.programmers.staybb.dto.room.valid;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.programmers.staybb.domain.room.Address;

public class AddressValidator implements ConstraintValidator<AddressValid, Address> {

    @Override
    public boolean isValid(Address address, ConstraintValidatorContext constraintValidatorContext) {

        if (!Pattern.matches("^[가-힣]+시$", address.getRegion())) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                "시 단위로 입력하세요.").addConstraintViolation();
            return false;
        }

        //시 구(군) 동(읍) regex 작성필요
        if (address.getAddress().isBlank()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                "주소를 입력하세요.").addConstraintViolation();
            return false;
        }

        if (address.getDetailAddress().isBlank()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("상세주소를 입력하세요.")
                .addConstraintViolation();
            return false;
        }

        return true;
    }
}
