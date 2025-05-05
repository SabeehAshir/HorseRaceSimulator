import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class RaceTrackPanel extends JPanel {
    private final Race race;
    //private final int raceLength;
    //rivate final Map<Integer,Horse> horses;
    private final Image horseImage;
    private final Image fallenHorseImage;

    public RaceTrackPanel(Race race) {
        this.race=race;

        // Load horse and fallen horse images
        this.horseImage = Toolkit.getDefaultToolkit().getImage("Horseimg.png");
        this.fallenHorseImage = Toolkit.getDefaultToolkit().getImage("fallen.png"); 

        updatePreferredSize();
        setBackground(Color.LIGHT_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int trackLength = race.getTrackLength();
        int numberOfLanes = race.getLaneCount();
        Map<Integer, Horse> horses = race.getHorses();
    
        // Draw track lanes0
        g.setColor(Color.BLACK);
        for (int i = 1; i <= numberOfLanes; i++) { // Dynamically handle the number of lanes
            int y = 20 + (i - 1) * 60; // Calculate the Y position for each lane
            g.drawLine(20, y, trackLength * 30 + 20, y); // Draw the lane line
        }
    
        // Draw horses
        for (Map.Entry<Integer, Horse> entry : horses.entrySet()) { // Iterate over the map entries
            int lane = entry.getKey(); // Get the lane number
            Horse horse = entry.getValue(); // Get the horse in the lane
            if (horse != null) { // If there is a horse in the lane
                int x = 20 + horse.getDistanceTravelled() * 30; // Calculate the X position based on distance
                int y = 40 + (lane - 1) * 60; // Calculate the Y position based on the lane
    
                // Draw the appropriate image based on the horse's status
                if (horse.hasFallen()) {
                    g.drawImage(fallenHorseImage, x, y - 20, 40, 40, this); // Fallen horse image
                } else {
                    g.drawImage(horseImage, x, y - 20, 40, 40, this); // Normal horse image
                }
    
                // Draw the horse's name
                g.setColor(Color.BLACK);
                g.drawString(horse.getName(), x + 45, y); // Display the horse's name next to the image
            }
        }
    }

    public void updateRace() {
        repaint();
    }
    
    private void updatePreferredSize() {
        // Dynamically calculate the preferred size based on the race's track length and number of lanes
        int trackLength = race.getTrackLength();
        int numberOfLanes = race.getLaneCount();
        setPreferredSize(new Dimension(trackLength * 30 + 50, numberOfLanes * 60 + 40));
        revalidate(); // Notify the layout manager of the size change
    }
}