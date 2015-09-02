package andrewtorski.casette.app.model.processor;

import android.test.AndroidTestCase;

import java.util.Date;

import andrewtorski.casette.app.model.CassetteModel;


public class CassetteModelProcessorTest extends AndroidTestCase {

    CassetteModel cassetteModel;
    /**
     * Timestamp for 2005-06-26 05:00:00.
     */
    static long epochTimeStamp = 1119798000000L;
    static Date date = new Date(epochTimeStamp);


    @Override
    protected void setUp() throws Exception {

        cassetteModel = new CassetteModel(0, "", "", date, -1, false, "", date, 123);
    }

    public void test_getUserReadableDate() {
        //  Arrange done in #setUp()
        //  Act
        String result = CassetteModelProcessor.getUserReadableDate(cassetteModel);
        //  Arrange
        //      we expect CassetteModelProcessor.getUserReadableDate to yield following pattern
        //      15:00 on Sun, 26 Jun 2005
        assertEquals("Dates strings do not match.", "05:00 Sun, Jun 26 2005", result);
    }

    public void test_getUserReadableLength_1seconds() {
        //  Arrange
        cassetteModel.setLength(1000); // 1000 milliseconds = 1 second

        //  Act
        String result = CassetteModelProcessor.getUserReadableLength(cassetteModel);

        //  Assert
        assertEquals("1 second", result);
    }

    public void test_getUserReadableLength_50seconds() {
        //  Arrange
        cassetteModel.setLength(50000); // 50000 milliseconds = 50 second

        //  Act
        String result = CassetteModelProcessor.getUserReadableLength(cassetteModel);

        //  Assert
        assertEquals("50 seconds", result);
    }

    public void test_getUserReadableLength_1Minute() {
        //  Arrange
        cassetteModel.setLength(60000); // 60000 milliseconds = 60 second

        //  Act
        String result = CassetteModelProcessor.getUserReadableLength(cassetteModel);

        //  Assert
        assertEquals("1 minute", result);
    }

    public void test_getUserReadableLength_1Minute30seconds() {
        //  Arrange
        cassetteModel.setLength(90000); // 90000 milliseconds = 90 second = 1min30sec

        //  Act
        String result = CassetteModelProcessor.getUserReadableLength(cassetteModel);

        //  Assert
        assertEquals("1 minute 30 seconds", result);
    }

    public void test_getUserReadableLength_1Minute1Second() {
        //  Arrange
        cassetteModel.setLength(61000); // 61000 milliseconds = 61 second = 1min1sec

        //  Act
        String result = CassetteModelProcessor.getUserReadableLength(cassetteModel);

        //  Assert
        assertEquals("1 minute 1 second", result);
    }

    public void test_getUserReadableLength_2Minutes() {
        //  Arrange
        cassetteModel.setLength(120000); // 120000 milliseconds = 120 seconds = 2min

        //  Act
        String result = CassetteModelProcessor.getUserReadableLength(cassetteModel);

        //  Assert
        assertEquals("2 minutes", result);
    }

    public void test_getUserReadableLength_30Minutes() {
        //  Arrange
        cassetteModel.setLength(1_800_000); // 1 800 000milliseconds = 1800 seconds = 30 min

        //  Act
        String result = CassetteModelProcessor.getUserReadableLength(cassetteModel);

        //  Assert
        assertEquals("30 minutes", result);
    }

}
