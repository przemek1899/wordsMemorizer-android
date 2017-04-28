package com.vcf.przemek.wordsmemorizer.db;

import android.provider.BaseColumns;

/**
 * Created by Przemek on 2017-04-23.
 */

public class GroupReader {

    private GroupReader(){}

    public static class GroupEntry implements BaseColumns {
        public static final String TABLE_NAME = "groups";
        public static final String COLUMN_LANGUAGE_ID_NAME = "language_id";
        public static final String COLUMN_PARENT_GROUP_ID_NAME = "parent_group_id";
        public static final String COLUMN_GROUP_NAME = "name";
    }
}
