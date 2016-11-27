package monitor.service.entity;

import java.util.List;

/**
 * @author 1
 * @since 22.11.2016.
 */
public class Result {

    private boolean success;
    private String message;
    private int position;
    private List<DataItem> constants;
    private List<DataItem> identificators;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<DataItem> getConstants() {
        return constants;
    }

    public void setConstants(List<DataItem> constants) {
        this.constants = constants;
    }

    public List<DataItem> getIdentificators() {
        return identificators;
    }

    public void setIdentificators(List<DataItem> identificators) {
        this.identificators = identificators;
    }
}
