package com.flowna.app.Tracking;


import com.flowna.app.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/user/tracking")
@RequiredArgsConstructor
public class TrackingController {
    TrackingService trackingService;

    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(
            @RequestBody TrackingRequest trackingRequest,
            @AuthenticationPrincipal User userDetails
    ){
        Tracking tracking = trackingService.create(trackingRequest, userDetails.getId());
        return ResponseEntity.ok(tracking);
    }
    @PostMapping("/complete")
    public ResponseEntity<?> complete(
            @RequestBody LocalDateTime localDateTime,
            @AuthenticationPrincipal User userDetails
    ){
        Tracking tracking = trackingService.completeSession(localDateTime, userDetails.getId());
        return ResponseEntity.ok(tracking);
    }

    @GetMapping("/uncompleted")
    public ResponseEntity<?> getUncompletedSession(
            @AuthenticationPrincipal User userDetails
    ){
        Tracking tracking = trackingService.getUncompletedSession(userDetails.getId());
        return ResponseEntity.ok(tracking);
    }

    @GetMapping("/sessions")
    public ResponseEntity<?> getSessions(
            @AuthenticationPrincipal User userDetails
    ){
        return ResponseEntity.ok(trackingService.getTrackingSet(userDetails.getId()));
    }


}
