package com.sparta.schedule.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private Long id;
    private String task;
    private String assignee;
    private String password;


    public String getassignee() {
        return assignee;
    }
}