package il.ac.huji.todolist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class TodoListAdapter extends ArrayAdapter<TodoItem> {

    private Context context;

    public TodoListAdapter(TodoListManagerActivity todoListManagerActivity, int simple_list_item_1, List<TodoItem> myStringArray) {
        super(todoListManagerActivity, simple_list_item_1, myStringArray);
        this.context = todoListManagerActivity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        TodosHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            vi = inflater.inflate(R.layout.todo_item, null);
            holder = new TodosHolder();
            holder.title  = (TextView) vi.findViewById(R.id.txtTodoTitle);
            holder.dueDate  = (TextView) vi.findViewById(R.id.txtTodoDueDate);
            vi.setTag(holder);
        } else {
            holder = (TodosHolder) vi.getTag();
        }
        TextView itemTitle = holder.title;
        TextView itemDate = holder.dueDate;

        TodoItem item = getItem(position);
        itemTitle.setText(item.getTitle());

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        itemDate.setText(dateFormat.format(item.getDueDate()));

        if (item.isLate()) {
            itemDate.setTextColor(Color.RED);
            itemTitle.setTextColor(Color.RED);
        } else {
            itemDate.setTextColor(Color.BLACK);
            itemTitle.setTextColor(Color.BLACK);
        }

        return vi;
    }
}
