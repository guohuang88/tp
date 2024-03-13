package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.EditCommand.createEditedPatient;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PATIENTS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.EditPatientDescriptor;
import seedu.address.model.patient.Patient;
import seedu.address.model.tag.Tag;

/**
 * Adds one or more tags to the specified patient.
 * Tags can only be a single word, with no space in between.
 * Repeated tags in command will be added as a single tag.
 * If the patient already has the tag, it will not be added.
 */
public class AddTagsCommand extends Command {

    public static final String COMMAND_WORD = "addt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds one or more tags (single word) to a person identified "
            + "by the index number used in the last person listing. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TAG + "[TAG]+ \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TAG + "fallRisk";

    public static final String MESSAGE_ADD_TAG_SUCCESS = "Tags successfully added for Patient %s";

    private final Index index;
    private final Set<Tag> tags;

    private final EditPatientDescriptor editPatientDescriptor;

    /**
     * @param index of the patient in the filtered patient list to add the tags
     * @param tags   to be added to the patient
     */
    public AddTagsCommand(Index index, Set<Tag> tags) {
        requireAllNonNull(index, tags);

        this.index = index;
        this.tags = tags;
        this.editPatientDescriptor = new EditPatientDescriptor();
    }

    /**
     * Executes the add tag command to add one or more tags to the patient.
     *
     * @param model {@code Model} which the command should operate on.
     * @return A command result message indicating the success of the operation.
     * @throws CommandException If there is an error executing the command.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);
        List<Patient> lastShownList = model.getFilteredPatientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        Patient patientToEdit = lastShownList.get(index.getZeroBased());

        // Create new Hashset to add in new tags as Patient.getTags() return unmodifiableSet
        Set<Tag> newTagList = new HashSet<>(patientToEdit.getTags());
        newTagList.addAll(tags);

        editPatientDescriptor.setTags(newTagList);

        Patient editedPatient = createEditedPatient(patientToEdit, editPatientDescriptor);

        model.setPatient(patientToEdit, editedPatient);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);

        return new CommandResult(String.format(MESSAGE_ADD_TAG_SUCCESS, editedPatient.getName()));
    }

    /**
     * Returns true if both add tag commands have the same index and tags to add.
     *
     * @param other Another object to compare to.
     * @return True if the other object is an AddTagsCommand with the same index and tags to add.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddTagsCommand)) {
            return false;
        }

        AddTagsCommand otherTagCommand = (AddTagsCommand) other;
        return index.equals(otherTagCommand.index)
                && tags.equals(otherTagCommand.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("tags", tags)
                .toString();
    }
}