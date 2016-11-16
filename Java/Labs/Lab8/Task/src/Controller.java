import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * @author 1
 * @since 30.10.2016.
 */
public class Controller {

    private double result;
    private boolean isTyping;
    private boolean clearFlag;
    private Action lastAction;
    private JLabel outLabel;
    private JTextField textField;

    public Controller(JTextField textField, JLabel outLabel) {
        clearFlag = false;
        isTyping = false;
        result = 0;
        lastAction = Action.NOTHING;
        this.outLabel = outLabel;
        this.textField = textField;
    }

    public void onAddClick() {
        doAction(Action.ADD);
    }

    public void onSubClick() {
        doAction(Action.SUB);
    }

    public void onDivClick() {
        doAction(Action.DIV);
    }

    public void onMulClick() {
        doAction(Action.MUL);
    }

    public void onPowClick() {
        doAction(Action.POW);
    }

    public void onResultClick() {
        calculate();
        printResult();
        lastAction = Action.NOTHING;
    }

    public void onSqrtClick() {
        if (lastAction == Action.NOTHING) {
            result = Double.parseDouble(textField.getText());
        }
        lastAction = Action.SQRT;
        calculate();
        printResult();
        clearFlag = true;
    }

    public void onResetClick() {
        if (textField.getText().equals("0") || textField.getText().equals("")) {
            lastAction = Action.NOTHING;
            result = 0;
            clearFlag = false;
        } else {
            textField.setText("0");
        }
    }

    public void onFloatClick() {
        if (clearFlag) {
            textField.setText("0.");
            clearFlag = false;
            if (lastAction == Action.SQRT) {
                result = 0;
            }
        } else {
            if (!textField.getText().equals("0")) {
                if (!textField.getText().contains("."))
                    textField.setText(textField.getText() + ".");
            } else {
                textField.setText("0.");
            }
        }
        isTyping = true;
    }

    public void onNumClick(int num) {
        if (clearFlag) {
            textField.setText(String.valueOf(num));
            clearFlag = false;
            if (lastAction == Action.SQRT) {
                result = 0;
            }
        } else {
            if (!textField.getText().equals("0")) {
                textField.setText(textField.getText() + num);
            } else {
                textField.setText(String.valueOf(num));
            }
        }
        isTyping = true;
    }

    public void onKeyPressed(KeyEvent event) {
        if (lastAction!=Action.NOTHING && !isTyping){
            textField.setText("");
        }
        if (event.getKeyChar()>='0' && event.getKeyChar()<='9'){
            isTyping = true;
        }
        if (event.getKeyChar() == '+') {
            onAddClick();
        }
        if (event.getKeyChar() == '-') {
            onSubClick();
        }
        if (event.getKeyChar() == '*') {
            onMulClick();
        }
        if (event.getKeyChar() == '/') {
            onDivClick();
        }
        if (event.getKeyChar() == '=') {
            onResultClick();
        }
    }

    //private
    private void doAction(Action action) {
        if (isTyping) {
            calculate();
            printResult();
            isTyping = false;
            clearFlag = true;
        }
        lastAction = action;
    }

    private void printResult() {
        String resultString;
        if (result <Integer.MAX_VALUE && result >Integer.MIN_VALUE  &&result % 1 == 0) {
            resultString = String.valueOf((int) result);
        } else {
            resultString = String.valueOf(result);
        }
        textField.setText(resultString);
    }

    private void calculate() {
        double value;
        try {
            value = Double.parseDouble(textField.getText());
        }catch (NumberFormatException e){
            outLabel.setText("Неверный формат числа!");
            return;
        }
        switch (lastAction) {
            case ADD:
                result += value;
                break;
            case SUB:
                result -= value;
                break;
            case MUL:
                result *= value;
                break;
            case DIV:
                if (value == 0) {
                    outLabel.setText("Деление на ноль!");
                } else {
                    result /= value;
                }
                break;
            case SQRT:
                if (result < 0) {
                    outLabel.setText("<html>Невозможно вычислить корень из<br> отрицательного числа!</html>");
                } else {
                    result = Math.sqrt(result);
                    lastAction = Action.NOTHING;
                }
                break;
            case POW:
                result = Math.pow(result, value);
                break;
            case NOTHING:
                result = value;
                break;
        }
    }

    public void onInverseClick() {
        if (!textField.getText().isEmpty()){
            if (textField.getText().charAt(0) == '-'){
                textField.setText(textField.getText().substring(1));
            }else {
                textField.setText('-' + textField.getText());
            }
        }else {
            textField.setText("-");
        }
    }

    private enum Action {
        ADD, SUB, MUL, DIV, POW, SQRT, NOTHING
    }
}
