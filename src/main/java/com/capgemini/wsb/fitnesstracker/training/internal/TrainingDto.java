package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TrainingDto {
    private Long id;

    @Nullable
    private User user;

    private Date startTime;

    private Date endTime;

    private ActivityType activityType;

    private Double distance;

    private Double averageSpeed;
}