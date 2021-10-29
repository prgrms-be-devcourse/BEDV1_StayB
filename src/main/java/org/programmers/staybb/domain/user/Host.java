package org.programmers.staybb.domain.user;

import com.sun.istack.NotNull;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.domain.room.Room;

@Entity
@Getter
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

    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<>();

    protected Host() {
    }

    @Builder
    public Host(boolean is_superHost, User user) {
        this.is_superHost = is_superHost;
        this.user = user;
    }

}
