package com.example.pmproject.DTO;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PmUseDTO {

    private Long pmUseId;

    private Long pm_id;

    private String member_name;

    @NotEmpty(message = "주소는 필수 입력 사항입니다.")
    private String location;

    private String startLocation;
    private String finishLocation;

    private Boolean isUse;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
