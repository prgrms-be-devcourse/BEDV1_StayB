package org.programmers.staybb.domain.room;

import com.sun.istack.NotNull;
import java.lang.reflect.Field;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.domain.user.Host;
import org.programmers.staybb.global.BaseTimeEntity;

@Getter
@Table(name = "room")
@Entity
public class Room extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Column(length = 100)
    private String roomName;

    @Embedded
    private Address address;

    @NotNull
    @Column(name = "max_guest")
    private int maxGuest;

    @Embedded
    private Option option;

    @Lob
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id", referencedColumnName = "id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Host host;

    @NotNull
    private int price;

    protected Room() {
    }

    @Builder
    public Room(String roomName, Address address, int maxGuest, Option option, String description,
        Host host, int price) {
        this.roomName = roomName;
        this.address = address;
        this.maxGuest = maxGuest;
        this.option = option;
        this.description = description;
        addRoom(host);
        this.price = price;
    }

    public void addRoom(Host host) {
        host.getRooms().add(this);
        this.host = host;
    }

    public void setField(String fieldToChange, Object value)
        throws IllegalAccessException, NoSuchFieldException {
        Field field = this.getClass().getDeclaredField(fieldToChange);
        field.set(this, value);
    }

}
