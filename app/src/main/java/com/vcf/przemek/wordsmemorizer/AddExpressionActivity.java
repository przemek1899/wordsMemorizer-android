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
import android.widget.Spinner;

import com.vcf.przemek.wordsmemorizer.adapters.SpinnerGroupAdapter;
import com.vcf.przemek.wordsmemorizer.db.ExpressionGroupReader;
import com.vcf.przemek.wordsmemorizer.db.ExpressionReader;
import com.vcf.przemek.wordsmemorizer.db.GroupReader;
import com.vcf.przemek.wordsmemorizer.db.LanguageReader;
import com.vcf.przemek.wordsmemorizer.db.WordsMemorizerDatabase;
import com.vcf.przemek.wordsmemorizer.objects.GroupWords;

import java.util.ArrayList;
import java.util.List;

public class AddExpressionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expression);

        populateGroupSpinner();
    }

    private void populateGroupSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.group_spinner);
//        List<String> groupStringArray = getGroupData();
        List<GroupWords> groupStringArray = getGroupData();
        GroupWords[] array = groupStringArray.toArray(new GroupWords[groupStringArray.size()]);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, groupStringArray);
        SpinnerGroupAdapter adapter = new SpinnerGroupAdapter(this, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

//    private List<String> getGroupData() {
    private List<GroupWords> getGroupData() {
        Cursor c = getGroupCursor();
//        List<String> list = new ArrayList<String>();
        List<GroupWords> list = new ArrayList<GroupWords>();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    Integer id = c.getInt(c.getColumnIndex(GroupReader.GroupEntry._ID));
                    String group_name = c.getString(c.getColumnIndex(GroupReader.GroupEntry.COLUMN_GROUP_NAME));
//                    list.add(group_name);
                    list.add(new GroupWords(id, group_name, null));
                } while (c.moveToNext());
            }
        }
        return list;
    }

    public Cursor getGroupCursor() {

        WordsMemorizerDatabase dbHelper = new WordsMemorizerDatabase(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                GroupReader.GroupEntry._ID,
                GroupReader.GroupEntry.COLUMN_GROUP_NAME
        };

        String sortOrder = GroupReader.GroupEntry.COLUMN_GROUP_NAME + " ASC";

        Cursor cursor = db.query(
                GroupReader.GroupEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null, //selection,                                // The columns for the WHERE clause
                null, //selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        return cursor;
    }

    public void saveExpression(View view) {

        Spinner spinner = (Spinner) findViewById(R.id.group_spinner);
        EditText expressionInput = (EditText) findViewById(R.id.expression_input);
        EditText translationInput = (EditText) findViewById(R.id.translation_input);
        EditText exampleInput = (EditText) findViewById(R.id.example_input);

//        String group_name = spinner.getSelectedItem().toString();
        String group_name = spinner.getSelectedItem().toString();

        Object o = spinner.getSelectedView().getTag();
        Integer group_id = null;
        if (o != null) {
            group_id = (Integer) o;
        }
        else{
            showAlertDialog("Nie znaleziono id", "brak id");
            return;
        }


        String expression = expressionInput.getText().toString();
        String translation = translationInput.getText().toString();
        String example = exampleInput.getText().toString();

        List<Integer> groups_id = new ArrayList<>();
        groups_id.add(group_id);
        insertExpression(groups_id, expression, translation, example);
    }

    private long insertExpression(List<Integer> groups_id, String expression, String translation, String example){
        if (groups_id == null || groups_id.size() < 1){
            // TODO throw exception or sth
            return -1;
        }

        WordsMemorizerDatabase dbHelper = new WordsMemorizerDatabase(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ExpressionReader.ExpressionEntry.COLUMN_KEY_NAME, expression);
        values.put(ExpressionReader.ExpressionEntry.COLUMN_TRANSLATION_NAME, translation);
        values.put(ExpressionReader.ExpressionEntry.COLUMN_EXAMPLE_NAME, example);

        long newRowId = db.insert(ExpressionReader.ExpressionEntry.TABLE_NAME, null, values);

        for(Integer group_id: groups_id) {
            ContentValues expression_group_values = new ContentValues();
            values.put(ExpressionGroupReader.ExpressionGroupEntry.COLUMN_EXPRESSION_ID_NAME, newRowId);
            values.put(ExpressionGroupReader.ExpressionGroupEntry.COLUMN_GROUP_ID_NAME, group_id);

            db.insert(ExpressionGroupReader.ExpressionGroupEntry.TABLE_NAME, null, expression_group_values);
        }

        return newRowId;
    }

    public void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
