package com.capgemini.wsb.fitnesstracker.mail.api;

import com.capgemini.wsb.fitnesstracker.training.api.Training;

import java.util.List;

/**
 * API interface for component responsible for sending emails.
 */
public interface EmailSender {

    /**
     * Sends the email message to the recipient from the provided {@link EmailDto}.
     *
     * @param email information on email to be sent
     */
    void send(EmailDto email);

    void emailToReport(final String emailAddress, final List<Training> trainingList);

}
