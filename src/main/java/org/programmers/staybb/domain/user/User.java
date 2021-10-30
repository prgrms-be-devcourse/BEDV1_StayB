package org.programmers.staybb.domain.user;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programmers.staybb.global.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user")
@Getter
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

    @NotNull
    @Column(columnDefinition = "TINYINT default false")
    private boolean isDeleted;

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

    public void ChangeInfo(String name, LocalDate birthday, String email, String phoneNumber,
        String bio) {
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.bio = bio;
    }

    public void deletedUser() {
        this.isDeleted = true;
    }

}
