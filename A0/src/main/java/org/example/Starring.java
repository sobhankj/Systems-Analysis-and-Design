package org.example;

public class Starring {
    private final String movieName;
    private final Date startDate;
    private final Date endDate;

    public Starring(String movieName, Date startDate, Date endDate) {
        if (movieName == null || movieName.isEmpty())
            throw new IllegalArgumentException("Movie name can't be null or empty");
        if (startDate == null || endDate == null)
            throw new IllegalArgumentException("Dates cannot be null");
        if (startDate.compareTo(endDate) > 0)
            throw new IllegalArgumentException("Start date must be before or equal to end date");

        this.movieName = movieName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean overlapsWith(Starring other) {
        return this.endDate.compareTo(other.startDate) >= 0 && this.startDate.compareTo(other.endDate) <= 0;
    }

    public int durationInDays() {
        int duration = 0;
        Date temp = startDate;
        while (!temp.equals(endDate)) {
            temp = temp.nextDay();
            duration++;
        }
        return duration + 1; // add with end day
    }

    @Override
    public String toString() {
        return "Movie: " + movieName + ", Start: " + startDate + ", End: " + endDate;
    }

    public String getMovieName() {
        return movieName;
    }
}

