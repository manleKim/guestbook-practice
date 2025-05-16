package com.manlekim.guestbookpractice.service;

import com.manlekim.guestbookpractice.dto.PageResponse;
import com.manlekim.guestbookpractice.dto.PostResponseDto;
import com.manlekim.guestbookpractice.entity.Post;
import com.manlekim.guestbookpractice.repository.PostRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ImageService imageService;

    public PostResponseDto createPost(String name, String title, String content, MultipartFile imageFile) {
        String imageUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            imageUrl = imageService.createImage(imageFile);
        }

        Post post = new Post(name, title, content, imageUrl);
        postRepository.save(post);

        return new PostResponseDto(
            post.getId(),
            post.getName(),
            post.getTitle(),
            post.getContent(),
            Optional.ofNullable(post.getImageUrl()),
            post.getCreatedAt()
        );
    }

    public PageResponse<PostResponseDto> getPosts(Pageable pageable){
        Page<Post> postPage = postRepository.findAll(pageable);

        List<PostResponseDto> content = postPage.stream()
            .map(post -> new PostResponseDto(
                post.getId(),
                post.getName(),
                post.getTitle(),
                post.getContent(),
                Optional.ofNullable(post.getImageUrl()),
                post.getCreatedAt()
            ))
            .toList();

        return new PageResponse<>(
            content,
            postPage.getTotalPages(),
            postPage.getTotalElements(),
            postPage.getSize(),
            postPage.getNumber()
        );
    }

    public PostResponseDto getPostById(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Post not found"));

        return new PostResponseDto(
            post.getId(),
            post.getName(),
            post.getTitle(),
            post.getContent(),
            Optional.ofNullable(post.getImageUrl()),
            post.getCreatedAt()
        );
    }
}
