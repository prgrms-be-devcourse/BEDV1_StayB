package org.programmers.staybb.controller;

import javax.validation.Valid;
import org.programmers.staybb.dto.Reservation.FindReservationByGuestResponse;
import org.programmers.staybb.dto.Reservation.FindReservationByHostResponse;
import org.programmers.staybb.dto.Reservation.FindReservationsByUserResponse;
import org.programmers.staybb.dto.Reservation.ReservationSaveRequest;
import org.programmers.staybb.dto.Reservation.ReservationUpdateRequest;
import org.programmers.staybb.global.exception.EntityNotFoundException;
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

@RestController
@RequestMapping("/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(
        ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Long> createReservation(
        final @Valid @RequestBody ReservationSaveRequest saveRequest)
        throws EntityNotFoundException {
        return ResponseEntity.ok(reservationService.createReservation(saveRequest));
    }

    @GetMapping("/guest/{id}")
    public ResponseEntity<FindReservationByGuestResponse> findOneByGuest(
        final @PathVariable long reservationId)
        throws EntityNotFoundException {
        return ResponseEntity.ok(reservationService.findOneByUser(reservationId));
    }

    @GetMapping("/host/{id}")
    public ResponseEntity<FindReservationByHostResponse> findOneByHost(
        final @PathVariable long reservationId)
        throws EntityNotFoundException {
        return ResponseEntity.ok(reservationService.findOneByHost(reservationId));
    }

    @GetMapping
    public ResponseEntity<Page<FindReservationsByUserResponse>> findAllByUser(
        final @PathVariable long userId, Pageable pageable)
        throws EntityNotFoundException {
        return ResponseEntity.ok(reservationService.findAllByUser(userId, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateOne(@PathVariable Long id,
        @Valid @RequestBody ReservationUpdateRequest updateRequest) throws EntityNotFoundException {
        return ResponseEntity.ok(reservationService.updateReservation(id, , updateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> removeOne(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(reservationService.deleteReservation(id));
    }

}
