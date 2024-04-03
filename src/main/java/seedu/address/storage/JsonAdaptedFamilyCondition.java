package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.FamilyCondition;

/**
 * Jackson-friendly version of {@link FamilyCondition}.
 */
class JsonAdaptedFamilyCondition {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Patient's %s field is missing!";
    private final String familyConditionName;

    /**
     * Constructs a {@code JsonAdaptedFamilyCondition} with the given {@code familyConditionName}.
     */
    @JsonCreator
    public JsonAdaptedFamilyCondition(String familyConditionName) {
        if (familyConditionName == null || familyConditionName.isEmpty()) {
            throw new IllegalArgumentException(FamilyCondition.MESSAGE_CONSTRAINTS);
        }
        this.familyConditionName = familyConditionName;
    }

    /**
     * Converts a given {@code FamilyCondition} into this class for Jackson use.
     */
    public JsonAdaptedFamilyCondition(FamilyCondition source) {
        familyConditionName = source.familyCondition;
    }

    @JsonValue
    public String getFamilyConditionName() {
        return familyConditionName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code FamilyCondition} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public FamilyCondition toModelType() throws IllegalValueException {
        if (familyConditionName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                FamilyCondition.class.getSimpleName()));
        }
        if (!FamilyCondition.isValidFamilyCondition(familyConditionName)) {
            throw new IllegalValueException(FamilyCondition.MESSAGE_CONSTRAINTS);
        }
        return new FamilyCondition(familyConditionName);
    }

}
