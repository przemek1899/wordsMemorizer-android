package com.vcf.przemek.wordsmemorizer.db;

import android.provider.BaseColumns;

/**
 * Created by Przemek on 2017-04-23.
 */

public class ExpressionReader {

    private ExpressionReader(){}

    public static class ExpressionEntry implements BaseColumns {
        public static final String TABLE_NAME = "expressions";
        public static final String COLUMN_KEY_NAME = "key";
        public static final String COLUMN_TRANSLATION_NAME = "translation";
        public static final String COLUMN_EXAMPLE_NAME = "example";

    }
}
