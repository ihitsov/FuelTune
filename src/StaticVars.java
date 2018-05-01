import java.util.ArrayList;

public class StaticVars {
	public static boolean InvertedYAxis;
	public static String XSupport;
	public static String YSupport;

	
	public static int CaseZ=0;
	public static int CaseTarget=0;
	public static boolean TargetFromCol;

	
	public static ArrayList<ArrayList<Float>> AFR = new ArrayList<ArrayList<Float>>();
	public static ArrayList<ArrayList<Float>> Mean = new ArrayList<ArrayList<Float>>();
	public static ArrayList<ArrayList<Float>> Mode = new ArrayList<ArrayList<Float>>();
	public static ArrayList<ArrayList<Float>> Target = new ArrayList<ArrayList<Float>>();
	public static ArrayList<ArrayList<Float>> Points = new ArrayList<ArrayList<Float>>();
	public static ArrayList<ArrayList<Float>> STD = new ArrayList<ArrayList<Float>>();
	public static ArrayList<ArrayList<Float>> AllData = new ArrayList<ArrayList<Float>>();
	public static ArrayList<ArrayList<Float>> ScatterXData = new ArrayList<ArrayList<Float>>();
	public static ArrayList<ArrayList<Float>> ScatterYData = new ArrayList<ArrayList<Float>>();
	
	public static ArrayList<ArrayList<Float>> FuelMap = new ArrayList<ArrayList<Float>>();
	public static ArrayList<ArrayList<Float>> TargetMap = new ArrayList<ArrayList<Float>>();
	
	public static ArrayList<ArrayList<Float>> getFuelMap() {
		return FuelMap;
	}
	public static void setFuelMap(ArrayList<ArrayList<Float>> fuelMap) {
		FuelMap = fuelMap;
	}
	public static ArrayList<ArrayList<Float>> getTargetMap() {
		return TargetMap;
	}
	public static void setTargetMap(ArrayList<ArrayList<Float>> targetMap) {
		TargetMap = targetMap;
	}
	public static ArrayList<ArrayList<Float>> getAFR() {
		return AFR;
	}
	public static void setAFR(ArrayList<ArrayList<Float>> aFR) {
		AFR = aFR;
	}
	public static ArrayList<ArrayList<Float>> getMean() {
		return Mean;
	}
	public static void setMean(ArrayList<ArrayList<Float>> mean) {
		Mean = mean;
	}
	public static ArrayList<ArrayList<Float>> getMode() {
		return Mode;
	}
	public static void setMode(ArrayList<ArrayList<Float>> mode) {
		Mode = mode;
	}
	public static ArrayList<ArrayList<Float>> getTarget() {
		return Target;
	}
	public static void setTarget(ArrayList<ArrayList<Float>> target) {
		Target = target;
	}
	public static ArrayList<ArrayList<Float>> getPoints() {
		return Points;
	}
	public static void setPoints(ArrayList<ArrayList<Float>> points) {
		Points = points;
	}
	public static ArrayList<ArrayList<Float>> getSTD() {
		return STD;
	}
	public static void setSTD(ArrayList<ArrayList<Float>> sTD) {
		STD = sTD;
	}
	public static ArrayList<ArrayList<Float>> getAllData() {
		return AllData;
	}
	public static void setAllData(ArrayList<ArrayList<Float>> allData) {
		AllData = allData;
	}
	public static ArrayList<ArrayList<Float>> getScatterXData() {
		return ScatterXData;
	}
	public static void setScatterXData(ArrayList<ArrayList<Float>> scatterXData) {
		ScatterXData = scatterXData;
	}
	public static ArrayList<ArrayList<Float>> getScatterYData() {
		return ScatterYData;
	}
	public static void setScatterYData(ArrayList<ArrayList<Float>> scatterYData) {
		ScatterYData = scatterYData;
	}
	public static boolean isInvertedYAxis() {
		return InvertedYAxis;
	}
	public static void setInvertedYAxis(boolean invertedYAxis) {
		InvertedYAxis = invertedYAxis;
	}
	public static String getXSupport() {
		return XSupport;
	}
	public static void setXSupport(String xSupport) {
		XSupport = xSupport;
	}
	public static String getYSupport() {
		return YSupport;
	}
	public static void setYSupport(String ySupport) {
		YSupport = ySupport;
	}
	public static int getCaseZ() {
		return CaseZ;
	}
	public static void setCaseZ(int caseZ) {
		CaseZ = caseZ;
	}
	public static int getCaseTarget() {
		return CaseTarget;
	}
	public static void setCaseTarget(int caseTarget) {
		CaseTarget = caseTarget;
	}
	public static boolean isTargetFromCol() {
		return TargetFromCol;
	}
	public static void setTargetFromCol(boolean targetFromCol) {
		TargetFromCol = targetFromCol;
	}

}
