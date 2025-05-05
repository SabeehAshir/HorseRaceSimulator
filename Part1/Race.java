import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
/**
 * three horses with dynamic lane number in a racing game
 *
 * 
 * @author Sabeeh Ashir
 * @version 1.0
 */
public class Race
{
    private final int raceLength;
    private final List<Horse> horses;
    private static final double BASE_FALL_PROBABILITY = 0.1;

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
        horses = new ArrayList<>();
    }
    
    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber)
    {
        while (horses.size() < laneNumber) {
            horses.add(null); // Fill empty lanes with null
        }
        if (laneNumber >= 1) {
            horses.set(laneNumber - 1, theHorse);
        } else {
            System.out.println("Invalid lane number: " + laneNumber);
        }
    }
    
    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void startRace() {
        // Reset all horses to start
        for (Horse horse : horses) {
            if (horse != null) {
                horse.goBackToStart();
            }
        }
    
        // Race loop
        while (true) {
            // Move each horse
            for (Horse horse : horses) {
                if (horse != null && !horse.hasFallen()) {
                    moveHorse(horse);
                }
            }
    
            printRace();
    
            // Check for winner
            for (Horse horse : horses) {
                if (horse != null && raceWonBy(horse)) {
                    announceWinner();
                    adjustConfidence();
                    return;
                }
            }
    
            // Check if all horses fell
            boolean allFallen = true;
            for (Horse horse : horses) {
                if (horse != null && !horse.hasFallen()) {
                    allFallen = false;
                    break;
                }
            }
            if (allFallen) {
                System.out.println("All horses have fallen!");
                announceWinner();
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
    private void moveHorse(Horse theHorse)
    {
        if (theHorse == null || theHorse.hasFallen()){
             return;
        }

        if (Math.random() < theHorse.getConfidence()) {
            //theHorse.moveForward();
        }

        if (Math.random() < (BASE_FALL_PROBABILITY * theHorse.getConfidence() * theHorse.getConfidence())) {
            theHorse.fall();
        }
    }
        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
    {
        return theHorse != null && theHorse.getDistanceTravelled() >= raceLength;
    }

    public void announceWinner() 
    {
        Horse winner = null;
        
        for (Horse theHorse : horses) {
            if (theHorse != null && raceWonBy(theHorse)) {
                winner = theHorse;
                break;
            }
        }
    
        if (winner != null) {
            System.out.println(winner.getName() + " wins the race!");
        } else {
            System.out.println("No winner :(");
        }
    }

    
    private void adjustConfidence() 
    {
        Horse winner = null;
        for (Horse theHorse : horses) {
            if (theHorse != null && raceWonBy(theHorse)) {
                winner = theHorse;
                break;
            }
        }
        
        for (Horse theHorse : horses) {
            if (theHorse == null) continue;
            if (theHorse == winner) {
                theHorse.setConfidence(Math.min(1.0, theHorse.getConfidence() + 0.1));
            } else if (theHorse.hasFallen()) {
                theHorse.setConfidence(Math.max(0.0, theHorse.getConfidence() - 0.05));
            }
        }
    }
    
    /***
     * Print the race on the terminal
     */
    private void printRace() {
        System.out.print("\033[H\033[2J"); // Clear terminal 
        System.out.flush();

        multiplePrint('=', raceLength + 3);
        System.out.println();

        horses.forEach(theHorse -> {
            printLane(theHorse);
            System.out.println();
        });

        multiplePrint('=', raceLength + 3);
        System.out.println();
    }

    private void printLane(Horse theHorse) {
        System.out.print('|');
        int spacesBefore = (theHorse != null) ? theHorse.getDistanceTravelled() : 0;
        multiplePrint(' ', spacesBefore);

        if (theHorse == null) {
            System.out.print(' ');
        } else if (theHorse.hasFallen()) {
            System.out.print('X');
        } else {
            System.out.print(theHorse.getSymbol());
        }

        int spacesAfter = raceLength - spacesBefore;
        multiplePrint(' ', spacesAfter);
        System.out.print('|');

        if (theHorse != null) {
            String status = theHorse.hasFallen() ? " (Fallen)" : "";
            System.out.printf("  %s (%.2f)%s", 
                             theHorse.getName(), 
                             theHorse.getConfidence(), 
                             status);
        } else {
            System.out.print("  Empty lane");
        }
    }

    private void multiplePrint(char c, int times) {
        for (int i = 0; i < times; i++) {
            System.out.print(c);
        }
    }
}
