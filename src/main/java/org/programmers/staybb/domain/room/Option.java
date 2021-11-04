package org.programmers.staybb.domain.room;

import com.sun.istack.NotNull;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Option {

    @NotNull
    private int bedNum;
    @NotNull
    private int bedroomNum;
    @NotNull
    private double bathroomNum;

    protected Option() {
    }

    @Builder
    public Option(int bedNum, int bedroomNum, double bathroomNum) {
        this.bedNum = bedNum;
        this.bedroomNum = bedroomNum;
        this.bathroomNum = bathroomNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Option option = (Option) o;
        return bedNum == option.bedNum && bedroomNum == option.bedroomNum
            && bathroomNum == option.bathroomNum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bedNum, bedroomNum, bathroomNum);
    }

}
