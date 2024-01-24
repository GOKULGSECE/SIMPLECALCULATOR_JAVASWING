package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {

    private JTextField display;
    private double firstOperand;
    private String operator;
    private boolean newInput;

    public Main() {
        setTitle("Simple Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);

        JPanel buttonPanel = createButtonPanel();

        add(display, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 4));

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ButtonClickListener());
            panel.add(button);
        }

        return panel;
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String buttonText = source.getText();

            if ("0123456789.".contains(buttonText)) {
                handleDigitOrDecimalPoint(buttonText);
            } else if ("+-*/".contains(buttonText)) {
                handleOperator(buttonText);
            } else if ("=".equals(buttonText)) {
                handleEquals();
            }
        }
    }

    private void handleDigitOrDecimalPoint(String buttonText) {
        if (newInput) {
            display.setText(buttonText);
            newInput = false;
        } else {
            display.setText(display.getText() + buttonText);
        }
    }

    private void handleOperator(String buttonText) {
        if (!newInput) {
            firstOperand = Double.parseDouble(display.getText());
            operator = buttonText;
            newInput = true;
        }
    }

    private void handleEquals() {
        if (!newInput) {
            double secondOperand = Double.parseDouble(display.getText());
            double result = performOperation(firstOperand, secondOperand, operator);
            display.setText(String.valueOf(result));
            newInput = true;
        }
    }

    private double performOperation(double firstOperand, double secondOperand, String operator) {
        switch (operator) {
            case "+":
                return firstOperand + secondOperand;
            case "-":
                return firstOperand - secondOperand;
            case "*":
                return firstOperand * secondOperand;
            case "/":
                if (secondOperand != 0) {
                    return firstOperand / secondOperand;
                } else {
                    JOptionPane.showMessageDialog(this, "Cannot divide by zero", "Error", JOptionPane.ERROR_MESSAGE);
                    return 0;
                }
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
