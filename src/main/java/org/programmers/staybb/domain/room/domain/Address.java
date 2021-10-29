package org.programmers.staybb.domain.room.domain;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
public class Address {

    @NotNull
    private String region;

    @NotNull
    private String address;

    @NotNull
    private String detail_address;

    protected Address() {
    }

    public Address(String region, String address, String detail_address) {
        this.region = region;
        this.address = address;
        this.detail_address = detail_address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address1 = (Address) o;
        return Objects.equals(region, address1.region) && Objects.equals(address, address1.address)
            && Objects.equals(detail_address, address1.detail_address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(region, address, detail_address);
    }

}
