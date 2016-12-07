package monitor.controller.entity;


/**
 * Класс результата моделирования.
 * Конструктор результата.
 */
public class Result {

    private StringBuilder result;

    private Result(){
        result = new StringBuilder();
    }

    public static Result build() {
        return new Result();
    }

    public Result text(String val) {
        this.result.append(val).append("\n");
        return this;
    }

    @Override
    public String toString() {
        return result.toString();
    }
}
