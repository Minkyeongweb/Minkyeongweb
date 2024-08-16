package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String task;
    private String assignee;
    private Schedule password;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.task = schedule.getTask();
        this.assignee = schedule.getAssignee();
        this.password = schedule;
    }
}