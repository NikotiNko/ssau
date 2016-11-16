/**
 * @author 1
 * @since 05.09.2016.
 */
public class MarkOutOfBoundsException extends IndexOutOfBoundsException {

    public MarkOutOfBoundsException() {
        super("Оценка может быть от 2 до 5!");
    }
}
