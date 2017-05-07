package com.vcf.przemek.wordsmemorizer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.vcf.przemek.wordsmemorizer.constants.Language;
import com.vcf.przemek.wordsmemorizer.db.GroupReader;
import com.vcf.przemek.wordsmemorizer.db.LanguageReader;
import com.vcf.przemek.wordsmemorizer.db.WordsMemorizerDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        populateLanguageSpinner();
    }

    private void populateLanguageSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.language_spinner);
        List<String> langStringArray = getLangData();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, langStringArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private List<String> getLangData(){
        Cursor c = getLangCursor();
        List<String> list = new ArrayList<String>();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String lang_name = c.getString(c.getColumnIndex(LanguageReader.LanguageEntry.COLUMN_LANGUAGE_NAME));
                    list.add(lang_name);
                } while (c.moveToNext());
            }
        }
        c.close();
        return list;
    }

    public Cursor getLangCursor() {

        WordsMemorizerDatabase dbHelper = new WordsMemorizerDatabase(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                LanguageReader.LanguageEntry._ID,
                LanguageReader.LanguageEntry.COLUMN_LANGUAGE_NAME
        };

        String sortOrder = LanguageReader.LanguageEntry.COLUMN_LANGUAGE_NAME + " DESC";

        Cursor cursor = db.query(
                LanguageReader.LanguageEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null, //selection,                                // The columns for the WHERE clause
                null, //selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        return cursor;
    }

    public void saveNewGroup(View v) {

        Spinner mySpinner = (Spinner) findViewById(R.id.language_spinner);
        String language = mySpinner.getSelectedItem().toString();

        EditText group_name_edit_text = (EditText) findViewById(R.id.group_input);
        String group_name = group_name_edit_text.getText().toString();

        if (groupNameExistsForLanguage(group_name, language) == true){
            showAlertDialog("Uwaga", "Grupa o nazwie " + group_name + " już istnieje dla języka " + language +".");
            return;
        }

        Integer lang_id = getLanguageId(language);
        if (lang_id > 0){
            insertGroup(group_name, lang_id);
            group_name_edit_text.setText("");
        }
        else{
            showAlertDialog("Wystąpił błąd", "Nie udało się dodać grupy.");
        }
    }

    private boolean groupNameExistsForLanguage(String group_name, String language){

        WordsMemorizerDatabase dbHelper = new WordsMemorizerDatabase(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String raw_query = "select count(*) from " + GroupReader.GroupEntry.TABLE_NAME + " where lower(" +
                GroupReader.GroupEntry.COLUMN_GROUP_NAME + ")='" + group_name.toLowerCase() + "' and " +
                GroupReader.GroupEntry.COLUMN_LANGUAGE_ID_NAME + " = (select " +
                LanguageReader.LanguageEntry._ID + " from " +
                LanguageReader.LanguageEntry.TABLE_NAME + " where " +
                LanguageReader.LanguageEntry.COLUMN_LANGUAGE_NAME + "='" + language + "');";

        Cursor mCount= db.rawQuery(raw_query, null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();

        if (count > 0)
            return true;
        return false;
    }

    private Integer getLanguageId(String language_name){
        WordsMemorizerDatabase dbHelper = new WordsMemorizerDatabase(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                LanguageReader.LanguageEntry._ID
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = LanguageReader.LanguageEntry.COLUMN_LANGUAGE_NAME + " = ?";
        String[] selectionArgs = { language_name };

        Cursor c = db.query(
                LanguageReader.LanguageEntry.TABLE_NAME,   // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );

        Integer id = -1;
        if (c != null) {
            if (c.getCount() > 1){
                // TODO raise/throw excepetion
            }
            if (c.moveToFirst()) {
                id = c.getInt(c.getColumnIndex(LanguageReader.LanguageEntry._ID));
            }
        }
        c.close();
        return id;
    }

    private long insertGroup(String name, Integer language_id) {
        WordsMemorizerDatabase dbHelper = new WordsMemorizerDatabase(getApplicationContext());

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GroupReader.GroupEntry.COLUMN_GROUP_NAME, name);
        values.put(GroupReader.GroupEntry.COLUMN_LANGUAGE_ID_NAME, language_id);

        long newRowId = db.insert(GroupReader.GroupEntry.TABLE_NAME, null, values);
        return newRowId;
    }

    public void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
