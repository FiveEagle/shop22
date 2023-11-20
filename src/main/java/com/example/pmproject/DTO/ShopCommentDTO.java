package com.example.pmproject.DTO;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopCommentDTO {

    private Long shopCommentId;

    private Long shopId;

    private String member_name;

    @NotNull(message = "내용은 필수 입력 사항입니다.")
    private String content;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
