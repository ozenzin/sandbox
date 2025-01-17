package oz;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Given an API which returns availability schedules of clinicians
 * implement function which would take desired appointment time and
 * return list of available clinicians.
 *
 */
public class AvailableDates {

    //an example schedule of workdays per clinician: 
    //keys are clinicianId:Integer, values - dayStart:LocalDateTime, dayEnd:LocalDateTime
    private static Map<Integer, List<List<LocalDateTime>>> schedule = new HashMap<>();

    static {
        schedule.put(1, Stream.of(
                Arrays.asList(LocalDateTime.of(1999, 1, 1, 8, 30),
                        LocalDateTime.of(1999, 1, 1, 20, 0)),
                Arrays.asList(LocalDateTime.of(2000, 11, 15, 8, 30),
                        LocalDateTime.of(2000, 11, 15, 14, 30)),
                Arrays.asList(LocalDateTime.of(2001, 10, 13, 8, 30),
                        LocalDateTime.of(2001, 10, 13, 18, 30)),
                Arrays.asList(LocalDateTime.of(2001, 12, 21, 8, 30),
                        LocalDateTime.of(2001, 12, 21, 18, 30)),
                Arrays.asList(LocalDateTime.of(2019, 11, 5, 8, 30),
                        LocalDateTime.of(2019, 11, 5, 20, 30))
        ).collect(toList()));
        schedule.put(2, Stream.of(
                Arrays.asList(LocalDateTime.of(1999, 1, 1, 8, 30),
                        LocalDateTime.of(1999, 1, 1, 20, 30)),
                Arrays.asList(LocalDateTime.of(2019, 11, 5, 7, 30),
                        LocalDateTime.of(2019, 11, 5, 20, 30)),
                Arrays.asList(LocalDateTime.of(2019, 12, 5, 6, 30),
                        LocalDateTime.of(2019, 12, 5, 20, 30))
        ).collect(toList()));
    }

    public static void main(String[] args) {//example input: >java oz.AvailableDates 1999-01-01T10:15:30 2001-10-13T10:17:30
        List<LocalDateTime> appointments = Arrays.stream(args).map(LocalDateTime::parse).collect(toList());

        System.out.printf("Out of %n%s%n", schedule);
        appointments.forEach(appt ->
                System.out.printf("Following clinicians are available for 45min appointment starting at %s: %s%n", appt, getAvailable(appt))
        );
    }

    private static List<Integer> getAvailable(LocalDateTime appt) {
        return schedule.entrySet().stream().filter(e ->
                e.getValue().stream().anyMatch(day ->
                    day.get(0).isBefore(appt) //clinician's day starts before appointment
                            &&
                    day.get(1).isAfter(appt.plus(45, ChronoUnit.MINUTES))//clinician's day ends after appointment of 45min duration
                )
        ).map(Map.Entry::getKey).collect(toList());
    }
}
