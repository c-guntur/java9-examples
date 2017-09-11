package samples.java9.optional;

import org.junit.Test;
import samples.domain.Preference;
import samples.domain.PreferenceFactory;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class OptionalOr {

    private static String FAKE_VALUE = "fakeValue";
    private static String TOP_RATED = "Top rated preference";

    @Test
    public void preJava8Example() {

        //Sunny day scenario, creation succeeds in the factory.
        Preference preferenceAAA = PreferenceFactory.findPreference("AAA");
        if (preferenceAAA == null) {
            preferenceAAA = PreferenceFactory.createPreference("AAA", TOP_RATED);
        }
        if (preferenceAAA == null) {
            preferenceAAA = new Preference("AAA", FAKE_VALUE);
        }

        assertEquals("AAA should map to a saved preference", TOP_RATED, preferenceAAA.getDescription());

        //Rainy day scenario, creation fails in the factory.
        Preference preferenceOOO = PreferenceFactory.findPreference("OOO");
        if (preferenceOOO == null) {
            preferenceOOO = PreferenceFactory.createPreference("OOO", TOP_RATED);
        }
        if (preferenceOOO == null) {
            preferenceOOO = new Preference("OOO", FAKE_VALUE);
        }

        assertEquals("OOO should default to a fake preference", FAKE_VALUE, preferenceOOO.getDescription());
    }

    @Test
    public void java8Example() {
        //Sunny day scenario, creation succeeds in the factory.
        Preference preferenceAAA =
                PreferenceFactory
                        .findOptionalPreference("AAA")
                        .orElseGet(PreferenceFactory.getPreferenceSupplier("AAA", TOP_RATED));
        if (preferenceAAA == null) {
            preferenceAAA = new Preference("AAA", FAKE_VALUE);
        }

        assertEquals("AAA should map to a saved preference", TOP_RATED, preferenceAAA.getDescription());

        //Rainy day scenario, creation fails in the factory.
        Preference preferenceOOO =
                PreferenceFactory
                        .findOptionalPreference("OOO")
                        .orElseGet(PreferenceFactory.getPreferenceSupplier("OOO", TOP_RATED));
        if (preferenceOOO == null) {
            preferenceOOO = new Preference("OOO", FAKE_VALUE);
        }

        assertEquals("OOO should default to a fake preference", FAKE_VALUE, preferenceOOO.getDescription());
    }

    @Test
    public void java9Example() {
        System.out.println("In Java 9 --------------------------------- >>>>");

        //Sunny day scenario, creation succeeds in the factory.
        Optional<Preference> optionalPreferenceAAA =
                PreferenceFactory
                        .findOptionalPreference("AAA")
                        .or(PreferenceFactory.getPreferenceOptionalSupplier("AAA", TOP_RATED));
        Preference preferenceAAA = optionalPreferenceAAA.orElse(new Preference("AAA", FAKE_VALUE));

        assertEquals("AAA should map to a saved preference", TOP_RATED, preferenceAAA.getDescription());

        //Rainy day scenario, creation fails in the factory.
        Optional<Preference> optionalPreferenceOOO =
                PreferenceFactory
                        .findOptionalPreference("OOO")
                        .or(PreferenceFactory.getPreferenceOptionalSupplier("OOO", TOP_RATED));
        Preference preferenceOOO = optionalPreferenceOOO.orElse(new Preference("OOO", FAKE_VALUE));

        assertEquals("OOO should default to a fake preference", FAKE_VALUE, preferenceOOO.getDescription());
    }
}
