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

public class ClothesActivity extends AppCompatActivity {
    private ArrayList<String> arrayListClothes;
    private ListView listViewClothes;
    private ArrayAdapter<String> arrayAdapterClothes;
    private int choiceItemPositionClothes;
    private static final String PREFERENCES = "PREFERENCES_CLOTHES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);

        arrayListClothes = new ArrayList<>();

        SharedPreferences preferencesRestore = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        for (int i = 0; i < preferencesRestore.getInt("length", 0); i++) {
            arrayListClothes.add(preferencesRestore.getString(String.valueOf(i), ""));
        }

        listViewClothes = findViewById(R.id.list_view_clothes);
        arrayAdapterClothes = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, arrayListClothes);
        listViewClothes.setAdapter(arrayAdapterClothes);

        listViewClothes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                choiceItemPositionClothes = position;
            }
        });
    }

    public void onClickButtonAdd(View view) {
        EditText editText = findViewById(R.id.edit_text_clothes);
        String item = editText.getText().toString();
        if (item.equals("")) {
            Toast toast = Toast.makeText(this, "Добавьте покупку!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            arrayListClothes.add(item);
            arrayAdapterClothes.notifyDataSetChanged();
            editText.setText("");
        }
    }

    public void onClickButtonRemove(View view) {
        if (arrayListClothes.isEmpty()) {
            Toast toast = Toast.makeText(this, "Список уже пуст!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            arrayListClothes.remove(choiceItemPositionClothes);
            arrayAdapterClothes.notifyDataSetChanged();
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
        String[] items = arrayListClothes.toArray(new String[0]);
        SharedPreferences preferencesSave = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencesSave.edit();
        for (int i = 0; i < items.length; i++) {
            editor.putString(String.valueOf(i), items[i]);
        }
        editor.putInt("length", items.length);
        editor.apply();
    }
}
