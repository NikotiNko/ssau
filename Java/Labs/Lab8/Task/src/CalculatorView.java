import service.CustomDocument;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author 1
 * @since 30.10.2016.
 */
public class CalculatorView extends JFrame {

    private JButton addButton;
    private JButton subButton;
    private JButton mulButton;
    private JButton divButton;
    private JButton sqrtButton;
    private JButton powButton;
    private JButton resultButton;
    private JButton resetButton;
    private JButton floatButton;
    private JButton inverseButton;
    private JLabel outLabel;
    private JTextField textField;
    private Controller controller;

    public CalculatorView() {
        super();
        this.setTitle("Calculator");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dimension = new Dimension(400, 400);
        this.setSize(dimension);
        this.setMaximumSize(dimension);
        this.setMinimumSize(dimension);
        this.getContentPane().setLayout(null);

        textField = new JTextField(new CustomDocument(),null, 0);
        textField.setText("0");
        textField.setBounds(30, 30, 320, 30);
        outLabel = new JLabel();
        outLabel.setBounds(30, 260, 320, 50);
        this.add(textField);
        this.add(outLabel);

        JPanel controlPanel = new JPanel();
        initControlButtons();
        controlPanel.add(addButton);
        controlPanel.add(subButton);
        controlPanel.add(mulButton);
        controlPanel.add(divButton);
        controlPanel.setVisible(true);
        controlPanel.setBounds(30, 90, 320, 150);
        controlPanel.setLayout(new GridLayout(5, 4));
        setNumberButtons(controlPanel);
        controlPanel.add(resetButton, 7);
        controlPanel.add(sqrtButton, 11);
        controlPanel.add(powButton, 15);
        controlPanel.add(floatButton);
        controlPanel.add(resultButton);
        this.getContentPane().add(controlPanel);
        initListeners();
        controller = new Controller(textField, outLabel);
    }


    private void initControlButtons() {
        addButton = new JButton("+");
        subButton = new JButton("−");
        mulButton = new JButton("×");
        divButton = new JButton("÷");
        sqrtButton = new JButton("√");
        powButton = new JButton("^");
        resultButton = new JButton("=");
        resetButton = new JButton("C");
        floatButton = new JButton(".");
        inverseButton = new JButton("±");
    }

    private void setNumberButtons(JPanel jpanel) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int name = 7 + j - i * 3;
                JButton button = new JButton(String.valueOf(name));
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        controller.onNumClick(name);
                    }
                });
                jpanel.add(button);
            }
        }
        jpanel.add(inverseButton);
        JButton button = new JButton("0");
        jpanel.add(button);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controller.onNumClick(0);
            }
        });
    }

    private void initListeners() {
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controller.onAddClick();
            }
        });
        subButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controller.onSubClick();
            }
        });
        mulButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controller.onMulClick();
            }
        });
        divButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controller.onDivClick();
            }
        });
        sqrtButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controller.onSqrtClick();
            }
        });
        powButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controller.onPowClick();
            }
        });
        resultButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {controller.onResultClick();
            }
        });
        resetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controller.onResetClick();
            }
        });
        floatButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controller.onFloatClick();
            }
        });
        inverseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {controller.onInverseClick();
            }
        });
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                controller.onKeyPressed(e);
            }
        });
    }

}
