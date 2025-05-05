import javax.swing.*;
import java.awt.*;


public class HorseRacingApp extends JFrame {
    private int trackLength = 15; // Default track length
    private Race race;
    private RaceTrackPanel raceTrackPanel;

    public HorseRacingApp() {
        setTitle("Horse Racing Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize race
        
        race = new Race(trackLength);
        initializeHorses();

        // Create race track panel
        

        // Create control panel
        JPanel controlPanel = new JPanel();
        JButton startButton = new JButton("Start Race");
        startButton.addActionListener(e -> startRace());
        controlPanel.add(startButton);
        
        JButton bettingButton = new JButton("Place Bet");
        bettingButton.addActionListener(e -> openBettingWindow());
        controlPanel.add(bettingButton);

        JButton customiseButton = new JButton("Customise Horses");
        customiseButton.addActionListener(e -> openCustomisationWindow());
        controlPanel.add(customiseButton);

        JSlider laneCountSlider = new JSlider(3, 10, 3); // Min 3 lane, max 10 lanes, default 3
        laneCountSlider.setMajorTickSpacing(1);
        laneCountSlider.setPaintTicks(true);
        laneCountSlider.setPaintLabels(true);
        laneCountSlider.addChangeListener(e -> {
            int laneCount = laneCountSlider.getValue();
            race.setLaneCount(laneCount);
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

        raceTrackPanel = new RaceTrackPanel(race);
        add(raceTrackPanel, BorderLayout.CENTER);

        

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void openBettingWindow() {
        PlayerAccount playerAccount = new PlayerAccount("Player", 1000.0); // Example account
        BettingWindow bettingWindow = new BettingWindow(playerAccount, race.getHorses());
        bettingWindow.setVisible(true);
    }
    private void openCustomisationWindow() {
        HorseCustomisationWindow customisationWindow = new HorseCustomisationWindow(race.getHorses());
        customisationWindow.setVisible(true);
    }

    private void initializeHorses() {
        race.addHorse(new Horse('A', "Lightning", 0.7), 1);
        race.addHorse(new Horse('B', "Thunder", 0.6), 2);
        race.addHorse(new Horse('C', "Storm", 0.5), 3);
    }

    private void startRace() {
        new Thread(() -> race.startRace(() -> raceTrackPanel.updateRace())).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HorseRacingApp::new);
    }
}