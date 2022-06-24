package dpd.lab.voting.model;

import dpd.lab.voting.exceptions.TotalVotesExceededException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static dpd.lab.voting.model.assertions.VotingDomainAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ElectionResultTest {

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
    public void shouldKeepElectionResultStillValid() {
        ElectionResult electionResult = new ElectionResult(Votes.valueOf(100))
                .registerVotesFor(leniRobredo).with(Votes.valueOf(30))
                .registerVotesFor(barackObama).with(Votes.valueOf(20))
                .registerVotesFor(anthonyAlbanese).with(Votes.valueOf(20));

        assertThat(electionResult).isNotNull()
                .withVotes(leniRobredo, Votes.valueOf(30))
                .withVotes(barackObama, Votes.valueOf(20))
                .withVotes(anthonyAlbanese, Votes.valueOf(20))
                .hasLosers(barackObama, anthonyAlbanese);
    }

    @Test
    public void shouldProperlyIncrementVotes() {
        ElectionResult electionResult = new ElectionResult(Votes.valueOf(100))
                .registerVotesFor(leniRobredo).with(Votes.valueOf(30))
                .registerVotesFor(barackObama).with(Votes.valueOf(20))
                .registerVotesFor(anthonyAlbanese).with(Votes.valueOf(20));

        electionResult.addVoteFor(leniRobredo);

        assertThat(electionResult).isNotNull()
                .withVotes(leniRobredo, Votes.valueOf(31))
                .withVotes(barackObama, Votes.valueOf(20))
                .withVotes(anthonyAlbanese, Votes.valueOf(20));
    }

    @Test
    public void shouldThrowExceptionWhenCountingVotesExceedTotalVotes() {
        assertThatThrownBy(() -> {
            new ElectionResult(Votes.valueOf(100))
                    .registerVotesFor(leniRobredo).with(Votes.valueOf(30))
                    .registerVotesFor(barackObama).with(Votes.valueOf(20))
                    .registerVotesFor(anthonyAlbanese).with(Votes.valueOf(70));
        }).isInstanceOf(TotalVotesExceededException.class)
        .hasMessage("Total counted votes 120 has exceeded total registered votes 100");
    }

    @Test
    public void shouldHaveWinnerIfSomeoneGotMajority() {
        ElectionResult electionResult = new ElectionResult(Votes.valueOf(100))
                .registerVotesFor(leniRobredo).with(Votes.valueOf(60))
                .registerVotesFor(barackObama).with(Votes.valueOf(20))
                .registerVotesFor(anthonyAlbanese).with(Votes.valueOf(20));

        assertThat(electionResult).winnerIs(leniRobredo).hasWinningVotes(Votes.valueOf(60));
    }
}
