package org.example.ubersocketservice.controller;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {

    private String name;

    private String message;

    private String timeStamp;
}
