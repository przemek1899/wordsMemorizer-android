package com.vcf.przemek.wordsmemorizer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vcf.przemek.wordsmemorizer.constants.Language;

/**
 * Created by Przemek on 2017-04-22.
 */

public class WordsMemorizerDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WORDS_MEMORIZER";
    private static final int DATABASE_VERSION = 1;

    private static final String LANGUAGE_TABLE_NAME = "languages";
    private static final String LANGUAGE_TABLE_CREATE =
            "CREATE TABLE " + LANGUAGE_TABLE_NAME + " (" +
                    LanguageReader.LanguageEntry._ID + " INTEGER PRIMARY KEY," +
                    LanguageReader.LanguageEntry.COLUMN_LANGUAGE_NAME + " TEXT NOT NULL);";

    private static final String GROUP_TABLE_NAME = "groups";
    private static final String GROUP_TABLE_CREATE =
            "CREATE TABLE " + GROUP_TABLE_NAME + " (" +
                    GroupReader.GroupEntry._ID + " INTEGER PRIMARY KEY," +
                    GroupReader.GroupEntry.COLUMN_LANGUAGE_ID_NAME + " INTEGER NOT NULL," +
                    GroupReader.GroupEntry.COLUMN_GROUP_NAME + " TEXT NOT NULL," +
                    "FOREIGN KEY(" + GroupReader.GroupEntry.COLUMN_LANGUAGE_ID_NAME +
                    ") REFERENCES " + LANGUAGE_TABLE_NAME + "(" + LanguageReader.LanguageEntry._ID + ") );";

    private static final String EXPRESSION_TABLE_NAME = "expressions";
    private static final String EXPRESSION_TABLE_CREATE =
            "CREATE TABLE " + EXPRESSION_TABLE_NAME + " (" +
                    ExpressionReader.ExpressionEntry._ID + " INTEGER PRIMARY KEY," +
                    ExpressionReader.ExpressionEntry.COLUMN_KEY_NAME + " TEXT NOT NULL," +
                    ExpressionReader.ExpressionEntry.COLUMN_TRANSLATION_NAME + " TEXT," +
                    ExpressionReader.ExpressionEntry.COLUMN_EXAMPLE_NAME + " TEXT);";

    private static final String EXPRESSION_GROUP_TABLE_NAME = "expression_group";
    private static final String EXPRESSION_GROUP_TABLE_CREATE =
            "CREATE TABLE " + EXPRESSION_GROUP_TABLE_NAME + " (" +
                    ExpressionGroupReader.ExpressionGroupEntry._ID + " INTEGER PRIMARY KEY," +
                    ExpressionGroupReader.ExpressionGroupEntry.COLUMN_EXPRESSION_ID_NAME + " INTEGER NOT NULL," +
                    "FOREIGN KEY(" + ExpressionGroupReader.ExpressionGroupEntry.COLUMN_EXPRESSION_ID_NAME +
                    ") REFERENCES " + EXPRESSION_TABLE_NAME + "(" + ExpressionReader.ExpressionEntry._ID + ")," +
                    ExpressionGroupReader.ExpressionGroupEntry.COLUMN_GROUP_ID_NAME + " INTEGER NOT NULL," +
                    "FOREIGN KEY(" + ExpressionGroupReader.ExpressionGroupEntry.COLUMN_GROUP_ID_NAME +
                    ") REFERENCES " + GROUP_TABLE_NAME + "(" + GroupReader.GroupEntry._ID + "));";

    public WordsMemorizerDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private void populateLanguageTable(SQLiteDatabase db){

        int len = Language.languages.length;
        for (int i=0; i<len; i++){
            ContentValues values = new ContentValues();
            values.put(LanguageReader.LanguageEntry.COLUMN_LANGUAGE_NAME, Language.languages[i]);

            db.insert(LANGUAGE_TABLE_NAME, null, values);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(LANGUAGE_TABLE_CREATE);
        // populate language db with default languages
        populateLanguageTable(db);
        db.execSQL(GROUP_TABLE_CREATE);
        db.execSQL(EXPRESSION_TABLE_CREATE);
        db.execSQL(EXPRESSION_GROUP_TABLE_CREATE);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){

    }
}


