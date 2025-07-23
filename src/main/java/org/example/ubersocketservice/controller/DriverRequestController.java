package org.example.ubersocketservice.controller;

import org.example.ubersocketservice.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@Controller
@RequestMapping("/socket/api")
public class DriverRequestController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private RestTemplate restTemplate;

    public DriverRequestController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.restTemplate = new RestTemplate();
    }

    @PostMapping("/newRide")
    @CrossOrigin(originPatterns = "*")
    public ResponseEntity<Boolean> raiseRideRequest(@RequestBody RideRequestDto  requestDto) {
        System.out.println("New Ride Request is received");
        sendDriverNewRideRequest(requestDto);
        return ResponseEntity.ok(true);
    }

    public void sendDriverNewRideRequest(RideRequestDto requestDto) {
        System.out.println("sendRideRequest");
        //Todo: Ideally the request should go to nearBy driver's for simplicity we sending to everyone
        simpMessagingTemplate.convertAndSend("/topic/rideRequest", requestDto);
    }

    @MessageMapping("/rideResponse/{userId}")
    public synchronized void rideResponseHandler(@DestinationVariable String userId, RideResponseDto rideResponseDto) {

        UpdatedBookingRequestDto  requestDto = UpdatedBookingRequestDto
                .builder()
                .driverId(Optional.of(Long.parseLong(userId)))
                .status("SCHEDULED")
                .build();
        System.out.println("rideResponse is received");
        System.out.println(rideResponseDto.getResponse()+ " "+ userId);
        ResponseEntity<UpdatedBookingResponseDto> result = this.restTemplate.postForEntity("http://localhost:8000/api/v1/booking/"+ rideResponseDto.bookingId, requestDto, UpdatedBookingResponseDto.class);
        System.out.println(result.getStatusCode());
    }

}
