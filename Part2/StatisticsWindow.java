import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class StatisticsWindow extends JFrame {
    public StatisticsWindow(Map<String, HorseStatistics> statisticsMap) {
        setTitle("Horse Statistics");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a table to display statistics
        String[] columnNames = { "Horse Name", "Avg Speed", "Win %", "Best Time", "Total Races" };
        Object[][] data = new Object[statisticsMap.size()][columnNames.length];

        int i = 0;
        for (Map.Entry<String, HorseStatistics> entry : statisticsMap.entrySet()) {
            HorseStatistics stats = entry.getValue();
            data[i][0] = stats.getHorse();
            data[i][1] = stats.getAverageSpeed();
            data[i][2] = stats.getWinPercentage();
            data[i][3] = stats.getBestTime();
            data[i][4] = stats.getTotalRaces();
            i++;
        }
        

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Add a close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);
    }
}