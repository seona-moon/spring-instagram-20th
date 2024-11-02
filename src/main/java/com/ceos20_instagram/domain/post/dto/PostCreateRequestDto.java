package com.ceos20_instagram.domain.post.dto;

import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostCreateRequestDto {
    private Long memberId;
    private String content;
    private Optional<String> location; // location은 선택적으로 받을 수 있도록 Optional 사용

    @Builder
    public PostCreateRequestDto(Long memberId, String content, Optional<String> location) {
        this.memberId = memberId;
        this.content = content;
        this.location = location;
    }
}
