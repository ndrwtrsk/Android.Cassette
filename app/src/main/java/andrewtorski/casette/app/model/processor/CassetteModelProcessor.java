package andrewtorski.casette.app.model.processor;

import andrewtorski.casette.app.model.CassetteModel;

/**
 * Exposes actions which format certain properties of Cassette into user-friendly Strings.
 */
public abstract class CassetteModelProcessor {

    public static String getUserReadableDate(CassetteModel cassetteModel) {
        if (cassetteModel == null || cassetteModel.getDateTimeOfCreation() == null) {
            return null;
        }
        return UserReadableProcessor.getUserReadableDate(cassetteModel.getDateTimeOfCreation());
    }

    public static String getUserReadableLength(CassetteModel cassetteModel) {
        if (cassetteModel == null) {
            return null;
        }
        return UserReadableProcessor.getUserReadableLengthOfTimeFromMilliseconds(cassetteModel.getLength());
    }

}
