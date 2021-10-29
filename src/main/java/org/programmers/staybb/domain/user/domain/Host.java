package org.programmers.staybb.domain.user.domain;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class Host {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotNull
    @Column(columnDefinition = "TINYINT", length = 1)
    private boolean is_superHost = false;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private User user;

}
