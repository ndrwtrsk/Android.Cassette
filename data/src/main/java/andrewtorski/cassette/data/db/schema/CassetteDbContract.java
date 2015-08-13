package andrewtorski.cassette.data.db.schema;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import andrewtorski.cassette.data.db.helper.ColumnTypeAndName;

/**
 * Contains meta-date about the database and all contained tables.
 */
public class CassetteDbContract {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "cassette.db";
    private static final String TEXT_TYPE = "TEXT";
    private static final String INTEGER_TYPE = "INTEGER";
    private static final String REAL_TYPE = "REAL";
    private static final String BLOB_TYPE = "BLOB";
    private static final String NULL = "NULL";
    private static final String COMMA_SEP = ",";

    public static abstract class CassetteTable {
        public static final String TABLE_NAME = "cassette";

        //region String Column Definitions

        public static final String COLUMN_NAME_ID = "id_pk";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_DATE_TIME_OF_CREATION = "date_time_of_creation";
        public static final String COLUMN_NAME_DATE_TIME_OF_COMPILATION = "date_time_of_compilation";
        public static final String COLUMN_NAME_LENGTH = "length";
        public static final String COLUMN_NAME_NUMBER_OF_RECORDINGS = "number_of_recordings";
        public static final String COLUMN_NAME_IS_COMPILED = "is_compiled";
        public static final String COLUMN_NAME_COMPILED_FILE_PATH = "compiled_file_path";

        public static final String COLUMN_TYPE_ID = INTEGER_TYPE + " PRIMARY KEY";
        public static final String COLUMN_TYPE_TITLE = TEXT_TYPE;
        public static final String COLUMN_TYPE_DESCRIPTION = TEXT_TYPE;
        public static final String COLUMN_TYPE_DATE_TIME_OF_CREATION = INTEGER_TYPE;
        public static final String COLUMN_TYPE_DATE_TIME_OF_COMPILATION = INTEGER_TYPE;
        public static final String COLUMN_TYPE_LENGTH = INTEGER_TYPE;
        public static final String COLUMN_TYPE_NUMBER_OF_RECORDINGS = INTEGER_TYPE;
        public static final String COLUMN_TYPE_IS_COMPILED = INTEGER_TYPE;
        public static final String COLUMN_TYPE_COMPILED_FILE_PATH = TEXT_TYPE;

        //endregion String Column Definitions

        public static final ColumnTypeAndName COLUMN_ID = new ColumnTypeAndName(COLUMN_NAME_ID, COLUMN_TYPE_ID);
        public static final ColumnTypeAndName COLUMN_TITLE = new ColumnTypeAndName(COLUMN_NAME_TITLE, COLUMN_TYPE_TITLE);
        public static final ColumnTypeAndName COLUMN_DESCRIPTION = new ColumnTypeAndName(COLUMN_NAME_DESCRIPTION, COLUMN_TYPE_DESCRIPTION);
        public static final ColumnTypeAndName COLUMN_DATE_TIME_OF_CREATION = new ColumnTypeAndName(COLUMN_NAME_DATE_TIME_OF_CREATION, COLUMN_TYPE_DATE_TIME_OF_CREATION);
        public static final ColumnTypeAndName COLUMN_DATE_TIME_OF_COMPILATION = new ColumnTypeAndName(COLUMN_NAME_DATE_TIME_OF_COMPILATION, COLUMN_TYPE_DATE_TIME_OF_COMPILATION);
        public static final ColumnTypeAndName COLUMN_LENGTH = new ColumnTypeAndName(COLUMN_NAME_LENGTH, COLUMN_TYPE_LENGTH);
        public static final ColumnTypeAndName COLUMN_NUMBER_OF_RECORDINGS = new ColumnTypeAndName(COLUMN_NAME_NUMBER_OF_RECORDINGS, COLUMN_TYPE_NUMBER_OF_RECORDINGS);
        public static final ColumnTypeAndName COLUMN_IS_COMPILED = new ColumnTypeAndName(COLUMN_NAME_IS_COMPILED, COLUMN_TYPE_IS_COMPILED);
        public static final ColumnTypeAndName COLUMN_COMPILED_FILE_PATH = new ColumnTypeAndName(COLUMN_NAME_COMPILED_FILE_PATH, COLUMN_TYPE_COMPILED_FILE_PATH);

        private static ArrayList<ColumnTypeAndName> getColumnsDefintions() {
            ArrayList<ColumnTypeAndName> columnTypesAndNames = new ArrayList<>(20);

            columnTypesAndNames.add(COLUMN_ID);
            columnTypesAndNames.add(COLUMN_TITLE);
            columnTypesAndNames.add(COLUMN_DESCRIPTION);
            columnTypesAndNames.add(COLUMN_DATE_TIME_OF_CREATION);
            columnTypesAndNames.add(COLUMN_DATE_TIME_OF_COMPILATION);
            columnTypesAndNames.add(COLUMN_LENGTH);
            columnTypesAndNames.add(COLUMN_NUMBER_OF_RECORDINGS);
            columnTypesAndNames.add(COLUMN_IS_COMPILED);
            columnTypesAndNames.add(COLUMN_COMPILED_FILE_PATH);

            return columnTypesAndNames;
        }

