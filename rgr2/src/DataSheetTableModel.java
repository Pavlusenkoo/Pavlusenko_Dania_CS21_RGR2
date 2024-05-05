import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class DataSheetTableModel extends AbstractTableModel{
    private static final long serialVersionUID = 1L;

    private final ArrayList <DataSheetChangeListener> listenersList = new ArrayList<>();
    private final DataSheetChangeEvent event = new DataSheetChangeEvent(this);

    private static final int columnCount = 3;
    private int rowCount = 1;
    private DataSheet dataSheet = null;
    String [] columnNames = {"Date", "X Value", "Y Value"};

    public DataSheetTableModel(DataSheet dataSheet){
        setDataSheet(dataSheet);
    }

    public DataSheet getDataSheet(){
        return dataSheet;
    }

    public void setDataSheet(DataSheet newDataSheet){
        dataSheet = newDataSheet;
        rowCount = dataSheet.dataCount();
        fireDataSheetChanges();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public String getColumnName(int column){
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return columnIndex >= 0;
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex){
        try {
            double d;
            if (dataSheet != null) {
                if (columnIndex == 0) {
                    dataSheet.getDataItem(rowIndex).setDate((String) value);
                } else if (columnIndex == 1) {
                    d = Double.parseDouble((String) value);
                    dataSheet.getDataItem(rowIndex).setX(d);
                } else if (columnIndex == 2) {
                    d = Double.parseDouble((String) value);
                    dataSheet.getDataItem(rowIndex).setY(d);
                }
            }
            fireDataSheetChanges();
        } catch (Exception ex) {}
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (dataSheet != null) {
            if (columnIndex == 0)
                return dataSheet.getDataItem(rowIndex).getDate();
            else if (columnIndex == 1)
                return dataSheet.getDataItem(rowIndex).getX();
            else if (columnIndex == 2)
                return dataSheet.getDataItem(rowIndex).getY();
        }
        return null;
    }

    public void setRowCount(int rowCount){
        if (rowCount > 0)
            this.rowCount = rowCount;
    }

    public void removeDataSheetChangeListener(DataSheetChangeListener listener){
        listenersList.remove(listener);
    }

    public void addDataSheetChangeListener(DataSheetChangeListener listener){
        listenersList.add(listener);
    }

    protected void fireDataSheetChanges(){
        for (DataSheetChangeListener aListenersList : listenersList)
            (aListenersList).dataChange(event);
    }

    public boolean isEmpty(){
        return dataSheet.dataCount() == 1 && dataSheet.getDataItem(0).getDate().equals("");
    }
}
