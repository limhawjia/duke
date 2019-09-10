package util;

import error.datetime.UnknownDateTimeException;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/***
 * <p>
 * Utility class to handle date and time.
 * </p>
 */
public class DateTime {
    /***
     * <p>
     * Parses a string into a LocalDateTime object. E.g. 24/02/2022 0315.
     * </p>
     * @param dateTimeString string to be parsed, must be in the form dd/mm/yyyy HHmm.
     * @return LocalDateTime.
     * @throws UnknownDateTimeException if date and time is not in the correct format.
     */
    public static LocalDateTime parse(String dateTimeString) throws UnknownDateTimeException {
        try {
            String[] dateTimeStrings = dateTimeString.split("\\s+");

            if (dateTimeStrings.length != 2) {
                throw new UnknownDateTimeException();
            }

            String date = dateTimeStrings[0];
            String time = dateTimeStrings[1];

            String[] dayMonthYear = date.split("/");
            if (dayMonthYear.length != 3) {
                throw new UnknownDateTimeException();
            }

            int day = Integer.parseInt(dayMonthYear[0]);
            int month = Integer.parseInt(dayMonthYear[1]);
            int year = Integer.parseInt(dayMonthYear[2]);

            if (time.length() != 4) {
                throw new UnknownDateTimeException();
            }

            int hour = Integer.parseInt(time.substring(0, 2));
            int minute = Integer.parseInt(time.substring(2, 4));

            return LocalDateTime.of(year, month, day, hour, minute);
        } catch (NumberFormatException | DateTimeException e) {
            throw new UnknownDateTimeException();
        }
    }

    /***
     * <p>
     * Formats LocalDateTime object into string.
     * </p>
     * @param dateTime LocalDateTime to be formatted.
     * @return formatted string.
     */
    public static String getString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, E, HH:mma");
        return formatter.format(dateTime);
    }
}
