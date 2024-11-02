package com.ceos20_instagram.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentCreateRequestDto {
    private Long postId;
    private Long memberId;
    private String content;
    private Long parentId; // 대댓글인 경우 부모 댓글 ID
}