package com.vcf.przemek.wordsmemorizer;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.vcf.przemek.wordsmemorizer.db.GroupReader;
import com.vcf.przemek.wordsmemorizer.db.WordsMemorizerDatabase;

public class AddGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
    }

    private void populateLanguageSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.language_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.planets_array, android.R.layout.simple_spinner_item);
//        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // Apply the adapter to the spinner
//        spinner.setAdapter(adapter);
    }

    public void saveNewGroup(View v) {

        Spinner mySpinner = (Spinner) findViewById(R.id.language_spinner);
        String language = mySpinner.getSelectedItem().toString();

        EditText group_name_edit_text = (EditText) findViewById(R.id.group_input);
        String group_name = group_name_edit_text.getText().toString();
//        insertGroup(group_name, Integer language_id);
    }

    private long insertGroup(String name, Integer language_id) {
        WordsMemorizerDatabase dbHelper = new WordsMemorizerDatabase(getApplicationContext());

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(GroupReader.GroupEntry.COLUMN_GROUP_NAME, name);
        values.put(GroupReader.GroupEntry.COLUMN_LANGUAGE_ID_NAME, language_id);
//        values.put(GroupReader.GroupEntry.COLUMN_GROUP_NAME, null);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(GroupReader.GroupEntry.TABLE_NAME, null, values);
        return newRowId;
    }
}
