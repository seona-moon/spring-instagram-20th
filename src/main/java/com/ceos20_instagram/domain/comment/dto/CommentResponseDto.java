package com.ceos20_instagram.domain.comment.dto;


import com.ceos20_instagram.domain.comment.entity.Comment;
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
public class CommentResponseDto {
    private Long id;
    private String content;
    private Long memberId;
    private Long postId;
    private Long parentId;

    // Comment 엔티티를 CommentResponseDto로 변환하는 정적 메서드
    public static CommentResponseDto from(Comment comment) {
        return CommentResponseDto.builder()
                                 .id(comment.getId())
                                 .content(comment.getContent())
                                 .memberId(comment.getMember()
                                                  .getId())
                                 .postId(comment.getPost()
                                                .getId())
                                 .parentId(comment.getParent() != null ? comment.getParent()
                                                                                .getId() : null)
                                 .build();
    }
}

