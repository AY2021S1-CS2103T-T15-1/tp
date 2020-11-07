package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_ALISSA;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BENJAMIN;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_ALISSA;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_BENJAMIN;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SPECIALISATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ALISSA;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_ALISSA;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BENJAMIN;
import static seedu.address.logic.commands.CommandTestUtil.SPECIALISATION_DESC_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.SPECIALISATION_DESC_PERM;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_ALISSA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_ALISSA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BENJAMIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_ALISSA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BENJAMIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALISSA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_ALISSA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BENJAMIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIALISATION_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIALISATION_PERM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_ALISSA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIALISATION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.ID_FIRST_HAIRDRESSER;
import static seedu.address.testutil.TypicalIndexes.ID_SECOND_HAIRDRESSER;
import static seedu.address.testutil.TypicalIndexes.ID_THIRD_HAIRDRESSER;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.hairdresser.EditHairdresserCommand;
import seedu.address.logic.commands.hairdresser.EditHairdresserCommand.EditHairdresserDescriptor;
import seedu.address.logic.parser.hairdresser.EditHairdresserCommandParser;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.hairdresser.HairdresserId;
import seedu.address.model.person.hairdresser.Title;
import seedu.address.model.specialisation.Specialisation;
import seedu.address.testutil.EditHairdresserDescriptorBuilder;

public class EditHairdresserCommandParserTest {

