import javax.swing.ImageIcon;

public class Horse {
    private String name;
    private char symbol;
    private int distanceTravelled;
    private boolean hasFallen;
    private double confidence;
    private ImageIcon image; // New field for the horse's image/icon

    public Horse(char symbol, String name, double confidence) {
        if (confidence < 0 || confidence > 1) {
            throw new IllegalArgumentException("Confidence must be between 0 and 1.");
        }
        this.symbol = symbol;
        this.name = name;
        this.confidence = confidence;
        this.distanceTravelled = 0;
        this.hasFallen = false;
        this.image = null; // Default to no image
    }

    public void moveForward() {
        distanceTravelled += (int) (Math.random() * 2 + 0.5); // Random movement
    }

    public void fall() {

        if(hasFallen) {
            return; // Already fallen, no need to fall again
        }
        hasFallen = true;
        this.setConfidence(Math.max(0.0, this.getConfidence() - 0.05));
        
    }
    

    public void goBackToStart() {
        distanceTravelled = 0;
        hasFallen = false;
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getDistanceTravelled() {
        return distanceTravelled;
    }

    public boolean hasFallen() {
        return hasFallen;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }
}