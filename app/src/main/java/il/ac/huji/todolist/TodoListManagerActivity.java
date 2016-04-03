package il.ac.huji.todolist;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Date;

public class TodoListManagerActivity extends AppCompatActivity {

    public static final int ADD_NEW_ITEM_REQUEST_CODE = 1;
    private TodoListAdapter adapter;
    private Context context = this;
    private ListView lstTodoItems;
    private SQLiteDatabase db;

    AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            listRightLongClick(parent, position);
            return false;
        }
    };

    private void listRightLongClick(AdapterView<?> parent, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final Cursor itemCursor = (Cursor) parent.getItemAtPosition(position);

        final String itemTitle = itemCursor.getString(itemCursor.getColumnIndex(RepositoryConsts.FIELD_TODOS_TITLE));
        String[] arr;
        arr = (itemTitle.toLowerCase().startsWith(getString(R.string.startDialPrefix))) ?
                new String[]{getString(R.string.menuItemDelete), itemTitle} : new String[]{getString(R.string.menuItemDelete)};
        builder.setTitle(itemTitle)
                .setItems(arr, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                db.delete(RepositoryConsts.TABLE_TODOS, RepositoryConsts.FIELD_TODOS_ID + "=?", new String[]{Integer.toString(itemCursor.getInt(itemCursor.getColumnIndex(RepositoryConsts.FIELD_TODOS_ID)))});
                                reQuery();
                                break;
                            case 1:
                                String phone = itemTitle.substring(getString(R.string.startDialPrefix).length());
                                Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                                startActivity(dial);
                                break;
                        }
                    }
                }).show();
    }

    private void reQuery() {
        adapter.swapCursor(db.rawQuery("select _id, title, due_date from " + RepositoryConsts.TABLE_TODOS, null));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case ADD_NEW_ITEM_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    insertNewItem(data);
                }
                break;
        }
    }

    private void insertNewItem(Intent data) {
        Bundle extras = data.getExtras();
        String title = extras.get(getString(R.string.dlgIntentTitle)).toString();
        Date dueDate = (Date) extras.get(getString(R.string.dlgIntentDueDate));

        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("due_date", dueDate.getTime());
        db.insertOrThrow("todos", null, values);

        reQuery();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_todo_list_manager);

        DBHelper helper = new DBHelper(context);
        db = helper.getWritableDatabase();
        lstTodoItems = (ListView) findViewById(R.id.lstTodoItems);
        adapter = new TodoListAdapter(this, R.layout.todo_item, null);
        reQuery();
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
                startActivityForResult(intent, ADD_NEW_ITEM_REQUEST_CODE);
                break;
        }
        return true;
    }
}