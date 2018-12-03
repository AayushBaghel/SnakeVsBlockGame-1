package sample;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class is used to create individual entries in the leader board.
 */
class LeaderBoard  implements Serializable {

    /**
     * The score of the player.
     */
    private int score;

    /**
     * The date and time at which this entry was made.
     */
    private String date;

    /**
     * A constructor that creates the entry for a player.
     * @param score The score the player earned.
     */
    LeaderBoard(int score) {
        this.score = score;
        this.date = getCurrentTimeAndDate();
    }

    /**
     * A function used to get the date and time at which this entry was made.
     * @return The date and time.
     */
    private static String getCurrentTimeAndDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    /**
     * A function to get the score of the player.
     * @return The score the player earned.
     */
    int getScore() {
        return score;
    }

    /**
     * A function to get the date and time at which this entry was made.
     * @return The date and time at which this entry was made.
     */
    String getDate() {
        return date;
    }
}
