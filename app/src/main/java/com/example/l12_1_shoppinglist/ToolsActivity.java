package com.example.l12_1_shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ToolsActivity extends AppCompatActivity {
    private ArrayList<String> arrayListTools;
    private ListView listViewTools;
    private ArrayAdapter<String> arrayAdapterTools;
    private int choiceItemPositionTools;
    private static final String PREFERENCES = "PREFERENCES_TOOLS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);

        arrayListTools = new ArrayList<>();

        SharedPreferences preferencesRestore = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        for (int i = 0; i < preferencesRestore.getInt("length", 0); i++) {
            arrayListTools.add(preferencesRestore.getString(String.valueOf(i), ""));
        }

        listViewTools = findViewById(R.id.list_view_tools);
        arrayAdapterTools = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, arrayListTools);
        listViewTools.setAdapter(arrayAdapterTools);

        listViewTools.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                choiceItemPositionTools = position;
            }
        });
    }

    public void onClickButtonAdd(View view) {
        EditText editText = findViewById(R.id.edit_text_tools);
        String item = editText.getText().toString();
        if (item.equals("")) {
            Toast toast = Toast.makeText(this, "Добавьте покупку!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            arrayListTools.add(item);
            arrayAdapterTools.notifyDataSetChanged();
            editText.setText("");
        }
    }

    public void onClickButtonRemove(View view) {
        if (arrayListTools.isEmpty()) {
            Toast toast = Toast.makeText(this, "Список уже пуст!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            arrayListTools.remove(choiceItemPositionTools);
            arrayAdapterTools.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onSaveData();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        onSaveData();
    }

    void onSaveData() {
        String[] items = arrayListTools.toArray(new String[0]);
        SharedPreferences preferencesSave = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencesSave.edit();
        for (int i = 0; i < items.length; i++) {
            editor.putString(String.valueOf(i), items[i]);
        }
        editor.putInt("length", items.length);
        editor.apply();
    }
}

