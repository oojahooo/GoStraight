package com.oojahooo.gostraight;

public class GostraightDBCtruct {

    private GostraightDBCtruct() {};

    public static final String TBL_FACILITY = "FACILITY";
    public static final String COL_ID = "_id";
    public static final String COL_CATEGORY = "CATEGORY";
    public static final String COL_BUILDING = "BUILDING";
    public static final String COL_DETAIL = "DETAIL";

    public static final String SQL_CREATE_TBL = "CREATE TABLE IF NOT EXISTS " + TBL_FACILITY + " " +
            "(" +
                COL_ID +       " INTEGER PRIMARY KEY AUTOINCREMENT" + ", " +
                COL_CATEGORY + " INTEGER"                             + ", " +
                COL_BUILDING + " TEXT"                                + ", " +
                COL_DETAIL   + " TEXT"                                +
            ")";

    public static final String SQL_DROP_TBL = "DROP TABLE IF EXISTS " + TBL_FACILITY;

    public static final String SQL_SELECT = "SELECT * FROM " + TBL_FACILITY;

    public static final String SQL_INSERT = "INSERT OR REPLACE INTO " + TBL_FACILITY + " " +
            "(" + COL_CATEGORY + ", " + COL_BUILDING + ", " + COL_DETAIL + ") VALUES ";

    public static final String SQL_DELETE = "DELETE FROM " + TBL_FACILITY;
}
