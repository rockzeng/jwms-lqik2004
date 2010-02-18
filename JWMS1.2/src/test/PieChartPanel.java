/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 *
 * @author res0w
 */
public class PieChartPanel {

    public ChartPanel PieChartPanel() {
        DefaultPieDataset pieDatabase = new DefaultPieDataset();
        pieDatabase.setValue("YT", 1198.3);
        pieDatabase.setValue("FR", 1233.44);
        pieDatabase.setValue("FN", 2633.44);
        pieDatabase.setValue("DEH", 9933.44);
        pieDatabase.setValue("JR", 5833.44);

        JFreeChart piechart=ChartFactory.createPieChart(null, pieDatabase, true, true, false);
        ChartPanel panel=new ChartPanel(piechart, 500, 500, 500, 500, 500, 500, true, true, true, true, true, true);
        return panel;
    }
}
