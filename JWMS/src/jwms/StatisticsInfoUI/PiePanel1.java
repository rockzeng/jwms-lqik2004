/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jwms.StatisticsInfoUI;

import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 *
 * @author res0w
 * @version 0.1
 * @since 2009-11-10
 */
public class PiePanel1 {

    private PieDataset createPieDatabase() {
        DefaultPieDataset pd = new DefaultPieDataset();
        pd.setValue("One", new Double(43.2));
        pd.setValue("Two", new Double(10.0));
        pd.setValue("Three", new Double(27.5));
        pd.setValue("Four", new Double(17.5));
        pd.setValue("Five", new Double(11.0));
        pd.setValue("Six", new Double(19.4));
        return pd;
    }

    private JFreeChart createChart() {
        JFreeChart chart = ChartFactory.createPieChart(null, createPieDatabase(), true, true, false);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionOutlinesVisible(true);
        plot.setLabelLinksVisible(false);
        plot.setNoDataMessage("No data available");
        return chart;
    }

    public JPanel getPanel() {
        JFreeChart chart = this.createChart();
        return new ChartPanel(chart);
    }
}
