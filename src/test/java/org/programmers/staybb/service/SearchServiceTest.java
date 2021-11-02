package org.programmers.staybb.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.programmers.staybb.domain.reservation.Guest;
import org.programmers.staybb.domain.room.Address;
import org.programmers.staybb.domain.room.Option;
import org.programmers.staybb.domain.user.Host;
import org.programmers.staybb.domain.user.User;
import org.programmers.staybb.dto.room.RoomRequest;
import org.programmers.staybb.dto.search.SearchAllResponse;
import org.programmers.staybb.dto.search.SearchOneResponse;
import org.programmers.staybb.dto.search.SearchRequest;
import org.programmers.staybb.repository.HostRepository;
import org.programmers.staybb.repository.RoomRepository;
import org.programmers.staybb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Slf4j
@SpringBootTest
class SearchServiceTest {
    @Autowired
    private SearchService searchService;
    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HostRepository hostRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() throws NotFoundException {
    }

    @Test
    void findOneTest() throws NotFoundException {
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

        RoomRequest roomRequest = RoomRequest.builder()
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

        //When
        Long savedRoomId = roomService.save(roomRequest);
        // Given
        Long id = 3L;
        // When
        SearchOneResponse one = searchService.findOne(id);
        // Then
        assertThat(one.getId()).isEqualTo(id);
    }

    @Test
    void findAllTest() throws NotFoundException{
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

        ////
        Long hostId = 2L;
        //Room 등록1
        RoomRequest roomRequest = RoomRequest.builder()
            .hostId(hostId)
            .roomName("beautiful apt in Seoul")
            .maxGuest(2)
            .price(20000)
            .description("visit and enjoy beautiful apt in Seoul!")
            .option(Option.builder()
                .bedNum(2)
                .bedroomNum(1)
                .bathroomNum(1)
                .build()
            )
            .address(Address.builder()
                .region("서울")
                .address("서울 중구 세종대로 110")
                .detail_address("11")
                .build()
            )
            .build();

        //When
        Long savedRoomId = roomService.save(roomRequest);
        // Given
        SearchRequest searchRequest = SearchRequest.builder()
            .location("서울")
            .startDate(null)
            .endDate(null)
            .guest(new Guest(1, 0 ,0))
            .option(Option.builder()
                .bedNum(2)
                .bedroomNum(1)
                .bathroomNum(1)
                .build())
            .build();
        PageRequest page = PageRequest.of(0, 10);
        // When
        Page<SearchAllResponse> all = searchService.findByFilters(searchRequest, page);
        // Then
        assertThat(all.getTotalElements()).isEqualTo(1L);
    }
}