package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showHairdresserAtIndex;
import static seedu.address.testutil.TypicalHairdressers.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.ID_FIRST_HAIRDRESSER;
import static seedu.address.testutil.TypicalIndexes.ID_SECOND_HAIRDRESSER;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.hairdresser.DeleteHairdresserCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.hairdresser.Hairdresser;
import seedu.address.model.person.hairdresser.HairdresserId;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteHairdresserCommand}.
 */
public class DeleteHairdresserCommandTest {

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Hairdresser personToDelete = model.getFilteredHairdresserList().get(ID_FIRST_HAIRDRESSER.id - 1);
        DeleteHairdresserCommand deleteCommand = new DeleteHairdresserCommand(ID_FIRST_HAIRDRESSER);

        String expectedMessage = String.format(DeleteHairdresserCommand.MESSAGE_DELETE_HAIRDRESSER_SUCCESS,
                personToDelete);

        ModelManager expectedModel = new ModelManager(model.getHairStyleX(), new UserPrefs());
        expectedModel.deleteHairdresser(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        HairdresserId outOfBoundIndex = new HairdresserId(String.valueOf(model
                .getFilteredHairdresserList().size() + 1));
        DeleteHairdresserCommand deleteCommand = new DeleteHairdresserCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_HAIRDRESSER_DISPLAYED_ID);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        showHairdresserAtIndex(model, ID_FIRST_HAIRDRESSER);

        Hairdresser personToDelete = model.getFilteredHairdresserList().get(ID_FIRST_HAIRDRESSER.id - 1);
        DeleteHairdresserCommand deleteCommand = new DeleteHairdresserCommand(ID_FIRST_HAIRDRESSER);

        String expectedMessage = String.format(DeleteHairdresserCommand.MESSAGE_DELETE_HAIRDRESSER_SUCCESS,
                personToDelete);

        Model expectedModel = new ModelManager(model.getHairStyleX(), new UserPrefs());
        expectedModel.deleteHairdresser(personToDelete);
        showNoHairdresser(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteHairdresserCommand deleteFirstCommand = new DeleteHairdresserCommand(ID_FIRST_HAIRDRESSER);
        DeleteHairdresserCommand deleteSecondCommand = new DeleteHairdresserCommand(ID_SECOND_HAIRDRESSER);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteHairdresserCommand deleteFirstCommandCopy = new DeleteHairdresserCommand(ID_FIRST_HAIRDRESSER);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoHairdresser(Model model) {
        model.updateFilteredHairdresserList(p -> false);

        assertTrue(model.getFilteredHairdresserList().isEmpty());
    }
}
