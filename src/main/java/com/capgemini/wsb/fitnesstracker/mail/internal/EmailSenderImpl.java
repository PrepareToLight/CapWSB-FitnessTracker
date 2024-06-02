package com.capgemini.wsb.fitnesstracker.mail.internal;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Calendar.DAY_OF_MONTH;

@Service
@RequiredArgsConstructor
public class EmailSenderImpl implements EmailSender {
    private static final String reportTitle = "Last week's report";
    private final JavaMailSender javaMailSender;

    @Override
    public void send(EmailDto email) {
        final SimpleMailMessage simpleEmail = new SimpleMailMessage();
        simpleEmail.setTo(email.toAddress());
        simpleEmail.setSubject(email.subject());
        simpleEmail.setText(email.content());
        javaMailSender.send(simpleEmail);
    }

    @Override
    public void emailToReport(String emailAddress, List<Training> trainingList) {
        this.send(createEmail(emailAddress, trainingList));
    }

    private EmailDto createEmail(final String user, final List<Training> trainingList){
        final List<Training> trainings = trainingList.stream().filter(training -> training.getStartTime().after(returnBeginningOfLastWeek()) && training.getStartTime().before(returnYesterday())).toList();
        final StringBuilder builder = new StringBuilder("""
                We hope this email finds you well. Here’s a summary of your running sessions for the past week:
                Running Summary:
                Total Trainings Sessions: [%s]
                Total Distance Covered: [%s]
                You've finished so far: [%s] trainings
               
                Last week's trainings can be found below:
                ----
                """.formatted(trainings.size(),
                trainings.isEmpty() ? 0 : trainings.stream().mapToDouble(Training::getDistance).sum(),
                trainings.size()
        ));
        trainings.forEach(training -> builder.append("""
            Start: %s
            End: %s
            Activity: %s
            Distance: %s
            Avr_speed: %s
            ----
            """.formatted(training.getStartTime(),
                training.getEndTime() == null ? "-" : training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed()
        )));
        System.out.println(builder); //task requirement
        return new EmailDto(user, reportTitle, builder.toString());
    }

    private Date returnBeginningOfLastWeek() {
        final Calendar now = Calendar.getInstance();
        now.add(DAY_OF_MONTH, -7);
        return now.getTime();
    }

    private Date returnYesterday() {
        final Calendar now = Calendar.getInstance();
        now.add(DAY_OF_MONTH, -1);
        return now.getTime();
    }
}