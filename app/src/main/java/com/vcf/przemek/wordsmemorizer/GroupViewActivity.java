package com.vcf.przemek.wordsmemorizer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.vcf.przemek.wordsmemorizer.adapters.ExpressionListAdapter;
import com.vcf.przemek.wordsmemorizer.db.ExpressionReader;
import com.vcf.przemek.wordsmemorizer.db.WordsMemorizerDatabase;
import com.vcf.przemek.wordsmemorizer.objects.Expression;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GroupViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_view);

        Intent intent = getIntent();
        Integer group_id = (Integer) intent.getIntExtra("GROUP_ID", 0);

        if (group_id == 0){
            // TODO
            // react somehow
        }

        initListViewWithExpressionAdapter(group_id);
    }

    public void initListViewWithExpressionAdapter(Integer group_id) {
        Cursor c = getCursorForLayout(group_id);
        List<Expression> list = mapEntriesFromDB(c);
        Expression[] array = list.toArray(new Expression[list.size()]);
        c.close();
        ExpressionListAdapter adapter = new ExpressionListAdapter(this, array);
        initInfusionSetListView(adapter);
    }

    private Cursor getCursorForLayout(Integer group_id) {

        WordsMemorizerDatabase dbHelper = new WordsMemorizerDatabase(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                ExpressionReader.ExpressionEntry._ID,
                ExpressionReader.ExpressionEntry.COLUMN_KEY_NAME,
                ExpressionReader.ExpressionEntry.COLUMN_TRANSLATION_NAME
        };
        String selection = ExpressionReader.ExpressionEntry._ID + " in ?";

        String sortOrder = ExpressionReader.ExpressionEntry.COLUMN_CREATION_DATE + " DESC";

        Cursor cursor = db.query(
                ExpressionReader.ExpressionEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null, //selection,                                // The columns for the WHERE clause
                null, //selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        return cursor;
    }

    public List<Expression> mapEntriesFromDB(Cursor c) {
        List<Expression> list = new ArrayList<Expression>();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(c.getColumnIndex(ExpressionReader.ExpressionEntry._ID));
                    String key = c.getString(c.getColumnIndex(ExpressionReader.ExpressionEntry.COLUMN_KEY_NAME));
                    String translation = c.getString(c.getColumnIndex(ExpressionReader.ExpressionEntry.COLUMN_TRANSLATION_NAME));
                    String example = c.getString(c.getColumnIndex(ExpressionReader.ExpressionEntry.COLUMN_EXAMPLE_NAME));

                    list.add(new Expression(id, key, translation, example));
                } while (c.moveToNext());
            }
        }
        return list;
    }

    public void initInfusionSetListView(BaseAdapter adapter) {
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
//                View firstTextView = ((ViewGroup)arg1).getChildAt(0);
//                TextView placeTextView = (TextView) firstTextView;
//
//                View secondTextView = ((ViewGroup)arg1).getChildAt(2);
//                TextView dateTextView = (TextView) secondTextView;
//
//                String rowText = placeTextView.getText().toString() + " " + dateTextView.getText().toString();
//                Object o = arg1.getTag();
//                Integer id_row = null;
//                if (o != null) {
//                    id_row = (Integer) o;
//                }
//                dialogOnItemLongClick(id_row, rowText);

                return true;
            }
        });
    }
}
