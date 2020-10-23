public class Lesson {

    private String type;
    private String venue;
    private String groupId;
    private Timeslot[] timeslots;

    public Lesson(String type, String venue, String groupId, Timeslot[] timeslots) {
        this.type = type;
        this.venue = venue;
        this.groupId = groupId;
        this.timeslots = timeslots;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVenue() {
        return this.venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Timeslot[] getTimeslots() {
        return this.timeslots;
    }

    public void setTimeslots(Timeslot[] timeslots) {
        this.timeslots = timeslots;
    }
}
