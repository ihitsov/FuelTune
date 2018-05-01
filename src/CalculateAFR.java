import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class CalculateAFR{

	
	public static ArrayList<Float> X_Support = new ArrayList<Float>();
	public static ArrayList<Float> Y_Support = new ArrayList<Float>();
	


	public static ArrayList<Float> mXax = new ArrayList<Float>();
	public static ArrayList<Float> mYax = new ArrayList<Float>();
	
	public static ArrayList<Float> XData = new ArrayList<Float>();
	public static ArrayList<Float> YData = new ArrayList<Float>();
	public static ArrayList<Float> ZData = new ArrayList<Float>();
	public static ArrayList<Float> TData = new ArrayList<Float>();

	public static void DataFiltering() {
		XData.clear();
		YData.clear();
		ZData.clear();
		TData.clear();
		float maxXStep=(0.1f+6f-AFRTableClass.filterSliderVal)*Math.abs(mXax.get(0)-mXax.get(mXax.size()-1))/mXax.size();
		float maxYStep=(0.1f+6f-AFRTableClass.filterSliderVal)*Math.abs(mYax.get(0)-mYax.get(mYax.size()-1))/mYax.size();
		float maxZStep=2000f;


		for (int i=0; i<ReadXYZData.XData.size();i++) {

			if (i>0 && (ReadXYZData.XData.get(i-1)-ReadXYZData.XData.get(i))<maxXStep && (ReadXYZData.YData.get(i-1)-ReadXYZData.YData.get(i))<maxYStep && (ReadXYZData.ZData.get(i-1)-ReadXYZData.ZData.get(i))<maxZStep) {
			XData.add(ReadXYZData.XData.get(i));
			YData.add(ReadXYZData.YData.get(i));
			ZData.add(ReadXYZData.ZData.get(i));
			TData.add(ReadXYZData.TData.get(i));
			}

		}
	}
	private static void xBins() {
		mXax.clear();
		for (Integer i=0; i<(X_Support.size()-1); i++) {
			    mXax.add((X_Support.get(i)+X_Support.get(i+1))/2);
		}
		Float maxXax=X_Support.get(X_Support.size()-1)*2-mXax.get(mXax.size()-1);
		Float minXax=X_Support.get(0)-X_Support.get(1)+mXax.get(0);

		mXax.add(0, minXax);
		mXax.add(maxXax);
		
	}
	
	private static void yBins() {
		mYax.clear();
		for (Integer i=0; i<(Y_Support.size()-1); i++) {
			    mYax.add((Y_Support.get(i)+Y_Support.get(i+1))/2);
		}
		Float maxYax=Y_Support.get(Y_Support.size()-1)*2-mYax.get(mYax.size()-1);
		Float minYax=Y_Support.get(0)-Y_Support.get(1)+mYax.get(0);
		
		mYax.add(0, minYax);
		mYax.add(maxYax);	
	}
	
	
	private static void GetXSupport() {
		X_Support.clear();
		Y_Support.clear();
		Scanner reader = new Scanner(StaticVars.getXSupport());
		reader.useDelimiter(",|\t| ");
	while (reader.hasNext()) {
		String temp= reader.next();
		try {	
			X_Support.add(Float.parseFloat(temp));
			} catch (NumberFormatException e) {}
	}
	reader.close();
	}
	
	private static void GetYSupport() {
		Scanner reader = new Scanner(StaticVars.getYSupport());
		reader.useDelimiter(",|\t| ");

	while (reader.hasNext()) {
		String temp= reader.next();
		try {
		Y_Support.add(Float.parseFloat(temp));
		} catch (NumberFormatException e) {}
	}
	reader.close();
	}
	
	private static float getMode(ArrayList<Float> Data){
		float mode = 0;
		ArrayList<Float> SP = new ArrayList<Float>();
		SP.clear();
		if (Data.size()==1){mode=Data.get(0);}
		if (Data.size()==0) {mode=0;}
		else {
			
//		System.out.println("null");
		Float min = 100f;
		Float max = 0f;
		for (int j=0;j<Data.size();j++) { // get min and max of the input dataset
			if (Data.get(j)<min) {min=Data.get(j);}
			if (Data.get(j)>max) {max=Data.get(j);}
		}
		
		int nBins;
		if (Data.size()>20) {nBins=20;}// select the number of bins
		else {nBins=Data.size();}
		
		for (int i=0; i<nBins+1;i++) {// construct the support points for the bins
			SP.add(min +i*(max-min)/(nBins));
		}
		ArrayList<ArrayList<Float>> binnedData = new ArrayList<ArrayList<Float>>();
		for (int i=0;i<SP.size()-1;i++) { //bin data
			binnedData.add(new ArrayList<Float>());
			for (int j=0;j<Data.size();j++) {
				if (Data.get(j)>SP.get(i) && Data.get(j)<SP.get(i+1)) {
					binnedData.get(i).add(Data.get(j));
				}
			}
		}
		int maxMode=0;
		float ModeSum=0;
		int ModeCount=0;
		for (int i=0;i<SP.size()-1;i++) { //
			if (maxMode==binnedData.get(i).size()) {
				ModeSum+=((SP.get(i)+SP.get(i+1))/2);
				ModeCount++;
				mode=ModeSum/ModeCount;
			}
			if (maxMode<binnedData.get(i).size()){
				maxMode=binnedData.get(i).size();
				ModeSum=0;
				ModeCount=0;
				mode=(SP.get(i)+SP.get(i+1))/2;
			}
		}
		
		
		
		}
		return mode;
	}
	private static void TargetMapConvert() {
    	ArrayList<ArrayList<Float>> TargetMapTemp = new ArrayList<ArrayList<Float>>();
		TargetMapTemp.clear();
 		for (Integer i=0; i<Y_Support.size();i++) {
 			TargetMapTemp.add(new ArrayList<Float>());
     		for (Integer j=0; j<X_Support.size();j++) {
	            switch (StaticVars.getCaseTarget()){
	            case 0: TargetMapTemp.get(i).add(StaticVars.getTargetMap().get(i).get(j)); break;
	            case 1: TargetMapTemp.get(i).add((float) (StaticVars.getTargetMap().get(i).get(j)*14.7)); break;
	            case 2: TargetMapTemp.get(i).add((float) (StaticVars.getTargetMap().get(i).get(j)*-3.008/1000+22.35)); break;
	            case 3: TargetMapTemp.get(i).add((float) (StaticVars.getTargetMap().get(i).get(j)*3.008/1000+7.35)); break;
	            }
     		}	
 		}
 		// Invert Y axis
 		for (Integer i=0; i<Y_Support.size();i++) {
     		for (Integer j=0; j<X_Support.size();j++) {
     			 int row;
//				if (StaticVars.isInvertedYAxis()==true && StaticVars.isTargetMapInverted()==false) {row=Y_Support.size()-i-1; StaticVars.setTargetMapInverted(true);}
     			 row=i;
//     			 else {row=i;}
     			StaticVars.getTargetMap().get(i).set(j,TargetMapTemp.get(row).get(j));
     		}	
 		}
 		}
	

//	private static void FuelMapConvert() {
//    	ArrayList<ArrayList<Float>> TempMapInv = new ArrayList<ArrayList<Float>>();
//    	TempMapInv.clear();
// 		for (Integer i=0; i<Y_Support.size();i++) {
// 			TempMapInv.add(new ArrayList<Float>());
//     		for (Integer j=0; j<X_Support.size();j++) {
//     			TempMapInv.get(i).add(j,StaticVars.getFuelMap().get(i).get(j));
//     		}	
// 		}
// 		// Invert Y axis
//		 if (StaticVars.isInvertedYAxis()==true && StaticVars.isFuelMapInverted()==false) {
// 		for (Integer i=0; i<Y_Support.size();i++) {
//     		for (Integer j=0; j<X_Support.size();j++) {
//     			StaticVars.getFuelMap().get(i).set(j,TempMapInv.get(Y_Support.size()-i-1).get(j));
//     		}	
// 		}
// 		StaticVars.setFuelMapInverted(true);
//		 }
//	}
	
	public static void CalcData() {
		StaticVars.getMean().clear();
		StaticVars.getMode().clear();
		StaticVars.getPoints().clear();
		StaticVars.getSTD().clear();
		StaticVars.getAFR().clear();
		StaticVars.getTarget().clear();
		StaticVars.getScatterXData().clear();
		StaticVars.getScatterYData().clear();
		StaticVars.getAllData().clear();
		
		for (Float i:mYax) { //
			StaticVars.getMean().add(new ArrayList<Float>());
			StaticVars.getMode().add(new ArrayList<Float>());
			StaticVars.getPoints().add(new ArrayList<Float>());
			StaticVars.getSTD().add(new ArrayList<Float>());
			StaticVars.getTarget().add(new ArrayList<Float>());
		}
		for (int i=0;i<(X_Support.size()*Y_Support.size());i++) {
		StaticVars.getAllData().add(new ArrayList<Float>());
		StaticVars.getScatterXData().add(new ArrayList<Float>());
		StaticVars.getScatterYData().add(new ArrayList<Float>());
		}
		
		for (Integer j=0; j< (mYax.size()-1); j++) {// iterate row


			for (Integer k=0;k< (mXax.size()-1); k++) { //iterate cols
//				System.out.println(StaticVars.getAllData().get(k).size());
				ArrayList<Float> mTarget = new ArrayList<Float>();
				mTarget.clear();
				int idx;
				if (StaticVars.isInvertedYAxis()==true) {idx=(Y_Support.size()-j-1)*(X_Support.size())+k;}
				else {idx=j*(X_Support.size())+k;}
				System.out.println(idx);
				int Conseq = 0;// how many consecutive samples are recorded
				int sizeMean=0;
				float sumMean=0f;
				float CenterX=(mXax.get(k)+mXax.get(k+1))/2;
				float DistanceX=mXax.get(k+1)-mXax.get(k);
				float CenterY=(mYax.get(j)+mYax.get(j+1))/2;
				float DistanceY=mYax.get(j+1)-mYax.get(j);	
				
				for (Integer i=0; i< XData.size();i++) { //iterate data
					// if it is in the bin range add to mAFR and mTarget
					if (ZData.get(i)<AFRTableClass.maxAFR && ZData.get(i)>AFRTableClass.minAFR && XData.get(i)>mXax.get(k)  && XData.get(i)<mXax.get(k+1)  && YData.get(i)>mYax.get(j) && YData.get(i)<mYax.get(j+1)) {
						Conseq++;
						mTarget.add(TData.get(i));
						StaticVars.getAllData().get(idx).add(ZData.get(i));
						StaticVars.getScatterXData().get(idx).add(XData.get(i));
						StaticVars.getScatterYData().get(idx).add(YData.get(i));
						int BonusX=(int) (1-(Math.abs(XData.get(i)-CenterX))/DistanceX);
						int BonusY=(int) (1-(Math.abs(YData.get(i)-CenterY))/DistanceY);
						if (Conseq>10){Conseq=5;}
						for (int n=0; n<(Conseq+BonusX+BonusY);n++) {
							 sizeMean++;	  
					         sumMean += ZData.get(i);		        

						}
					}
					else {Conseq=0;}
			}

//		        float sumMean=(float)0;
		        float meanAFR=(float)0;
		        float meanTarget=(float)0;
//		        float sumTarget=(float)0;
		        float temp=(float)0;
		        float STDev=(float)0;

		        meanAFR=sumMean/sizeMean;	
		        System.out.println(meanAFR);
		        for(int a=0 ; a<StaticVars.getAllData().get(idx).size();a++) {
		            temp += (StaticVars.getAllData().get(idx).get(a)-meanAFR)*(StaticVars.getAllData().get(idx).get(a)-meanAFR); 
				}
				STDev=(float) Math.sqrt(temp/(StaticVars.getAllData().get(idx).size()-1));
				if (Float.isNaN(STDev)) {
				    STDev=(float) 0;
				}

				int row;
		        if (StaticVars.isInvertedYAxis()==true) {
		        row=Y_Support.size()-j-1;	
		        }else {row=j;}

				StaticVars.getMean().get(row).add(meanAFR);
				StaticVars.getMode().get(row).add(getMode(StaticVars.getAllData().get(idx)));
				StaticVars.getTarget().get(row).add(meanTarget);
				StaticVars.getPoints().get(row).add((float)StaticVars.getAllData().get(idx).size());
				StaticVars.getSTD().get(row).add(STDev);     
		}
	}
      System.gc();

	}

	
	public static void getAFR() {
		int MaxCount=100;
		for (int i=0; i<Y_Support.size();i++) {
			StaticVars.getAFR().add(new ArrayList<Float>());
     		for (int j=0; j<X_Support.size();j++) {
     			float Count=StaticVars.getPoints().get(i).get(j);
     			if (Count>100){Count=MaxCount;}
     			// the 0.8 coeff makes it that the final afr is up to 80 percent mode and 20 mean
     			Float tempVal = (float) ((StaticVars.getMean().get(i).get(j)*(MaxCount-Count*0)+StaticVars.getMode().get(i).get(j)*(Count*0))/MaxCount);
     			StaticVars.getAFR().get(i).add(tempVal);
     			tempVal=0f;
     			}
 		}
	}
	public static void main(String[] args) {
		
		// Convert T and Z to proper units
		int ZFactor=0;
		if (ReadXYZData.ZDataSum/ReadXYZData.ZDataCount>1000 && ReadXYZData.ZDataSum/ReadXYZData.ZDataCount<5000)
			{ZFactor=1000;}
		else {ZFactor=1;}		
		for (Integer i=0; i<ReadXYZData.ZData.size();i++) {
	        if (ReadXYZData.ZData.get(i)!=0){
	            switch (StaticVars.getCaseZ()){
	            case 0: ; break;
	            case 1: ReadXYZData.ZData.set(i,(float) (ReadXYZData.ZData.get(i)*14.7)); break;
	            case 2: ReadXYZData.ZData.set(i,(float) (ReadXYZData.ZData.get(i)*-3.008/ZFactor+22.35)); break;
	            case 3: ReadXYZData.ZData.set(i,(float) (ReadXYZData.ZData.get(i)*3.008/ZFactor+7.35)); break;
	            }
		}
		}
		StaticVars.setCaseZ(0);// reset to zero in case it is run again.
		
		int TFactor=0;
		if (ReadXYZData.TDataSum/ReadXYZData.TDataCount>1000 && ReadXYZData.TDataSum/ReadXYZData.TDataCount<5000)
			{TFactor=1000;} // If Z is in volts use proper TFactor
		else {TFactor=1;}
		
		for (Integer i=0; i<ReadXYZData.TData.size();i++) {
	        if (ReadXYZData.TData.get(i)!=0){
	            switch (StaticVars.getCaseTarget()){
	            case 0: ; break;
	            case 1: ReadXYZData.TData.set(i,(float) (ReadXYZData.TData.get(i)*14.7)); break;
	            case 2: ReadXYZData.TData.set(i,(float) (ReadXYZData.TData.get(i)*-3.008/TFactor+22.35)); break;
	            case 3: ReadXYZData.TData.set(i,(float) (ReadXYZData.TData.get(i)*3.008/TFactor+7.35)); break;
	            }
		}
		}

		
		
		X_Support.clear();
		Y_Support.clear();
		StaticVars.getMean().clear();
		StaticVars.getTarget().clear();
		StaticVars.getPoints().clear();
		StaticVars.getSTD().clear();
		mXax.clear();
		mYax.clear();
		
		GetXSupport();
		GetYSupport();

		xBins();
		yBins();

		DataFiltering();

		if (StaticVars.isTargetFromCol()==false) {
        try {
			TargetMapConvert();
		} catch (Exception e) {
			String infoMessage="Problem occured, check if the size of X and Y axes are the same as the fuel and target maps";
    		String titleBar="Error!";
    		JOptionPane.showMessageDialog(null, infoMessage, "" + titleBar, JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}
		}
        StaticVars.setCaseTarget(0);//reset to zero in case it is run again
//        FuelMapConvert();



		
        StaticVars.getMean().clear();
        StaticVars.getMode().clear();

			CalcData();
			getAFR();
			if (StaticVars.isTargetFromCol()==true) {
//			XYZData.setTAFR(StaticVars.getTarget());
			}
	}


}
