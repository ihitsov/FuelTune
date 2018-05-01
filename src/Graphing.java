import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Graphing {
	
	Graphing(int row, int col, ArrayList<Float> mXax, ArrayList<Float> mYax){
//	int XSize=mXax.size();
//	int ize=mXax.size();
	    if (row>=0 && col>=0){
//	        float STD=CalculateAFR.STD.get(row).get(col);
//	        float Count=CalculateAFR.Points.get(row).get(col);
//	        float AFR=CalculateAFR.AFR.get(row).get(col);
//	        float Correction=AFRTable.CorrectionMap.get(row).get(col);
//			DecimalFormat format = new DecimalFormat("0.#");
//	        NumberFormat nf = new DecimalFormat("##.#");
//	        AFRTable.textArea.setText("AFR = "+nf.format(AFR)+"\n"+"STD = "+nf.format(STD)+"\n"+"Count = "+nf.format(Count)+"\n"+"Corr = "+format.format(Correction)+" %"+"\n");
	        
	        
	        HistogramDataset dataset = new HistogramDataset();
	        dataset.setType(HistogramType.RELATIVE_FREQUENCY);

			 	double[] data = new double[StaticVars.getAllData().get(row*(mXax.size()-1)+col).size()];

			 	for (int i = 0; i < StaticVars.getAllData().get(row*(mXax.size()-1)+col).size(); i++) {
				 		data[i] = (double)StaticVars.getAllData().get(row*(mXax.size()-1)+col).get(i);
			 	}
				try {
					dataset.addSeries("Hist",data,20); // Number of bins is 20
				} catch (Exception e1) {}
		        data=null;
				String plotTitle = "";
				String xAxis = "AFR";
				String yAxis = "Relative number of samples";
				PlotOrientation orientation = PlotOrientation.VERTICAL;

				boolean show = false;
				boolean toolTips = false;
				boolean urls = false;
				
				JFreeChart chart = ChartFactory.createHistogram(plotTitle, xAxis, yAxis,
				        dataset, orientation, show, toolTips, urls);
				chart.setBackgroundPaint(Color.white);
				dataset=null;

				ChartPanel chartpanel = new ChartPanel(chart);
				chartpanel.removeAll();
				chart=null;
				
				chartpanel.setDomainZoomable(false);
				chartpanel.setPreferredSize(new java.awt.Dimension(240,220));

				JPanel panel = new JPanel();
				panel.setBounds(826, 190, 240, 220);
				panel.removeAll();
				AFRTableClass.tableFrame.getContentPane().remove(panel);
				AFRTableClass.tableFrame.invalidate();
				AFRTableClass.tableFrame.validate();
				AFRTableClass.tableFrame.getContentPane().add(panel);
				panel.removeAll();
				panel.setVisible(true);
				panel.add(chartpanel, BorderLayout.CENTER);
				chartpanel=null;
				panel=null;

//			}	        		        
	        
	        XYSeries series = new XYSeries("Scatter");
		    series.clear();
			Float cellMin=30f;
			Float cellMax=0f;
			int idx=row*(mXax.size()-1)+col;	  // -1 because it is 1 bigger than the support point vector      
			for (int i = 0; i < StaticVars.getScatterXData().get(idx).size(); i++) {							      
				series.add((double)StaticVars.getScatterXData().get(idx).get(i),(double)StaticVars.getScatterYData().get(idx).get(i)); // Number of bins is 50
//				if (cellMin > StaticVars.getAllData().get(row*(mXax.size()-1)+col).get(i))
//				{
//					cellMin=StaticVars.getAllData().get(row*(mXax.size()-1)+col).get(i);
//				}
//				if (cellMax < StaticVars.getAllData().get(row*(mXax.size()-1)+col).get(i))
//				{
//					cellMax=StaticVars.getAllData().get(row*(mXax.size()-1)+col).get(i);
//				}
				cellMin=10f;
				cellMax=19f;
			}
		    XYSeriesCollection data1 = new XYSeriesCollection();
			data1.addSeries(series);
			series=null;
			String title = "";
			String xAxisLabel = "";
			String yAxisLabel = "";
			boolean legend = false;

			PlotOrientation orientation1 = PlotOrientation.VERTICAL;

			boolean toolTips1 = false;
			boolean urls1 = false;
			JFreeChart chart1 = ChartFactory.createScatterPlot(title, xAxisLabel, yAxisLabel, data1, orientation1, legend, toolTips1, urls1);
			chart1.setBackgroundPaint(Color.white);
			
	        XYPlot xyPlot = (XYPlot) chart1.getPlot();
	        xyPlot.setDomainCrosshairVisible(true);
	        xyPlot.setRangeCrosshairVisible(true);
	        data1=null;
			for (int i = 0; i < StaticVars.getScatterXData().get(idx).size(); i++) {							      
				xyPlot.setRenderer(new MyScatterRenderer(idx, cellMin, cellMax));
			}
	        
	        ValueAxis domainAxis = xyPlot.getDomainAxis();
	        ValueAxis rangeAxis = xyPlot.getRangeAxis();

	        domainAxis.setRange(mXax.get(col), mXax.get(col+1));//X axis range
	        int ROW;
	        if (StaticVars.isInvertedYAxis()==true) {ROW=mYax.size()-row-2;}
	        else {ROW=row;}
	        rangeAxis.setRange(mYax.get(ROW), mYax.get(ROW+1));// Y axis range
	        
			ChartPanel chartpanel1 = new ChartPanel(chart1);
			chartpanel1.removeAll();
	        chart1=null;
		
			chartpanel1.setDomainZoomable(false);
			if (StaticVars.isInvertedYAxis()==false) {
			chartpanel1.getChart().getXYPlot().getRangeAxis().setInverted(true);}
			chartpanel1.setPreferredSize(new java.awt.Dimension(240,220));

			JPanel panel1 = new JPanel();
			panel1.removeAll();
			panel1.setBounds(826, 420, 240, 220);
			AFRTableClass.tableFrame.getContentPane().remove(panel1);
			AFRTableClass.tableFrame.invalidate();
			AFRTableClass.tableFrame.validate();
			AFRTableClass.tableFrame.getContentPane().add(panel1);
			panel1.setVisible(true);
			panel1.add(chartpanel1, BorderLayout.CENTER);
			panel1=null;
			xyPlot=null;

	        }
	};
	public static void main(int row, int col)  {
//	    Graphing object = new Graphing(row, col); //creating object

}
	
}
