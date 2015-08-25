package andrewtorski.cassette.data.repository.test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import andrewtorski.cassette.domain.entity.Cassette;
import andrewtorski.cassette.domain.entity.Recording;

/**
 * Contains static methods for providing the caller with sets of interconnected data.
 * <p/>
 * For start there are 5 Cassettes with Id's from 1 to 5. With two recordings each.
 */
public abstract class SampleDataProvider {

    public static List<Cassette> getCassettes() {

        Cassette cassette1, cassette2, cassette3, cassette4, cassette5;

        cassette1 = new Cassette(1, "Cassette #1", "Lorem ipsum doloret sit amet", new Date(), 12000, false, "/", null, 2);
        cassette2 = new Cassette(2, "Cassette #2", "Lorem ipsum doloret sit amet", new Date(), 12000, false, "/", null, 2);
        cassette3 = new Cassette(3, "Cassette #3", "Lorem ipsum doloret sit amet", new Date(), 12000, false, "/", null, 2);
        cassette4 = new Cassette(4, "Cassette #4", "Lorem ipsum doloret sit amet", new Date(), 12000, false, "/", null, 2);
        cassette5 = new Cassette(5, "Cassette #5", "Lorem ipsum doloret sit amet", new Date(), 12000, false, "/", null, 2);

        List<Cassette> cassettes = new LinkedList<>();

        cassettes.add(cassette1);
        cassettes.add(cassette2);
        cassettes.add(cassette3);
        cassettes.add(cassette4);
        cassettes.add(cassette5);

        return cassettes;
    }

    public static List<Recording> getRecordings() {
        Recording recording1, recording2, recording3, recording4, recording5,
                recording6, recording7, recording8, recording9, recording0;


        recording1 = new Recording(1, 1, "Rec #1", "Lorem ipsum doloret sit amet", new Date(), 600, "/", 1);
        recording2 = new Recording(2, 1, "Rec #2", "Lorem ipsum doloret sit amet", new Date(), 600, "/", 2);
        recording3 = new Recording(3, 2, "Rec #3", "Lorem ipsum doloret sit amet", new Date(), 600, "/", 1);
        recording4 = new Recording(4, 2, "Rec #4", "Lorem ipsum doloret sit amet", new Date(), 600, "/", 2);
        recording5 = new Recording(5, 3, "Rec #5", "Lorem ipsum doloret sit amet", new Date(), 600, "/", 1);
        recording6 = new Recording(6, 3, "Rec #6", "Lorem ipsum doloret sit amet", new Date(), 600, "/", 2);
        recording7 = new Recording(7, 4, "Rec #7", "Lorem ipsum doloret sit amet", new Date(), 600, "/", 1);
        recording8 = new Recording(8, 4, "Rec #8", "Lorem ipsum doloret sit amet", new Date(), 600, "/", 2);
        recording9 = new Recording(9, 5, "Rec #9", "Lorem ipsum doloret sit amet", new Date(), 600, "/", 1);
        recording0 = new Recording(10, 5, "Rec #10", "Lorem ipsum doloret sit amet", new Date(), 600, "/", 2);


        List<Recording> recordings = new LinkedList<>();

        recordings.add(recording1);
        recordings.add(recording2);
        recordings.add(recording3);
        recordings.add(recording4);
        recordings.add(recording5);
        recordings.add(recording6);
        recordings.add(recording7);
        recordings.add(recording8);
        recordings.add(recording9);
        recordings.add(recording0);

        return recordings;
    }

}
