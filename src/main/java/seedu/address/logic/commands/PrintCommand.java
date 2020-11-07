package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.client.Client;
import seedu.address.model.person.hairdresser.Hairdresser;
import seedu.address.model.specialisation.Specialisation;
import seedu.address.model.tag.Tag;

/**
 * Copies all appointments, hairdressers and clients into a csv file.
 */
public class PrintCommand extends Command {

    public static final String COMMAND_WORD = "print";

    public static final String MESSAGE_SUCCESS = "CSV files successfully made.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves all appointments, hairdressers and clients"
            + " into 3 separate CSV files.\n"
            + "Parameters: No parameters\n"
            + "Example: " + COMMAND_WORD;

    public static final char CSV_SEPARATOR = ',';

    private ObservableList<Hairdresser> hairdresserList;
    private ObservableList<Client> clientList;
    private ObservableList<Appointment> appointmentList;

    private enum ExportType {
        hairdresser, client, appointment;
    }

    // Solution below adapted from https://github.com/AY2021S1-CS2103T-T15-3/tp/pull/143/files

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        hairdresserList = model.getHairStyleX().getHairdresserList();
        clientList = model.getHairStyleX().getClientList();
        appointmentList = model.getHairStyleX().getAppointmentList();

        Callable<Void> writeAppt = () -> {
            writeToCsv(ExportType.appointment, model);
            return null;
        };

        Callable<Void> writeClient = () -> {
            writeToCsv(ExportType.client, model);
            return null;
        };

        Callable<Void> writeHairdresser = () -> {
            writeToCsv(ExportType.hairdresser, model);
            return null;
        };

        List<Callable<Void>> taskList = new ArrayList<>();
        taskList.add(writeAppt);
        taskList.add(writeClient);
        taskList.add(writeHairdresser);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        try {
            executor.invokeAll(taskList);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        executor.shutdown();

        return new CommandResult(MESSAGE_SUCCESS);
    }

    private void writeToCsv(ExportType type, Model model) {
        String fileName;
        switch (type) {
        case hairdresser:
            fileName = "hairdressers";
            break;
        case appointment:
            fileName = "appointments";
            break;
        case client:
            fileName = "clients";
            break;
        default:
            throw new IllegalArgumentException();
        }

        Path csvFilePath = model.getUserPrefs().getCsvFilePath().resolve(fileName + ".csv");

        try {
            FileUtil.createIfMissing(csvFilePath);
            BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath.toString()));
            appendToWriter(writer, type);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void appendToWriter(BufferedWriter writer, ExportType type) throws IOException {
        switch (type) {
        case hairdresser:
            writer.append(printCreationTime("Hairdresser"));
            writer.append(printHairdresserHeader());
            for (Hairdresser hairdresser : hairdresserList) {
                writer.append(printHairdresser(hairdresser));
            }
            break;
        case appointment:
            writer.append(printCreationTime("Appointment"));
            writer.append(printAppointmentHeader());
            for (Appointment appt : appointmentList) {
                writer.append(printAppointment(appt));
            }
            break;
        case client:
            writer.append(printCreationTime("Client"));
            writer.append(printClientHeader());
            for (Client client : clientList) {
                writer.append(printClient(client));
            }
            break;
        default:
            throw new IllegalArgumentException();
        }

        writer.close();
    }

    private String printHairdresser(Hairdresser hairdresser) {
        String specialisations = hairdresser.getSpecs()
                .stream()
                .map(Specialisation::toString)
                .collect(Collectors.joining());

        return String.valueOf(hairdresser.getId()) + CSV_SEPARATOR
                + hairdresser.getName() + CSV_SEPARATOR
                + hairdresser.getTitle() + CSV_SEPARATOR
                + hairdresser.getGender() + CSV_SEPARATOR
                + hairdresser.getPhone() + CSV_SEPARATOR
                + hairdresser.getEmail() + CSV_SEPARATOR
                + specialisations + CSV_SEPARATOR
                + System.lineSeparator();
    }

    private String printHairdresserHeader() {
        return "ID" + CSV_SEPARATOR
                + "Name" + CSV_SEPARATOR
                + "Title" + CSV_SEPARATOR
                + "Gender" + CSV_SEPARATOR
                + "Phone" + CSV_SEPARATOR
                + "Email" + CSV_SEPARATOR
                + "Specialisations" + CSV_SEPARATOR
                + System.lineSeparator();
    }

    private String printClient(Client client) {
        String tags = client.getTags()
                .stream()
                .map(Tag::toString)
                .collect(Collectors.joining());

        return String.valueOf(client.getId()) + CSV_SEPARATOR
                + client.getName() + CSV_SEPARATOR
                + client.getGender() + CSV_SEPARATOR
                + client.getPhone() + CSV_SEPARATOR
                + client.getEmail() + CSV_SEPARATOR
                + removeCommaConflict(client.getAddress().toString()) + CSV_SEPARATOR
                + tags + CSV_SEPARATOR
                + System.lineSeparator();
    }

    private String printClientHeader() {
        return "ID" + CSV_SEPARATOR
                + "Name" + CSV_SEPARATOR
                + "Gender" + CSV_SEPARATOR
                + "Phone" + CSV_SEPARATOR
                + "Email" + CSV_SEPARATOR
                + "Address" + CSV_SEPARATOR
                + "Tags" + CSV_SEPARATOR
                + System.lineSeparator();
    }

    private String printAppointment(Appointment appointment) {
        return String.valueOf(appointment.getId()) + CSV_SEPARATOR
                + appointment.getClient().getName() + CSV_SEPARATOR
                + appointment.getHairdresser().getName() + CSV_SEPARATOR
                + appointment.getDate() + CSV_SEPARATOR
                + appointment.getTime() + CSV_SEPARATOR
                + appointment.getAppointmentStatus().name().toLowerCase() + CSV_SEPARATOR
                + System.lineSeparator();
    }

    private String printAppointmentHeader() {
        return "ID" + CSV_SEPARATOR
                + "Client Name" + CSV_SEPARATOR
                + "Hairdresser Name" + CSV_SEPARATOR
                + "Date" + CSV_SEPARATOR
                + "Time" + CSV_SEPARATOR
                + "Status" + CSV_SEPARATOR
                + System.lineSeparator();
    }

    private String printCreationTime(String type) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy 'at' HH:mm:ss.");
        Date date = new Date(System.currentTimeMillis());

        return type + " list updated as of " + formatter.format(date)
                + System.lineSeparator() + System.lineSeparator();
    }

    private String removeCommaConflict(String input) {
        if (input.contains(",")) {
            return String.format("\"%s\"", input);
        } else {
            return input;
        }
    }
}