    private static final String SPEC_EMPTY = " " + PREFIX_SPECIALISATION;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditHairdresserCommand.MESSAGE_USAGE);

    private EditHairdresserCommandParser parser = new EditHairdresserCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_ALISSA, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditHairdresserCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_ALISSA, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_ALISSA, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_GENDER_DESC, Gender.MESSAGE_CONSTRAINTS); // invalid gender
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC, Title.MESSAGE_CONSTRAINTS); // invalid title
        assertParseFailure(parser, "1" + INVALID_SPECIALISATION_DESC, Specialisation.MESSAGE_CONSTRAINTS);

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_ALISSA, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PHONE_DESC_BENJAMIN + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_SPECIALISATION} alone will reset the specs of the
        // {@code Hairdresser} being edited, parsing it together with a valid specialisation results in error
        assertParseFailure(parser, "1" + SPECIALISATION_DESC_COLOR + SPECIALISATION_DESC_PERM + SPEC_EMPTY,
                Specialisation.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + SPECIALISATION_DESC_COLOR + SPEC_EMPTY + SPECIALISATION_DESC_PERM,
                Specialisation.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + SPEC_EMPTY + SPECIALISATION_DESC_COLOR + SPECIALISATION_DESC_PERM,
                Specialisation.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_GENDER_ALISSA
                        + VALID_PHONE_ALISSA, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        HairdresserId targetHairdresserId = ID_SECOND_HAIRDRESSER;
        String userInput = targetHairdresserId.id + PHONE_DESC_BENJAMIN + SPECIALISATION_DESC_PERM
                + EMAIL_DESC_ALISSA + GENDER_DESC_ALISSA + TITLE_DESC_ALISSA
                + NAME_DESC_ALISSA + SPECIALISATION_DESC_COLOR;

        EditHairdresserDescriptor descriptor = new EditHairdresserDescriptorBuilder().withName(VALID_NAME_ALISSA)
                .withPhone(VALID_PHONE_BENJAMIN).withEmail(VALID_EMAIL_ALISSA).withGender(VALID_GENDER_ALISSA)
                .withTitle(VALID_TITLE_ALISSA)
                .withSpecs(VALID_SPECIALISATION_COLOR, VALID_SPECIALISATION_PERM).build();
        EditHairdresserCommand expectedCommand = new EditHairdresserCommand(targetHairdresserId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        HairdresserId targetHairdresserId = ID_FIRST_HAIRDRESSER;
        String userInput = targetHairdresserId.id + PHONE_DESC_BENJAMIN + EMAIL_DESC_ALISSA;

        EditHairdresserDescriptor descriptor = new EditHairdresserDescriptorBuilder().withPhone(VALID_PHONE_BENJAMIN)
                .withEmail(VALID_EMAIL_ALISSA).build();
        EditHairdresserCommand expectedCommand = new EditHairdresserCommand(targetHairdresserId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        HairdresserId targetHairdresserId = ID_THIRD_HAIRDRESSER;
        String userInput = targetHairdresserId.id + NAME_DESC_ALISSA;
        EditHairdresserDescriptor descriptor = new EditHairdresserDescriptorBuilder()
                .withName(VALID_NAME_ALISSA).build();
        EditHairdresserCommand expectedCommand = new EditHairdresserCommand(targetHairdresserId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetHairdresserId.id + PHONE_DESC_ALISSA;
        descriptor = new EditHairdresserDescriptorBuilder().withPhone(VALID_PHONE_ALISSA).build();
        expectedCommand = new EditHairdresserCommand(targetHairdresserId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetHairdresserId.id + EMAIL_DESC_ALISSA;
        descriptor = new EditHairdresserDescriptorBuilder().withEmail(VALID_EMAIL_ALISSA).build();
        expectedCommand = new EditHairdresserCommand(targetHairdresserId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // gender
        userInput = targetHairdresserId.id + GENDER_DESC_ALISSA;
        descriptor = new EditHairdresserDescriptorBuilder().withGender(VALID_GENDER_ALISSA).build();
        expectedCommand = new EditHairdresserCommand(targetHairdresserId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // title
        userInput = targetHairdresserId.id + TITLE_DESC_ALISSA;
        descriptor = new EditHairdresserDescriptorBuilder().withTitle(VALID_TITLE_ALISSA).build();
        expectedCommand = new EditHairdresserCommand(targetHairdresserId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetHairdresserId.id + SPECIALISATION_DESC_COLOR;
        descriptor = new EditHairdresserDescriptorBuilder().withSpecs(VALID_SPECIALISATION_COLOR).build();
        expectedCommand = new EditHairdresserCommand(targetHairdresserId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        HairdresserId targetHairdresserId = ID_FIRST_HAIRDRESSER;
        String userInput = targetHairdresserId.id + PHONE_DESC_ALISSA + GENDER_DESC_ALISSA + EMAIL_DESC_ALISSA
                + SPECIALISATION_DESC_COLOR + PHONE_DESC_ALISSA + GENDER_DESC_ALISSA
                + EMAIL_DESC_ALISSA + SPECIALISATION_DESC_COLOR
                + PHONE_DESC_BENJAMIN + GENDER_DESC_BENJAMIN + EMAIL_DESC_BENJAMIN + SPECIALISATION_DESC_PERM;

        EditHairdresserDescriptor descriptor = new EditHairdresserDescriptorBuilder().withPhone(VALID_PHONE_BENJAMIN)
                .withEmail(VALID_EMAIL_BENJAMIN).withGender(VALID_GENDER_BENJAMIN).withSpecs(VALID_SPECIALISATION_PERM,
                VALID_SPECIALISATION_COLOR)
                .build();
        EditHairdresserCommand expectedCommand = new EditHairdresserCommand(targetHairdresserId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        HairdresserId targetHairdresserId = ID_FIRST_HAIRDRESSER;
        String userInput = targetHairdresserId.id + INVALID_PHONE_DESC + PHONE_DESC_BENJAMIN;
        EditHairdresserDescriptor descriptor = new EditHairdresserDescriptorBuilder()
                .withPhone(VALID_PHONE_BENJAMIN).build();
        EditHairdresserCommand expectedCommand = new EditHairdresserCommand(targetHairdresserId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetHairdresserId.id + EMAIL_DESC_BENJAMIN + INVALID_PHONE_DESC + GENDER_DESC_BENJAMIN
                + PHONE_DESC_BENJAMIN;
        descriptor = new EditHairdresserDescriptorBuilder().withPhone(VALID_PHONE_BENJAMIN)
                .withEmail(VALID_EMAIL_BENJAMIN).withGender(VALID_GENDER_BENJAMIN).build();
        expectedCommand = new EditHairdresserCommand(targetHairdresserId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetSpecialisations_success() {
        HairdresserId targetHairdresserId = ID_THIRD_HAIRDRESSER;
        String userInput = targetHairdresserId.id + SPEC_EMPTY;

        EditHairdresserDescriptor descriptor = new EditHairdresserDescriptorBuilder().withSpecs().build();
        EditHairdresserCommand expectedCommand = new EditHairdresserCommand(targetHairdresserId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

