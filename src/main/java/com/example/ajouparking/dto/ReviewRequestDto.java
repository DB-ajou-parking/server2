package com.example.ajouparking.dto;

import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDto {
    private String author;
    private String content;
}
