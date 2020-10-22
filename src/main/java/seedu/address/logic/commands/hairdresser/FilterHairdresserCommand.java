package seedu.address.logic.commands.hairdresser;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.person.hairdresser.HairdresserNameContainsKeywordsPredicate;

/**
 * Finds and lists all hairdressers whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindHairdresserCommand extends Command {

    public static final String COMMAND_WORD = "find_hairdresser";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all hairdressers whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final HairdresserNameContainsKeywordsPredicate predicate;

    public FindHairdresserCommand(HairdresserNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredHairdresserList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredHairdresserList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindHairdresserCommand // instanceof handles nulls
                && predicate.equals(((FindHairdresserCommand) other).predicate)); // state check
    }
}