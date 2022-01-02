package CA1;

import java.util.Date;

public class DateUtility {

    //    This method returns the duration string between two times
    public static String getDurationString(Date startTime, Date endTime) {
        long diff = endTime.getTime() - startTime.getTime();

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;

        String hoursFormat = "";
        String minutesFormat = "";
        String secondsFormat = "";


        if (diffHours == 0 && diffMinutes == 0 && diffSeconds == 0) {
            return "";
        } else {
            if (diffHours > 0) {
                hoursFormat = String.format(" %s hours", diffHours);
            }

            if (diffMinutes > 0) {
                minutesFormat = String.format(" %s minutes", diffMinutes);
            }

            if (diffSeconds > 0) {
                secondsFormat = String.format(" %s seconds", diffSeconds);
            }
        }

        return String.format("You have used the program for%s%s%s.", hoursFormat, minutesFormat, secondsFormat);
    }
}
