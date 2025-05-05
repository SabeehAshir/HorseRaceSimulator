import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.HashMap;


public class HorseRacingApp extends JFrame {
    private int trackLength = 15; // Default track length
    private Race race;
    private RaceTrackPanel raceTrackPanel;
    private HorseStatistics horseStatistics;

    public HorseRacingApp() {
        setTitle("Horse Racing Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize race
        
        race = new Race(trackLength);
        initializeHorses();

        // Create race track panel
        raceTrackPanel = new RaceTrackPanel(race);
        add(raceTrackPanel, BorderLayout.CENTER);
        

        // Create control panel
        JPanel controlPanel = new JPanel();
        JButton startButton = new JButton("Start Race");
        startButton.addActionListener(e -> startRace());
        controlPanel.add(startButton);
        
        JButton bettingButton = new JButton("Place Bet");
        bettingButton.addActionListener(e -> openBettingWindow());
        controlPanel.add(bettingButton);

        JButton customiseButton = new JButton("Customise Horses");
        customiseButton.addActionListener(e -> {
        ensureAllLanesHaveHorses(race.getLaneCount()); // Ensure all lanes have horses
        new HorseCustomization(this, race.getHorses(),raceTrackPanel).setVisible(true); // Pass the entire map of horses
        });
        controlPanel.add(customiseButton);

        Map<String,HorseStatistics> statisticsMap = new HashMap<String,HorseStatistics>();
        JButton statsButton = new JButton("View Statistics");
        statsButton.addActionListener(e -> {
        new StatisticsWindow(statisticsMap).setVisible(true);
        });
        controlPanel.add(statsButton);

        JSlider laneCountSlider = new JSlider(3, 10, 3); // Min 3 lane, max 10 lanes, default 3
        laneCountSlider.setMajorTickSpacing(1);
        laneCountSlider.setPaintTicks(true);
        laneCountSlider.setPaintLabels(true);
        laneCountSlider.addChangeListener(e -> {
            int laneCount = laneCountSlider.getValue();
            race.setLaneCount(laneCount);
        
            // Add placeholder horses for new lanes
            for (int i = 1; i <= laneCount; i++) {
                if (!race.getHorses().containsKey(i)) {
                    race.addHorse(new Horse((char) ('A' + i - 1), "Horse " + i, 0.5), i); // Add a default horse
                }
            }
        
            raceTrackPanel.repaint(); // Update the GUI
        });
        controlPanel.add(new JLabel("Lane Count:"));
        controlPanel.add(laneCountSlider);
        add(controlPanel, BorderLayout.SOUTH);

        JSlider trackLengthSlider = new JSlider(10, 30, 15); // Min 10, max 30, default 15
        trackLengthSlider.setMajorTickSpacing(50);
        trackLengthSlider.setPaintTicks(true);
        trackLengthSlider.setPaintLabels(true);
        trackLengthSlider.addChangeListener(e -> {
        int trackLength = trackLengthSlider.getValue();
        race.setTrackLength(trackLength);
        raceTrackPanel.repaint(); // Update the GUI
        });
        controlPanel.add(new JLabel("Track Length:"));
        controlPanel.add(trackLengthSlider);

        JComboBox<String> shapeSelector = new JComboBox<>(new String[] { "oval", "figure-eight", "zigzag" });
        shapeSelector.addActionListener(e -> {
        String selectedShape = (String) shapeSelector.getSelectedItem();
        race.setTrackShape(selectedShape);
        });
        controlPanel.add(new JLabel("Track Shape:"));
        controlPanel.add(shapeSelector);

        JComboBox<String> weatherSelector = new JComboBox<>(new String[] { "dry", "muddy", "icy" });
        weatherSelector.addActionListener(e -> {
        String selectedWeather = (String) weatherSelector.getSelectedItem();
        race.setWeatherCondition(selectedWeather);
        });
        controlPanel.add(new JLabel("Weather:"));
        controlPanel.add(weatherSelector);


        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void openBettingWindow() {
        PlayerAccount playerAccount = new PlayerAccount("Player", 1000.0); // Example account
        BettingWindow bettingWindow = new BettingWindow(playerAccount, race.getHorses());
        bettingWindow.setVisible(true);
    }
    private void initializeHorses() {
        race.addHorse(new Horse('A', "Lightning", 0.7), 1);
        race.addHorse(new Horse('B', "Thunder", 0.6), 2);
        race.addHorse(new Horse('C', "Storm", 0.5), 3);
        for (Horse horse : race.getHorses().values()) {
            horse.setImage(new ImageIcon("Horseimg.png")); // Default image
        }
    }

    private void startRace() {
        new Thread(() -> race.startRace(() -> raceTrackPanel.updateRace())).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HorseRacingApp::new);
    }
    private void ensureAllLanesHaveHorses(int laneCount) {
        for (int i = 1; i <= laneCount; i++) {
            if (!race.getHorses().containsKey(i)) {
                race.addHorse(new Horse((char) ('A' + i - 1), "Horse " + i, 0.5), i); // Add a default horse
            }
        }
    }
}