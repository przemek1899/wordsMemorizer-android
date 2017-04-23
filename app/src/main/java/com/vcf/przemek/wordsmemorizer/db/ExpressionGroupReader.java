package com.vcf.przemek.wordsmemorizer.db;

import android.provider.BaseColumns;

/**
 * Created by Przemek on 2017-04-23.
 */

public class ExpressionGroupReader {

    private ExpressionGroupReader(){}

    public static class ExpressionGroupEntry implements BaseColumns {
        public static final String TABLE_NAME = "expression_group";
        public static final String COLUMN_EXPRESSION_ID_NAME = "expression_id";
        public static final String COLUMN_GROUP_ID_NAME = "group_id";

    }
}
