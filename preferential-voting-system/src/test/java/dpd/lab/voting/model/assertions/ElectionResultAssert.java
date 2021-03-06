package dpd.lab.voting.model.assertions;

import dpd.lab.voting.model.Candidate;
import dpd.lab.voting.model.ElectionResult;
import dpd.lab.voting.model.ElectionRounds;
import dpd.lab.voting.model.Votes;
import org.assertj.core.api.AbstractAssert;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class ElectionResultAssert extends AbstractAssert<ElectionResultAssert, ElectionResult>  {

    protected ElectionResultAssert(ElectionResult electionResult) {
        super(electionResult, ElectionResultAssert.class);
    }

    public ElectionResultAssert winnerIs(Candidate candidate) {
        assertThat(actual.getWinner().isPresent()).as("There is no election winner").isTrue();
        assertThat(actual.getWinner().get()).isEqualTo(candidate);

        return myself;
    }

    public ElectionResultAssert hasLosers(Candidate... candidates) {
        assertThat(actual.getLowestVotes()).isEqualTo(new HashSet<>(Arrays.asList(candidates)));

        return myself;
    }

    public ElectionResultAssert withElectionRounds(ElectionRounds electionRounds) {
        assertThat(actual.getElectionRounds()).isEqualTo(electionRounds);

        return myself;
    }

    public ElectionResultAssert hasWinningVotes(Votes votes) {
        assertThat(actual.getWinningVotes().isPresent()).isTrue();
        assertThat(actual.getWinningVotes().get()).isEqualTo(votes);

        return myself;
    }

    public ElectionResultAssert withVotes(Candidate candidate, Votes votes) {
        assertThat(actual.getVotesFor(candidate).isPresent()).isTrue();
        assertThat(actual.getVotesFor(candidate).get()).isEqualTo(votes);

        return myself;
    }

    public ElectionResultAssert hasTotalVotes(Votes votes) {
        assertThat(actual.getTotalVotes()).isEqualTo(votes);

        return myself;
    }
}
