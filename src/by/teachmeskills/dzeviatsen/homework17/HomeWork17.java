package by.teachmeskills.dzeviatsen.homework17;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Scanner;

public class HomeWork17 {
    static ChatService telegramPro;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter limit num of messages");
        int limitNumOfMessages = Integer.parseInt(sc.nextLine());
        System.out.println("Enter freezing period");
        Duration nonSpamPeriod = Duration.ofSeconds(Integer.parseInt(sc.nextLine()));
        telegramPro = new ChatService(limitNumOfMessages, nonSpamPeriod);
        while (true) {
            System.out.println(">");
            String command = sc.nextLine();
            switch (command) {
                default -> {

                    String[] parts = command.split(":\\s*");
                    if (parts.length != 2) throw new IllegalStateException("Неверный формат команды");

                    User user = new User(parts[0]);
                    String text = parts[1];

                    try {
                        telegramPro.ifAddNewMessage(text, user);
                        System.out.println("Доставлено");
                    } catch (UserMessagesRateLimitingExceededException e) {
                        System.out.println("Слишком частые запросы, напишите через "
                                + Duration.between(Instant.now(), e.getNonBlockedTime()).getSeconds() + " секунд");
                    }

                }
                case "history" -> {
                    System.out.println(Arrays.toString(telegramPro.getAll()));
                }
                case "exit" -> {
                    return;

                }
            }
        }
    }
}