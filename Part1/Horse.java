

/**
 * This class represents a horse in a horse racing game.
>>>>>>> gui-development
 * 
 * @author Sabeeh Ashir
 * @version 1.0
 */
public class Horse
{
     private String name;
    private char symbol;
    private int distanceTravelled;
    private boolean hasFallen;
    private double confidence;

    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
        
        this.symbol = horseSymbol;
        this.name = horseName;
        this.confidence = horseConfidence;
        this.distanceTravelled = 0;
        this.hasFallen = false;
    }
    
    
    
    //Other methods of class Horse
    public void fall()
    {
        if(hasFallen) {
            return; // Already fallen, no need to fall again
        }
        hasFallen = true;
        this.setConfidence(Math.max(0.0, this.getConfidence() - 0.05));
    }
    
    public double getConfidence()
    {
        return confidence;
    }
    
    public int getDistanceTravelled()
    {
        return distanceTravelled;
    }
    
    public String getName()
    {
        return name;
    }
    
    public char getSymbol()
    {
        return symbol;
    }
    
    public void goBackToStart()
    {
        distanceTravelled = 0;
        hasFallen = false;
    }
    
    public boolean hasFallen()
    {
        return hasFallen;
    }

    public void moveForward()
    {
        distanceTravelled += (int) (Math.random() * 2 * confidence + 0.5); // Random movement
    }

    public void setConfidence(double newConfidence)
    {
        if (newConfidence <0.0 || newConfidence > 1.0) {
            throw new IllegalArgumentException("Confidence must be between 0.0 and 1.0");
        }
        this.confidence = newConfidence;
    }
    
    public void setSymbol(char newSymbol)
    {
        this.symbol = newSymbol;
    }
    
}
