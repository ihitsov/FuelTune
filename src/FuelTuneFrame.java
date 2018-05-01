import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

//package com.zetcode.readopencsv;

import java.awt.Font;


public class FuelTuneFrame {
	private Preferences prefs;

	private JFrame frame;
	private JTextField XDataIndex;
	private JTextField YDataIndex;
	private JTextField ZDataIndex;
	private JTextField ChecksumCol;
	private JTextField TDataIndex;
	public Preferences getPrefs() {
		return prefs;
	}

	public void setPrefs(Preferences prefs) {
		this.prefs = prefs;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}



	private JTextField CurrentFuelMap;
	private JTextField TargetMap;
	public JTextField XSupportField;
	public JTextField YSupportField;

	private JTextField FileName;

	private ButtonGroup group;
	private ButtonGroup groupTarget;
	

	
	/**
	 * Launch the application.
	 */
	
		  
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FuelTuneFrame window = new FuelTuneFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FuelTuneFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	    prefs = Preferences.userRoot().node(this.getClass().getName());
	    
		frame = new JFrame();
		frame.setBounds(100, 100, 1399, 675);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("fuelTune");
//		frame.setIconImage(new ImageIcon("icon.png").getImage());
//		frame.setIconImage(new ImageIcon(new File("icon.png")));

		
		XDataIndex = new JTextField();
		XDataIndex.setText("14");
		XDataIndex.setText(prefs.get("XDataIndexPref","14"));
		XDataIndex.setBounds(29, 104, 133, 29);
		frame.getContentPane().add(XDataIndex);
		XDataIndex.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("X data in column:");
		lblNewLabel.setBounds(29, 79, 133, 20);
		frame.getContentPane().add(lblNewLabel);
		
		YDataIndex = new JTextField();
		YDataIndex.setText(prefs.get("YDataIndexPref","8"));
		YDataIndex.setColumns(10);
		YDataIndex.setBounds(191, 104, 133, 29);
		frame.getContentPane().add(YDataIndex);
		
		JLabel lblYDataIn = new JLabel("Y data in column:");
		lblYDataIn.setBounds(191, 79, 133, 20);
		frame.getContentPane().add(lblYDataIn);
		
		ZDataIndex = new JTextField();
		ZDataIndex.setText(prefs.get("ZDataIndexPref","6"));
		ZDataIndex.setColumns(10);
		ZDataIndex.setBounds(354, 104, 133, 29);
		frame.getContentPane().add(ZDataIndex);
		
		JLabel lblZDataIn = new JLabel("Z data in column:");
		lblZDataIn.setBounds(354, 79, 133, 20);
		frame.getContentPane().add(lblZDataIn);
		
		ChecksumCol = new JTextField();
		ChecksumCol.setVisible(false);
		ChecksumCol.setFont(new Font("Tahoma", Font.BOLD, 16));
		ChecksumCol.setColumns(10);
		ChecksumCol.setBounds(606, 104, 133, 29);
		frame.getContentPane().add(ChecksumCol);
		
		JLabel lblChecksumInColumn = new JLabel("Checksum in column:");
		lblChecksumInColumn.setVisible(false);
		lblChecksumInColumn.setBounds(606, 79, 153, 20);
		frame.getContentPane().add(lblChecksumInColumn);
		
		
		JRadioButton ZOpt1 = new JRadioButton("Z in AFR",prefs.getBoolean("ZOpt1Pref",false));
		ZOpt1.setBounds(29, 145, 93, 29);
//		if ZOpt1.isSelected(){
//			CaseZ=1;
//		}
		frame.getContentPane().add(ZOpt1);
		
		JRadioButton ZOpt2 = new JRadioButton("Z in Lambda",prefs.getBoolean("ZOpt2Pref",false));
		ZOpt2.setBounds(29, 182, 133, 29);
		frame.getContentPane().add(ZOpt2);
		JRadioButton ZOpt3 = new JRadioButton("mV, 0-5V=7.4-22.4 AFR (Inverted)",prefs.getBoolean("ZOpt3Pref",true));
		ZOpt3.setBounds(29, 219, 295, 29);
		frame.getContentPane().add(ZOpt3);
		
