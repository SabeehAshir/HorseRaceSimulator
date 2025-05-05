import javax.swing.ImageIcon;

public class Horse {
    private String name;
    private char symbol;
    private int distanceTravelled;
    private boolean hasFallen;
    private double confidence;
    private ImageIcon image; // New field for the horse's image/icon
    private String breed;
    private String coatColor;
    private String saddle;
    private String horseshoes;
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

    public void moveForward(double speedModifier) {
        distanceTravelled += (int) (Math.random() * 3 * confidence * speedModifier + 1); // Random value between 1 and 3, scaled by confidence
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
    public void setDistanceTravelled(int distance) {
        this.distanceTravelled = distance;
    }
    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getCoatColor() {
        return coatColor;
    }

    public void setCoatColor(String coatColor) {
        this.coatColor = coatColor;
    }
    public String getSaddle() {
        return saddle;
    }
    public void setSaddle(String saddle) {
        this.saddle = saddle;
    }
    public String getHorseshoes() {
        return horseshoes;
    }
    public void setHorseshoes(String horseshoes) {
        this.horseshoes = horseshoes;
    }
}