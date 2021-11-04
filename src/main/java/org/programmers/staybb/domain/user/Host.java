package org.programmers.staybb.domain.user;

import com.sun.istack.NotNull;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.domain.room.Room;

@Getter
@Entity
public class Host {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Column(columnDefinition = "TINYINT default false", length = 1)
    private boolean is_superHost;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private User user;

    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<>();

    protected Host() {
    }

    @Builder
    public Host(User user) {
        this.is_superHost = false;
        this.user = user;
    }

    public void beSuperHost() {
        this.is_superHost = true;
    }
}
