
package salesinvoice.model;

import java.util.ArrayList;
import javax.swing.event.EventListenerList;
import javax.swing.table.AbstractTableModel;


public class TableOfLinesModel extends AbstractTableModel{
      private ArrayList<LineClass>Lines ;
      private String[] columns = {"Number", "Item Name", "Item Price", "Count", "Item Total"};

    public ArrayList<LineClass> getLines() {
        return Lines;
    }

    public EventListenerList getListenerList() {
        return listenerList;
    }

    public TableOfLinesModel(ArrayList<LineClass> Lines) {
        this.Lines = Lines;
    }
      
      

    @Override
    public int getRowCount() {
       return Lines.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }
    
     public String getColumnName(int column)
   {
       return columns[column];
   }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
         
         LineClass line = Lines.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return line.getInvoice().getNumber();
            case 1: return line.getItem();
            case 2: return line.getPrice();
            case 3: return line.getCount();
            case 4: return line.getTotalLine();
            default : return "";
        }
    }
    
}
