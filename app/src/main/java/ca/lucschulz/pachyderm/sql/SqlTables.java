package ca.lucschulz.pachyderm.sql;

public class SqlTables extends SqlStrings {

    public String createTaskItemsTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append(getTableTaskItems()).append("(")
                .append(getKeyId())
                .append(" INTEGER PRIMARY KEY, ")
                .append(getKeyTaskName())
                .append(" TEXT, ")
                .append(getKeyDateAdded())
                .append(" DATE, ")
                .append(getKeyDateDue())
                .append(" DATE, ")
                .append(getKeyCompleted())
                .append(" BIT)");

        return sb.toString();
    }

    public String createSettingsTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append(getTableSettings()).append("(")
                .append(getKeyId())
                .append(" INTEGER PRIMARY KEY, ")
                .append(getKeySettingName())
                .append(" TEXT, ")
                .append(getKeySettingValue())
                .append(" TEXT)");

        return sb.toString();
    }
}