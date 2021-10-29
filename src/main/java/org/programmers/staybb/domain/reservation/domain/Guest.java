package org.programmers.staybb.domain.reservation.domain;

import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
public class Guest {

    @ColumnDefault("1")
    private int adult;
    @ColumnDefault("0")
    private int teen;
    @ColumnDefault("0")
    private int kid;

    protected Guest() {
    }

    public Guest(int adult, int teen, int kid) {
        this.adult = adult;
        this.teen = teen;
        this.kid = kid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return adult == guest.adult && teen == guest.teen && kid == guest.kid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(adult, teen, kid);
    }

}
