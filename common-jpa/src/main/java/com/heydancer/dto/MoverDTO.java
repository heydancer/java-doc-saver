package com.heydancer.dto;

import com.heydancer.entity.enums.MoverState;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class MoverDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String link;
    private String subdivision;
    private LocalDateTime firstLoginDate;
    private Boolean isActive;
    private MoverState state;
}
