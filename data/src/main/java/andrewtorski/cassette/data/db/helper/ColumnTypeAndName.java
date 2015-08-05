package andrewtorski.cassette.data.db.helper;

/**
 * Provides information about column's name and datatype. Allows for quick building of column
 * creation statements in CREATE TABLE statement.
 */
public class ColumnTypeAndName {

    private String name;

    private String dataType;

    private ColumnTypeAndName() {
    }

    public ColumnTypeAndName(String name, String dataType) {
        this.name = name;
        this.dataType = dataType;
    }

    public String getColumnCreationStatement(String commaSeparator) {
        return getColumnCreationStatement() + commaSeparator;
    }

    public String getColumnCreationStatement() {
        return name + " " + dataType;
    }
}
