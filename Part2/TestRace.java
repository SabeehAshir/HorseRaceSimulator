

public class TestRace {
    public static void main(String[] args) {
        Race race = new Race(15);
        
        // Create some horses with different confidence levels
        Horse horse1 = new Horse('A', "Lightning", 0.9);
        Horse horse2 = new Horse('B', "Thunder", 0.9);
        Horse horse3 = new Horse('C', "Storm", 0.9);
        
        // Add horses to the race in different lanes
        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);
        race.addHorse(horse3, 3);
        
        // Start the race!
        System.out.println("Starting the race...");
        //race.startRace();
    }
}
