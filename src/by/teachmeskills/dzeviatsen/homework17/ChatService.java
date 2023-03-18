package by.teachmeskills.dzeviatsen.homework17;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatService {
    private final int limitNumOfMessages;
    private final Duration nonSpamPeriod;
    private List<Message> history;

    public ChatService(int limitNumOfMessages, Duration nonSpamPeriod) {
        validatePeriod(nonSpamPeriod);
        validateLimitNumOfMessages(limitNumOfMessages);
        this.limitNumOfMessages = limitNumOfMessages;
        this.nonSpamPeriod = nonSpamPeriod;
        history = new ArrayList<>();
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
        for (int i = history.size() - 1; i >= 0; i--) {
            if (history.get(i).getSendTime().isBefore(sendingTime.minus(nonSpamPeriod))) {
                break;
            }
            if (history.get(i).getUser().equals(user)) {
                counterOfMessages++;
                if (counterOfMessages == limitNumOfMessages) {
                    Instant firstBanTime = history.get(i).getSendTime();
                    throw new UserMessagesRateLimitingExceededException(firstBanTime.plus(nonSpamPeriod));
                }
            }
        }
        history.add(new Message(text, user, sendingTime));

    }

    public void addMessage(Message newMessage) {
        history.add(newMessage);
    }

    public List<Message> getAll() {
        history = Collections.unmodifiableList(history);
        return history;
    }

    public Instant getSendingTime() {
        return Instant.now();
    }
}
