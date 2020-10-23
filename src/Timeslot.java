import java.time.*;

public class Timeslot {

    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    private int[] weeks;

    public DayOfWeek getDay() {
        return this.day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public int[] getWeeks() {
        return this.weeks;
    }

    public void setWeeks(int[] weeks) {
        this.weeks = weeks;
    }

}