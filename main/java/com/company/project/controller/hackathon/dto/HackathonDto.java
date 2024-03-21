package com.company.project.controller.hackathon.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HackathonDto {
    private String id;
    private String name;
    private String startDate;
    private String mode;
    private String registrationFee;
    private String registerLink;
    private String imageLink;
    private String source;
}
