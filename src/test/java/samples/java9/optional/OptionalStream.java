package samples.java9.optional;

import org.junit.Before;
import org.junit.Test;
import samples.domain.Preference;
import samples.domain.PreferenceFactory;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Test shows the same functionality using different versions of Java.
 * Code readability and fluency increases with each version of Java.
 * The scenario is to fetch all preferences into a TreeSet.
 * TreeSet is used only to pretty-print the output, since other Set
 * implementations (which do no guarantee any order) result in erratic
 * assertion failure messages that are not easy to read.
 */
public class OptionalStream {

    private Set<Preference> expected;

    @Before
    public void setUp() {
        expected = new TreeSet<>();
        expected.add(new Preference("ABC", "01_02_03"));
        expected.add(new Preference("DEF", "04_05_06"));
        expected.add(new Preference("XYZ", "24_25_26"));
    }

    @Test
    public void preJava8Example() {
        Set<Preference> preferences = new TreeSet<>();
        for (String preferenceName : PreferenceFactory.PREFERENCE_NAMES) {
            Preference aPreference = PreferenceFactory.findPreference(preferenceName);
            if (aPreference != null) {
                preferences.add(aPreference);
            }
        }
        assertEquals("The two collections should be the same", expected, preferences);
    }

    @Test
    public void java8Example() {
        Set<Preference> preferences =
                PreferenceFactory.PREFERENCE_NAMES.stream()
                        .map(PreferenceFactory::findOptionalPreference)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toSet());
        preferences = new TreeSet<>(preferences);
        assertEquals("The two collections should be the same", expected, preferences);
    }

    @Test
    public void java9Example() {
        Set<Preference> preferences =
                PreferenceFactory.PREFERENCE_NAMES.stream()
                        .map(PreferenceFactory::findOptionalPreference)
                        .flatMap(Optional::stream)
                        .collect(Collectors.toSet());
        preferences = new TreeSet<>(preferences);
        assertEquals("The two collections should be the same", expected, preferences);
    }
}
