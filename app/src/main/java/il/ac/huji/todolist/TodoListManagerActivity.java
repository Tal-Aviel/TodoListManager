package il.ac.huji.todolist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodoListManagerActivity extends AppCompatActivity {

    private List<TodoItem> todosArray = new ArrayList<>();
    private TodoListAdapter adapter;
    private Context context = this;
    private ListView lstTodoItems;

    AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final String itemTitle = ((TodoItem) parent.getItemAtPosition(position)).getTitle();
            String[] arr;
            arr = (itemTitle.toLowerCase().startsWith(getString(R.string.startDialPrefix))) ?
                    new String[]{getString(R.string.menuItemDelete), itemTitle} : new String[]{getString(R.string.menuItemDelete)};
            builder.setTitle(itemTitle)
                    .setItems(arr, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    todosArray.remove(position);
                                    adapter.notifyDataSetChanged();
                                    break;
                                case 1:
                                    String phone = itemTitle.substring(getString(R.string.startDialPrefix).length());
                                    Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                                    startActivity(dial);
                                    break;
                            }
                        }
                    }).show();
            return false;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            String title = extras.get(getString(R.string.dlgIntentTitle)).toString();
            Date dueDate = (Date) extras.get(getString(R.string.dlgIntentDueDate));
            todosArray.add(new TodoItem(title, dueDate));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_todo_list_manager);

        lstTodoItems = (ListView) findViewById(R.id.lstTodoItems);
        adapter = new TodoListAdapter(this,
                android.R.layout.simple_list_item_1, todosArray);
        lstTodoItems.setAdapter(adapter);

        lstTodoItems.setOnItemLongClickListener(onItemLongClickListener);
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
                Intent intent = new Intent(context, AddNewTodoItemActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
        return true;
    }
}