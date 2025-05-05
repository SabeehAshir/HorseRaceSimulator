import javax.swing.*;
import java.awt.*;

import java.util.Map;

public class HorseCustomization extends JDialog {
    private String[] horseImages = {
            "Horseimg.png", "BlackHorse.png", "BrownHorse.png",
            "BlueHorse.png", "GreyHorse.png", "PurpleHorse.png",
    };
    private Map<Integer, Horse> horses; // Map of horses to customize
    private JTabbedPane tabbedPane;
    private RaceTrackPanel raceTrackPanel; // Reference to
  

    public HorseCustomization(JFrame parent, Map<Integer, Horse> horses, RaceTrackPanel raceTrackPanel) {
        super(parent, "Customize Horses", true);
        this.horses = horses;
        this.raceTrackPanel = raceTrackPanel; 

      

        setLayout(new BorderLayout());
        setSize(600, 600);
        setLocationRelativeTo(parent);

        // Create a tabbed pane for each horse
        tabbedPane = new JTabbedPane();
        for (Map.Entry<Integer, Horse> entry : horses.entrySet()) {
            int lane = entry.getKey();
            Horse horse = entry.getValue();
            if (horse != null) { // Skip null horses
                tabbedPane.addTab("Lane " + lane, createHorseCustomizationPanel(horse));
            }
        }

        // Add the tabbed pane to the dialog
        add(tabbedPane, BorderLayout.CENTER);

        // Save Button
        JButton saveButton = new JButton("Save All");
        saveButton.addActionListener(e -> saveAllCustomizations());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createHorseCustomizationPanel(Horse horse) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));

        // Breed Selector
        panel.add(new JLabel("Breed:"));
        JComboBox<String> breedSelector = new JComboBox<>(new String[] { "Thoroughbred", "Arabian", "Quarter Horse" });
        breedSelector.setSelectedItem(horse.getBreed());
        breedSelector.addActionListener(e -> horse.setBreed((String) breedSelector.getSelectedItem()));
        panel.add(breedSelector);
        // Symbol Image Selector
        panel.add(new JLabel("Symbol Image:"));
        JPanel imageSelectionPanel = createImageSelectionPanel(horse);
        panel.add(imageSelectionPanel);

        // Saddle Selector
        panel.add(new JLabel("Saddle:"));
        JComboBox<String> saddleSelector = new JComboBox<>(new String[] { "Standard", "Lightweight", "Decorative" });
        saddleSelector.setSelectedItem(horse.getSaddle());
        saddleSelector.addActionListener(e -> horse.setSaddle((String) saddleSelector.getSelectedItem()));
        panel.add(saddleSelector);

        // Horseshoe Selector
        panel.add(new JLabel("Horseshoes:"));
        JComboBox<String> horseshoeSelector = new JComboBox<>(new String[] { "Regular", "Lightweight", "Grip" });
        horseshoeSelector.setSelectedItem(horse.getHorseshoes());
        horseshoeSelector.addActionListener(e -> horse.setHorseshoes((String) horseshoeSelector.getSelectedItem()));
        panel.add(horseshoeSelector);

        return panel;
    }

    private JPanel createImageSelectionPanel(Horse horse) {
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout(10, 10)); // Use BorderLayout for navigation and image display
    
        // Track the current image index
        final int[] currentIndex = {0};
    
        // Label to display the current image
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(200, 200)); // Increase size for better visibility
        updateImageLabel(imageLabel, currentIndex[0]); // Display the first image
    
        // Navigation buttons
        JButton prevButton = new JButton("Previous");
        JButton nextButton = new JButton("Next");
    
        // Action for "Previous" button
        prevButton.addActionListener(e -> {
            if (currentIndex[0] > 0) {
                currentIndex[0]--;
                updateImageLabel(imageLabel, currentIndex[0]);
            }
        });
    
        // Action for "Next" button
        nextButton.addActionListener(e -> {
            if (currentIndex[0] < horseImages.length - 1) {
                currentIndex[0]++;
                updateImageLabel(imageLabel, currentIndex[0]);
            }
        });
    
        // Select button to set the current image as the horse's symbol
        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(e -> {
            horse.setImage(new ImageIcon(horseImages[currentIndex[0]])); // Set the selected image for the horse
            JOptionPane.showMessageDialog(this, "Image selected: " + horseImages[currentIndex[0]]);
        });
    
        // Add components to the panel
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new FlowLayout());
        navigationPanel.add(prevButton);
        navigationPanel.add(nextButton);
    
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        imagePanel.add(navigationPanel, BorderLayout.SOUTH);
        imagePanel.add(selectButton, BorderLayout.NORTH);
    
        return imagePanel;
    }
    
    private void updateImageLabel(JLabel imageLabel, int index) {
        ImageIcon icon = new ImageIcon(horseImages[index]);
        Image scaledImage = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH); // Scale image to 200x200
        imageLabel.setIcon(new ImageIcon(scaledImage));
    }
    

   
    private void saveAllCustomizations() {
        try {
            JOptionPane.showMessageDialog(this, "All customizations saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
    
            // Refresh the race track panel to reflect updated horse images
            SwingUtilities.invokeLater(() -> {
                if (raceTrackPanel != null) {
                    raceTrackPanel.repaint(); // Ensure raceTrackPanel is not null
                } else {
                    System.err.println("Error: raceTrackPanel is null.");
                }
            });
    
            dispose(); // Close the customization dialog
        } catch (Exception ex) {
            ex.printStackTrace(); // Print the stack trace for debugging
            JOptionPane.showMessageDialog(this, "An error occurred while saving customizations: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}