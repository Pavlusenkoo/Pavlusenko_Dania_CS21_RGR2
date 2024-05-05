import javax.swing.*;
import java.awt.*;


public class MainFrame extends JFrame {
    private DataSheetTable dataSheetTable;
    private DataSheetGraph dataSheetGraph;
    private DataSheet dataSheet;
    private JPanel downButtonsPanel;
    private JFileChooser fileChooser;

    public MainFrame(){
        initMainFrame();
        initMainFrameComponents();
    }

    public void initMainFrame(){
        setTitle("Test Beans");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 400);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public void initMainFrameComponents(){
        JPanel panelWithGraphAndTable = new JPanel(new GridLayout(0, 2));

        dataSheet = new DataSheet();
        dataSheet.addData(new Data());

        dataSheetTable = new DataSheetTable(dataSheet);
        panelWithGraphAndTable.add(dataSheetTable);

        dataSheetGraph = new DataSheetGraph(dataSheet);
        panelWithGraphAndTable.add(dataSheetGraph);

        dataSheetTable.getTableModel().addDataSheetChangeListener(e -> {
            dataSheetGraph.revalidate();
            dataSheetGraph.repaint();
        });

        add(panelWithGraphAndTable, BorderLayout.CENTER);

        initDownButtonsPanel();
    }

    private void initDownButtonsPanel() {
        downButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));

        initFileChooserButton();
        initClearButton();
        initSaveButton();
        initExitButton();

        add(downButtonsPanel, BorderLayout.SOUTH);
    }

    private void initFileChooserButton() {
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new java.io.File("."));

        JButton chooseFileButton = new JButton("Відкрити");
        downButtonsPanel.add(chooseFileButton);

        chooseFileButton.addActionListener(e -> {
            if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {
                String filePath = fileChooser.getSelectedFile().getPath();
                dataSheet = XMLParser.parseXMLToDataSheet(filePath);
                updateDataSheet();
            }
        });
    }

    private void updateDataSheet(){
        dataSheetTable.getTableModel().setDataSheet(dataSheet);
        dataSheetTable.revalidate();
        dataSheetGraph.setDataSheet(dataSheet);
    }

    private void initClearButton(){
        JButton clearButton = new JButton("Очистити");
        downButtonsPanel.add(clearButton);

        clearButton.addActionListener(e -> {
            dataSheet = new DataSheet();
            dataSheet.addData(new Data());
            updateDataSheet();
        });
    }

    private void initSaveButton(){
        JButton saveButton = new JButton("Зберегти");
        downButtonsPanel.add(saveButton);

        saveButton.addActionListener(e -> {
            if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(null)){
                String fileName = fileChooser.getSelectedFile().getPath();
                XMLParser.parseDataSheetToXML(dataSheet, fileName);
                JOptionPane.showMessageDialog(null, fileName.trim() + " saved!",
                        "Результати збережені.", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void initExitButton(){
        JButton exitButton = new JButton("Завершити");
        downButtonsPanel.add(exitButton);

        exitButton.addActionListener(e -> dispose());
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}
