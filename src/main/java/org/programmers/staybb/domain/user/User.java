package org.programmers.staybb.domain.user;

import com.sun.istack.NotNull;
import java.lang.reflect.Field;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.global.BaseTimeEntity;

@Getter
@Table(name = "user")
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Column(length = 30)
    private String name;

    @NotNull
    @Column(name = "date_of_birth")
    private LocalDate birthday;

    @NotNull
    @Column(length = 30)
    private String email;

    @NotNull
    @Column(length = 30)
    private String phoneNumber;

    @Lob
    private String bio;

    protected User() {
    }

    @Builder
    public User(String name, LocalDate birthday, String email, String phoneNumber, String bio) {
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.bio = bio;
    }

    public void setField(String fieldToChange, Object value)
        throws IllegalAccessException, NoSuchFieldException {
        Field field = this.getClass().getDeclaredField(fieldToChange);
        field.set(this, value);
    }

}
