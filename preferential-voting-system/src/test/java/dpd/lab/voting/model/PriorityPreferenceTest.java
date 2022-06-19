package dpd.lab.voting.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PriorityPreferenceTest {

    @Test
    public void samePriorityPreferenceShouldBeEqual() {
        PriorityPreference priority = new PriorityPreference(2);
        PriorityPreference anotherPriority = new PriorityPreference(2);

        assertThat(priority).isEqualTo(anotherPriority);
        assertThat(priority.hashCode()).isEqualTo(anotherPriority.hashCode());
    }

    @Test
    public void differentPriorityPreferenceShouldNotBeEqual() {
        PriorityPreference priority = new PriorityPreference(2);
        PriorityPreference anotherPriority = new PriorityPreference(3);

        assertThat(priority).isNotEqualTo(anotherPriority);
        assertThat(priority.hashCode()).isNotEqualTo(anotherPriority.hashCode());
    }
}
