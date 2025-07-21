package org.example.ubersocketservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequest {

    private String name;

    private String message;


}
