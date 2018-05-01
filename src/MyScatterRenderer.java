import java.awt.Color;
import java.awt.Paint;
import org.jfree.chart.renderer.xy.XYShapeRenderer;

//public class ScatterColors extends JFrame {
		public class MyScatterRenderer extends XYShapeRenderer {
			
//		int row;
//		int col;
		int idx;
//		Float cellAFR;
		Float cellMin;
		Float cellMax;
        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		public MyScatterRenderer(boolean shapes) {
            super();
        }

        public MyScatterRenderer(int idxin, Float cellMinin, Float cellMaxin) {
			// TODO Auto-generated constructor stub    
//        	row=rowin;
//        	col=colin;
        	idx=idxin;
//        	cellAFR=cellAFRin;
//			System.out.println(cellAFR);
        	cellMin=cellMinin;
        	cellMax=cellMaxin;
		}

		@Override
        public Paint getItemPaint(int row, int col) {
			Float Span=cellMax-cellMin;
//			System.out.println("cellMax="+cellMax);
//			System.out.println("cellMin="+cellMin);
//			System.out.println("cellAFR="+CalculateAFR.AllData.get(idx).get(col));
//			System.out.println("RED="+255*(cellAFR-cellMin)/Span);
//			System.out.println("row="+row);
//			System.out.println("col="+col);
//          if (col <1000000) {
////			Color Col;
			float red=255*(StaticVars.getAllData().get(idx).get(col)-cellMin)/Span;
			if (red<0){red=0f;};
			if (red>255){red=255f;};

			Color Col = new Color((int) red,255,50);
                return Col;
//            } else {
//                return super.getItemPaint(row, col);
//            }
        }
    }

