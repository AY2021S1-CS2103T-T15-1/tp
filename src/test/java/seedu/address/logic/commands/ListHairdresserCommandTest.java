package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showHairdresserAtIndex;
import static seedu.address.testutil.TypicalHairdressers.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.ID_FIRST_HAIRDRESSER;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.hairdresser.ListHairdresserCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListHairdresserCommand.
 */
public class ListHairdresserCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getHairStyleX(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListHairdresserCommand(), model,
                ListHairdresserCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showHairdresserAtIndex(model, ID_FIRST_HAIRDRESSER);
        assertCommandSuccess(new ListHairdresserCommand(), model,
                ListHairdresserCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
