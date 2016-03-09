package il.ac.huji.todolist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TodoListManagerActivity extends AppCompatActivity {

    List<String> todosArray = new ArrayList<>();
    ArrayAdapter<String> adapter;
    Context context = this;
    ListView lstTodoItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_todo_list_manager);

        lstTodoItems = (ListView) findViewById(R.id.lstTodoItems);
        adapter = new AlternateArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, todosArray);
        lstTodoItems.setAdapter(adapter);

        lstTodoItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(((TextView) view).getText())
                        .setItems(R.array.cmenu, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    todosArray.remove(position);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }).show();
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menuItemAdd:
                TextView edtNewItem = (TextView) findViewById(R.id.edtNewItem);
                todosArray.add(edtNewItem.getText().toString());
                adapter.notifyDataSetChanged();
                break;
        }
        return true;
    }
}