package edu.test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatDateTimeExample {

    private static final String DATE_FORMATTER= "yyyy-MM-dd HH:mm:ss";

    public static void main(String[] args) {
//        formatNow();
        formatString();
    }

    private static void formatNow() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("Current Time: " + localDateTime);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        String formatDateTime = localDateTime.format(formatter);

        System.out.println("Formatted Time: " + formatDateTime);
    }

    private static void formatString() {
        String dateTime = "2018-12-11 17:30";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime formatDateTime = LocalDateTime.parse(dateTime, formatter);

        System.out.println("Parsed Date: " + formatDateTime);
    }

}
