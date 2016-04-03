package il.ac.huji.todolist;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoListAdapter extends SimpleCursorAdapter {

    private Context context;

    public TodoListAdapter(TodoListManagerActivity todoListManagerActivity, int simple_list_item_1, Cursor cursor) {
        super(todoListManagerActivity, simple_list_item_1, cursor, new String[]{RepositoryConsts.FIELD_TODOS_TITLE, RepositoryConsts.FIELD_TODOS_DUE_DATE}, new int[]{R.id.txtTodoTitle, R.id.txtTodoDueDate}, 0);

        setViewBinder(new ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                Date dueDate = new Date(cursor.getLong(cursor.getColumnIndex(RepositoryConsts.FIELD_TODOS_DUE_DATE)));
                if (view.getId() == R.id.txtTodoDueDate)
                {
                    TextView tv = (TextView)view;
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    tv.setText(dateFormat.format(dueDate));
                    colorView((TextView)view, dueDate);
                    return true;
                }
                if(view.getId() == R.id.txtTodoTitle) {
                    colorView((TextView)view, dueDate);
                }
                return false;
            }
        });
        this.context = todoListManagerActivity;
    }

    private static void colorView(TextView view, Date dueDate) {
        if (new Date().compareTo(dueDate) > 0) {
            view.setTextColor(Color.RED);
        } else {
            view.setTextColor(Color.BLACK);
        }
    }
}
