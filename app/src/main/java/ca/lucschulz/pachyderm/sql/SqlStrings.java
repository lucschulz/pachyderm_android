package ca.lucschulz.pachyderm.sql;

public class SqlStrings {

    private static final String DATABASE_NAME = "db_items.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TASK_ITEMS = "task_items";
    private static final String KEY_ID = "id";
    private static final String KEY_TASK_NAME = "task_name";
    private static final String KEY_DATE_ADDED = "date_added";
    private static final String KEY_DATE_DUE = "date_due";
    private static final String KEY_COMPLETED = "completed";

    private static final String TABLE_SETTINGS = "settings";
    private static final String KEY_SETTING_NAME = "setting_name";
    private static final String KEY_SETTING_VALUE = "setting_value";

    public static String getKeyDateAdded() {
        return KEY_DATE_ADDED;
    }

    public static String getDatabaseName() {
        return DATABASE_NAME;
    }

    public static int getDatabaseVersion() {
        return DATABASE_VERSION;
    }

    public static String getTableTaskItems() {
        return TABLE_TASK_ITEMS;
    }

    public static String getKeyId() {
        return KEY_ID;
    }

    public static String getKeyTaskDescription() {
        return KEY_TASK_NAME;
    }

    public static String getKeyCompleted() {
        return KEY_COMPLETED;
    }

    public static String getKeyDateDue() {
        return KEY_DATE_DUE;
    }

    public static String getTableSettings() {
        return TABLE_SETTINGS;
    }

    public static String getKeySettingName() {
        return KEY_SETTING_NAME;
    }

    public static String getKeySettingValue() {
        return KEY_SETTING_VALUE;
    }


    public String dropTable() {
        return "DROP TABLE IF EXISTS " + getTableTaskItems();
    }

    public String retrieveItems() {
        return "SELECT * FROM " + TABLE_TASK_ITEMS + " ORDER BY " + KEY_COMPLETED + ", " + KEY_DATE_DUE + " ASC";
    }
}
