package org.programmers.staybb.controller;

import org.programmers.staybb.dto.user.HostFindResponse;
import org.programmers.staybb.dto.user.HostIdResponse;
import org.programmers.staybb.global.exception.EntityNotFoundException;
import org.programmers.staybb.service.HostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/host")
@RestController
public class HostController {

    private final HostService hostService;

    public HostController(HostService hostService) {
        this.hostService = hostService;
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<HostIdResponse> addHost(final @PathVariable Long userId)
        throws EntityNotFoundException {
        return ResponseEntity.ok(hostService.addHost(userId));
    }

    @GetMapping("/{hostId}")
    public ResponseEntity<HostFindResponse> getHost(final @PathVariable Long hostId)
        throws EntityNotFoundException {
        return ResponseEntity.ok(hostService.findHost(hostId));
    }

}
