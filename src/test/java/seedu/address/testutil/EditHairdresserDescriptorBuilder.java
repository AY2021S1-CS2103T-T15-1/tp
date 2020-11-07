package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.hairdresser.EditHairdresserCommand.EditHairdresserDescriptor;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.hairdresser.Hairdresser;
import seedu.address.model.person.hairdresser.Title;
import seedu.address.model.specialisation.Specialisation;

/**
 * A utility class to help with building EditHairdresserDescriptor objects.
 */
public class EditHairdresserDescriptorBuilder {
    private EditHairdresserDescriptor descriptor;

    public EditHairdresserDescriptorBuilder() {
        descriptor = new EditHairdresserDescriptor();
    }

    public EditHairdresserDescriptorBuilder(EditHairdresserDescriptor descriptor) {
        this.descriptor = new EditHairdresserDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditHairdresserDescriptor} with fields containing {@code hairdresser}'s details
     */
    public EditHairdresserDescriptorBuilder(Hairdresser hairdresser) {
        descriptor = new EditHairdresserDescriptor();
        descriptor.setName(hairdresser.getName());
        descriptor.setPhone(hairdresser.getPhone());
        descriptor.setEmail(hairdresser.getEmail());
        descriptor.setTitle(hairdresser.getTitle());
        descriptor.setGender(hairdresser.getGender());
        descriptor.setSpecs(hairdresser.getSpecs());
    }

    /**
     * Sets the {@code Name} of the {@code EditHairdresserDescriptor} that we are building.
     */
    public EditHairdresserDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditHairdresserDescriptor} that we are building.
     */
    public EditHairdresserDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditHairdresserDescriptor} that we are building.
     */
    public EditHairdresserDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Title} of the {@code EditHairdresserDescriptor} that we are building.
     */
    public EditHairdresserDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(new Title(title));
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code EditHairdresserDescriptor} that we are building.
     */
    public EditHairdresserDescriptorBuilder withGender(String gender) {
        descriptor.setGender(new Gender(gender));
        return this;
    }

    /**
     * Parses the {@code specs} into a {@code Set<Specialisation>} and set it to the {@code EditHairdresserDescriptor}
     * that we are building.
     */
    public EditHairdresserDescriptorBuilder withSpecs(String... specs) {
        Set<Specialisation> tagSet = Stream.of(specs).map(Specialisation::new).collect(Collectors.toSet());
        descriptor.setSpecs(tagSet);
        return this;
    }

    public EditHairdresserDescriptor build() {
        return descriptor;
    }
}

