package org.example.ubersocketservice.controller;

import org.example.ubersocketservice.dto.RideRequestDto;
import org.example.ubersocketservice.dto.RideResponseDto;
import org.example.ubersocketservice.dto.TestRequest;
import org.example.ubersocketservice.dto.TestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/socket/api")
public class DriverRequestController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public DriverRequestController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @GetMapping("/newRide")
    public ResponseEntity<Boolean> raiseRideRequest(@RequestBody RideRequestDto  requestDto) {
        sendDriverNewRideRequest(requestDto);
        return ResponseEntity.ok(true);
    }

    public void sendDriverNewRideRequest(RideRequestDto requestDto) {
        System.out.println("sendRideRequest");
        //Todo: Ideally the request should go to nearBy driver's for simplicity we sending to everyone
        simpMessagingTemplate.convertAndSend("/topic/rideRequest", requestDto);
    }

    @MessageMapping("/rideResponse")
    public void rideResponseHandler(RideResponseDto rideResponseDto) {
        System.out.println("rideResponse is received");
        System.out.println(rideResponseDto.getResponse());

    }

}
