package UI;

import java.util.Random;

import javax.swing.*;
import java.awt.*;

import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.*;

public class testChart {
    public static void main(String[] args) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 1; i <= 7; i++) {
            Random ran = new Random();
            Double total = ran.nextDouble() * Math.pow(10, 8);
            dataset.addValue(total, "VND", String.valueOf(i));
        }

        JFreeChart chart = ChartFactory.createBarChart("BIỂU ĐỒ DOANH THU", "Ngày", "VND", dataset,
                PlotOrientation.VERTICAL, false, false, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(760, 567));

        JFrame frame = new JFrame();
        frame.add(chartPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
