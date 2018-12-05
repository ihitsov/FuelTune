import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

class setdata {
		static Float min;
		static Float max;
		static Float tempval= 0f;
		public static String[][] data = new String[CalculateAFR.Y_Support.size()+1][CalculateAFR.X_Support.size()+1];// +1 to make space for axis labels
		public static ArrayList<ArrayList<Float>> AFRTemp = new ArrayList<ArrayList<Float>>();
		public static String[][] dataTemp = new String[CalculateAFR.Y_Support.size()][CalculateAFR.X_Support.size()];
		
		public static Float round(Float value, int places) {
		    if (places < 0 || value==null) {return value;};//throw new IllegalArgumentException();
		    BigDecimal bd = new BigDecimal(value);
		    bd = bd.setScale(places, RoundingMode.HALF_UP);
		    return bd.floatValue();
		}
		
public static void main(){
	min=(float) 0;
	max=(float) 0;
    for (Integer row=0; row<CalculateAFR.Y_Support.size();row++) {	 
    	for (Integer col=0; col<CalculateAFR.X_Support.size();col++) {
    		try {
				if (AFRTemp.get(row).get(col) == (Float)AFRTemp.get(row).get(col)) {
				tempval = round(AFRTemp.get(row).get(col),3);
				if (tempval<min) {min=tempval;}
				if (tempval>max) {max=tempval;}
				}
				DecimalFormat format = new DecimalFormat("0.###");
				dataTemp[row][col]=format.format(tempval);
				if (AFRTemp.get(row).get(col) == 0){
					dataTemp[row][col]="";
				}
			} catch (Exception e) {dataTemp[row][col]="";}
    	}
    }
    
    // Make space for axis labels and invert Y axis to make it T7suite like
    for (Integer row=0; row<CalculateAFR.Y_Support.size();row++) {	    
    	for (Integer col=0; col<CalculateAFR.X_Support.size();col++) {
   		 int Yindex;
//   		 if (StaticVars.isInvertedYAxis()==true){Yindex=(CalculateAFR.Y_Support.size()-row-1);}
//   		 else {Yindex=(row);}
   		Yindex=(row);
        data[row+1][col+1]=dataTemp[Yindex][col];
    	}
    }	        
    
	for (Integer i=0; i< CalculateAFR.X_Support.size();i++) {
		DecimalFormat format = new DecimalFormat("0.#");
		data[0][i+1]=(format.format(CalculateAFR.X_Support.get(i))+"\u200B");//put an invisible character in the axes to distinguish them in the color scheme
       }	
	//CalculateAFR.Y_Support.size()
	for (Integer i=0; i< CalculateAFR.Y_Support.size();i++) {
		 int Yindex;
		 if (StaticVars.isInvertedYAxis()==true){Yindex=(CalculateAFR.Y_Support.size()-i-1);}
		 else {Yindex=(i);}
	    DecimalFormat format = new DecimalFormat("0.#");
		data[i+1][0]=(format.format(CalculateAFR.Y_Support.get(Yindex))+"\u200B");//put an invisible character in the axes to distinguish them in the color scheme
       }
	}

public static Float getMin() {
	return min;
}

public static void setMin(Float min) {
	setdata.min = min;
}

public static Float getMax() {
	return max;
}

public static void setMax(Float max) {
	setdata.max = max;
}

public static String[][] getData() {
	return data;
}

public static void setData(String[][] data) {
	setdata.data = data;
}

public static ArrayList<ArrayList<Float>> getAFRTemp() {
	return AFRTemp;
}

public static void setAFRTemp(ArrayList<ArrayList<Float>> aFR) {
	AFRTemp = aFR;
}
}
