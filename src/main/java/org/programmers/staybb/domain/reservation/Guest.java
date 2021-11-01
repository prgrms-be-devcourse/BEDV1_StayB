package org.programmers.staybb.domain.reservation;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
public class Guest {

    private int adult;
    private int teen;
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Guest guest = (Guest) o;
        return adult == guest.adult && teen == guest.teen && kid == guest.kid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(adult, teen, kid);
    }

}
