package andrewtorski.cassette.data.db;

import org.junit.Test;

import andrewtorski.cassette.data.db.schema.CassetteDbContract;

import static org.junit.Assert.assertEquals;

/**
 * Created by andrew on 05.08.15.
 */
public class CassetteDbContractTest {

    @Test
    public void test_RecordingTable_getCreateTableStatement_Method() {
        assertEquals("CREATE TABLE recording (id_pk INTEGER PRIMARY KEY,cassette_id_fk INTEGER,title TEXT,description TEXT,date_time_of_recording INTEGER,length INTEGER,audio_file_path TEXT,sequence_in_cassette INTEGER,FOREIGN KEY(cassette_id_fk) REFERENCES cassette(id_pk)",
                CassetteDbContract.RecordingTable.getCreateTableStatement());
    }

    @Test
    public void test_CassetteTable_getCreateTableStatement_Method() {
        assertEquals("CREATE TABLE cassette (id_pk INTEGER PRIMARY KEY,title TEXT,description TEXT,date_time_of_creation INTEGER,date_time_of_compilation INTEGER,length INTEGER,number_of_recordings INTEGER,is_compiled INTEGER,compiled_file_path TEXT)",
                CassetteDbContract.CassetteTable.getCreateTableStatement());
    }

}
