package dpd.lab.voting.model;

import dpd.lab.voting.exceptions.CandidateNotInBallotException;
import dpd.lab.voting.exceptions.PreferencePriorityTakenException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static dpd.lab.voting.model.assertions.VotingDomainAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BallotTest {

    private static Candidate LENI_ROBREDO;

    private static Candidate BARACK_OBAMA;

    private static Candidate ANTHONY_ALBANESE;

    @BeforeAll
    public static void setUp() {
        LENI_ROBREDO = new Candidate("Leni Robredo");
        BARACK_OBAMA = new Candidate("Barack Obama");
        ANTHONY_ALBANESE = new Candidate("Anthony Albanese");
    }

    @Test
    public void shouldBuildBallotWithCandidate() {
        Ballot myBallot = new Ballot().vote(LENI_ROBREDO).withPreference(1);

        assertThat(myBallot).isNotNull();
        assertThat(myBallot.ballotContains(LENI_ROBREDO)).isTrue();
    }

    @Test
    public void shouldBuildBallotWithMultipleCandidates() {
        Ballot myBallot = new Ballot().vote(BARACK_OBAMA).withPreference(1)
                .vote(LENI_ROBREDO).withPreference(2);

        assertThat(myBallot).hasTotalVotes(2)
                .hasCandidate(LENI_ROBREDO)
                .hasCandidate(BARACK_OBAMA)
                .hasPreferenceVotes(
                        new Preference(LENI_ROBREDO, PriorityPreference.of(2)),
                        new Preference(BARACK_OBAMA, PriorityPreference.of(1))
                );
    }

    @Test
    public void shouldChangeCandidatePreferencePriority() {
        Ballot myBallot = new Ballot().vote(BARACK_OBAMA).withPreference(1)
                .vote(LENI_ROBREDO).withPreference(2);
        myBallot.changeCandidatePreference(BARACK_OBAMA).withPreference(3)
                .changeCandidatePreference(LENI_ROBREDO).withPreference(1)
                .vote(ANTHONY_ALBANESE).withPreference(2);

        assertThat(myBallot).hasTotalVotes(3)
                .hasPreferenceVotes(
                    new Preference(LENI_ROBREDO, PriorityPreference.of(1)),
                    new Preference(BARACK_OBAMA, PriorityPreference.of(3)),
                    new Preference(ANTHONY_ALBANESE, PriorityPreference.of(2))
                );
    }

    @Test
    public void shouldSwapCandidatePreference() {
        Ballot myBallot = new Ballot().vote(BARACK_OBAMA).withPreference(1)
                .vote(LENI_ROBREDO).withPreference(2);

        myBallot.swapPreferences(LENI_ROBREDO, BARACK_OBAMA)
                .vote(ANTHONY_ALBANESE).withPreference(3);

        assertThat(myBallot).hasTotalVotes(3)
                .hasPreferenceVotes(
                        new Preference(LENI_ROBREDO, PriorityPreference.of(1)),
                        new Preference(BARACK_OBAMA, PriorityPreference.of(2))
                );
    }

    @Test
    public void shouldRemoveCandidateFromBallot() {
        Ballot myBallot = new Ballot().vote(BARACK_OBAMA).withPreference(1)
                .vote(LENI_ROBREDO).withPreference(2)
                .vote(ANTHONY_ALBANESE).withPreference(3);

        myBallot.remove(ANTHONY_ALBANESE);

        assertThat(myBallot).hasTotalVotes(2)
                .hasPreferenceVotes(
                        new Preference(LENI_ROBREDO, PriorityPreference.of(2)),
                        new Preference(BARACK_OBAMA, PriorityPreference.of(1))
                );
    }

    @Test
    public void shouldNotAddVoteForTakenPriority() {
        assertThatThrownBy(() ->
            new Ballot().vote(BARACK_OBAMA).withPreference(1).vote(LENI_ROBREDO).withPreference(1)
        ).isInstanceOf(PreferencePriorityTakenException.class);
    }

    @Test
    public void shouldNotChangePriorityForTakenPriority() {
        assertThatThrownBy(() ->
            new Ballot().vote(BARACK_OBAMA).withPreference(1).vote(LENI_ROBREDO).withPreference(2)
                    .changeCandidatePreference(LENI_ROBREDO).withPreference(1)
        ).isInstanceOf(PreferencePriorityTakenException.class);
    }

    @Test
    public void shouldNotSwapWithNonExistingCandidates() {
        assertThatThrownBy(() ->
                new Ballot().vote(BARACK_OBAMA).withPreference(1)
                        .vote(LENI_ROBREDO).withPreference(2)
                        .swapPreferences(LENI_ROBREDO, ANTHONY_ALBANESE)
            ).isInstanceOf(CandidateNotInBallotException.class);
    }
}
