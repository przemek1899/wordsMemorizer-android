package com.vcf.przemek.wordsmemorizer.db;

import android.provider.BaseColumns;

/**
 * Created by Przemek on 2017-04-22.
 */

public final class LanguageReader {

    private LanguageReader(){}

    public static class LanguageEntry implements BaseColumns {
        public static final String TABLE_NAME = "languages";
        public static final String COLUMN_LANGUAGE_NAME = "name";
    }
}
