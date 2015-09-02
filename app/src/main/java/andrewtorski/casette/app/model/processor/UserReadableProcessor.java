package andrewtorski.casette.app.model.processor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Provides functionalities of converting data to user readable string outputs. Be it datetime, time
 * expressed in milliseconds.
 */
public final class UserReadableProcessor {

    //region Constructor

    /**
     * Private constructor which prevents instantiating this class.
     */
    private UserReadableProcessor() {
    }

    //endregion Constructor

    public static String getUserReadableDate(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm EEE, MMM d yyyy");

        return dateFormat.format(date);
    }

    public static String getUserReadableLengthOfTimeFromMilliseconds(long milliseconds) {
        long seconds = milliseconds / 1000;

        if (seconds == 0) {
            return "0 seconds";
        }

        if (seconds == 1) {
            return "1 second";
        }

        if (seconds < 60) {
            return seconds + " seconds";
        }

        long minutes = seconds / 60;

        String result;

        if (minutes == 1) {
            result = "1 minute";
        } else {
            result = minutes + " minutes";
        }

        long secondsLeft = seconds % 60;

        if (secondsLeft == 0) {
            return result;
        }

        if (secondsLeft == 1) {
            result += " 1 second";
        } else {
            result += " " + secondsLeft + " seconds";
        }

        return result;
    }
}
