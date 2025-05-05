
import java.lang.Math;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author Sabeeh Ashir
 * @version 1.0
 */
public class Race
{
    private  int raceLength;
    private final Map<Integer,Horse> horses;
    private int numberOfLanes= 0;
    private String trackShape = "oval"; // Default track shape
    private String weatherCondition = "dry"; // Default weather condition
    private final Map<String, HorseStatistics> statisticsMap = new HashMap<>(); // Map to store horse statistics

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance)
    {
        if (distance <= 0) throw new IllegalArgumentException("Race length must be positive.");
        raceLength = distance;
        horses = new HashMap<>();
    }
    
    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber) {
        if (laneNumber < 1) {
            throw new IllegalArgumentException("Invalid lane number: " + laneNumber);
        }
        if (horses.containsKey(laneNumber)) {
            throw new IllegalArgumentException("Lane " + laneNumber + " is already occupied by another horse.");
        }
         horses.put(laneNumber, theHorse);

        if (laneNumber > numberOfLanes) {
            numberOfLanes = laneNumber;
        }

        
    }
    public Map<Integer,Horse> getHorses() {
        return horses;
    }
    
    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void startRace(Runnable updateGUI) {
        long startTime = System.currentTimeMillis();
        // Reset all horses to start
        for (Horse horse : horses.values()) {
            if (horse != null) { // Skip null horses
                horse.goBackToStart();
            }
        }
    
        // Race loop
        while (true) {
            // Move each horse
            for (Horse horse : horses.values()) {
                if (horse != null) { // Skip null horses
                    moveHorseIfNotFallen(horse);
                }
            }
    
            // Update GUI
            if (updateGUI != null) {
                updateGUI.run();
            }
    
            // Check for winner
            for (Horse horse : horses.values()) {
                if (horse != null && hasWonRace(horse)) { // Skip null horses
                    System.out.println("Horse " + horse.getName() + " has won the Race!");
                    adjustConfidenceForWinner(horse);
                    long endTime = System.currentTimeMillis(); // Record the end time
                    displayStatistics(horse, startTime, endTime); // Call displayStatistics
                    return;
                }
            }
    
            // Check if all horses fell
            boolean allFallen = true;
            for (Horse horse : horses.values()) {
                if (horse != null && !horse.hasFallen()) { // Skip null horses
                    allFallen = false;
                    break;
                }
            }
            if (allFallen) {
                System.out.println("All horses have fallen!");
                long endTime = System.currentTimeMillis(); // Record the end time
                displayStatistics(null, startTime, endTime); // Pass null as no horse won
                return;
            }
    
            // Short pause
            try {
                Thread.sleep(100);
            } catch (Exception e) {}
        }
    }
    
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorseIfNotFallen(Horse theHorse) {
        double speedModifier = 1.0; // Default speed modifier
        if (theHorse == null || theHorse.hasFallen()) {
            return;
        }
    
        double baseFallProbability = 0.05; // Minimum chance of falling (5%)
        double fallProbability = baseFallProbability + (0.1 * (1 - theHorse.getConfidence())); // Add confidence-based probability
    
        if (Math.random() < fallProbability) {
            theHorse.fall();
            return;
        }

        switch (weatherCondition) {
            case "dry":
                speedModifier = 1.0; 
                break;
            case "muddy":
                speedModifier = 0.7; 
            case "icy":
                speedModifier = 0.5; 
                break;
        }
    
        switch (trackShape) {
            case "oval":
                theHorse.moveForward(speedModifier); // Normal movement
                break;
            case "figure-eight":
                theHorse.moveForward(speedModifier);
                if (theHorse.getDistanceTravelled() % 50 == 0) { // Slow down at intersections
                    theHorse.setDistanceTravelled(Math.max(0, theHorse.getDistanceTravelled() - 1)); // Ensure distance doesn't go below 0
                }
                break;
            case "zigzag":
                theHorse.moveForward(speedModifier);
                if (theHorse.getDistanceTravelled() % 30 == 0) { // Adjust speed at sharp turns
                    theHorse.setDistanceTravelled(Math.max(0, theHorse.getDistanceTravelled() - 2)); // Ensure distance doesn't go below 0
                }
                break;
        }
    }
        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean hasWonRace(Horse theHorse) {
        if (theHorse == null) { // Skip null horses
            return false;
        }
        if(theHorse.getDistanceTravelled() >= raceLength){
            theHorse.setPosition(1);
            return true;
        } else {
            return false;

        } 
    }

    private void adjustConfidenceForWinner(Horse winner) 
    {
       
        winner.setConfidence(Math.min(1.0, winner.getConfidence() + 0.1));
           
    }
    private void displayStatistics(Horse winner, long startTime, long endTime) {
    String message = "Winner: " + (winner != null ? winner.getName() : "No winner") +
                     "\nTime Taken: " + (endTime - startTime) / 1000.0 + " seconds";
    JOptionPane.showMessageDialog(null, message, "Race Statistics", JOptionPane.INFORMATION_MESSAGE);
    }
    public void setLaneCount(int laneCount) {
        if (laneCount < 1) {
            throw new IllegalArgumentException("Lane count must be at least 1.");
        }
    
        // Add new lanes if needed
        for (int i = 1; i <= laneCount; i++) {
            if (!horses.containsKey(i)) {
                horses.put(i, null); // Add empty lanes
            }
        }
    
        // Remove extra lanes if needed
        for (int i = laneCount + 1; i <= numberOfLanes; i++) {
            horses.remove(i); // Remove lanes beyond the new count
        }
    
        numberOfLanes = laneCount;
    }
    public void setTrackLength(int length) {
        if (length <= 0) throw new IllegalArgumentException("Track length must be positive.");
        this.raceLength = length;
    }
    public int getTrackLength() {
        return this.raceLength;
    }
    public int getLaneCount() {
        return numberOfLanes;
    }
    public void setTrackShape(String shape) {
        if (!shape.equals("oval") && !shape.equals("figure-eight") && !shape.equals("zigzag")) {
            throw new IllegalArgumentException("Invalid track shape: " + shape);
        }
        this.trackShape = shape;
    }
    public void setWeatherCondition(String condition) {
        if (!condition.equals("dry") && !condition.equals("muddy") && !condition.equals("icy")) {
            throw new IllegalArgumentException("Invalid weather condition: " + condition);
        }
        this.weatherCondition = condition;
    }
    public void endRace(long startTime, long endTime) {
        double raceTime = (endTime - startTime) / 1000.0; // Calculate race time in seconds
        for (Horse horse : horses.values()) {
            double speed = horse.getDistanceTravelled() /raceTime; // Example calculation
            boolean won = horse.getPosition() == 1; // Check if the horse won
            String trackCondition = this.weatherCondition; // Current track condition
    
            // Update statistics
            HorseStatistics stats = statisticsMap.get(horse.getName());
            if (stats == null) {
                stats = new HorseStatistics(horse.getName());
                statisticsMap.put(horse.getName(), stats);
            }
            stats.addRace(speed, raceTime, won, trackCondition);
        }
    }
    
}
