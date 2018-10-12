package com.example.kuba.todolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ItemsListAdapter itemsAdapter;
    private List<Item> items;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO Log all onCreate call backs (easy 'cuz only one Activity)

        listView = (ListView) findViewById(R.id.listView);
        items = new ArrayList<Item>();

        items.add(new Item("Item 1", false));
        itemsAdapter = new ItemsListAdapter(this, items);
        listView.setAdapter(itemsAdapter);


        itemsAdapter.add("Item 2", false);
        items.add(new Item("Item 3", false));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO Figure a way  out  to add and run url to YouTube video Feature (hard one)

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNameInputDialog(view);
            }
        });

    }

    private void onAddItem(View view, String msg) {
        Toast.makeText(getApplicationContext(), "You've clicked", Toast.LENGTH_LONG).show();
//        showNameInputDialog();
        itemsAdapter.add(msg, false);
        listView.setAdapter(itemsAdapter);
        Snackbar.make(view, "You've successfully added a new TASK to list", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void showNameInputDialog(final View view) {
        Log.i("INFO", "Inside showName");
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.input_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editText = (EditText) dialogView.findViewById(R.id.edit1);

        dialogBuilder.setTitle("Title");
        dialogBuilder.setMessage(R.string.dialogMsg);
        dialogBuilder.setPositiveButton(R.string.dialogButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onAddItem(view, editText.getText().toString());
            }
        });
        AlertDialog b = dialogBuilder.create();
        Log.i("INFO", "After create");
        b.show();
        Log.i("INFO", "After show");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //TODO See email if need to do Add/Delete options (hope not)
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static class ViewHolder {
        CheckBox checkBox;
        TextView textView;
    }

    public class Item {
        boolean checked;
        String ItemString;

        Item(String s, boolean b) {
            this.ItemString = s;
            this.checked = b;
        }

        public boolean isChecked() {
            return checked;
        }
    }

    public class ItemsListAdapter extends BaseAdapter {
        private Context context;
        private List<Item> list;

        ItemsListAdapter(Context c, List<Item> l) {
            this.context = c;
            this.list = l;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public boolean isChecked(int position) {
            return list.get(position).checked;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;

            ViewHolder viewHolder = new ViewHolder();
            if (rowView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                rowView = inflater.inflate(R.layout.row, null);

                viewHolder.checkBox = (CheckBox) rowView.findViewById(R.id.rowCheckBox);
                viewHolder.textView = (TextView) rowView.findViewById(R.id.rowTextBox);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) rowView.getTag();
            }

            viewHolder.checkBox.setChecked(list.get(position).checked);

            final String itemStr = list.get(position).ItemString;
            viewHolder.textView.setText(itemStr);

            viewHolder.checkBox.setTag(position);

            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean newState = !list.get(position).isChecked();
                    list.get(position).checked = newState;
                    //TODO Add some fancy removing after click with, maybe, half second delay and tasty Toast pop-up
                }
            });
            viewHolder.checkBox.setChecked(isChecked(position));

            return rowView;
        }

        public void add(String s, boolean b) {
            Item item = new Item(s, b);
            //TODO change string to capital letters Ez Pez
            list.add(item);
        }
    }
}
