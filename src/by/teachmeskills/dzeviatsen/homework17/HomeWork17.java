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
        Duration period = Duration.ofSeconds(Integer.parseInt(sc.nextLine()));
        do {
            System.out.println("Enter operation number:");
            System.out.println("1-enter chat");
            System.out.println("2-show chat history");
            int taskNum = Integer.parseInt(sc.nextLine());
            switch (taskNum) {
                case 1 -> {
                    telegramPro = new ChatService(limitNumOfMessages, period);
                    int count = 0;
                    do {
                        System.out.println("If you want to quit enter STOP, if no press ENTER");
                        if (sc.nextLine().equalsIgnoreCase("STOP")) {
                            System.out.println("Goodbye");
                            break;
                        }
                        System.out.println("enter username");
                        User user = new User(sc.nextLine());
                        System.out.println("Enter text");
                        String text = sc.nextLine();
                        if (telegramPro.ifAddNewMessage(text, user, Instant.now())) {
                            System.out.println("Message sent");
                        } else {
                            System.out.println("Do not spam, please");
                        }
                    }
                    while (true);

                }
                case 2 -> {
                    System.out.println(Arrays.toString(telegramPro.getAll()));
                }
            }
        } while (true);
    }
}