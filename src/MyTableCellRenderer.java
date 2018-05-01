import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MyTableCellRenderer extends DefaultTableCellRenderer {
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;







		@Override
	    public Color getBackground() {
			Float Span=(float) (setdata.max-setdata.min);
	        String steak = getText();
	        if (steak.isEmpty()==false) {
	        	Color Col;
				try {
					Col = new Color((int) (255*(Float.parseFloat(steak)-setdata.min)/Span),255,50);
				} catch (Exception e) {
					Col = new Color(255,255,255);
				}
				if (steak.contains("\u200B")){Col = new Color(200,200,200);} //color the axes

	            return Col;
	        }

	            return super.getBackground();
	        }
		
		@Override // Bold cells that will be corrected in all maps
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
		        int column) {

		    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		    try {
		    	if (isSelected==true){
		    		try {
						int ROW=row-1;

						AFRTableClass.NewFuelMap.get(ROW).set(column-1, (float) Float.parseFloat(value.toString()));
						
					} catch (Exception e) {}

		    		Color Col = new Color (150,150,150);
		    		super.setBorder(BorderFactory.createMatteBorder(TOP, LEFT, BOTTOM, RIGHT, Col)); 
		    	}
		    	
		    	int ROW=row-1;
				if ( row>0 && column >0 && AFRTableClass.CorrectionMap.get(ROW).get(column-1)!=0) {
				    this.setFont(this.getFont().deriveFont(Font.BOLD));
				}
			} catch (Exception e) {}
		    return this;
		}
		
	    }
	