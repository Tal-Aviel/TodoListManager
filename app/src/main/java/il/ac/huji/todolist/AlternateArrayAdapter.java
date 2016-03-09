package il.ac.huji.todolist;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AlternateArrayAdapter<T> extends ArrayAdapter<T> {

    public AlternateArrayAdapter(TodoListManagerActivity todoListManagerActivity, int simple_list_item_1, List<T> myStringArray) {
        super(todoListManagerActivity, simple_list_item_1, myStringArray);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView)super.getView(position, convertView, parent);
        if (position % 2 == 1) {
            view.setTextColor(Color.BLUE);
        } else {
            view.setTextColor(Color.RED);
        }
        return view;
    }
}
