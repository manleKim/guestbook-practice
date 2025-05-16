package com.manlekim.guestbookpractice.dto;

import java.time.Instant;
import java.util.Optional;

public record PostResponseDto(
        Long id,
        String name,
        String title,
        String content,
        Optional<String> imageUrl,
        Instant createdAt
) {

}
