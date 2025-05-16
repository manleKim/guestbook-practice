package com.manlekim.guestbookpractice.dto;

import java.util.List;

public record PageResponse<T>(
    List<T> content,
    long totalPages,
    long totalElements,
    int size,
    int number
) {

}
