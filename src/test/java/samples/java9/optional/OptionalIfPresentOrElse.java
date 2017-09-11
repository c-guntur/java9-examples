package samples.java9.optional;

import org.junit.Before;
import org.junit.Test;
import samples.domain.Preference;
import samples.domain.PreferenceFactory;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

/**
 * Test shows the same functionality using different versions of Java.
 * Lines of code progressively decrease with each increasing version of Java.
 * The scenario is to fetch a preference and get the description.
 * If the preference does not exist, or the description is null,
 * then set the description value to FAKE_VALUE.
 */
public class OptionalIfPresentOrElse {

    private static String FAKE_VALUE = "fakeValue";

    private String description;

    @Before
    public void setUp() {
        description = null;
    }

    @Test
    public void preJava8Example() {
        Preference preferenceABC = PreferenceFactory.findPreference("ABC");
        if (preferenceABC != null) {
            description = preferenceABC.getDescription();
        }
        if (description == null) {
            description = FAKE_VALUE;
        }
        assertEquals("ABC should map to a valid preference", "01_02_03", description);

        // Reset the description value.
        description = null;

        Preference preferenceOOO = PreferenceFactory.findPreference("OOO");
        if (preferenceOOO != null) {
            description = preferenceOOO.getDescription();
        }
        if (description == null) {
            description = FAKE_VALUE;
        }
        assertEquals("OOO should not map to a valid preference", FAKE_VALUE, description);
    }

    @Test
    public void java8Example() {
        Consumer<Preference> preferenceAction = preference -> description = preference.getDescription();
        Function<String, String> fakeValueGenerator = s -> s == null ? FAKE_VALUE : s;

        Optional<Preference> optionalPreferenceABC = PreferenceFactory.findOptionalPreference("ABC");
        optionalPreferenceABC.ifPresent(preferenceAction);
        description = fakeValueGenerator.apply(description);
        assertEquals("ABC should map to a valid preference", "01_02_03", description);

        // Reset the description value.
        description = null;

        Optional<Preference> optionalPreferenceOOO = PreferenceFactory.findOptionalPreference("OOO");
        optionalPreferenceOOO.ifPresent(preferenceAction);
        description = fakeValueGenerator.apply(description);
        assertEquals("OOO should not map to a valid preference", FAKE_VALUE, description);
    }

    /*
     * No need to reset the description, since either presence or absence triggers an action to set a value.
     */
    @Test
    public void java9Example() {
        Consumer<Preference> preferenceAction = preference -> description = preference.getDescription();
        Runnable alternateAction = () -> description = "fakeValue";

        Optional<Preference> optionalPreferenceABC = PreferenceFactory.findOptionalPreference("ABC");
        optionalPreferenceABC.ifPresentOrElse(preferenceAction, alternateAction);
        assertEquals("ABC should map to a valid preference", "01_02_03", description);

        Optional<Preference> optionalPreferenceOOO = PreferenceFactory.findOptionalPreference("OOO");
        optionalPreferenceOOO.ifPresentOrElse(preferenceAction, alternateAction);
        assertEquals("OOO should not map to a valid preference", FAKE_VALUE, description);
    }
}
