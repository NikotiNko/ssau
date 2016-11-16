package error;

/**
 * @author 1
 * @since 08.09.2016.
 */
public class PupilParseException extends Exception {

    public PupilParseException() {
    }

    public PupilParseException(String s) {
        super(s);
    }

    public PupilParseException(int errorOffset) {
        super("Обнаружен неверный символ, позиция:" +errorOffset);
    }
}
