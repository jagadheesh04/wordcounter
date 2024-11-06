import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.Map;

public class MedicineCounterGUI {
    private Frame frame;
    private TextField medicineField;
    private TextArea displayArea;
    private BinarySearchTree medicines;
    private final String filename = "medicines.txt";

    public MedicineCounterGUI() {
        medicines = new BinarySearchTree();
        medicines.loadFromFile(filename);
        createGUI();
    }

    private void createGUI() {
        frame = new Frame("Medicine Frequency Counter");
        frame.setSize(400, 400);
        frame.setLayout(new FlowLayout());

        Label label = new Label("Enter Medicine Name:");
        medicineField = new TextField(20);
        Button addButton = new Button("Add Medicine");
        Button removeButton = new Button("Remove Medicine");
        Button searchButton = new Button("Search Medicine");
        Button countButton = new Button("Count Frequencies");
        displayArea = new TextArea(10, 30);
        displayArea.setEditable(false);

        addButton.addActionListener(e -> addMedicine());
        removeButton.addActionListener(e -> removeMedicine());
        searchButton.addActionListener(e -> searchMedicine());
        countButton.addActionListener(e -> countWordFrequencies("medicines.txt")); // Update with your text file

        frame.add(label);
        frame.add(medicineField);
        frame.add(addButton);
        frame.add(removeButton);
        frame.add(searchButton);
        frame.add(countButton);
        frame.add(displayArea);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                medicines.saveToFile(filename);
                frame.dispose();
            }
        });

        frame.setVisible(true);
    }

    private void addMedicine() {
        String name = medicineField.getText();
        if (!name.isEmpty()) {
            medicines.add(name);
            displayMedicines();
            medicineField.setText("");
            medicines.saveToFile(filename);
        }
    }

    private void removeMedicine() {
        String name = medicineField.getText();
        if (!name.isEmpty()) {
            if (medicines.remove(name)) {
                displayMedicines();
                medicineField.setText("");
                medicines.saveToFile(filename);
            } else {
                displayArea.append(name + " not found!\n");
            }
        }
    }

    private void searchMedicine() {
        String name = medicineField.getText();
        if (!name.isEmpty()) {
            if (medicines.contains(name)) {
                displayArea.append(name + " found!\n");
            } else {
                displayArea.append(name + " not found!\n");
            }
            medicineField.setText("");
        }
    }

    private void countWordFrequencies(String filename) {
        Map<String, Integer> frequencyMap = medicines.countWordFrequencies(filename);
        List<Map.Entry<String, Integer>> sortedFrequencies = medicines.getSortedFrequencies(frequencyMap);

        displayArea.setText("Word Frequencies:\n");
        for (Map.Entry<String, Integer> entry : sortedFrequencies) {
            displayArea.append(entry.getKey() + ": " + entry.getValue() + "\n");
        }
    }

    private void displayMedicines() {
        displayArea.setText("");
        for (String entry : medicines.inOrderTraversal()) {
            displayArea.append(entry + "\n");
        }
    }

    public static void main(String[] args) {
        new MedicineCounterGUI();
    }
}
