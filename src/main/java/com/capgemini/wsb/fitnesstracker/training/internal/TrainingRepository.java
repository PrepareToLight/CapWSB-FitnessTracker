package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

interface TrainingRepository extends JpaRepository<Training, Long> {
    default List<Training> getTrainingByUserId(final Long userId){
        return findAll().stream()
                .filter(training -> training.getUser().getId().equals(userId)).collect(Collectors.toList());

    }


    default List<Training> getTrainingsAfterDate(final Date date){
        return findAll().stream().filter(training -> (training.getEndTime().after(date))).collect(Collectors.toList());
    }

    default List<Training> getTrainingsByAcitityType(final ActivityType activityType){
        return findAll().stream().filter(training -> training.getActivityType().equals(activityType)).collect(Collectors.toList());
    }
}
