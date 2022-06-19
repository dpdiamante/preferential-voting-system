package dpd.lab.voting.model.assertions;

import dpd.lab.voting.model.Ballot;
import dpd.lab.voting.model.Preference;

public class VotingDomainAssertions {

    public static PreferenceAssert assertThat(Preference preference) {
        return new PreferenceAssert(preference);
    }

    public static BallotAssert assertThat(Ballot ballot) {
        return new BallotAssert(ballot);
    }
}
