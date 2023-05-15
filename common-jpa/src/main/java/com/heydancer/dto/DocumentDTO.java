package com.heydancer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class DocumentDTO {
    private Long id;
    private String authorFirstName;
    private String authorLastName;
    private String subdivision;
    private String link;
    private LocalDate created;
}
