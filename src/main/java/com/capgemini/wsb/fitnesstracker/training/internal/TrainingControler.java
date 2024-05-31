package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingControler {

    private final TrainingProvider trainingProvider;
    private final UserServiceImpl userService;
    private final TrainingMapper trainingMapper;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping("/activity/{activityString}")
    public List<TrainingDto> getTrainingsByAcitityType(@PathVariable String activityString){
        ActivityType activityType = ActivityType.valueOf(activityString.toUpperCase());
        return trainingProvider.getTrainingsByAcitityType(activityType)
                .stream()
                .map(trainingMapper::toTrainingDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/activityType")
    public List<TrainingDto> getTrainingsByAcitityType(@RequestParam ActivityType activityType){
        //ActivityType activityType = ActivityType.valueOf(activityString.toUpperCase());
        return trainingProvider.getTrainingsByAcitityType(activityType)
                .stream()
                .map(trainingMapper::toTrainingDto)
                .collect(Collectors.toList());
    }


    @GetMapping()
    public List<TrainingDto> getTrainings(){
        return trainingProvider.getTrainings()
                .stream()
                .map(training -> trainingMapper.toTrainingDto(training))
                .collect(Collectors.toList());
    }
    @GetMapping("/{userId}")
    public List<TrainingDto> getTrainingByUserId(@PathVariable Long userId){
        return trainingProvider.getTrainingsByUserId(userId).stream().map(trainingMapper::toTrainingDto).toList();
    }

    @GetMapping("/finished/{date}")
    public List<TrainingDto> getTrainingsAfterDate(@PathVariable String date) throws ParseException {
        Date parsedDate = sdf.parse(date);
        return trainingProvider.getTrainingsAfterDate(parsedDate)
                .stream()
                .map(trainingMapper::toTrainingDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/add/{userId}")
    public TrainingDto createTraining(@RequestBody TrainingDto trainingDto, @PathVariable Long userId){

        User user = userService.getUser(userId).get();
        trainingDto.setUser(user);
        Training training = trainingMapper.toTrainingEntity(trainingDto);
        TrainingDto newTraining = trainingMapper.toTrainingDto(trainingProvider.addTraining(training));
        return newTraining;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingDto createTraining(@RequestBody TrainingSupportDto trainingSupportDto){

        TrainingDto trainingDto = trainingMapper.toTrainingToFromTrainingSupportDto(trainingSupportDto);
        User user = userService.getUser(trainingSupportDto.getUserId()).get();
        trainingDto.setUser(user);

        Training training = trainingMapper.toTrainingEntity(trainingDto);

        TrainingDto newTraining = trainingMapper.toTrainingDto(trainingProvider.addTraining(training));
        return newTraining;
    }

    @PutMapping("/{trainingId}")
    public TrainingDto editTraining(@RequestBody TrainingDto trainingDto, @PathVariable Long trainingId){
        trainingProvider.editTraining(trainingId, trainingDto);
        TrainingDto newTrainingDto = trainingMapper.toTrainingDto(trainingProvider.editTraining(trainingId, trainingDto));
        return newTrainingDto;
    }


}
