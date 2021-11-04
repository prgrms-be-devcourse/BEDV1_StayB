package org.programmers.staybb.domain.room;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
public class Address {

    @NotNull
    private String region;

    @NotNull
    private String address;

    @NotNull
    private String detailAddress;

    protected Address() {
    }

    @Builder
    public Address(String region, String address, String detailAddress) {
        this.region = region;
        this.address = address;
        this.detailAddress = detailAddress;
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
            && Objects.equals(detailAddress, address1.detailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(region, address, detailAddress);
    }

}
