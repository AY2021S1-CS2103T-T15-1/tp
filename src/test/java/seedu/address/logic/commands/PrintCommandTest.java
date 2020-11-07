package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalClients;
import static seedu.address.testutil.TypicalHairdressers.getTypicalHairdressers;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.HairStyleX;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.client.Client;
import seedu.address.model.person.hairdresser.Hairdresser;


class PrintCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        HairStyleX ab = new HairStyleX();
        for (Client client : getTypicalClients()) {
            ab.addClient(client);
        }
        for (Hairdresser hairdresser : getTypicalHairdressers()) {
            ab.addHairdresser(hairdresser);
        }
        model = new ModelManager(ab, new UserPrefsStub());
        expectedModel = new ModelManager(model.getHairStyleX(), new UserPrefsStub());
    }

    @Test
    public void execute_printSuccessful() {
        assertCommandSuccess(new PrintCommand(), model,
                PrintCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * A UserPrefs stub that overwrites the csv file path to test directory.
     */
    private static class UserPrefsStub extends UserPrefs {
        private final Path csvFilePath = Paths.get("src", "test", "data", "PrintCommandTest");

        @Override
        public Path getCsvFilePath() {
            return csvFilePath;
        }
    }

}