        public static String getCreateTableStatement() {
            StringBuilder sb = new StringBuilder();

            sb.append("CREATE TABLE " + TABLE_NAME + " (");

            List<ColumnTypeAndName> columns = getColumnsDefintions();

            for (int i = 0; i < columns.size(); i++) {
                ColumnTypeAndName column = columns.get(i);
                boolean isLastColumn = (i == columns.size() - 1);
                String toAppend = isLastColumn ? column.getColumnCreationStatement() : column.getColumnCreationStatement(COMMA_SEP);
                sb.append(toAppend);
            }

            sb.append(")");

            return sb.toString();
        }

        public static String getDropTableStatement() {
            return "drop table " + TABLE_NAME;
        }

    }

    public static abstract class RecordingTable {
        public static final String TABLE_NAME = "recording";

        public static final String COLUMN_NAME_ID = "id_pk";
        public static final String COLUMN_NAME_CASSETTE_ID = "cassette_id_fk";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_DATE_TIME_OF_RECORDING = "date_time_of_recording";
        public static final String COLUMN_NAME_LENGTH = "length";
        public static final String COLUMN_NAME_AUDIO_FILE_PATH = "audio_file_path";
        public static final String COLUMN_NAME_SEQUENCE_IN_CASSETTE = "sequence_in_cassette";

        public static final String COLUMN_TYPE_ID = INTEGER_TYPE + " PRIMARY KEY";
        public static final String COLUMN_TYPE_CASSETTE_ID = INTEGER_TYPE;
        public static final String COLUMN_TYPE_TITLE = TEXT_TYPE;
        public static final String COLUMN_TYPE_DESCRIPTION = TEXT_TYPE;
        public static final String COLUMN_TYPE_DATE_TIME_OF_RECORDING = INTEGER_TYPE;
        public static final String COLUMN_TYPE_LENGTH = INTEGER_TYPE;
        public static final String COLUMN_TYPE_AUDIO_FILE_PATH = TEXT_TYPE;
        public static final String COLUMN_TYPE_SEQUENCE_IN_CASSETTE = INTEGER_TYPE;


        public static final ColumnTypeAndName COLUMN_ID = new ColumnTypeAndName(COLUMN_NAME_ID, COLUMN_TYPE_ID);
        public static final ColumnTypeAndName COLUMN_CASSETTE_ID = new ColumnTypeAndName(COLUMN_NAME_CASSETTE_ID, COLUMN_TYPE_CASSETTE_ID);
        public static final ColumnTypeAndName COLUMN_TITLE = new ColumnTypeAndName(COLUMN_NAME_TITLE, COLUMN_TYPE_TITLE);
        public static final ColumnTypeAndName COLUMN_DESCRIPTION = new ColumnTypeAndName(COLUMN_NAME_DESCRIPTION, COLUMN_TYPE_DESCRIPTION);
        public static final ColumnTypeAndName COLUMN_DATE_TIME_OF_RECORDING = new ColumnTypeAndName(COLUMN_NAME_DATE_TIME_OF_RECORDING, COLUMN_TYPE_DATE_TIME_OF_RECORDING);
        public static final ColumnTypeAndName COLUMN_LENGTH = new ColumnTypeAndName(COLUMN_NAME_LENGTH, COLUMN_TYPE_LENGTH);
        public static final ColumnTypeAndName COLUMN_AUDIO_FILE_PATH = new ColumnTypeAndName(COLUMN_NAME_AUDIO_FILE_PATH, COLUMN_TYPE_AUDIO_FILE_PATH);
        public static final ColumnTypeAndName COLUMN_SEQUENCE_IN_CASSETTE = new ColumnTypeAndName(COLUMN_NAME_SEQUENCE_IN_CASSETTE, COLUMN_TYPE_SEQUENCE_IN_CASSETTE);

        private static List<ColumnTypeAndName> getColumnsDefintions() {
            List<ColumnTypeAndName> columnTypesAndNames = new LinkedList<>();

            columnTypesAndNames.add(COLUMN_ID);
            /*
            Following column is commented out because we're going to be specifiying this column
            individually as it we require this column to be also NOT NULL.
            THIS HAS TO BE DONE in #getCreateTableStatement.
             */
            //columnTypesAndNames.add(COLUMN_CASSETTE_ID);
            columnTypesAndNames.add(COLUMN_TITLE);
            columnTypesAndNames.add(COLUMN_DESCRIPTION);
            columnTypesAndNames.add(COLUMN_DATE_TIME_OF_RECORDING);
            columnTypesAndNames.add(COLUMN_LENGTH);
            columnTypesAndNames.add(COLUMN_AUDIO_FILE_PATH);
            columnTypesAndNames.add(COLUMN_SEQUENCE_IN_CASSETTE);

            return columnTypesAndNames;
        }

        public static String getCreateTableStatement() {
            StringBuilder sb = new StringBuilder();

            sb.append("CREATE TABLE " + TABLE_NAME + " (");

            for (ColumnTypeAndName column : getColumnsDefintions()) {
                sb.append(column.getColumnCreationStatement(COMMA_SEP));
            }

            sb.append(COLUMN_CASSETTE_ID.getColumnCreationStatement() + " NOT NULL,");

            sb.append("FOREIGN KEY(");
            sb.append(COLUMN_NAME_CASSETTE_ID);
            sb.append(") REFERENCES ");
            sb.append(CassetteTable.TABLE_NAME);
            sb.append("(" + CassetteTable.COLUMN_NAME_ID + ") ON DELETE CASCADE)");

            return sb.toString();
        }

        public static String getDropTableStatement() {
            return "drop table " + TABLE_NAME;
        }

    }
}
