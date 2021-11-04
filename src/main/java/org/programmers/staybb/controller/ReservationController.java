package org.programmers.staybb.controller;

import java.util.List;
import javax.validation.Valid;
import org.programmers.staybb.dto.Reservation.CheckDateResponse;
import org.programmers.staybb.dto.Reservation.FindReservationByGuestResponse;
import org.programmers.staybb.dto.Reservation.FindReservationByHostResponse;
import org.programmers.staybb.dto.Reservation.FindReservationsByUserResponse;
import org.programmers.staybb.dto.Reservation.ReservationIdResponse;
import org.programmers.staybb.dto.Reservation.ReservationSaveRequest;
import org.programmers.staybb.dto.Reservation.ReservationUpdateRequest;
import org.programmers.staybb.global.exception.EntityNotFoundException;
import org.programmers.staybb.global.exception.OverCrowdingException;
import org.programmers.staybb.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/reservation")
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(
        ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationIdResponse> createReservation(
        final @Valid @RequestBody ReservationSaveRequest saveRequest)
        throws EntityNotFoundException, OverCrowdingException {
        return ResponseEntity.ok(reservationService.createReservation(saveRequest));
    }

    @GetMapping("/guest/{id}")
    public ResponseEntity<FindReservationByGuestResponse> findOneByGuest(
        final @PathVariable Long id)
        throws EntityNotFoundException {
        return ResponseEntity.ok(reservationService.findOneByUser(id));
    }

    @GetMapping("/host/{id}")
    public ResponseEntity<FindReservationByHostResponse> findOneByHost(
        final @PathVariable Long id)
        throws EntityNotFoundException {
        return ResponseEntity.ok(reservationService.findOneByHost(id));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Page<FindReservationsByUserResponse>> findAllByUser(
        final @PathVariable Long userId, Pageable pageable)
        throws EntityNotFoundException {
        return ResponseEntity.ok(reservationService.findAllByUser(userId, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationIdResponse> updateOne(final @PathVariable Long id,
        final @Valid @RequestBody ReservationUpdateRequest updateRequest)
        throws EntityNotFoundException, OverCrowdingException {
        return ResponseEntity.ok(reservationService.updateReservation(id, updateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReservationIdResponse> deleteReservation(final @PathVariable Long id)
        throws EntityNotFoundException {
        return ResponseEntity.ok(reservationService.deleteReservation(id));
    }

    @GetMapping("/checkDate/{roomId}")
    public ResponseEntity<List<CheckDateResponse>> findAllCheckDate(
        final @PathVariable Long roomId) {
        return ResponseEntity.ok(reservationService.findAllCheckDate(roomId));
    }

}