		JRadioButton ZOpt4 = new JRadioButton("mV, 5-0V=7.4-22.4 AFR (Standard)",prefs.getBoolean("ZOpt4Pref",false));
		ZOpt4.setBounds(29, 256, 309, 29);
		frame.getContentPane().add(ZOpt4);
		
		group = new ButtonGroup();
		
		group.add(ZOpt1);
		group.add(ZOpt2);
		group.add(ZOpt3);
		group.add(ZOpt4);

		JPanel panel = new JPanel();
		panel.setBounds(783, 276, 579, 331);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JRadioButton TargetOpt0 = new JRadioButton("Target in AFR",prefs.getBoolean("TargetOpt0Pref",false));
//		TargetOpt0.setSelected(true);
		
		TargetOpt0.setBounds(11, 165, 172, 29);
		panel.add(TargetOpt0);
		
		JRadioButton TargetOpt1 = new JRadioButton("Target in Lambda",prefs.getBoolean("TargetOpt1Pref",false));
		TargetOpt1.setBounds(11, 202, 157, 29);
		panel.add(TargetOpt1);
		
		JRadioButton TargetOpt2 = new JRadioButton("Target in mV, 0-5V=7.4-22.4 AFR (Inverted)",true);
		TargetOpt2.setSelected(prefs.getBoolean("TargetOpt2Pref",true));
		TargetOpt2.setBounds(11, 239, 366, 29);
		panel.add(TargetOpt2);
		
		JRadioButton TargetOpt3 = new JRadioButton("Target in mV, 5-0V=7.4-22.4 AFR (Standard)",prefs.getBoolean("TargetOpt3Pref",false));
		TargetOpt3.setBounds(11, 276, 366, 29);
		panel.add(TargetOpt3);
		
		groupTarget = new ButtonGroup();
		
		groupTarget.add(TargetOpt0);
		groupTarget.add(TargetOpt1);
		groupTarget.add(TargetOpt2);
		groupTarget.add(TargetOpt3);
		
		
		
		XSupportField = new JTextField();
		XSupportField.setText(prefs.get("XSupportFieldPref","800 1300 1800 2200 2600 3000 3500 4000 4500 5000 5500 6000"));
		XSupportField.setColumns(10);
		XSupportField.setBounds(29, 424, 710, 29);
		frame.getContentPane().add(XSupportField);
		
		JLabel lblXSupportFieldPoints = new JLabel("X support points (space or comma separated)");
		lblXSupportFieldPoints.setBounds(29, 399, 295, 20);
		frame.getContentPane().add(lblXSupportFieldPoints);
		
		YSupportField = new JTextField();
		YSupportField.setText(prefs.get("YSupportFieldPref","1500 1700 2000 2500 3500 4200 6000 9000 12000 14000 15000 16000"));
		YSupportField.setColumns(10);
		YSupportField.setBounds(29, 509, 710, 29);
		frame.getContentPane().add(YSupportField);
		
		JLabel YSupportFieldLabel = new JLabel("Y support points (space or comma separated)");
		YSupportFieldLabel.setBounds(29, 484, 324, 20);
		frame.getContentPane().add(YSupportFieldLabel);
		
	
		JLabel lblPasteCurrentFuel = new JLabel("Current fuel map (tab or comma separated)");
		lblPasteCurrentFuel.setBounds(11, 16, 288, 20);
		panel.add(lblPasteCurrentFuel);
		
		CurrentFuelMap = new JTextField();
		CurrentFuelMap.setText(prefs.get("CurrentFuelMapPref","FuelMap222GAS.csv"));

		CurrentFuelMap.setBounds(11, 41, 170, 29);
		panel.add(CurrentFuelMap);
		CurrentFuelMap.setColumns(10);
		
		JLabel lblPasteCurrentTarget = new JLabel("Target map  (tab or comma separated)");
		lblPasteCurrentTarget.setVisible(prefs.getBoolean("lblPasteCurrentTarget",true));
		lblPasteCurrentTarget.setBounds(11, 86, 288, 20);
		panel.add(lblPasteCurrentTarget);
		
		TargetMap = new JTextField();
		TargetMap.setText(prefs.get("TargetMapPref","TargetMapGAS.csv"));
		TargetMap.setVisible(prefs.getBoolean("TargetMapVisiblePref",true));
		
