import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JSlider;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.event.MouseMotionAdapter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JScrollPane;

public class AFRTableClass{
	public String[] XLabels = new String[CalculateAFR.X_Support.size()+1];
	public String[] YLabels = new String[CalculateAFR.Y_Support.size()];

	static JFrame tableFrame;
	private JTable AFRTable;
	public static JTextArea textArea;
	public static ArrayList<ArrayList<Float>> AFRTemp = new ArrayList<ArrayList<Float>>();
	public static ArrayList<ArrayList<Float>> CorrectionMap = new ArrayList<ArrayList<Float>>();
	public static ArrayList<ArrayList<Float>> NewFuelMap = new ArrayList<ArrayList<Float>>();
	
	public static ArrayList<ArrayList<Float>> WorkingTAFR = new ArrayList<ArrayList<Float>>();


	private ButtonGroup group;
	public static int Case;

	private JTextField minAFRBox;
	private JTextField maxAFRBox;
	public static float minAFR=7f;
	public static float maxAFR=23f;
	
	public static float sliderSTDVal=1.5f;
	public static float sliderCountVal=10;
	public static float filterSliderVal=0f;

	public static void setAFRTemp(ArrayList<ArrayList<Float>> aFR) {
		AFRTemp = aFR;
	}

	public static ArrayList<ArrayList<Float>> getAFRTemp() {
		return AFRTemp;
	}
	

		
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AFRTableClass window = new AFRTableClass();
					AFRTableClass.tableFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @return 
	 */
	
		
	
	
	public AFRTableClass() {
		TableInit();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void TableInit() {

		WorkingTAFR=StaticVars.getTargetMap();
		tableFrame = new JFrame();
		tableFrame.getContentPane().setForeground(Color.RED);
		tableFrame.setResizable(false);
		tableFrame.setBounds(100, 100, 1085, 700);
		tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
//		getAFRTemp(); 
    	setdata.setAFRTemp(StaticVars.getAFR()); //set up data for the model	
    	setdata.main(); //set up data for the model	
	        DefaultTableModel model = new DefaultTableModel(setdata.data, XLabels);
	        
//	        model.setRowCount(CalculateAFR.Y_Support.size());
//	        model.setColumnCount(CalculateAFR.X_Support.size());
	        tableFrame.setTitle("Results");
	        tableFrame.getContentPane().setLayout(null);
	        
			JTextArea textArea = new JTextArea(6,20);
			textArea.setFont(new Font("Tahoma", Font.BOLD, 16));
			textArea.setBounds(831, 67, 184, 124);
			tableFrame.getContentPane().add(textArea);
	        		
	        		JScrollPane scrollPane = new JScrollPane();
	        		scrollPane.setBounds(10, 40, 806, 363);
	        		tableFrame.getContentPane().add(scrollPane);
	        

	        		
	        		AFRTable = new JTable();
	        		scrollPane.setViewportView(AFRTable);
	        		
					// Display data in the small window to the right of the main table
	        		AFRTable.addMouseMotionListener(new MouseMotionAdapter() {
	        			@Override
	        			public void mouseMoved(MouseEvent e) {

	        			    System.gc();
	        			    Point p = e.getPoint();
	        		        int row = AFRTable.rowAtPoint(p)-1;
	        		        int col = AFRTable.columnAtPoint(p)-1;
	        		        if (row>=0 && row<CalculateAFR.Y_Support.size() && col>=0 && col<CalculateAFR.X_Support.size()) {
		        		   
	        		        if (row>=0 && col>=0){	
	        		        	Graphing Graph = new Graphing(row, col, CalculateAFR.mXax, CalculateAFR.mYax);
	        		        Graph=null;
	        		        float STD=StaticVars.getSTD().get(row).get(col);
	        		        float Count=StaticVars.getPoints().get(row).get(col);
	        		        float AFR=StaticVars.getAFR().get(row).get(col);
	        		        float Correction;
							try {Correction = CorrectionMap.get(row).get(col);} 
							catch (Exception e1) {Correction=0;}
	        		        float OldMap=StaticVars.getFuelMap().get(row).get(col);
	        		        float NewMap=NewFuelMap.get(row).get(col);
	        		        float ModeCell=StaticVars.getMode().get(row).get(col);
	        		        System.out.println(ModeCell);
	        				DecimalFormat format = new DecimalFormat("0.#");
	        		        NumberFormat nf = new DecimalFormat("##.#");
	        		        textArea.setText("AFR = "+nf.format(AFR)+"\n"+"STD = "+nf.format(STD)+"\n"+"Count = "+nf.format(Count)+"\n"+"Corr = "+format.format(Correction)+" %"+"\n"+"Old fuel map = "+nf.format(OldMap) +"\n"+"New fuel map = "+nf.format(NewMap) +"\n");
	        		        }

	        		        }
	        		        p=null;
	        		        
//	        		        HistogramDataset dataset = new HistogramDataset();
//	        		        dataset.setType(HistogramType.RELATIVE_FREQUENCY);
//	        		        
//	       				 	double[] data = new double[CalculateAFR.AllData.get(row*(CalculateAFR.mXax.size()-1)+col).size()];
//	 
//	       				 	for (int i = 0; i < CalculateAFR.AllData.get(row*(CalculateAFR.mXax.size()-1)+col).size(); i++) {
//		       				 		data[i] = (double)CalculateAFR.AllData.get(row*(CalculateAFR.mXax.size()-1)+col).get(i);
//	       				 	}
//								try {
//									dataset.addSeries("Hist",data,20); // Number of bins is 20
//								} catch (Exception e1) {}
//		        		        data=null;
//								String plotTitle = "";
//								String xAxis = "AFR";
//								String yAxis = "Relative number of samples";
//								PlotOrientation orientation = PlotOrientation.VERTICAL;
//
//								boolean show = false;
//								boolean toolTips = false;
//								boolean urls = false;
//								
//								JFreeChart chart = ChartFactory.createHistogram(plotTitle, xAxis, yAxis,
//								        dataset, orientation, show, toolTips, urls);
//								chart.setBackgroundPaint(Color.white);
//								dataset=null;
//								
//								ChartPanel chartpanel = new ChartPanel(chart);
//								chart=null;
//								
//								chartpanel.setDomainZoomable(false);
//								chartpanel.setPreferredSize(new java.awt.Dimension(240,220));
//
//								JPanel panel = new JPanel();
//								panel.setBounds(826, 170, 240, 220);
//								tableFrame.getContentPane().remove(panel);
//								tableFrame.getContentPane().add(panel);
//								panel.removeAll();
//								panel.setVisible(true);
//								panel.add(chartpanel, BorderLayout.CENTER);
//								chartpanel=null;
//								panel=null;
//								tableFrame.invalidate();
//								tableFrame.validate();
////							}	        		        
//	        		        
//	        		        XYSeries series = new XYSeries("Scatter");
//						    series.clear();
//							Float cellMin=30f;
//							Float cellMax=0f;
//							
//							for (int i = 0; i < CalculateAFR.ScatterXData.get(row*(CalculateAFR.mXax.size()-1)+col).size(); i++) {							      
//								series.add((double)CalculateAFR.ScatterXData.get(row*(CalculateAFR.mXax.size()-1)+col).get(i),(double)CalculateAFR.ScatterYData.get(row*(CalculateAFR.mXax.size()-1)+col).get(i)); // Number of bins is 50
////								if (cellMin > CalculateAFR.AllData.get(row*(CalculateAFR.mXax.size()-1)+col).get(i))
////								{
////									cellMin=CalculateAFR.AllData.get(row*(CalculateAFR.mXax.size()-1)+col).get(i);
////								}
////								if (cellMax < CalculateAFR.AllData.get(row*(CalculateAFR.mXax.size()-1)+col).get(i))
////								{
////									cellMax=CalculateAFR.AllData.get(row*(CalculateAFR.mXax.size()-1)+col).get(i);
////								}
//								cellMin=10f;
//								cellMax=19f;
//							}
//						    XYSeriesCollection data1 = new XYSeriesCollection();
//							data1.addSeries(series);
//							series=null;
//							String title = "";
//							String xAxisLabel = "";
//							String yAxisLabel = "";
//							boolean legend = false;
//
//							PlotOrientation orientation1 = PlotOrientation.VERTICAL;
//
//							boolean toolTips1 = false;
//							boolean urls1 = false;
//							JFreeChart chart1 = ChartFactory.createScatterPlot(title, xAxisLabel, yAxisLabel, data1, orientation1, legend, toolTips1, urls1);
//							chart1.setBackgroundPaint(Color.white);
//							
//					        XYPlot xyPlot = (XYPlot) chart1.getPlot();
//					        xyPlot.setDomainCrosshairVisible(true);
//					        xyPlot.setRangeCrosshairVisible(true);
//					        data1=null;
//					        
//							for (int i = 0; i < CalculateAFR.ScatterXData.get(row*(CalculateAFR.mXax.size()-1)+col).size(); i++) {							      
//								int idx=row*(CalculateAFR.mXax.size()-1)+col;
//								xyPlot.setRenderer(new MyScatterRenderer(0, i, idx, cellMin, cellMax));
//							}
//					        
//					        ValueAxis domainAxis = xyPlot.getDomainAxis();
//					        ValueAxis rangeAxis = xyPlot.getRangeAxis();
//
//					        domainAxis.setRange(CalculateAFR.mXax.get(col), CalculateAFR.mXax.get(col+1));//X axis range
//					        rangeAxis.setRange(CalculateAFR.mYax.get(row), CalculateAFR.mYax.get(row+1));// Y axis range
//					        
//							ChartPanel chartpanel1 = new ChartPanel(chart1);
//					        chart1=null;
//						
//							chartpanel1.setDomainZoomable(true);
//							chartpanel1.getChart().getXYPlot().getRangeAxis().setInverted(true);
//							chartpanel1.setPreferredSize(new java.awt.Dimension(240,220));
//
//							JPanel panel1 = new JPanel();
//							panel1.setBounds(826, 400, 240, 220);
//							tableFrame.getContentPane().remove(panel1);
//							tableFrame.getContentPane().add(panel1);
//							panel1.setVisible(true);
//							panel1.add(chartpanel1, BorderLayout.CENTER);
//							panel1=null;
//							xyPlot=null;
//							tableFrame.invalidate();
//							tableFrame.validate();
//							System.gc();
//	        		        }
	        			}
	        		});
	        		
	        AFRTable.setTableHeader(null);
	        AFRTable.setDefaultRenderer(model.getColumnClass(0), new MyTableCellRenderer());
	        AFRTable.setCellSelectionEnabled(true);
	        AFRTable.setColumnSelectionAllowed(true);
	        AFRTable.setFont(new Font("Tahoma", Font.PLAIN, 16));

	        AFRTable.setModel(model);
	        AFRTable.setRowHeight(20);

	        AFRTable.setDefaultEditor(Object.class, null);        
	        
	        refreshTableData();
		}
		private void initialize() {
		JLabel Title = new JLabel("Measured AFR Map");
		Title.setBounds(369, 0, 362, 36);
		Title.setFont(new Font("Arial", Font.BOLD, 25));
		tableFrame.getContentPane().add(Title);
		
		JButton CopyToSuite = new JButton("Copy to Trionic Suite");
		CopyToSuite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String FuelMapExportSuite="2";
				for (Integer i=0; i<CalculateAFR.Y_Support.size();i++) {
		     		for (Integer j=0; j<CalculateAFR.X_Support.size();j++) {
//		     			int i1=CalculateAFR.Y_Support.size()-i-1;
		     			FuelMapExportSuite=(FuelMapExportSuite+j+":"+i+":"+Math.round(NewFuelMap.get(i).get(j))+"~");
		     		}
				}
//	     		FuelMapExport.SuiteExportSTR=FuelMapExportSuite;
	     	    StringSelection selection = new StringSelection(FuelMapExportSuite);
	     	    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	     	    clipboard.setContents(selection, selection);
	    		String infoMessage="The map is copied into the clipboard\n Paste in the suite using 'Paste selected cells' -> 'At original position' ";
	    		String titleBar="Done!";
	    		JOptionPane.showMessageDialog(null, infoMessage, "" + titleBar, JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		CopyToSuite.setVisible(false);
		CopyToSuite.setBounds(357, 493, 200, 29);
		tableFrame.getContentPane().add(CopyToSuite);
		
		JButton btnCopyToClipboard = new JButton("Copy to clipboard");
		btnCopyToClipboard.addActionListener(new ActionListener() {
			
		public void actionPerformed(ActionEvent arg0) {
		ActionEvent nev = new ActionEvent(AFRTable, ActionEvent.ACTION_PERFORMED, "copy");
		AFRTable.addRowSelectionInterval(1, CalculateAFR.Y_Support.size());
		AFRTable.addColumnSelectionInterval(1, CalculateAFR.X_Support.size());
		AFRTable.getActionMap().get(nev.getActionCommand()).actionPerformed(nev);
		AFRTable.clearSelection();
		String infoMessage="The map is copied into the clipboard";
		String titleBar="Done!";
		JOptionPane.showMessageDialog(null, infoMessage, "" + titleBar, JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		btnCopyToClipboard.setBounds(357, 538, 200, 29);
		tableFrame.getContentPane().add(btnCopyToClipboard);
			
			
	    JSlider sliderSTD = new JSlider();
	    sliderSTD.setBounds(589, 439, 200, 26);
	    sliderSTD.setToolTipText("");
	    sliderSTD.setPaintTicks(true);
	    sliderSTD.setMajorTickSpacing(10);
	    sliderSTD.setValue((int) sliderSTDVal*10);
	    sliderSTD.setMinorTickSpacing(1);
	    sliderSTD.setMaximum(50);
	    tableFrame.getContentPane().add(sliderSTD);
	    
	     JSlider sliderCount = new JSlider();
	     sliderCount.setBounds(589, 499, 200, 42);
	     sliderCount.setMinimum(5);
	     sliderCount.setMinorTickSpacing(1);
	     sliderCount.setPaintTicks(true);
	     sliderCount.setMajorTickSpacing(10);
	     sliderCount.setValue((int) sliderCountVal);
	     sliderCount.setMaximum(50);
	     tableFrame.getContentPane().add(sliderCount);
	       	 
	     JRadioButton rbtn0 = new JRadioButton("AFR",true);
	     rbtn0.setBounds(10, 419, 155, 29);
	     rbtn0.addActionListener(new ActionListener() {
	     	public void actionPerformed(ActionEvent arg0) {
	     		CopyToSuite.setVisible(false);
	     		Title.setText("Measured AFR Map");
	     		setdata.setAFRTemp(StaticVars.getAFR());
	        	setdata.main(); //set up data for the model	
		        DefaultTableModel model = new DefaultTableModel(setdata.data, XLabels);
		        AFRTable.setModel(model);
		        AFRTable.setDefaultEditor(Object.class, null);
	     	}
	     });
	     tableFrame.getContentPane().add(rbtn0);
	     
	     JRadioButton rbtn1 = new JRadioButton("Count");
	     rbtn1.setBounds(10, 456, 155, 29);
	     rbtn1.addActionListener(new ActionListener() {
	     	public void actionPerformed(ActionEvent e) {
	     		CopyToSuite.setVisible(false);
	     		Title.setText("Count Map");
		 		setdata.setAFRTemp(StaticVars.getPoints());
	        	setdata.main(); //set up data for the model	
		        DefaultTableModel model = new DefaultTableModel(setdata.data, XLabels);
		        AFRTable.setModel(model);
		        AFRTable.setDefaultEditor(Object.class, null);
	     	}
	     });
	     tableFrame.getContentPane().add(rbtn1);
	     
	     JRadioButton rbtn2 = new JRadioButton("Standard Deviation");
	     rbtn2.setBounds(10, 493, 174, 29);
	     rbtn2.addActionListener(new ActionListener() {
	     	public void actionPerformed(ActionEvent e) {
	     		CopyToSuite.setVisible(false);

	     		Title.setText("Standard Deviation Map");
		 		setdata.setAFRTemp(StaticVars.getSTD());
	        	setdata.main(); //set up data for the model	
		        DefaultTableModel model = new DefaultTableModel(setdata.data, XLabels);
		        AFRTable.setModel(model);
		        AFRTable.setDefaultEditor(Object.class, null);
	     	}
	     });
	     tableFrame.getContentPane().add(rbtn2);
	     
	     JRadioButton rbtn3 = new JRadioButton("Fuel map");
	     rbtn3.setBounds(191, 454, 155, 29);
	     rbtn3.addActionListener(new ActionListener() {
	     	public void actionPerformed(ActionEvent e) {
	     		CopyToSuite.setVisible(false);
	     		
	     		Title.setText("Fuel Map");
		 		setdata.setAFRTemp(StaticVars.getFuelMap());
	        	setdata.main(); //set up data for the model	
		        DefaultTableModel model = new DefaultTableModel(setdata.data, XLabels);
		        AFRTable.setModel(model);	
		        AFRTable.setDefaultEditor(Object.class, null);
	     	}
	     });
	     tableFrame.getContentPane().add(rbtn3);
	     
	     JRadioButton rbtn4 = new JRadioButton("New fuel map");
	     rbtn4.setBounds(191, 493, 155, 29);
	     rbtn4.addActionListener(new ActionListener() {
	     	public void actionPerformed(ActionEvent e) {
	     		CopyToSuite.setVisible(true);
		 		Title.setText("New fuel Map");
		 		refreshTableData();
	     		setdata.setAFRTemp(NewFuelMap);
	        	setdata.main(); //set up data for the model	
		        DefaultTableModel model = new DefaultTableModel(setdata.data, XLabels);
		        AFRTable.setModel(model);	
		        
		        JTextField tf = new JTextField();
    	        tf.setEditable(true);
    	        DefaultCellEditor editor = new DefaultCellEditor( tf );
        		AFRTable.setDefaultEditor(Object.class, editor);		        
	     	}
	     });
	     tableFrame.getContentPane().add(rbtn4);
	     
	     JRadioButton rbtn5 = new JRadioButton("Target map");
	     rbtn5.setBounds(191, 417, 155, 29);
	     rbtn5.addActionListener(new ActionListener() {
	     	public void actionPerformed(ActionEvent e) {
	     		CopyToSuite.setVisible(false);
	     		
	     		Title.setText("Target Map, AFR");
		 		setdata.setAFRTemp(WorkingTAFR);
	        	setdata.main(); //set up data for the model	
		        DefaultTableModel model = new DefaultTableModel(setdata.data, XLabels);
		        AFRTable.setModel(model);	
		        AFRTable.setDefaultEditor(Object.class, null);
	     	}
	     });
	     tableFrame.getContentPane().add(rbtn5);
	     
	     JRadioButton rbtn6 = new JRadioButton("Correction, %");
	     rbtn6.setBounds(10, 530, 155, 29);
	     rbtn6.addActionListener(new ActionListener() {
	     	public void actionPerformed(ActionEvent e) {
	     		CopyToSuite.setVisible(false);
	     		Title.setText("Correction Map, %");
     			refreshTableData();
	     		setdata.setAFRTemp(CorrectionMap);
	        	setdata.main(); //set up data for the model	
		        DefaultTableModel model = new DefaultTableModel(setdata.data, XLabels);
		        AFRTable.setModel(model);		
		        AFRTable.setDefaultEditor(Object.class, null);
	     	}
	     });
	     tableFrame.getContentPane().add(rbtn6);
	     
	     JLabel lblStdevCutoff = new JLabel("Standard deviation cutoff");
	     lblStdevCutoff.setBounds(592, 421, 217, 20);
	     tableFrame.getContentPane().add(lblStdevCutoff);
	     
	     JLabel lblCountCutoff = new JLabel("Count cutoff");
	     lblCountCutoff.setBounds(592, 481, 217, 20);
	     tableFrame.getContentPane().add(lblCountCutoff);
	     
	     JLabel label = new JLabel("0");
	     label.setBounds(572, 439, 18, 20);
	     tableFrame.getContentPane().add(label);
	     
	     JLabel label_1 = new JLabel("5");
	     label_1.setBounds(791, 439, 18, 20);
	     tableFrame.getContentPane().add(label_1);
	     
	     JLabel label_3 = new JLabel("5");
	     label_3.setBounds(572, 499, 18, 20);
	     tableFrame.getContentPane().add(label_3);
	     
	     JLabel label_4 = new JLabel("50");
	     label_4.setBounds(791, 499, 18, 20);
	     tableFrame.getContentPane().add(label_4);
	     
	     JButton btnNewButton = new JButton("Export fuel map");
	     btnNewButton.setVisible(false);
	     btnNewButton.setBounds(344, 182, 200, 63);
	     btnNewButton.addActionListener(new ActionListener() {
	     	public void actionPerformed(ActionEvent e) {
	     		CopyToSuite.setVisible(false);

     			CorrectionMap.clear();
     			NewFuelMap.clear();
     			String FuelMapExportSuite = "2";
	     		String FuelMapExportCSV="";
	     		String FuelMapExportCSVGAS="";
				for (Integer i=0; i<CalculateAFR.Y_Support.size();i++) {
	     			CorrectionMap.add(new ArrayList<Float>());
	     			NewFuelMap.add(new ArrayList<Float>());
		     		for (Integer j=0; j<CalculateAFR.X_Support.size();j++) {
		     			if (StaticVars.getSTD().get(i).get(j)<sliderSTD.getValue()/10.0 && StaticVars.getPoints().get(i).get(j)> sliderCount.getValue()  )
		     			{
		     			Float tempVal = StaticVars.getAFR().get(i).get(j)/WorkingTAFR.get(i).get(j);
		     			if (tempVal>1.05) {tempVal=1.05f;}
		     			if (tempVal<0.95) {tempVal=0.95f;}
		     			CorrectionMap.get(i).add((float)Math.round((tempVal-1)*100));
		     			NewFuelMap.get(i).add((float)Math.round(StaticVars.getFuelMap().get(i).get(j)*tempVal));
		     			}
		     			else {
			     			CorrectionMap.get(i).add((float) 0);
			     			NewFuelMap.get(i).add((float)Math.round(StaticVars.getFuelMap().get(i).get(j)));
		     			}
		     		}	
	     		}
//				
				for (Integer i=0; i<CalculateAFR.Y_Support.size();i++) {
		     		for (Integer j=0; j<CalculateAFR.X_Support.size();j++) {
		     			int i1=CalculateAFR.Y_Support.size()-i-1;
		     			FuelMapExportSuite=(FuelMapExportSuite+j+":"+i1+":"+Math.round(NewFuelMap.get(i).get(j))+"~");
		     			FuelMapExportCSV=(FuelMapExportCSV+Math.round(NewFuelMap.get(i1).get(j))+"\t");
		     			FuelMapExportCSVGAS=(FuelMapExportCSVGAS+Math.round(NewFuelMap.get(i).get(j))+"\t");
		     		}
	     			FuelMapExportCSV=FuelMapExportCSV+"\n";
	     			FuelMapExportCSVGAS=FuelMapExportCSVGAS+"\n";
				}
//	     		FuelMapExport.SuiteExportSTR=FuelMapExportSuite;
//	     		FuelMapExport.CSVExportSTR=FuelMapExportCSVGAS;
//	     		FuelMapExport.CSVExportSTRGAS=FuelMapExportCSV;
//	     		FuelMapExport.main(null);
	     	}
	     });
	     tableFrame.getContentPane().add(btnNewButton);
	     

			group = new ButtonGroup();
			
			group.add(rbtn0);
			group.add(rbtn1);
			group.add(rbtn2);
			group.add(rbtn3);
			group.add(rbtn4);
			group.add(rbtn5);
			group.add(rbtn6);
			
			JLabel label_2 = new JLabel("0");
			label_2.setBounds(572, 579, 18, 20);
			tableFrame.getContentPane().add(label_2);
			
			JLabel lblFilterBadMeasurements = new JLabel("Filter fast changing data");
			lblFilterBadMeasurements.setBounds(592, 561, 217, 20);
			tableFrame.getContentPane().add(lblFilterBadMeasurements);
			
			JSlider FilterSlider = new JSlider();
			FilterSlider.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent arg0) {
					filterSliderVal=FilterSlider.getValue();
//					CalculateAFR.DataFiltering();
					CalculateAFR.main(null);
    			    System.gc();
    			    refreshTableData();	     			
		        	setdata.main(); //set up data for the model	
			        DefaultTableModel model = new DefaultTableModel(setdata.data, XLabels);
			        AFRTable.setModel(model);	    
			        AFRTable.setDefaultEditor(Object.class, null);
		     		model.fireTableDataChanged();
			        AFRTable.setModel(model);	
			        AFRTable.repaint();

				}
			});
			FilterSlider.setValue((int) filterSliderVal);
			FilterSlider.setToolTipText("");
			FilterSlider.setPaintTicks(true);
			FilterSlider.setMinorTickSpacing(1);
			FilterSlider.setMaximum(6);
			FilterSlider.setMajorTickSpacing(10);
			FilterSlider.setBounds(589, 579, 200, 26);
			tableFrame.getContentPane().add(FilterSlider);
			
			JLabel label_6 = new JLabel("3");
			label_6.setBounds(791, 579, 18, 20);
			tableFrame.getContentPane().add(label_6);
			
			minAFRBox = new JTextField();
			minAFRBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					minAFR=Float.parseFloat(minAFRBox.getText());
					CalculateAFR.main(null);
					refreshTableData();
		     			
		        	setdata.main(); //set up data for the model	
			        DefaultTableModel model = new DefaultTableModel(setdata.data, XLabels);
			        AFRTable.setModel(model);	    
			        AFRTable.setDefaultEditor(Object.class, null);
		     		model.fireTableDataChanged();
			        AFRTable.setModel(model);	
			        AFRTable.repaint();
				}
			});
			minAFRBox.setText(String.valueOf(minAFR));
			minAFRBox.setBounds(357, 443, 54, 26);
			tableFrame.getContentPane().add(minAFRBox);
			minAFRBox.setColumns(10);
			
			maxAFRBox = new JTextField();
			maxAFRBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					maxAFR=Float.parseFloat(maxAFRBox.getText());
					CalculateAFR.main(null);
					refreshTableData();
		        	setdata.main(); //set up data for the model	
			        DefaultTableModel model = new DefaultTableModel(setdata.data, XLabels);
			        AFRTable.setModel(model);	    
			        AFRTable.setDefaultEditor(Object.class, null);
		     		model.fireTableDataChanged();
			        AFRTable.setModel(model);	
			        AFRTable.repaint();
				}
			});
			maxAFRBox.setText(String.valueOf(maxAFR));
			maxAFRBox.setColumns(10);
			maxAFRBox.setBounds(441, 443, 54, 26);
			tableFrame.getContentPane().add(maxAFRBox);
			
			JLabel lblMaxAfr = new JLabel("Min AFR");
			lblMaxAfr.setBounds(357, 419, 69, 20);
			tableFrame.getContentPane().add(lblMaxAfr);
			
			JLabel label_5 = new JLabel("Max AFR");
			label_5.setBounds(441, 419, 69, 20);
			tableFrame.getContentPane().add(label_5);
			
			JButton btnNewButton_1 = new JButton("I want to support this project!");
			btnNewButton_1.setForeground(Color.BLUE);
			btnNewButton_1.setBackground(Color.LIGHT_GRAY);
			btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 13));
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(Desktop.isDesktopSupported())
					{
					  try {
						Desktop.getDesktop().browse(new URI("www.paypal.me/ihitsov/10"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				}
			});
			btnNewButton_1.setBounds(820, 0, 244, 51);
			tableFrame.getContentPane().add(btnNewButton_1);
			


				
    sliderCount.addChangeListener(new ChangeListener() {
     	public void stateChanged(ChangeEvent e) {
	 		sliderCountVal=(float) (sliderCount.getValue());
	 		refreshTableData();
        	setdata.main(); //set up data for the model	
	        DefaultTableModel model = new DefaultTableModel(setdata.data, XLabels);
	        AFRTable.setModel(model);	    
	        AFRTable.setDefaultEditor(Object.class, null);

     	}
     });
 
 
	 sliderSTD.addChangeListener(new ChangeListener() {
	 	public void stateChanged(ChangeEvent arg0) {
	 	sliderSTDVal=(float) (sliderSTD.getValue()/10.0);
    		
	 	refreshTableData();

	 	setdata.main(); //set up data for the model	
        DefaultTableModel model = new DefaultTableModel(setdata.data, XLabels);
        AFRTable.setModel(model);	
        AFRTable.setDefaultEditor(Object.class, null);

	 	}
	 });
	 
	 
	 
	}
		
		public static void refreshTableData() {
			CorrectionMap.clear();
				NewFuelMap.clear();
	 		for (Integer i=0; i<CalculateAFR.Y_Support.size();i++) {
	 			CorrectionMap.add(new ArrayList<Float>());
	 			NewFuelMap.add(new ArrayList<Float>());
	     		for (Integer j=0; j<CalculateAFR.X_Support.size();j++) {
	     			if (StaticVars.getSTD().get(i).get(j)<sliderSTDVal && StaticVars.getPoints().get(i).get(j)> sliderCountVal)
	     			{
	     			Float tempVal = StaticVars.getAFR().get(i).get(j)/WorkingTAFR.get(i).get(j);
	     			if (tempVal>1.05) {tempVal=1.05f;}
	     			if (tempVal<0.95) {tempVal=0.95f;}
	     			CorrectionMap.get(i).add((float)Math.round((tempVal-1)*100));
	     			NewFuelMap.get(i).add((float)Math.round(StaticVars.getFuelMap().get(i).get(j)*tempVal));
	     			}
	     			else {
		     			CorrectionMap.get(i).add(null);
		     			NewFuelMap.get(i).add((float)Math.round(StaticVars.getFuelMap().get(i).get(j)));
	     			}
	     		}	
	 		}
		}
}
