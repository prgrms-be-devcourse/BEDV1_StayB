package org.programmers.staybb.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.domain.user.User;

@Getter
@Builder
public class UserRequest {

    @NotEmpty
    private final String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    @NotNull(message = "생년월일을 입력해주세요.")
    @Past(message = "생년월일을 다시 입력해주세요.")
    private final LocalDate birthday;

    @NotBlank(message = "메일을 작성해주세요.")
    @Email(message = "메일의 양식을 지켜주세요.")
    private final String email;

    @NotBlank(message = "전화번호를 작성해주세요")
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 양식과 맞지 않습니다. 01x-xxx(x)-xxxx")
    private final String phoneNumber;

    private final String bio;

    public User toEntity() {
        return User.builder()
            .name(this.name)
            .birthday(this.birthday)
            .email(this.email)
            .phoneNumber(this.phoneNumber)
            .bio(this.bio)
            .build();
    }

}
