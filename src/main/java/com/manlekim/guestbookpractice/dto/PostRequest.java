package com.manlekim.guestbookpractice.dto;

public record PostRequest(
    String name,
    String title,
    String content
) {

}
