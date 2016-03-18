package il.ac.huji.todolist;

import java.util.Date;

public class TodoItem {
    private String title;
    private Date dueDate;

    public TodoItem(String title, Date dueDate) {
        this.title = title;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public boolean isLate() {
        return (new Date().compareTo(dueDate) > 0);
    }
}