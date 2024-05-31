import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MarksCalculatorGUI extends JFrame {
    private JComboBox<String> subjectTypeComboBox;
    private JComboBox<String> creditHoursField;
    private JTextField assignmentObtainedMarksField;
    private JTextField assignmentTotalMarksField;
    private JTextField quizObtainedMarksField;
    private JTextField quizTotalMarksField;
    private JTextField midTermObtainedMarksField;
    private JTextField midTermTotalMarksField;
    private JTextField finalObtainedMarksField;
    private JTextField finalTotalMarksField;
    private JTextField labAssignmentObtainedMarksField;
    private JTextField labAssignmentTotalMarksField;
    private JTextField labMidTermObtainedMarksField;
    private JTextField labMidTermTotalMarksField;
    private JLabel resultLabel;
    private JLabel noteLabel;
    private int creditHrs;

    public MarksCalculatorGUI() {
        setTitle("Marks Calculator");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel subjectTypeLabel = new JLabel("Subject Type:");
        subjectTypeComboBox = new JComboBox<>(new String[]{"Theory", "Lab"});
        subjectTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFieldsVisibility();
            }
        });

        JLabel creditHoursLabel = new JLabel("Credit Hours:");
        //creditHoursField = new JTextField();
        creditHoursField = new JComboBox<>();

        // Add header labels
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(subjectTypeLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(subjectTypeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(creditHoursLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(creditHoursField, gbc);

        // Table headers
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Obtained Marks"), gbc);
        gbc.gridx = 2;
        panel.add(new JLabel("Total Marks"), gbc);

        // Initialize text fields
        assignmentObtainedMarksField = new JTextField();
        assignmentTotalMarksField = new JTextField();
        quizObtainedMarksField = new JTextField();
        quizTotalMarksField = new JTextField();
        midTermObtainedMarksField = new JTextField();
        midTermTotalMarksField = new JTextField();
        finalObtainedMarksField = new JTextField();
        finalTotalMarksField = new JTextField();
        labAssignmentObtainedMarksField = new JTextField();
        labAssignmentTotalMarksField = new JTextField();
        labMidTermObtainedMarksField = new JTextField();
        labMidTermTotalMarksField = new JTextField();

        // Add marks fields
        addMarksRow(panel, gbc, 3, "Assignment", assignmentObtainedMarksField, assignmentTotalMarksField);
        addMarksRow(panel, gbc, 4, "Quiz", quizObtainedMarksField, quizTotalMarksField);
        addMarksRow(panel, gbc, 5, "Mid Term", midTermObtainedMarksField, midTermTotalMarksField);
        addMarksRow(panel, gbc, 6, "Final", finalObtainedMarksField, finalTotalMarksField);
        addMarksRow(panel, gbc, 7, "Lab Assignment", labAssignmentObtainedMarksField, labAssignmentTotalMarksField);
        addMarksRow(panel, gbc, 8, "Lab Mid Term", labMidTermObtainedMarksField, labMidTermTotalMarksField);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        panel.add(new JLabel(), gbc);  // Empty cell for spacing
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        JButton calculateButton = new JButton("Calculate");
        panel.add(calculateButton, gbc);
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        resultLabel = new JLabel("Marks Lost: ");
        panel.add(resultLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 3;
        noteLabel = new JLabel("Note: If anything is not conducted, leave it empty.");
        panel.add(noteLabel, gbc);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateMarksLost();
            }
        });

        add(panel);
        updateFieldsVisibility();
    }

    private void addMarksRow(JPanel panel, GridBagConstraints gbc, int row, String label, JTextField obtainedField, JTextField totalField) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(new JLabel(label + ":"), gbc);
        gbc.gridx = 1;
        panel.add(obtainedField, gbc);
        gbc.gridx = 2;
        panel.add(totalField, gbc);

        if (label.contains("Lab")) {
            obtainedField.setVisible(false);
            totalField.setVisible(false);
        }
    }

    private void updateFieldsVisibility() {
        boolean isLab = subjectTypeComboBox.getSelectedItem().equals("Lab");
        labAssignmentObtainedMarksField.setVisible(isLab);
        labAssignmentTotalMarksField.setVisible(isLab);
        labMidTermObtainedMarksField.setVisible(isLab);
        labMidTermTotalMarksField.setVisible(isLab);
        labAssignmentObtainedMarksField.getParent().revalidate();
        labAssignmentObtainedMarksField.getParent().repaint();
        creditHoursField.removeAllItems();
        if (isLab){
            creditHoursField.addItem("3(2+1)");
            creditHoursField.addItem("4(3+1)");
        }
        else
        {
            creditHoursField.addItem("2");
            creditHoursField.addItem("3");
        }
    }

    private void calculateMarksLost() {
        try {
            String creditHourCalc = creditHoursField.getSelectedItem().toString();
            if (creditHourCalc.equals("2")){
                creditHrs = 2;
            }
            else if (creditHourCalc.equals("3")){
                creditHrs = 3;
            }
            else if (creditHourCalc.equals("3(2+1)")){
                creditHrs = 4;
            }
            else if (creditHourCalc.equals("4(3+1)")){
                creditHrs = 5;
            }
            int creditHours = creditHrs;
            double assignmentObtained = parseOrZero(assignmentObtainedMarksField.getText());
            double assignmentTotal = parseOrZero(assignmentTotalMarksField.getText());
            double quizObtained = parseOrZero(quizObtainedMarksField.getText());
            double quizTotal = parseOrZero(quizTotalMarksField.getText());
            double midTermObtained = parseOrZero(midTermObtainedMarksField.getText());
            double midTermTotal = parseOrZero(midTermTotalMarksField.getText());
            double finalObtained = parseOrZero(finalObtainedMarksField.getText());
            double finalTotal = parseOrZero(finalTotalMarksField.getText());

            double labAssignmentObtained = 0;
            double labAssignmentTotal = 0;
            double labMidTermObtained = 0;
            double labMidTermTotal = 0;

            if (subjectTypeComboBox.getSelectedItem().equals("Lab")) {
                labAssignmentObtained = parseOrZero(labAssignmentObtainedMarksField.getText());
                labAssignmentTotal = parseOrZero(labAssignmentTotalMarksField.getText());
                labMidTermObtained = parseOrZero(labMidTermObtainedMarksField.getText());
                labMidTermTotal = parseOrZero(labMidTermTotalMarksField.getText());
            }
            double assignLost = 0;
            double quizLost = 0;
            double midTermLost = 0;
            double finalTermLost = 0;
            double labAssignLost = 0;
            double labMidLost = 0;
            double totalMarks = 0;
            if (creditHrs == 2){
                 assignLost = 100 - ((assignmentObtained/assignmentTotal) * 100);
                 assignLost = ((assignLost/100) * 10);
                 quizLost = 100 - ((quizObtained/quizTotal) * 100);
                 quizLost = ((quizLost/100) * 15);
                 midTermLost = 100 - ((midTermObtained/midTermTotal) * 100);
                 midTermLost = ((midTermLost/100) * 25);
                 finalTermLost = 100 - ((finalObtained/finalTotal) * 100);
                 finalTermLost = ((finalTermLost/100) * 50);
                 totalMarks = assignLost+quizLost+midTermLost+finalTermLost;
            }
            else if (creditHrs == 3){
                assignLost = 100 - ((assignmentObtained/assignmentTotal) * 100);
                assignLost = ((assignLost/100) * 10);
                quizLost = 100 - ((quizObtained/quizTotal) * 100);
                quizLost = ((quizLost/100) * 15);
                midTermLost = 100 - ((midTermObtained/midTermTotal) * 100);
                midTermLost = ((midTermLost/100) * 25);
                finalTermLost = 100 - ((finalObtained/finalTotal) * 100);
                finalTermLost = ((finalTermLost/100) * 50);
                totalMarks = assignLost+quizLost+midTermLost+finalTermLost;
            }
            else if (creditHrs == 4){
                assignLost = 100 - ((assignmentObtained/assignmentTotal) * 100);
                assignLost = ((assignLost/100) * 10) * 0.666666;
                quizLost = 100 - ((quizObtained/quizTotal) * 100);
                quizLost = ((quizLost/100) * 15) *0.6666666;
                midTermLost = 100 - ((midTermObtained/midTermTotal) * 100);
                midTermLost = ((midTermLost/100) * 25) * 0.6666666;
                finalTermLost = 100 - ((finalObtained/finalTotal) * 100);
                finalTermLost = ((finalTermLost/100) * 50) * 0.6666666;
                labAssignLost = 100 - ((labAssignmentObtained/labAssignmentTotal) * 100);
                labAssignLost = ((labAssignLost/100) * 25) * 0.33333333;
                labMidLost = 100 - ((labMidTermObtained/labMidTermTotal) * 100);
                labMidLost = ((labMidLost/100) * 25) * 0.333333333;
                totalMarks = assignLost+quizLost+midTermLost+finalTermLost + labMidLost + labAssignLost;
            }
            else if (creditHrs == 5){
                assignLost = 100 - ((assignmentObtained/assignmentTotal) * 100);
                assignLost = ((assignLost/100) * 10) * 0.75;
                quizLost = 100 - ((quizObtained/quizTotal) * 100);
                quizLost = ((quizLost/100) * 15) *0.75;
                midTermLost = 100 - ((midTermObtained/midTermTotal) * 100);
                midTermLost = ((midTermLost/100) * 25) * 0.75;
                finalTermLost = 100 - ((finalObtained/finalTotal) * 100);
                finalTermLost = ((finalTermLost/100) * 50) * 0.75;
                labAssignLost = 100 - ((labAssignmentObtained/labAssignmentTotal) * 100);
                labAssignLost = ((labAssignLost/100) * 25) * 0.25;
                labMidLost = 100 - ((labMidTermObtained/labMidTermTotal) * 100);
                labMidLost = ((labMidLost/100) * 25) * 0.25;
                totalMarks = assignLost+quizLost+midTermLost+finalTermLost + labMidLost + labAssignLost;
            }


            double marksLost = totalMarks;

            resultLabel.setText("Marks Lost: " + marksLost);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double parseOrZero(String text) {
        if (text.isEmpty()) {
            return 50;
        }
        return Double.parseDouble(text);
    }

}