import javax.swing.*;
import java.awt.*;


public class HorseRacingApp extends JFrame {
    private Race race;
    private RaceTrackPanel raceTrackPanel;

    public HorseRacingApp() {
        setTitle("Horse Racing Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize race
        int raceLength = 20;
        race = new Race(raceLength);
        initializeHorses();

        // Create race track panel
        raceTrackPanel = new RaceTrackPanel(raceLength, race.getHorses());
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
        customiseButton.addActionListener(e -> openCustomisationWindow());
        controlPanel.add(customiseButton);

        JSlider laneCountSlider = new JSlider(1, 10, 3); // Min 1 lane, max 10 lanes, default 3
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

        JComboBox<String> shapeSelector = new JComboBox<>(new String[] { "oval", "figure-eight", "zigzag" });
        shapeSelector.addActionListener(e -> {
        String selectedShape = (String) shapeSelector.getSelectedItem();
        race.setTrackShape(selectedShape);
        });
        controlPanel.add(new JLabel("Track Shape:"));
        controlPanel.add(shapeSelector);

        

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