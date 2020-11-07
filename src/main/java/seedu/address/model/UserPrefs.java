package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path hairStyleXFilePath = Paths.get("data" , "hairstylex.json");
    private Path csvFilePath = Paths.get("data");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setHairStyleXFilePath(newUserPrefs.getHairStyleXFilePath());
        setCsvFilePath(newUserPrefs.getCsvFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getHairStyleXFilePath() {
        return hairStyleXFilePath;
    }

    public void setHairStyleXFilePath(Path hairStyleXFilePath) {
        requireNonNull(hairStyleXFilePath);
        this.hairStyleXFilePath = hairStyleXFilePath;
    }

    public Path getCsvFilePath() {
        return csvFilePath;
    }

    public void setCsvFilePath(Path csvFilePath) {
        requireNonNull(csvFilePath);
        this.csvFilePath = csvFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && hairStyleXFilePath.equals(o.hairStyleXFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, hairStyleXFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + hairStyleXFilePath);
        return sb.toString();
    }

}
