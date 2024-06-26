package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.patient.Patient;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX = "The patient index provided is invalid";
    public static final String MESSAGE_INVALID_EVENT_DISPLAYED_INDEX = "The event index provided is invalid";
    public static final String MESSAGE_PATIENT_LISTED_OVERVIEW = "%1$d patients listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Appends the elements of the given set to the provided StringBuilder with commas between each element.
     *
     * @param builder The StringBuilder to which the elements will be appended.
     * @param elements The set of elements to be appended to the StringBuilder.
     * @param <T> The type of elements in the set.
     */
    private static <T> void appendWithCommas(StringBuilder builder, Set<T> elements) {
        if (!elements.isEmpty()) {
            builder.append(elements.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", ")));
        }
    }

    /**
     * Formats the {@code patient} for display to the user.
     */
    public static String format(Patient patient) {
        final StringBuilder builder = new StringBuilder();
        builder.append(patient.getName())
                .append("; PreferredName: ")
                .append(patient.getPreferredName())
                .append("; PatientHospitalId: ")
                .append(patient.getPatientHospitalId())
                .append("; FoodPreferences: ");
        appendWithCommas(builder, patient.getFoodPreferences());
        builder.append("; FamilyConditions: ");
        appendWithCommas(builder, patient.getFamilyConditions());
        builder.append("; Hobbies: ");
        appendWithCommas(builder, patient.getHobbies());
        builder.append("; Tags: ");
        patient.getTags().forEach(builder::append);
        return builder.toString();
    }

}
