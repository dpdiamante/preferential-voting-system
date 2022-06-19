package dpd.lab.voting.model;

import org.junit.jupiter.api.Test;

import static dpd.lab.voting.model.assertions.VotingDomainAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

public class CandidateTest {

    @Test
    public void shouldReturnEqualCandidatesWithTheSameNames() {
        Candidate johnDoe = new Candidate("John Doe");
        Candidate johnDoeClone = new Candidate("John Doe");

        assertThat(johnDoe).isEqualTo(johnDoeClone);
        assertThat(johnDoe.hashCode()).isEqualTo(johnDoeClone.hashCode());
    }

    @Test
    public void shouldReturnProperPreferenceObject() {
        Candidate johnDoe = new Candidate("John Doe");

        Preference preference = johnDoe.voteWithPreference(PriorityPreference.of(1));
        assertThat(preference)
            .hasCandidate(johnDoe)
            .hasPreferenceVote(PriorityPreference.of(1));
    }
}
