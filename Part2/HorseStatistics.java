import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HorseStatistics implements Serializable {
    private String horseName;
    private int totalRaces;
    private int wins;
    private double totalSpeed; // Sum of speeds for calculating average
    private double bestTime; // Best finishing time
    private List<RaceRecord> raceHistory; // List of past race performances

    public HorseStatistics(String horseName) {
        this.horseName = horseName;
        this.totalRaces = 0;
        this.wins = 0;
        this.totalSpeed = 0.0;
        this.bestTime = Double.MAX_VALUE; // Initialize to a very high value
        this.raceHistory = new ArrayList<>();
    }

    public void addRace(double speed, double time, boolean won, String trackCondition) {
        totalRaces++;
        totalSpeed += speed;
        if (won) wins++;
        if (time < bestTime) bestTime = time;

        // Add to race history
        raceHistory.add(new RaceRecord(speed, time, won, trackCondition));
    }

    public double getAverageSpeed() {
        return totalRaces > 0 ? totalSpeed / totalRaces : 0.0;
    }

    public double getWinPercentage() {
        return totalRaces > 0 ? (wins * 100.0) / totalRaces : 0.0;
    }

    public double getBestTime() {
        return bestTime;
    }

    public List<RaceRecord> getRaceHistory() {
        return raceHistory;
    }
    public String getHorse() {
        return horseName;
    }
    public int getTotalRaces() {
        return totalRaces;
    }
}

class RaceRecord implements Serializable {
    private double speed;
    private double time;
    private boolean won;
    private String trackCondition;

    public RaceRecord(double speed, double time, boolean won, String trackCondition) {
        this.speed = speed;
        this.time = time;
        this.won = won;
        this.trackCondition = trackCondition;
    }

    public double getSpeed() {
        return speed;
    }

    public double getTime() {
        return time;
    }

    public boolean isWon() {
        return won;
    }

    public String getTrackCondition() {
        return trackCondition;
    }
}