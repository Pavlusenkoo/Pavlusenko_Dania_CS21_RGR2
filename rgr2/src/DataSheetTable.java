import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class DataSheetTable extends JPanel{
    private JPanel panelWithButtons;
    private JButton deleteButton;
    private JScrollPane scrollPane;
    private JTable dataTable;
    private DataSheetTableModel dataSheetTableModel;

    public DataSheetTable(DataSheet dataSheet){
        initDataSheetPanel();
        initDataSheetTable(dataSheet);
        initScrollPane();
        initPanelWithButtons();
    }

    private void initDataSheetPanel(){
        setLayout(new BorderLayout());
    }

    private void initScrollPane(){
        scrollPane = new JScrollPane(dataTable);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);
    }

    private void initPanelWithButtons(){
        panelWithButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        add(panelWithButtons, BorderLayout.SOUTH);

        initAddButton();
        initDeleteButton();
    }

    private void initAddButton(){
        JButton addButton = new JButton("Add (+)");
        panelWithButtons.add(addButton);
        Random r = new Random();

        addButton.addActionListener(e -> {
            Data data = getRandomData();

            if (dataSheetTableModel.isEmpty()) {
                dataSheetTableModel.getDataSheet().getDataItem(0).setDate(data.getDate());
                dataSheetTableModel.getDataSheet().getDataItem(0).setX(data.getX());
                dataSheetTableModel.getDataSheet().getDataItem(0).setY(data.getY());
            } else
                dataSheetTableModel.getDataSheet().addData(data);

            dataTable.repaint();
            dataSheetTableModel.setRowCount(dataSheetTableModel.getDataSheet().dataCount());
            revalidate();
            dataSheetTableModel.fireDataSheetChanges();
        });
    }

    private Data getRandomData(){
        Random random = new Random();
        String date = "01.01.1930";
        double x = random.nextDouble() * 5;
        double y = random.nextDouble() * 5;
        return new Data(x, y, date);
    }

    private void initDeleteButton(){
        deleteButton = new JButton("Del (-)");
        panelWithButtons.add(deleteButton);
        deleteButton.addActionListener(e -> {
            if (dataSheetTableModel.getRowCount() > 1){
                dataSheetTableModel.setRowCount(dataSheetTableModel.getRowCount() - 1);
                dataSheetTableModel.getDataSheet().removeData(dataSheetTableModel.getDataSheet().dataCount() - 1);

            } else {
                dataSheetTableModel.getDataSheet().getDataItem(0).setDate("");
                dataSheetTableModel.getDataSheet().getDataItem(0).setX(0);
                dataSheetTableModel.getDataSheet().getDataItem(0).setY(0);
            }

            dataTable.revalidate();
            dataTable.repaint();
            dataSheetTableModel.fireDataSheetChanges();
            repaint();
        });
    }

    private void initDataSheetTable(DataSheet dataSheet){
        dataTable = new JTable();
        dataSheetTableModel = new DataSheetTableModel(dataSheet);
        dataTable.setModel(dataSheetTableModel);
    }

    public void setTableModel(DataSheetTableModel dataSheetTableModel){
        this.dataSheetTableModel = dataSheetTableModel;
        revalidate();
    }

    public DataSheetTableModel getTableModel(){
        return dataSheetTableModel;
    }

    public void revalidate(){
        if (dataTable != null)
            dataTable.revalidate();
    }
}
