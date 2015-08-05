package andrewtorski.cassette.data.entity.mapper;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import andrewtorski.cassette.data.entity.CassetteEntity;
import andrewtorski.cassette.domain.entity.Cassette;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CassetteEntityDataMapperTest {

    private static CassetteEntityDataMapper cassetteEntityDataMapper;
    final String title = "title", description = "description";
    final long ThirdNovember1993_UnixTime = 752284800;
    final long FourthNovember1993_UnixTime = 752371200;
    final int length = 123, numberOfORecordings = 2;
    final int isCompiled = 0;
    final String compiledFilePath = "path";

    @BeforeClass
    public static void setUpBeforeTests() {
        cassetteEntityDataMapper = new CassetteEntityDataMapper();
    }

    @Test
    public void test_transform_Method() throws Exception {
        //  Arrange
        //region
        CassetteEntity cassetteEntity = new CassetteEntity();
        cassetteEntity.dateTimeOfCreation = ThirdNovember1993_UnixTime;
        cassetteEntity.dateTimeOfCompilation = FourthNovember1993_UnixTime;
        cassetteEntity.title = title;
        cassetteEntity.descripition = description;
        cassetteEntity.compiledFilePath = compiledFilePath;
        cassetteEntity.isCompiled = isCompiled;
        cassetteEntity.length = length;
        cassetteEntity.numberOfRecordings = numberOfORecordings;
        //endregion
        Cassette cassette;

        //  Act
        cassette = cassetteEntityDataMapper.transform(cassetteEntity);

        //  Assert


        assertNotNull("Transformed Cassette is null.", cassette);
        assertEquals("title", cassette.getTitle());
        assertEquals("description", cassette.getDescription());
        assertEquals("path", cassette.getCompiledFilePath());
        assertNull(cassette.getCompiledFile());
        assertFalse(cassette.isCompiled());
        assertEquals(123, cassette.getLengthInMilliseconds());
        assertEquals(2, cassette.getNumberOfRecordings());

        //  Dates
        Calendar calendar = Calendar.getInstance(Locale.US);
        //  Creation
        Date cassetteCreationDate = cassette.getDateTimeOfCreation();

        assertNotNull(cassetteCreationDate);

        calendar.setTime(cassetteCreationDate);
        int creationDay = calendar.get(Calendar.DAY_OF_MONTH),
                creationMonth = calendar.get(Calendar.MONTH),
                creationyear = calendar.get(Calendar.YEAR);

        assertEquals(3, creationDay);
        assertEquals(11, creationMonth);
        assertEquals(1993, creationyear);

        //  Compilation
        Date cassetteCompilationDate = cassette.getDateTimeOfCompilation();

        assertNotNull(cassetteCompilationDate);

        calendar.setTime(cassetteCompilationDate);
        int compilationDay = calendar.get(Calendar.DAY_OF_MONTH),
                compilationMonth = calendar.get(Calendar.MONTH),
                compilationyear = calendar.get(Calendar.YEAR);

        assertEquals(4, compilationDay);
        assertEquals(11, compilationMonth);
        assertEquals(1993, compilationyear);
    }

}