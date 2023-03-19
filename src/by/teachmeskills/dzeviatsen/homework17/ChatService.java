package by.teachmeskills.dzeviatsen.homework17;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class ChatService {
    private final int limitNumOfMessages;
    private final Duration nonSpamPeriod;
    private List<Message> history;

    public ChatService(int limitNumOfMessages, Duration nonSpamPeriod) {
        validatePeriod(nonSpamPeriod);
        validateLimitNumOfMessages(limitNumOfMessages);
        this.limitNumOfMessages = limitNumOfMessages;
        this.nonSpamPeriod = nonSpamPeriod;
        history = new LinkedList<>();
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
        ListIterator <Message>listIterator= history.listIterator(history.size());
        while (listIterator.hasPrevious()){
            Message message = listIterator.previous();
            if(message.getSendTime().isBefore(sendingTime.minus(nonSpamPeriod))){
                break;
            }
            if(message.getUser().equals(user)){
                counterOfMessages++;
                if (counterOfMessages==limitNumOfMessages){
                    throw new UserMessagesRateLimitingExceededException(message.getSendTime().plus(nonSpamPeriod));
                }
            }
        }

        history.add(new Message(text, user, sendingTime));

    }


    public List<Message> getAll() {
        history = Collections.unmodifiableList(history);
        return history;
    }

    private Instant getSendingTime() {
        return Instant.now();
    }
}
