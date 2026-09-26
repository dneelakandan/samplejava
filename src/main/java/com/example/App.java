package com.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class App {
    public static String getGreeting() {
        return "Hello, World from Docker in WSL via Jenkins!";
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(getGreeting());
        System.out.println("Timestamp   : " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("OS          : " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ")");
        System.out.println("==================================================");
    }
}
