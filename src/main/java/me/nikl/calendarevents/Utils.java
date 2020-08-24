package me.nikl.calendarevents;

import java.time.Duration;

public class Utils {
    public static String getTimeUntil(Duration duration) {
        if (duration.toDays() > 1)
            return duration.toDays() + " days";
        else if (duration.toHours() > 1)
            return duration.toDays() + " hours";
        else
            return duration.toMinutes() + " minutes";
    }

    public static String separateByCasing(String str) {
        return String.join(" ", str.split("(?<=[a-z])(?=[A-Z])"));
    }
}
