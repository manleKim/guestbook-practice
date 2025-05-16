package com.manlekim.guestbookpractice.controller;

import com.manlekim.guestbookpractice.dto.PageResponse;
import com.manlekim.guestbookpractice.dto.PostResponseDto;
import com.manlekim.guestbookpractice.service.PostService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/guestbooks")
public class PostController {

    private static final Logger log = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponseDto> createPost(
        @RequestParam("name") String name,
        @RequestParam("title") String title,
        @RequestParam("content") String content,
        @RequestParam(value = "image", required = false) MultipartFile image
    ) {

        log.info("Received Post Creation request: name={}, title={}, content={}", name, title, content);

        PostResponseDto response = postService.createPost(name, title, content, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<PageResponse<PostResponseDto>> getAllPosts(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        log.info("Received Post get request: page={}, size={}", page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        PageResponse<PostResponseDto> response = postService.getPosts(pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {
        log.info("Received Post get request: postID={}", id);

        PostResponseDto response = postService.getPostById(id);
        return ResponseEntity.ok(response);
    }
}
