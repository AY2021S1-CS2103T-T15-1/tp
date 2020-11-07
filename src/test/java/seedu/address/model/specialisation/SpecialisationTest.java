package seedu.address.model.specialisation;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SpecialisationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Specialisation(null));
    }

    @Test
    public void constructor_invalidSpecialisationName_throwsIllegalArgumentException() {
        String invalidSpecialisationName = "";
        assertThrows(IllegalArgumentException.class, () -> new Specialisation(invalidSpecialisationName));
    }

    @Test
    public void isValidSpecialisationName() {
        // null specialisation name
        assertThrows(NullPointerException.class, () -> Specialisation.isValidSpecialisation(null));
    }

}