		TargetMap.setBounds(11, 111, 170, 29);
		panel.add(TargetMap);
		TargetMap.setColumns(10);
				
		JRadioButton GetTargetFromCol = new JRadioButton("Get target from column",false);
		GetTargetFromCol.setVisible(false);
		GetTargetFromCol.setSelected(prefs.getBoolean("GetTargetFromColPref",false));
		
		GetTargetFromCol.setBounds(317, 111, 199, 29);
		panel.add(GetTargetFromCol);
		
		GetTargetFromCol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StaticVars.setTargetFromCol(GetTargetFromCol.isSelected());
				if (GetTargetFromCol.isSelected()==true){
					TargetMap.setVisible(false);
					lblPasteCurrentTarget.setVisible(false);
					TDataIndex.setVisible(true);					
				}
				else {
					TargetMap.setVisible(true);
					lblPasteCurrentTarget.setVisible(true);
					TDataIndex.setVisible(false);
				}
			}
		});

		
		TDataIndex = new JTextField();
		TDataIndex.setBounds(522, 111, 42, 29);
		TDataIndex.setText(prefs.get("TDataIndexPref","6"));
		TDataIndex.setVisible(prefs.getBoolean("TDataIndexVisiblePref", false));
		TDataIndex.setColumns(10);
		panel.add(TDataIndex);
		
		JButton btnNewButton = new JButton("Browse");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			            JFileChooser chooser = new JFileChooser();
			            chooser.setCurrentDirectory(new java.io.File("."));
			            chooser.setDialogTitle("Browse the Targer map file");
			            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			            chooser.setAcceptAllFileFilterUsed(true);
			            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files","txt","csv","t7l");
			            chooser.addChoosableFileFilter((javax.swing.filechooser.FileFilter) filter);
			            chooser.setFileFilter(filter);
			            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			                TargetMap.setText(chooser.getSelectedFile().toString());
			            } 			
			            
				}
			});
		btnNewButton.setBounds(200, 111, 99, 29);
		panel.add(btnNewButton);
		
		JButton button = new JButton("Browse");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
	            JFileChooser chooser = new JFileChooser();
	            chooser.setCurrentDirectory(new java.io.File("."));
	            chooser.setDialogTitle("Browse the Targer map file");
	            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	            chooser.setAcceptAllFileFilterUsed(true);
	            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files","txt","csv","t7l");
	            chooser.addChoosableFileFilter((javax.swing.filechooser.FileFilter) filter);
	            chooser.setFileFilter(filter);
	            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	                CurrentFuelMap.setText(chooser.getSelectedFile().toString());
	            } 			
	            
			}
		});
		button.setBounds(200, 41, 99, 29);
		panel.add(button);

		
		JLabel lblNewLabel_1 = new JLabel("Optional");
		lblNewLabel_1.setVisible(false);
		lblNewLabel_1.setBounds(783, 256, 69, 20);
		frame.getContentPane().add(lblNewLabel_1);

		
		FileName = new JTextField();
		FileName.setText(prefs.get("FileNamePref","Putty222GAS.t7l"));

		FileName.setColumns(10);
		FileName.setBounds(29, 34, 295, 29);
		frame.getContentPane().add(FileName);
		
		JLabel lblFilename = new JLabel("Filename");
		lblFilename.setBounds(30, 8, 133, 20);
		frame.getContentPane().add(lblFilename);
		
		JRadioButton InvertedYAxisBtn = new JRadioButton("Inverted Y axis (t7suite style)",prefs.getBoolean("InvertedYAxisBtnPref",false));
		InvertedYAxisBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StaticVars.setInvertedYAxis(InvertedYAxisBtn.isSelected());
			}
		});
		InvertedYAxisBtn.setBounds(495, 34, 264, 29);
		frame.getContentPane().add(InvertedYAxisBtn);
		
		
		JButton btnNewButton_1 = new JButton("RUN!");
		btnNewButton_1.setBounds(867, 16, 287, 189);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnBrowse = new JButton("Browse");
	    btnBrowse.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
	            JFileChooser chooser = new JFileChooser();
	            chooser.setCurrentDirectory(new java.io.File("."));
	            chooser.setDialogTitle("Browse the file to process");
	            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	            chooser.setAcceptAllFileFilterUsed(true);
	            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files","txt","csv","t7l");
	            chooser.addChoosableFileFilter((javax.swing.filechooser.FileFilter) filter);
	            chooser.setFileFilter(filter);


	            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

	                FileName.setText(chooser.getSelectedFile().toString());
	            } 
	        }
	    });
		btnBrowse.setBounds(349, 34, 83, 29);
		frame.getContentPane().add(btnBrowse);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				System.gc();
				prefs.put("XDataIndexPref", XDataIndex.getText());
				prefs.put("YDataIndexPref", YDataIndex.getText());
				prefs.put("ZDataIndexPref", ZDataIndex.getText());
				prefs.put("TDataIndexPref", TDataIndex.getText());
				
				prefs.put("FileNamePref", FileName.getText());
				prefs.put("CurrentFuelMapPref", CurrentFuelMap.getText());
				prefs.put("TargetMapPref", TargetMap.getText());

				prefs.put("XSupportFieldPref", XSupportField.getText());
				prefs.put("YSupportFieldPref", YSupportField.getText());

				prefs.putBoolean("ZOpt1Pref", ZOpt1.isSelected());
				prefs.putBoolean("ZOpt2Pref", ZOpt2.isSelected());
				prefs.putBoolean("ZOpt3Pref", ZOpt3.isSelected());
				prefs.putBoolean("ZOpt4Pref", ZOpt4.isSelected());

				prefs.putBoolean("TargetOpt0Pref", TargetOpt0.isSelected());
				prefs.putBoolean("TargetOpt1Pref", TargetOpt1.isSelected());
				prefs.putBoolean("TargetOpt2Pref", TargetOpt2.isSelected());
				prefs.putBoolean("TargetOpt3Pref", TargetOpt3.isSelected());
				
				prefs.putBoolean("InvertedYAxisBtnPref", InvertedYAxisBtn.isSelected());

				prefs.putBoolean("GetTargetFromColPref", GetTargetFromCol.isSelected());
				prefs.putBoolean("GetTargetFromColVisiblePref", GetTargetFromCol.isVisible());
				prefs.putBoolean("TargetMapVisiblePref", TargetMap.isVisible());
				prefs.putBoolean("lblPasteCurrentTargetPref", lblPasteCurrentTarget.isVisible());
				prefs.putBoolean("TDataIndexVisiblePref", TDataIndex.isVisible());

			    
				StaticVars.setXSupport(XSupportField.getText());
				StaticVars.setYSupport(YSupportField.getText());
				StaticVars.setInvertedYAxis(InvertedYAxisBtn.isSelected());

				ReadXYZData CSVRead	= new ReadXYZData();	
				CSVRead.setxIndex(Integer.parseInt(XDataIndex.getText()));
				CSVRead.setyIndex(Integer.parseInt(YDataIndex.getText()));
				CSVRead.setzIndex(Integer.parseInt(ZDataIndex.getText()));
				CSVRead.setTIndex(Integer.parseInt(TDataIndex.getText()));

				CSVRead.setFileName(FileName.getText());
				CSVRead.setFuelMapFileName(CurrentFuelMap.getText());
				CSVRead.setTargetMapFileName(TargetMap.getText());

				try {
					ReadXYZData.main();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (ZOpt1.isSelected()){
					StaticVars.setCaseZ(0);
				}
				if (ZOpt2.isSelected()){
					StaticVars.setCaseZ(1);
				}
				if (ZOpt3.isSelected()){
					StaticVars.setCaseZ(2);
				}
				if (ZOpt4.isSelected()){
					StaticVars.setCaseZ(3);
				}
				
				
				if (TargetOpt0.isSelected()){
					StaticVars.setCaseTarget(0);
				}
				if (TargetOpt1.isSelected()){
					StaticVars.setCaseTarget(1);
				}
				if (TargetOpt2.isSelected()){
					StaticVars.setCaseTarget(2);
				}
				if (TargetOpt3.isSelected()){
					StaticVars.setCaseTarget(3);
				}
				
				CalculateAFR.main(null);

				AFRTableClass.setAFRTemp(StaticVars.getAFR());
				AFRTableClass.main(null);

			}
		});

		
		
	}
}
