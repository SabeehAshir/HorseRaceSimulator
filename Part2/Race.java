
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
    private final int raceLength;
    private final Map<Integer,Horse> horses;
    private int numberOfLanes= 0;

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

            horse.goBackToStart();
        }
    
        // Race loop
        while (true) {
            // Move each horse
            for (Horse horse : horses.values()) {
                
                moveHorseIfNotFallen(horse);
                
            }
    
            // Update GUI
            if (updateGUI != null) {
                updateGUI.run();
            }
    
            // Check for winner
            for (Horse horse : horses.values()) {
                if ( hasWonRace(horse)) {
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
                if (horse != null && !horse.hasFallen()) {
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
    private void moveHorseIfNotFallen(Horse theHorse)
    {
        if (theHorse.hasFallen()){
             return;
        }
        
        if (Math.random() < (theHorse.getConfidence() * theHorse.getConfidence())) {
            theHorse.fall();
            return;
        }
        
            theHorse.moveForward();
        
    }
        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean hasWonRace(Horse theHorse)
    {
        return theHorse.getDistanceTravelled() >= raceLength;
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
}
