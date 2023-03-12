package by.teachmeskills.dzeviatsen.homework17;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

public class ChatService {
    private final int limitNumOfMessages;
    private final Duration nonSpamPeriod;
    private Message[] history;

    public ChatService(int limitNumOfMessages, Duration nonSpamPeriod) {
        validatePeriod(nonSpamPeriod);
        validateLimitNumOfMessages(limitNumOfMessages);
        this.limitNumOfMessages = limitNumOfMessages;
        this.nonSpamPeriod = nonSpamPeriod;
        history = new Message[0];
    }

    public static void validateLimitNumOfMessages(int limitNumOfMessages) {
        if (limitNumOfMessages <= 0) {
            throw new IllegalArgumentException("Value is out of limit");
        }
    }

    public static void validatePeriod(Duration nonSpamPeriod) {
        if (nonSpamPeriod.compareTo(Duration.ZERO) <= 0) {
            throw new IllegalArgumentException("Period is incorrect");
        }
    }

    public void ifAddNewMessage(String text, User user) throws UserMessagesRateLimitingExceededException {
        int counterOfMessages = 0;
        Instant sendingTime = getSendingTime();
        for (int i = history.length - 1; i >= 0; i--) {
            if (history[i].getSendTime().isBefore(sendingTime.minus(nonSpamPeriod))) {
                break;
            }
            if (history[i].getUser().getNickName().equals(user.getNickName())) {
                counterOfMessages++;
                if (counterOfMessages == limitNumOfMessages) {
                    Instant firstBanTime = history[i].getSendTime();
                    throw new UserMessagesRateLimitingExceededException(firstBanTime.plus(nonSpamPeriod));
                }
            }
        }
        addMessage(new Message(text, user, sendingTime));

    }

    public void addMessage(Message newMessage) {
        history = Arrays.copyOf(history, history.length + 1);
        history[history.length - 1] = newMessage;
    }

    public Message[] getAll() {
        return Arrays.copyOf(history, history.length);
    }

    public Instant getSendingTime() {
        return Instant.now();
    }
}
