package andrewtorski.data_test.cassette.data.db.test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import andrewtorski.cassette.data.db.CassetteDataDbAdapter;
import andrewtorski.global.GlobalValues;

/**
 * Created by andrew on 09.08.15.
 */
public class CasseteDataDbAdapterTest extends AndroidTestCase {

    private CassetteDataDbAdapter testedAdapter;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        GlobalValues.setContext(context);
        testedAdapter = CassetteDataDbAdapter.getInstance();
    }

    public void testTrue() {
        assertTrue(true);
    }

    public void testFalse() {
        assertTrue(false);
    }

}
