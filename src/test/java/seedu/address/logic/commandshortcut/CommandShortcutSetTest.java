package seedu.address.logic.commandshortcut;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commandshortcut.exceptions.CommandWordException;

class CommandShortcutSetTest {

    @Test
    void insertNewShortcut() {
        CommandShortcutSet.reset();
        CommandShortcutSet shortcutSet = CommandShortcutSet.getInstance();
        assertThrows(CommandWordException.class, ()->
                shortcutSet.insertNewShortcut(CommandWord.EDIT_HAIRDRESSER, "edit_hairdresser"));
        assertThrows(CommandWordException.class, ()->
                shortcutSet.insertNewShortcut(CommandWord.EDIT_HAIRDRESSER, "help"));
        assertDoesNotThrow(()-> shortcutSet.insertNewShortcut(CommandWord.EDIT_HAIRDRESSER, "e_h"));
        assertThrows(CommandWordException.class, ()->
                shortcutSet.insertNewShortcut(CommandWord.EDIT_HAIRDRESSER, "e_h"));
        assertThrows(CommandWordException.class, ()->shortcutSet.insertNewShortcut(CommandWord.CLEAR, "e_h"));


    }

    @Test
    void getCommandWord() {
        CommandShortcutSet.reset();
        CommandShortcutSet shortcutSet = CommandShortcutSet.getInstance();
        assertThrows(CommandWordException.class, ()->shortcutSet.getCommandWord("e"));
        assertDoesNotThrow(()->assertTrue(shortcutSet.getCommandWord("help") == CommandWord.HELP));
        assertDoesNotThrow(()-> shortcutSet.insertNewShortcut(CommandWord.EDIT_HAIRDRESSER, "e_h"));
        assertDoesNotThrow(()->
                assertTrue(shortcutSet.getCommandWord("e_h") == CommandWord.EDIT_HAIRDRESSER));

    }

    @Test
    void deleteShortcut() {
        CommandShortcutSet.reset();
        CommandShortcutSet shortcutSet = CommandShortcutSet.getInstance();
        assertThrows(CommandWordException.class, ()->shortcutSet.deleteShortcut("edit_hairdresser"));
        assertThrows(CommandWordException.class, ()->shortcutSet.deleteShortcut("e_h"));
        assertDoesNotThrow(()-> shortcutSet.insertNewShortcut(CommandWord.EDIT_HAIRDRESSER, "e_h"));
        assertDoesNotThrow(()-> shortcutSet.deleteShortcut("e_h"));
        assertThrows(CommandWordException.class, ()->shortcutSet.getCommandWord("e_h"));
    }
}
