package dpd.lab.voting.model.assertions;

import dpd.lab.voting.model.Candidate;
import dpd.lab.voting.model.ElectionResult;
import org.assertj.core.api.AbstractAssert;

import static org.assertj.core.api.Assertions.assertThat;

public class ElectionResultAssert extends AbstractAssert<ElectionResultAssert, ElectionResult>  {

    protected ElectionResultAssert(ElectionResult electionResult) {
        super(electionResult, ElectionResultAssert.class);
    }

    public ElectionResultAssert winnerIs(Candidate candidate) {
        assertThat(actual.getWinner().isPresent()).isTrue();
        assertThat(actual.getWinner().get()).isEqualTo(candidate);

        return myself;
    }
}
