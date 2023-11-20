package com.example.pmproject.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PmDTO {

    private Long pmId;

    private Integer type;

    private Boolean isUse;

    private String location;

    private String img;
}
