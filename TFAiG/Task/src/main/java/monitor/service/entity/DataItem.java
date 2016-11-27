package monitor.service.entity;

/**
 * @author 1
 * @since 22.11.2016.
 */
public class DataItem {

    private String data;
    private int id;

    public DataItem(int id, String data) {
        this.id = id;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
