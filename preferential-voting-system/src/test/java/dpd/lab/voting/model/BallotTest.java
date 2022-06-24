package dpd.lab.voting.model;

import dpd.lab.voting.exceptions.CandidateNotInBallotException;
import dpd.lab.voting.exceptions.PreferencePriorityTakenException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static dpd.lab.voting.model.assertions.VotingDomainAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BallotTest {

    private static Candidate leniRobredo;

    private static Candidate barackObama;

    private static Candidate anthonyAlbanese;

    @BeforeAll
    public static void setUp() {
        leniRobredo = new Candidate("Leni Robredo");
        barackObama = new Candidate("Barack Obama");
        anthonyAlbanese = new Candidate("Anthony Albanese");
    }

    @Test
    public void shouldBuildBallotWithCandidate() {
        Ballot myBallot = new Ballot().vote(leniRobredo).withPreference(1);

        assertThat(myBallot).isNotNull();
        assertThat(myBallot.ballotContains(leniRobredo)).isTrue();
    }

    @Test
    public void shouldFindCandidateWithPreference() {
        Ballot myBallot = new Ballot().vote(barackObama).withPreference(1)
                .vote(leniRobredo).withPreference(2);

        assertThat(myBallot.withPreference(PriorityPreference.of(2)).isPresent()).isTrue();
        assertThat(myBallot.withPreference(PriorityPreference.of(2)).get()).isEqualTo(leniRobredo);
    }

    @Test
    public void shouldReturnCorrectActiveCandidate() {
        Ballot myBallot = new Ballot().vote(barackObama).withPreference(1)
            .vote(leniRobredo).withPreference(2)
            .vote(anthonyAlbanese).withPreference(3);

        myBallot.movePriority();
        assertThat(myBallot).hasActiveVote(leniRobredo);
    }

    @Test
    public void shouldBuildBallotWithMultipleCandidates() {
        Ballot myBallot = new Ballot().vote(barackObama).withPreference(1)
                .vote(leniRobredo).withPreference(2);

        assertThat(myBallot).hasTotalVotes(2)
                .hasCandidate(leniRobredo)
                .hasCandidate(barackObama)
                .hasPreferenceVotes(
                        new Preference(leniRobredo, PriorityPreference.of(2)),
                        new Preference(barackObama, PriorityPreference.of(1))
                );
    }

    @Test
    public void shouldBuildBallotWithMultipleCandidatesInProperOrderOfPreference() {
        Ballot myBallot = new Ballot().voteInOrder(barackObama, leniRobredo, anthonyAlbanese);

        assertThat(myBallot).hasTotalVotes(3)
                .hasCandidate(leniRobredo)
                .hasCandidate(barackObama)
                .hasCandidate(anthonyAlbanese)
                .hasPreferenceVotes(
                        new Preference(anthonyAlbanese, PriorityPreference.of(3)),
                        new Preference(leniRobredo, PriorityPreference.of(2)),
                        new Preference(barackObama, PriorityPreference.of(1))
                );
    }

    @Test
    public void shouldChangeCandidatePreferencePriority() {
        Ballot myBallot = new Ballot().vote(barackObama).withPreference(1)
                .vote(leniRobredo).withPreference(2);
        myBallot.changeCandidatePreference(barackObama).withPreference(3)
                .changeCandidatePreference(leniRobredo).withPreference(1)
                .vote(anthonyAlbanese).withPreference(2);

        assertThat(myBallot).hasTotalVotes(3)
                .hasPreferenceVotes(
                    new Preference(leniRobredo, PriorityPreference.of(1)),
                    new Preference(barackObama, PriorityPreference.of(3)),
                    new Preference(anthonyAlbanese, PriorityPreference.of(2))
                );
    }

    @Test
    public void shouldSwapCandidatePreference() {
        Ballot myBallot = new Ballot().vote(barackObama).withPreference(1)
                .vote(leniRobredo).withPreference(2);

        myBallot.swapPreferences(leniRobredo, barackObama)
                .vote(anthonyAlbanese).withPreference(3);

        assertThat(myBallot).hasTotalVotes(3)
                .hasPreferenceVotes(
                        new Preference(leniRobredo, PriorityPreference.of(1)),
                        new Preference(barackObama, PriorityPreference.of(2))
                );
    }

    @Test
    public void shouldRemoveCandidateFromBallot() {
        Ballot myBallot = new Ballot().vote(barackObama).withPreference(1)
                .vote(leniRobredo).withPreference(2)
                .vote(anthonyAlbanese).withPreference(3);

        myBallot.remove(anthonyAlbanese);

        assertThat(myBallot).hasTotalVotes(2)
                .hasPreferenceVotes(
                        new Preference(leniRobredo, PriorityPreference.of(2)),
                        new Preference(barackObama, PriorityPreference.of(1))
                );
    }

    @Test
    public void shouldNotAddVoteForTakenPriority() {
        assertThatThrownBy(() ->
            new Ballot().vote(barackObama).withPreference(1).vote(leniRobredo).withPreference(1)
        ).isInstanceOf(PreferencePriorityTakenException.class);
    }

    @Test
    public void shouldNotChangePriorityForTakenPriority() {
        assertThatThrownBy(() ->
            new Ballot().vote(barackObama).withPreference(1).vote(leniRobredo).withPreference(2)
                    .changeCandidatePreference(leniRobredo).withPreference(1)
        ).isInstanceOf(PreferencePriorityTakenException.class);
    }

    @Test
    public void shouldNotSwapWithNonExistingCandidates() {
        assertThatThrownBy(() ->
                new Ballot().vote(barackObama).withPreference(1)
                        .vote(leniRobredo).withPreference(2)
                        .swapPreferences(leniRobredo, anthonyAlbanese)
            ).isInstanceOf(CandidateNotInBallotException.class);
    }
}
