package by.teachmeskills.dzeviatsen.homework17;

import java.time.Instant;

public class UserMessagesRateLimitingExceededException extends Exception{
    private final Instant nonBlockedTime;

    public UserMessagesRateLimitingExceededException(Instant nonBlockedTime) {
        this.nonBlockedTime = nonBlockedTime;
    }

    public Instant getNonBlockedTime() {
        return nonBlockedTime;
    }
}
