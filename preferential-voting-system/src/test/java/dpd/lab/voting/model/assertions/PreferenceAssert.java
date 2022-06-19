package dpd.lab.voting.model.assertions;

import dpd.lab.voting.model.Candidate;
import dpd.lab.voting.model.Preference;
import org.assertj.core.api.AbstractAssert;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PreferenceAssertions {

    public static class PreferenceAssert extends AbstractAssert<PreferenceAssert, Preference> {

        protected PreferenceAssert(Preference preference) {
            super(preference, PreferenceAssert.class);
        }

        public PreferenceAssert hasCandidate(Candidate candidate) {
            assertThat(actual.getCandidate()).isEqualTo(candidate);
            return myself;
        }

        public PreferenceAssert hasPreferenceVote(int priority) {
            assertThat(actual.getPriorityPreference()).isEqualTo(priority);
            return myself;
        }
    }
}
