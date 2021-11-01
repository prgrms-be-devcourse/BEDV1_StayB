package org.programmers.staybb.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.sun.istack.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javassist.NotFoundException;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.programmers.staybb.domain.room.Address;
import org.programmers.staybb.domain.room.Option;
import org.programmers.staybb.domain.room.Room;
import org.programmers.staybb.domain.user.Host;
import org.programmers.staybb.domain.user.User;
import org.programmers.staybb.dto.room.RoomRequest;
import org.programmers.staybb.repository.HostRepository;
import org.programmers.staybb.repository.RoomRepository;
import org.programmers.staybb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
class RoomServiceTest {
    @Autowired
    private RoomService roomService;
    @Autowired
    private HostRepository hostRepository;
    @Autowired
    private UserRepository userRepository;

    private RoomRequest roomRequest;

    @BeforeEach
    void setUp() throws NotFoundException {
        //Given
        //User 등록
        User user = User.builder()
            .name("변민지")
            .birthday(LocalDate.of(1996,01,01))
            .email("bnminji@gmail.com")
            .phoneNumber("01050483601")
            .bio("변민지입니다")
            .build();
        userRepository.save(user);

        //Host 등록
        Host host = Host.builder()
            .user(user)
            .build();
        hostRepository.save(host);

        //Room 등록
        Long hostId = 2L;

        roomRequest = RoomRequest.builder()
            .hostId(hostId)
            .roomName("beautiful apt in Seoul")
            .maxGuest(2)
            .price(20000)
            .description("visit and enjoy beautiful apt in Seoul!")
            .option(Option.builder()
                .bedNum(1)
                .bedroomNum(1)
                .bathroomNum(0.5)
                .build()
            )
            .address(Address.builder()
                .region("서울")
                .address("서울 중구 세종대로 110")
                .detail_address("11")
                .build()
            )
            .build();
    }

    //save
    @Test
    void saveTest() throws NotFoundException {
        Long savedRoomId = roomService.save(roomRequest);
    }

    //delete
    @Test
    void deleteTest() throws NotFoundException {
        //Given
        Long savedRoomId = roomService.save(roomRequest);
        //When
        Long deletedRoomId = roomService.delete(savedRoomId);
        //Then
        assertThat(deletedRoomId).isEqualTo(savedRoomId);
    }
}