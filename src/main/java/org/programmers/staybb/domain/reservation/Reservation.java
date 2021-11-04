package org.programmers.staybb.domain.reservation;

import com.sun.istack.NotNull;
import java.time.LocalDate;
import java.time.Period;
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
import org.programmers.staybb.domain.room.Room;
import org.programmers.staybb.domain.user.User;
import org.programmers.staybb.global.BaseTimeEntity;

@Getter
@Table(name = "reservation")
@Entity
public class Reservation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

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
    public Reservation(LocalDate startDate, LocalDate endDate, Guest guest,
        String message, User user, Room room) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = countTotalPrice(startDate, endDate, room.getPrice());
        this.guest = guest;
        this.message = message;
        this.user = user;
        this.room = room;
    }

    public void changeInfo(LocalDate startDate, LocalDate endDate, Guest guest) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.guest = guest;
        this.totalPrice = countTotalPrice(startDate, endDate, this.room.getPrice());
    }

    private int countTotalPrice(LocalDate startDate, LocalDate endDate, int price) {
        Period between = startDate.until(endDate);
        return between.getDays() * price;
    }

}
