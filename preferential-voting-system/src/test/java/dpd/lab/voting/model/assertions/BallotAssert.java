package dpd.lab.voting.model.assertions;

import dpd.lab.voting.model.Ballot;
import dpd.lab.voting.model.Candidate;
import dpd.lab.voting.model.Preference;
import org.assertj.core.api.AbstractAssert;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class BallotAssert extends AbstractAssert<BallotAssert, Ballot> {

    BallotAssert(Ballot ballot) {
        super(ballot, BallotAssert.class);
    }

    public BallotAssert hasTotalVotes(int expectedVotes) {
        assertThat(actual.getVotes().size()).isEqualTo(expectedVotes);

        return myself;
    }

    public BallotAssert hasActiveVote(Candidate candidate) {
        assertThat(actual.getActiveVote().isPresent()).isTrue();
        assertThat(actual.getActiveVote().get()).isEqualTo(candidate);

        return myself;
    }

    public BallotAssert hasCandidate(Candidate candidate) {
        assertThat(actual.ballotContains(candidate)).isTrue();

        return myself;
    }

    public BallotAssert hasPreferenceVotes(Preference... preferences) {
        assertThat(actual.getVotes()).contains(preferences);

        return myself;
    }
}
