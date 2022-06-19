package dpd.lab.voting.model.assertions;

import dpd.lab.voting.model.Candidate;
import dpd.lab.voting.model.Preference;
import dpd.lab.voting.model.PriorityPreference;
import org.assertj.core.api.AbstractAssert;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PreferenceAssert extends AbstractAssert<PreferenceAssert, Preference> {

    PreferenceAssert(Preference preference) {
        super(preference, PreferenceAssert.class);
    }

    public PreferenceAssert hasCandidate(Candidate candidate) {
        assertThat(actual.getCandidate()).isEqualTo(candidate);
        return myself;
    }

    public PreferenceAssert hasPreferenceVote(PriorityPreference priority) {
        assertThat(actual.getPriorityPreference()).isEqualTo(priority);
        return myself;
    }
}
