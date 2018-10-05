package ca.lucschulz.pachyderm.sql;

public class SqlCommands {

    private static final String DATABASE_NAME = "db_items.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TASK_ITEMS = "task_items";
    private static final String KEY_ID = "id";
    private static final String KEY_TASK_NAME = "task_name";
    private static final String KEY_DATE_ADDED = "date_added";
    private static final String KEY_DATE_DUE = "date_due";
    private static final String KEY_COMPLETED = "completed";

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

    public static String getKeyTaskName() {
        return KEY_TASK_NAME;
    }

    public static String getKeyCompleted() {
        return KEY_COMPLETED;
    }


    public String createTable() {
        StringBuilder sb = new StringBuilder();

        sb.append("CREATE TABLE ").append(getTableTaskItems()).append("(")
                .append(getKeyId())
                .append(" INTEGER PRIMARY KEY, ")
                .append(KEY_TASK_NAME)
                .append(" TEXT, ")
                .append(KEY_DATE_ADDED)
                .append(" DATE, ")
                .append(KEY_DATE_DUE)
                .append(" DATE, ")
                .append(KEY_COMPLETED)
                .append(" BIT)");

        return sb.toString();
    }

    public String dropTable() {
        return "DROP TABLE IF EXISTS " + getTableTaskItems();
    }

    public String retrieveItems() {
        return "SELECT * FROM " + TABLE_TASK_ITEMS + " ORDER BY " + KEY_COMPLETED;
    }
}
