package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.Event;
import seedu.address.model.patient.FamilyCondition;
import seedu.address.model.patient.FoodPreference;
import seedu.address.model.patient.Hobby;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.PatientHospitalId;
import seedu.address.model.patient.PreferredName;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Patient}.
 */
class JsonAdaptedPatient {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Patient's %s field is missing!";

    private final String patientHospitalId;
    private final String name;
    private final String preferredName;
    private final List<JsonAdaptedFoodPreference> foodPreferences = new ArrayList<>();
    private final List<JsonAdaptedFamilyCondition> familyConditions = new ArrayList<>();
    private final List<JsonAdaptedHobby> hobbies = new ArrayList<>();
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedEvent> events = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPatient} with the given patient details.
     */
    @JsonCreator
    public JsonAdaptedPatient(@JsonProperty("patientHospitalId") String patientHospitalId,
                              @JsonProperty("name") String name, @JsonProperty("preferredName") String preferredName,
                              @JsonProperty("foodPreferences") List<JsonAdaptedFoodPreference> foodPreferences,
                              @JsonProperty("familyConditions") List<JsonAdaptedFamilyCondition> familyConditions,
                              @JsonProperty("hobbies") List<JsonAdaptedHobby> hobbies,
                              @JsonProperty("tags") List<JsonAdaptedTag> tags,
                              @JsonProperty("events") List<JsonAdaptedEvent> events) {
        this.patientHospitalId = patientHospitalId;
        this.name = name;
        this.preferredName = preferredName;
        if (foodPreferences != null) {
            this.foodPreferences.addAll(foodPreferences);
        }
        if (familyConditions != null) {
            this.familyConditions.addAll(familyConditions);
        }
        if (hobbies != null) {
            this.hobbies.addAll(hobbies);
        }
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (events != null) {
            this.events.addAll(events);
        }
    }

    /**
     * Converts a given {@code Patient} into this class for Alex use.
     */
    public JsonAdaptedPatient(Patient source) {
        patientHospitalId = source.getPatientHospitalId().patientHospitalId;
        name = source.getName().fullName;
        preferredName = source.getPreferredName().preferredName;
        foodPreferences.addAll(source.getFoodPreferences().stream()
            .map(JsonAdaptedFoodPreference::new)
            .collect(Collectors.toList()));
        familyConditions.addAll(source.getFamilyConditions().stream()
            .map(JsonAdaptedFamilyCondition::new)
            .collect(Collectors.toList()));
        hobbies.addAll(source.getHobbies().stream()
            .map(JsonAdaptedHobby::new)
            .collect(Collectors.toList()));
        tags.addAll(source.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList()));
        events.addAll(source.getEvents().stream()
            .map(JsonAdaptedEvent::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted patient object into the model's {@code Patient} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted patient.
     */
    public Patient toModelType() throws IllegalValueException {
        final List<FoodPreference> patientFoodPreferences = new ArrayList<>();
        for (JsonAdaptedFoodPreference foodPreference : foodPreferences) {
            patientFoodPreferences.add(foodPreference.toModelType());
        }
        if (patientFoodPreferences.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                FoodPreference.class.getSimpleName()));
        }

        final List<FamilyCondition> patientFamilyConditions = new ArrayList<>();
        for (JsonAdaptedFamilyCondition familyCondition : familyConditions) {
            patientFamilyConditions.add(familyCondition.toModelType());
        }
        if (patientFamilyConditions.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                FamilyCondition.class.getSimpleName()));
        }

        final List<Hobby> patientHobbies = new ArrayList<>();
        for (JsonAdaptedHobby hobby : hobbies) {
            patientHobbies.add(hobby.toModelType());
        }
        if (patientHobbies.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Hobby.class.getSimpleName()));
        }

        final List<Tag> patientTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            patientTags.add(tag.toModelType());
        }

        final List<Event> patientEvents = new ArrayList<>();
        for (JsonAdaptedEvent date : events) {
            patientEvents.add(date.toModelType());
        }

        if (patientHospitalId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                PatientHospitalId.class.getSimpleName()));
        }

        if (!PatientHospitalId.isValidPatientHospitalId(patientHospitalId)) {
            throw new IllegalValueException(PatientHospitalId.MESSAGE_CONSTRAINTS);
        }
        final PatientHospitalId modelPatientHospitalId = new PatientHospitalId(patientHospitalId);


        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (preferredName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                PreferredName.class.getSimpleName()));
        }
        if (!PreferredName.isValidPreferredName(preferredName)) {
            throw new IllegalValueException(PreferredName.MESSAGE_CONSTRAINTS);
        }
        final PreferredName modelPreferredName = new PreferredName(preferredName);

        final Set<FoodPreference> modelFoodPreferences = new HashSet<>(patientFoodPreferences);
        final Set<FamilyCondition> modelFamilyConditions = new HashSet<>(patientFamilyConditions);
        final Set<Hobby> modelHobbies = new HashSet<>(patientHobbies);
        final Set<Tag> modelTags = new HashSet<>(patientTags);
        final Set<Event> modelEvents = new HashSet<>(patientEvents);

        return new Patient(modelPatientHospitalId, modelName, modelPreferredName, modelFoodPreferences,
            modelFamilyConditions, modelHobbies, modelTags, modelEvents);
    }

}
