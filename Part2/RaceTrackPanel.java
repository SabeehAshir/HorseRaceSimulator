import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class RaceTrackPanel extends JPanel {
    private final int raceLength;
    private final Map<Integer,Horse> horses;
    private final Image horseImage;
    private final Image fallenHorseImage;

    public RaceTrackPanel(int raceLength, Map<Integer,Horse> horses) {
        this.raceLength = raceLength;
        this.horses = horses;

        // Load horse and fallen horse images
        this.horseImage = Toolkit.getDefaultToolkit().getImage("Horseimg.png");
        this.fallenHorseImage = Toolkit.getDefaultToolkit().getImage("fallen.png"); 

        setPreferredSize(new Dimension(raceLength * 30 + 50, horses.size() * 60 + 40));
        setBackground(Color.LIGHT_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw track lanes
        g.setColor(Color.BLACK);
        for (int i = 0; i <= horses.size(); i++) {
            int y = 20 + i * 60;
            g.drawLine(20, y, raceLength * 30 + 20, y);
        }

        // Draw horses
        for (int i = 0; i < horses.size(); i++) {
            Horse horse = horses.get(i);
            if (horse != null) {
                int x = 20 + horse.getDistanceTravelled() * 30;
                int y = 40 + i * 60;

                // Draw appropriate image based on horse's status
                if (horse.hasFallen()) {
                    g.drawImage(fallenHorseImage, x, y - 20, 40, 40, this);
                } else {
                    g.drawImage(horseImage, x, y - 20, 40, 40, this);
                }

                // Draw horse name
                g.setColor(Color.BLACK);
                g.drawString(horse.getName(), x + 45, y);
            }
        }
    }

    public void updateRace() {
        repaint();
    }
}