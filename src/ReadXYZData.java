import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;



public class ReadXYZData {
	public static String fileName;
	public static String FuelMapfileName;
	public static String TargetMapfileName;

	public static int xIndex;
	public static int yIndex;
	public static int zIndex;
	public static int TIndex;//Target column index
	
	public static float TDataSum=0;
	public static float ZDataSum=0;
	public static int ZDataCount=0;
	public static int TDataCount=0;
	
	
	public void setxIndex(int xIndex) {
		ReadXYZData.xIndex = xIndex;
	}

	public void setyIndex(int yIndex) {
		ReadXYZData.yIndex = yIndex;
	}

	public void setzIndex(int zIndex) {
		ReadXYZData.zIndex = zIndex;
	}

	public void setTIndex(int tIndex) {
		ReadXYZData.TIndex = tIndex;
	}

	public void setFileName(String fileName) {
		ReadXYZData.fileName = fileName;
	}
	
	public void setFuelMapFileName(String CurrentFuelMap) {
		ReadXYZData.FuelMapfileName = CurrentFuelMap;
	}

	public void setTargetMapFileName(String TargetMap) {
		ReadXYZData.TargetMapfileName = TargetMap;
	}
	
	public static ArrayList<Float> XData = new ArrayList<Float>();
	public static ArrayList<Float> YData = new ArrayList<Float>();
	public static ArrayList<Float> ZData = new ArrayList<Float>();
	public static ArrayList<Float> TData= new ArrayList<Float>();


	


	public static void main() throws IOException {
		BufferedReader xyzreader = new BufferedReader(new FileReader(fileName));

	  	String line = null;
		Scanner scanner = null;
		ArrayList<String> record = new ArrayList<String>();
		XData.clear();
		YData.clear();
		ZData.clear();
		TData.clear();
		StaticVars.getFuelMap().clear();
		StaticVars.getTargetMap().clear();
		
		while ((line = xyzreader.readLine()) != null) {
		record.clear();	
//			String[] record = iterator.next();
			scanner = new Scanner(line);
			scanner.useDelimiter(",|\t|, ");
			while (scanner.hasNext()) {
				try {
					record.add(scanner.next());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
				try {
					XData.add(Float.parseFloat(record.get(xIndex-1)));//(record[0]);
				} catch (Exception e) {
					// TODO Auto-generated catch block
			        continue;
				}
				try {
					YData.add(Float.parseFloat(record.get(yIndex-1)));//(record[0]);
				} catch (Exception e) {
					// TODO Auto-generated catch block
			        continue;
				}
				try {
					ZData.add(Float.parseFloat(record.get(zIndex-1)));//(record[0]);
				} catch (Exception e) {
					// TODO Auto-generated catch block
			        continue;
				}
			
				if (StaticVars.isTargetFromCol()==true) {
				try {
					TData.add(Float.parseFloat(record.get(TIndex-1)));//(record[0]);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				else {
				TData.add(Float.parseFloat(record.get(xIndex-1)));//(record[0]);
				}

			
//			 Filtering to remove oversampled values
			if (XData.size()>2 && YData.size()>2 && ZData.size()>2) {
				if  (  XData.get(XData.size()-1).equals(XData.get(XData.size()-2)) && YData.get(YData.size()-1).equals(YData.get(YData.size()-2))) {// || ZData.get(ZData.size()-1).equals(ZData.get(ZData.size()-2)) || TData.get(TData.size()-1).equals(TData.get(TData.size()-2)) ) {
					XData.remove(XData.size()-1);
					YData.remove(YData.size()-1);
					ZData.remove(ZData.size()-1);
					TData.remove(TData.size()-1);
				}
				else {
				TDataSum=TDataSum+TData.get(TData.size()-1);
				ZDataSum=ZDataSum+ZData.get(ZData.size()-1);
				ZDataCount++;
				TDataCount++;
				
				}
			}

		}
		xyzreader.close();
        System.gc();

		
		BufferedReader FuelMapreader = new BufferedReader(new FileReader(FuelMapfileName));
	  	String line1 = null;
		int row=0;
		while ((line1 = FuelMapreader.readLine()) != null) {			
		scanner = new Scanner(line1);
		scanner.useDelimiter(",|\t");
		while (scanner.hasNext()) {
				StaticVars.getFuelMap().add(new ArrayList<Float>());
				StaticVars.getFuelMap().get(row).add(Float.parseFloat(scanner.next()));
				}
		row++;
		}

		FuelMapreader.close();
		// If the map is copied in the suite it has a header, so remove it if its still there
		if (StaticVars.getFuelMap().get(0).get(0)==0 && StaticVars.getFuelMap().get(0).get(1)==1 && StaticVars.getFuelMap().get(0).get(2)==2 &&StaticVars.getFuelMap().get(0).get(3)==3)
		{
			StaticVars.getFuelMap().remove(0); 
		}
		if (StaticVars.isTargetFromCol()==false) {

			
			
			
			
			BufferedReader TargetMapreader = new BufferedReader(new FileReader(TargetMapfileName));
		  	String line2 = null;
			int row1=0;
			while ((line2 = TargetMapreader.readLine()) != null) {			
			scanner = new Scanner(line2);
			scanner.useDelimiter(",|\t");
			while (scanner.hasNext()) {
				StaticVars.getTargetMap().add(new ArrayList<Float>());
				StaticVars.getTargetMap().get(row1).add(Float.parseFloat(scanner.next()));
					}
			row1++;
			}
			
			TargetMapreader.close();
			
			TargetMapreader.close();			
			
			
			
		
		
		}
		

	}
}