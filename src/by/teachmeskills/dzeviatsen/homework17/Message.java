package by.teachmeskills.dzeviatsen.homework17;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Message {
    private String text;
    private User user;
    private Instant sendTime;

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("hh:mm").withZone(ZoneId.of("Europe/Minsk"));

    public Message(String text, User user) {
        this.text = text;
        this.user = user;
        this.sendTime = getSendingTime();
    }

    public String getText() {
        return text;
    }

    public User getUser() {
        return user;
    }

    public Instant getSendTime() {
        return sendTime;
    }

    public String toString() {
        return "%s:%n%s%30s%n%n".formatted(user.getNickName(), text, DATE_TIME_FORMATTER.format(sendTime));
    }

    public Instant getSendingTime() {
        return Instant.now();
    }
}
