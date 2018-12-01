package sample;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class LeaderBoard {
    private int score;
    private String date;

    LeaderBoard(int score) {
        this.score = score;
        this.date = getCurrentTimeAndDate();
    }

    public static String getCurrentTimeAndDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public int getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }
}
