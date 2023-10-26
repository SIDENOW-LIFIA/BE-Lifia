package com.sidenow.domain.board.service;

import com.sidenow.domain.board.dto.request.PostRequest.PostCreateRequest;
import com.sidenow.domain.board.dto.response.PostResponse;
import com.sidenow.domain.board.dto.response.PostResponse.AllPosts;
import com.sidenow.domain.board.dto.response.PostResponse.PostCreateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {
    PostCreateResponse createPost(Long boardId, Long memberId,
                                               MultipartFile image, PostCreateRequest request);
    AllPosts getPostsByBoard(Long boardId, Integer page, Long memberId);
    PostResponse.PostDetailResponse getPost(Long postId);
    Page<PostResponse.PostSimpleResponse> getHotPosts(Pageable pageable);
}
