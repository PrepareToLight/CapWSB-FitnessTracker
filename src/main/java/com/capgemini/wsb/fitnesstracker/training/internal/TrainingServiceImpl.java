package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

// TODO: Provide Impl
@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingProvider {


    private final TrainingRepository trainingRepository;
    private final UserMapper userMapper;
    @Override
    public List<Training> getTrainingsByUserId(final Long userId) {
        return trainingRepository.getTrainingByUserId(userId);
    }

    @Override
    public List<Training> getTrainings(){
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> getTrainingsAfterDate(Date date) {
        return trainingRepository.getTrainingsAfterDate(date);
    }


    @Override
    public List<Training> getTrainingsByAcitityType(ActivityType activityType){
        return trainingRepository.getTrainingsByAcitityType(activityType);
    }

    @Override
    public Training addTraining(Training training){
        return trainingRepository.save(training);
    }

    @Override
    public Training editTraining(Long trainingId, TrainingDto trainingDto){
        Training training = trainingRepository.findById(trainingId).get();

        if (trainingDto.getUser() != null)
        {
            training.setUser(trainingDto.getUser());
        }
        if (trainingDto.getStartTime() != null)
        {
            training.setStartTime(trainingDto.getStartTime());
        }
        if (trainingDto.getEndTime() != null)
        {
            training.setEndTime(trainingDto.getEndTime());
        }
        if (trainingDto.getActivityType() != null)
        {
            training.setActivityType(trainingDto.getActivityType());
        }
        if (trainingDto.getDistance() != null)
        {
            training.setDistance(trainingDto.getDistance());
        }
        if (trainingDto.getAverageSpeed() != null)
        {
            training.setAverageSpeed(trainingDto.getAverageSpeed());
        }



        return training;

    }

}
