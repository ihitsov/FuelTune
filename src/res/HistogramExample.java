package res;

import java.io.*;
import java.util.Random;
import org.jfree.chart.*;
import org.jfree.data.statistics.*;
import org.jfree.chart.plot.PlotOrientation;

 public class HistogramExample {
       public static void main(String[] args) {
       double[] value = new double[100];
       Random generator = new Random();
       for (int i=1; i < 100; i++) {
       value[i] = generator.nextDouble();
           int number = 25;
       HistogramDataset dataset = new HistogramDataset();
       dataset.setType(HistogramType.RELATIVE_FREQUENCY);
       dataset.addSeries("Histogram",value,number);
       String plotTitle = "Histogram"; 
       String xaxis = "number";
       String yaxis = "value"; 
       PlotOrientation orientation = PlotOrientation.VERTICAL; 
       boolean show = false; 
       boolean toolTips = false;
       boolean urls = false; 
       JFreeChart chart = ChartFactory.createHistogram( plotTitle, xaxis, yaxis, 
                dataset, orientation, show, toolTips, urls);
       int width = 500;
       int height = 300; 
        try {
        ChartUtilities.saveChartAsPNG(new File("histogram.PNG"), chart, width, height);
        } catch (IOException e) {}
         }
   }
 }