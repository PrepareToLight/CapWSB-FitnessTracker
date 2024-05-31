package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingMapper {
    private final UserServiceImpl userService;
    TrainingDto toTrainingDto(Training training){
        return new TrainingDto(
                training.getId(),
                training.getUser(),
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed());

    }

    public Training toTrainingEntity(TrainingDto trainingDto){
        return new Training(
                userService.getUser(trainingDto.getUser().getId()).get(), trainingDto.getStartTime(), trainingDto.getEndTime(), trainingDto.getActivityType(), trainingDto.getDistance(), trainingDto.getAverageSpeed()
        );
    }

    public TrainingDto toTrainingToFromTrainingSupportDto(TrainingSupportDto trainingSupportDto){
        return new TrainingDto(
                trainingSupportDto.getId(),
                trainingSupportDto.getUser(),
                trainingSupportDto.getStartTime(),
                trainingSupportDto.getEndTime(),
                trainingSupportDto.getActivityType(),
                trainingSupportDto.getDistance(),
                trainingSupportDto.getAverageSpeed());
    }

}
