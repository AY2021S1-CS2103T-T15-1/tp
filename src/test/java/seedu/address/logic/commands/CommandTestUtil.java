package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_APPT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HAIRDRESSER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIALISATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.hairdresser.EditHairdresserCommand;
import seedu.address.model.HairStyleX;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.client.Client;
import seedu.address.model.person.hairdresser.Hairdresser;
import seedu.address.model.person.hairdresser.HairdresserId;
import seedu.address.model.person.hairdresser.HairdresserNameContainsKeywordsPredicate;
import seedu.address.testutil.EditHairdresserDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    // for hairdresser testings
    public static final String VALID_NAME_ALISSA = "Alissa Bee";
    public static final String VALID_NAME_BENJAMIN = "Benjamin Choo";
    public static final String VALID_PHONE_ALISSA = "33333333";
    public static final String VALID_PHONE_BENJAMIN = "22222222";
    public static final String VALID_EMAIL_ALISSA = "alissa@example.com";
    public static final String VALID_EMAIL_BENJAMIN = "benjamin@example.com";
    public static final String VALID_GENDER_ALISSA = "F";
    public static final String VALID_GENDER_BENJAMIN = "M";
    public static final String VALID_TITLE_ALISSA = "Colourist";
    public static final String VALID_TITLE_BENJAMIN = "Assistant";
    public static final String VALID_SPECIALISATION_PERM = "Perm";
    public static final String VALID_SPECIALISATION_COLOR = "Color";

    public static final String NAME_DESC_ALISSA = " " + PREFIX_NAME + VALID_NAME_ALISSA;
    public static final String NAME_DESC_BENJAMIN = " " + PREFIX_NAME + VALID_NAME_BENJAMIN;
    public static final String PHONE_DESC_ALISSA = " " + PREFIX_PHONE + VALID_PHONE_ALISSA;
    public static final String PHONE_DESC_BENJAMIN = " " + PREFIX_PHONE + VALID_PHONE_BENJAMIN;
    public static final String EMAIL_DESC_ALISSA = " " + PREFIX_EMAIL + VALID_EMAIL_ALISSA;
    public static final String EMAIL_DESC_BENJAMIN = " " + PREFIX_EMAIL + VALID_EMAIL_BENJAMIN;
    public static final String TITLE_DESC_ALISSA = " " + PREFIX_TITLE + VALID_TITLE_ALISSA;
    public static final String TITLE_DESC_BENJAMIN = " " + PREFIX_TITLE + VALID_TITLE_BENJAMIN;
    public static final String GENDER_DESC_ALISSA = " " + PREFIX_GENDER + VALID_GENDER_ALISSA;
    public static final String GENDER_DESC_BENJAMIN = " " + PREFIX_GENDER + VALID_GENDER_BENJAMIN;
    public static final String SPECIALISATION_DESC_PERM = " " + PREFIX_SPECIALISATION + VALID_SPECIALISATION_PERM;
    public static final String SPECIALISATION_DESC_COLOR = " " + PREFIX_SPECIALISATION + VALID_SPECIALISATION_COLOR;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE; // empty string not allowed for gender
    public static final String INVALID_GENDER_DESC = " " + PREFIX_GENDER + "G"; // non-'F'/'M' not allowed for gender
    public static final String INVALID_SPECIALISATION_DESC = " " + PREFIX_SPECIALISATION + "hubby*";
    // '*' not allowed in specs

    public static final EditHairdresserCommand.EditHairdresserDescriptor DESC_ALISSA;
    public static final EditHairdresserCommand.EditHairdresserDescriptor DESC_BENJAMIN;

    static {
        DESC_ALISSA = new EditHairdresserDescriptorBuilder().withName(VALID_NAME_ALISSA)
                .withPhone(VALID_PHONE_ALISSA).withEmail(VALID_EMAIL_ALISSA).withGender(VALID_GENDER_ALISSA)
                .withTitle(VALID_TITLE_ALISSA).withSpecs(VALID_SPECIALISATION_COLOR).build();
        DESC_BENJAMIN = new EditHairdresserDescriptorBuilder().withName(VALID_NAME_BENJAMIN)
                .withPhone(VALID_PHONE_BENJAMIN).withEmail(VALID_EMAIL_BENJAMIN).withGender(VALID_GENDER_BENJAMIN)
                .withTitle(VALID_TITLE_BENJAMIN)
                .withSpecs(VALID_SPECIALISATION_COLOR, VALID_SPECIALISATION_PERM).build();
    }

    //for client testings

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    // for testing appointment
    public static final String VALID_CLIENT_ID = "1";
    public static final String INVALID_APPT_CLIENT_ID = "-1";
    public static final String VALID_HAIRDRESSER_ID = "2";
    public static final String INVALID_APPT_HAIRDRESSER_ID = "-2";
    public static final LocalDateTime FUTURE_DATE_TIME = LocalDateTime.now().withHour(9).withMinute(0).plusDays(1);
    public static final String VALID_DATE_OF_APPT = FUTURE_DATE_TIME.toLocalDate().toString();
    public static final String INVALID_DATE_OF_APPT = "20190833";
    public static final String VALID_START_TIME = FUTURE_DATE_TIME.toLocalTime()
            .format(DateTimeFormatter.ofPattern("HH:mm"));
    public static final String INVALID_START_TIME = "9";
    public static final String VALID_STATUS = "ACTIVE";
    public static final String INVALID_STATUS = "ASDF";
    public static final String VALID_MARK_APPT_INDEX = "1";
    public static final String INVALID_MARK_APPT_INDEX = "-1";
    public static final String DESC_VALID_CLIENT_ID = " " + PREFIX_CLIENT_ID + VALID_CLIENT_ID;
    public static final String DESC_INVALID_CLIENT_ID = " " + PREFIX_CLIENT_ID + INVALID_APPT_CLIENT_ID;
    public static final String DESC_VALID_HAIRDRESSER_ID = " " + PREFIX_HAIRDRESSER_ID + VALID_HAIRDRESSER_ID;
    public static final String DESC_INVALID_HAIRDRESSER_ID = " " + PREFIX_HAIRDRESSER_ID + INVALID_APPT_HAIRDRESSER_ID;
    public static final String DESC_VALID_DATE_OF_APPT = " " + PREFIX_DATE_OF_APPT + VALID_DATE_OF_APPT;
    public static final String DESC_INVALID_DATE_OF_APPT = " " + PREFIX_DATE_OF_APPT + INVALID_DATE_OF_APPT;
    public static final String DESC_VALID_START_TIME = " " + PREFIX_START_TIME + VALID_START_TIME;
    public static final String DESC_INVALID_START_TIME = " " + PREFIX_START_TIME + INVALID_START_TIME;
    public static final String DESC_VALID_STATUS = " " + PREFIX_APPT_STATUS + VALID_STATUS;
    public static final String DESC_INVALID_STATUS = " " + PREFIX_APPT_STATUS + INVALID_STATUS;
    public static final String DESC_VALID_MARK_APPT = " " + VALID_MARK_APPT_INDEX + " " + PREFIX_APPT_STATUS
            + VALID_STATUS;
    public static final String DESC_INVALID_MARK_APPT_INDEX = " " + INVALID_MARK_APPT_INDEX + " " + DESC_VALID_STATUS;
    public static final String DESC_INVALID_MARK_APPT_STATUS = " " + VALID_MARK_APPT_INDEX + " " + DESC_INVALID_STATUS;


    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }
    //
    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - HairStyleX, filtered list and selected items in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        HairStyleX expectedHairStyleX = new HairStyleX(actualModel.getHairStyleX());
        List<Hairdresser> expectedFilteredHairdresserList = new ArrayList<>(actualModel.getFilteredHairdresserList());
        List<Client> expectedFilteredClientList = new ArrayList<>(actualModel.getFilteredClientList());
        List<Appointment> expectedFilteredApptList = new ArrayList<>(actualModel.getFilteredAppointmentList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedHairStyleX, actualModel.getHairStyleX());
        assertEquals(expectedFilteredHairdresserList, actualModel.getFilteredHairdresserList());
        assertEquals(expectedFilteredClientList, actualModel.getFilteredClientList());
        assertEquals(expectedFilteredApptList, actualModel.getFilteredAppointmentList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the hairdresser at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showHairdresserAtIndex(Model model, HairdresserId targetIndex) {

        Hairdresser hairdresser = model.getFilteredHairdresserList().get(targetIndex.id - 1);
        final String[] splitName = hairdresser.getName().fullName.split("\\s+");
        model.updateFilteredHairdresserList(new HairdresserNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredHairdresserList().size());
    }

    ///**
    // * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
    // * {@code model}'s address book.
    // */
    //public static void showPersonAtIndex(Model model, Index targetIndex) {
    //    assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());
    //
    //    Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
    //    final String[] splitName = person.getName().fullName.split("\\s+");
    //    model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));
    //
    //    assertEquals(1, model.getFilteredPersonList().size());
    //}

}
