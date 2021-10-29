package org.programmers.staybb.domain.reservation.domain;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;
import org.programmers.staybb.domain.reservation.domain.Guest;
import org.programmers.staybb.domain.room.domain.Room;
import org.programmers.staybb.domain.user.domain.User;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "reservation")
@Getter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotNull
    private LocalTime startDate;

    @NotNull
    private LocalTime endDate;

    @NotNull
    private int totalPrice;

    @NotNull
    @Embedded
    private Guest guest;

    @Lob
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Room room;

    protected Reservation() {
    }

    @Builder
    public Reservation(LocalTime startDate, LocalTime endDate, int totalPrice, Guest guest,
        String message) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.guest = guest;
        this.message = message;
    }

    public void changeInfo(LocalTime startDate, LocalTime endDate, int totalPrice, Guest guest,
        String message) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.guest = guest;
        this.message = message;
    }

}
