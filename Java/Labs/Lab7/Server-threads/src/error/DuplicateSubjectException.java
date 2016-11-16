package error;

/**
 * @author 1
 * @since 05.09.2016.
 */
public class DuplicateSubjectException extends Exception {

    public DuplicateSubjectException() {
        super("Невозможно добавить существующий предмет!");
    }
}
