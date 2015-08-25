package andrewtorski.cassette.domain.entity.test;

import junit.framework.Assert;

import org.junit.Test;

import andrewtorski.cassette.domain.entity.Cassette;

public class CassetteUnitTest {

    /*@Test
    public void Test_Constructors() {
        //  Arrange
        Cassette cassetteFromData;
        Cassette cassetteFromPresentation;
        String title = "1", description = "2", path = "3";
        Date date = new Date(2014, 12, 12);
        int number1 = 1, number2 = 2, number3 = 3;
        //  we set cassetteFromData.isCompiled to false, so that the logic inside the constructor
        //  won't try to open a file.
        boolean bool1 = false;
        //  Act

        *//*cassetteFromData = new Cassette(number1, title, description,
                date, number2, bool1, path,
                date, number3, new ArrayList<Recording>());*//*

        cassetteFromPresentation = new Cassette(title, description);

        //  Assert

        //  Cassette from data constructor assertions.
        assertEquals("Title for cassette from data is not matching", title, cassetteFromData.getTitle());
        assertEquals("Description for cassette from data is not matching", description, cassetteFromData.getDescription());
        assertEquals("Id for cassette from data is not matching", number1, cassetteFromData.getId());
        assertEquals("Date and time of creation for cassette from data is not matching", date, cassetteFromData.getDateTimeOfCreation());
        assertEquals("Compiled file path for cassette from data is not matching", path, cassetteFromData.getCompiledFilePath());
        assertEquals("Date and time of compilation is not matching", date, cassetteFromData.getDateTimeOfCompilation());
        assertEquals("Number of Recordings connected to Cassette from Data is not matching", number3, cassetteFromData.getNumberOfRecordings());
        assertFalse("Cassette from data is compiled.", cassetteFromData.isCompiled());
        assertNotNull("Recordings inside Cassette from Data is null", cassetteFromData.getRecordings());
        //  Cassette from presentation constructor assertions.
        assertEquals("Title for cassette from presentation is not matching", title, cassetteFromPresentation.getTitle());
        assertEquals("Description for cassette from presentation is not matching", description, cassetteFromPresentation.getDescription());
        assertEquals("Id for cassette from presentation is not matching", 0, cassetteFromPresentation.getId());
        assertEquals("Number of Recordings connected to Cassette from presentation is not matching", 0, cassetteFromPresentation.getNumberOfRecordings());
        assertNotNull("Date and time for cassette from presentation is null", cassetteFromPresentation.getDateTimeOfCreation());
        assertNull("Compiled file path for cassette from presentation is not matching", cassetteFromPresentation.getCompiledFilePath());
        assertNull("Date and time of compilation of Cassette is not null", cassetteFromPresentation.getDateTimeOfCompilation());
        assertNull("Recordings inside Cassette from presentation is not null", cassetteFromPresentation.getRecordings());
        assertFalse("Cassette from presentation is compiled.", cassetteFromPresentation.isCompiled());
    }*/

    @Test
    public void Test_incrementAndReturnNumberOfRecordings_Method() {
        //  Arrange
        Cassette cassette = new Cassette("Hello", "World");
        int firstCallResult, secondCallResult, thirdCallResut;

        //  Act
        //  first call to incrementAndReturnNumberOfRecordings() should result in return of 1.
        firstCallResult = cassette.incrementAndReturnNumberOfRecordings();
        //  second call to incrementAndReturnNumberOfRecordings() should result in return of 1.
        secondCallResult = cassette.incrementAndReturnNumberOfRecordings();
        //  third call to incrementAndReturnNumberOfRecordings() should result in return of 1.
        thirdCallResut = cassette.incrementAndReturnNumberOfRecordings();

        //  Assert
        Assert.assertEquals("First call result is not 1", 1, firstCallResult);
        Assert.assertEquals("First call result is not 2", 2, secondCallResult);
        Assert.assertEquals("First call result is not 3", 3, thirdCallResut);
    }
}
