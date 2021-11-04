package org.programmers.staybb.domain.reservation;

import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Guest {

    private int adult;
    private int teen;
    private int kid;
    private int totalGuest;

    protected Guest() {
    }

    public Guest(int adult, int teen, int kid) {
        this.adult = adult;
        this.teen = teen;
        this.kid = kid;
        this.totalGuest = sumGuest(adult, teen);
    }

    private int sumGuest(int adult, int teen) {
        return adult + teen;
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
