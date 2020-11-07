package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.hairdresser.AddHairdresserCommand;
import seedu.address.model.HairStyleX;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyHairStyleX;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.person.Person;
import seedu.address.model.person.client.Client;
import seedu.address.model.person.client.ClientId;
import seedu.address.model.person.hairdresser.Hairdresser;
import seedu.address.model.person.hairdresser.HairdresserId;
import seedu.address.testutil.HairdresserBuilder;

public class AddHairdresserCommandTest {

    @Test
    public void constructor_nullHairdresser_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddHairdresserCommand(null));
    }

    @Test
    public void execute_hairdresserAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingHairdresserAdded modelStub = new ModelStubAcceptingHairdresserAdded();
        Hairdresser validHairdresser = new HairdresserBuilder().build();

        CommandResult commandResult = new AddHairdresserCommand(validHairdresser).execute(modelStub);

        assertEquals(String.format(AddHairdresserCommand.MESSAGE_SUCCESS, validHairdresser),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validHairdresser), modelStub.hairdressersAdded);
    }

    @Test
    public void execute_duplicateHairdresser_throwsCommandException() {
        Hairdresser validHairdresser = new HairdresserBuilder().build();
        AddHairdresserCommand addCommand = new AddHairdresserCommand(validHairdresser);
        ModelStub modelStub = new ModelStubWithHairdresser(validHairdresser);

        assertThrows(CommandException.class, AddHairdresserCommand.MESSAGE_DUPLICATE_HAIRDRESSER, ()
            -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Hairdresser alice = new HairdresserBuilder().withName("Alice").build();
        Hairdresser bob = new HairdresserBuilder().withName("Bob").build();
        AddHairdresserCommand addAliceCommand = new AddHairdresserCommand(alice);
        AddHairdresserCommand addBobCommand = new AddHairdresserCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddHairdresserCommand addAliceCommandCopy = new AddHairdresserCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different hairdresser -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getHairStyleXFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setHairStyleXFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addHairdresser(Hairdresser hairdresser) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setHairStyleX(ReadOnlyHairStyleX newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyHairStyleX getHairStyleX() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Client getClientById(ClientId clientId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Hairdresser getHairdresserById(HairdresserId hairdresserId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Appointment getAppointmentById(AppointmentId appointmentId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasClient(Client client) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteClient(Client client) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addClient(Client client) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClient(Client target, Client editedClient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Client> getFilteredClientList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredClientList(Predicate<Client> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasHairdresser(Hairdresser hairdresser) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteHairdresser(Hairdresser target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setHairdresser(Hairdresser target, Hairdresser editedHairdresser) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Hairdresser> getFilteredHairdresserList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredHairdresserList(Predicate<Hairdresser> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAppointment(Appointment appointment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAppointment(Appointment appointment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAppointment(Appointment appointment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAppointment(Appointment target, Appointment changedAppointment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Appointment> getAppointmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Appointment> getFilteredAppointmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Appointment getSelectedAppointment() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedAppointment(Appointment appointment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Appointment> selectedAppointmentProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single hairdresser.
     */
    private class ModelStubWithHairdresser extends ModelStub {
        private final Hairdresser hairdresser;

        ModelStubWithHairdresser(Hairdresser hairdresser) {
            requireNonNull(hairdresser);
            this.hairdresser = hairdresser;
        }

        @Override
        public boolean hasHairdresser(Hairdresser hairdresser) {
            requireNonNull(hairdresser);
            return this.hairdresser.isSame(hairdresser);
        }
    }

    /**
     * A Model stub that always accept the hairdresser being added.
     */
    private class ModelStubAcceptingHairdresserAdded extends ModelStub {
        final ArrayList<Hairdresser> hairdressersAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return hairdressersAdded.stream().anyMatch(person::isSame);
        }

        @Override
        public boolean hasHairdresser(Hairdresser hairdresser) {
            requireNonNull(hairdresser);
            return hairdressersAdded.stream().anyMatch(hairdresser::isSame);
        }

        @Override
        public void addHairdresser(Hairdresser hairdresser) {
            requireNonNull(hairdresser);
            hairdressersAdded.add(hairdresser);
        }

        @Override
        public ReadOnlyHairStyleX getHairStyleX() {
            return new HairStyleX();
        }
    }

}
