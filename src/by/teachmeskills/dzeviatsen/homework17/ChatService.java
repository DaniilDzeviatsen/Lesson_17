package by.teachmeskills.dzeviatsen.homework17;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Scanner;

public class ChatService {
    private int limitNumOfMessages;
    private Duration period;
    private Message[] history;

    public ChatService(int limitNumOfMessages, Duration period) {
        validatePeriod(period);
        validateLimitNumOfMessages(limitNumOfMessages);
        this.limitNumOfMessages = limitNumOfMessages;
        this.period = period;
        history = new Message[0];
    }

    public static void validateLimitNumOfMessages(int limitNumOfMessages) {
        if (limitNumOfMessages > 10) {
            throw new IllegalArgumentException("Value is out of limit");
        }
    }

    public static void validatePeriod(Duration period) {
        if (period.isNegative() || period.isZero() || period.compareTo(Duration.ofSeconds(20)) > 0) {
            throw new IllegalArgumentException("Period is incorrect");
        }
    }

    public boolean ifAddNewMessage(String text, User user, Instant sendTime) {
        int counterOfMessages = 0;
        for (int i = history.length - 1; i >= 0; i--) {
            if (history[i].getSendTime().isAfter(Instant.now().minus(period)) &&
                    history[i].getUser().getNickName().equals(user.getNickName())) {
                counterOfMessages++;
                if (counterOfMessages == limitNumOfMessages) {
                    return false;
                }
                if (history[i].getSendTime().isBefore(Instant.now().minus(period))) {
                    break;
                }
            }
        }
        addMessage(new Message(text, user, sendTime));
        return true;
    }

    public void addMessage(Message newMessage) {
        history = Arrays.copyOf(history, history.length + 1);
        history[history.length - 1] = newMessage;
    }

    public Message[] getAll() {
        return Arrays.copyOf(history, history.length);
    }

}
