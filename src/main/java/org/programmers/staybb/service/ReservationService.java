package org.programmers.staybb.service;

import java.util.List;
import java.util.stream.Collectors;
import org.programmers.staybb.domain.reservation.Reservation;
import org.programmers.staybb.domain.room.Room;
import org.programmers.staybb.domain.user.User;
import org.programmers.staybb.dto.Reservation.CheckDateResponse;
import org.programmers.staybb.dto.Reservation.FindReservationByGuestResponse;
import org.programmers.staybb.dto.Reservation.FindReservationByHostResponse;
import org.programmers.staybb.dto.Reservation.FindReservationsByUserResponse;
import org.programmers.staybb.dto.Reservation.ReservationIdResponse;
import org.programmers.staybb.dto.Reservation.ReservationSaveRequest;
import org.programmers.staybb.dto.Reservation.ReservationUpdateRequest;
import org.programmers.staybb.global.exception.EntityNotFoundException;
import org.programmers.staybb.global.exception.ErrorCode;
import org.programmers.staybb.global.exception.OverCrowdingException;
import org.programmers.staybb.repository.ReservationRepository;
import org.programmers.staybb.repository.RoomRepository;
import org.programmers.staybb.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public ReservationService(
        ReservationRepository reservationRepository,
        UserRepository userRepository,
        RoomRepository roomRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    public ReservationIdResponse updateReservation(final Long id,
        final ReservationUpdateRequest updateRequest) throws OverCrowdingException {
        Reservation findReservation = validateReservationId(id);

        if (findReservation.getRoom().getMaxGuest() < updateRequest.getGuestRequest().toEntity()
            .getTotalGuest()) {
            throw new OverCrowdingException(ErrorCode.OVER_CROWDING);
        }

        findReservation.changeInfo(updateRequest.getStartDate(), updateRequest.getEndDate(),
            updateRequest.getGuestRequest().toEntity());

        return new ReservationIdResponse(findReservation.getId());
    }

    public ReservationIdResponse createReservation(final ReservationSaveRequest saveRequest)
        throws EntityNotFoundException, OverCrowdingException {
        User user = validateUserId(saveRequest.getUserId());

        Room room = roomRepository.findById(saveRequest.getRoomId())
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ROOM_NOT_FOUND));

        if (room.getMaxGuest() < saveRequest.getGuestRequest().toEntity().getTotalGuest()) {
            throw new OverCrowdingException(ErrorCode.OVER_CROWDING);
        }

        Reservation savedReservation = reservationRepository.save(saveRequest.toEntity(user, room));

        return new ReservationIdResponse(savedReservation.getId());
    }

    public ReservationIdResponse deleteReservation(final Long reservationId) {
        Reservation findReservation = validateReservationId(reservationId);

        reservationRepository.delete(findReservation);
        return new ReservationIdResponse(reservationId);
    }

    @Transactional(readOnly = true)
    public Page<FindReservationsByUserResponse> findAllByUser(final Long userId,
        final Pageable pageable) {
        User user = validateUserId(userId);

        return reservationRepository.findAllByUserIdOrderByCreatedAt(user.getId(), pageable)
            .map(entity -> FindReservationsByUserResponse.of(entity,
                entity.getRoom().getAddress().getRegion(), entity.getRoom().getRoomName()));
    }

    @Transactional(readOnly = true)
    public FindReservationByGuestResponse findOneByUser(final Long id) {
        Reservation findReservation = validateReservationId(id);

        return FindReservationByGuestResponse.of(findReservation);
    }

    @Transactional(readOnly = true)
    public FindReservationByHostResponse findOneByHost(final Long id) {
        Reservation findReservation = validateReservationId(id);

        return FindReservationByHostResponse.of(findReservation);
    }

    @Transactional(readOnly = true)
    public List<CheckDateResponse> findAllCheckDate(final Long roomId) {
        return reservationRepository.findAllByRoomId(roomId)
            .stream().map(CheckDateResponse::of).collect(Collectors.toList());
    }

    private User validateUserId(Long userId) throws EntityNotFoundException {
        return userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    private Reservation validateReservationId(Long reservationId) throws EntityNotFoundException {
        return reservationRepository.findById(reservationId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.RESERVATION_NOT_FOUND));
    }

}
