package org.programmers.staybb.domain.room.domain;

import com.sun.istack.NotNull;
import lombok.Builder;
import org.programmers.staybb.domain.user.domain.Host;

import javax.persistence.*;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

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

    public void changeInfo(String roomName, Address address, int maxGuest, Option option,
        String description, int price) {
        this.roomName = roomName;
        this.address = address;
        this.maxGuest = maxGuest;
        this.option = option;
        this.description = description;
        this.price = price;
    }

    public void addRoom(Host host) {
        host.getRooms().add(this);
        this.host = host;
    }

}
