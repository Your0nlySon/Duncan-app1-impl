package baseline;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Items {

    private final SimpleStringProperty desc;
    private final SimpleStringProperty date;
    private BooleanProperty completed;

    public Items(String description, String dueDate) {
        this.desc = new SimpleStringProperty(description);
        this.date = new SimpleStringProperty(dueDate);
        this.completed = new SimpleBooleanProperty(false);
    }


    public String getDesc() {
        return desc.get();
    }

    public void setDesc(String description) {
        desc.set(description);
    }

    public StringProperty descriptionProperty() {
        return desc;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String dueDate) {
        date.set(dueDate);
    }

    public StringProperty dueDateProperty() {
        return date;
    }

    public Boolean isCompleted() {
        return completed.get();
    }

    public void setCompleted(Boolean completed) {
        this.completed.set(completed);
    }

    public BooleanProperty completedProperty() {
        return completed;
    }

}