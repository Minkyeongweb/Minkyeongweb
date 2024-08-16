package com.sparta.schedule.entity;

import com.sparta.schedule.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private Long id;
    private String task;
    private String assignee;
    private String password;

    public Schedule(ScheduleRequestDto requestDto) {
        this.id = requestDto.getId();
        this.task = requestDto.getTask();
        this.assignee = requestDto.getassignee();
        this.password = requestDto.getPassword();
    }

    public void update(ScheduleRequestDto requestDto) {
        this.task = requestDto.getTask();
        this.assignee = requestDto.getassignee();
        this.password = requestDto.getPassword();
    }
}