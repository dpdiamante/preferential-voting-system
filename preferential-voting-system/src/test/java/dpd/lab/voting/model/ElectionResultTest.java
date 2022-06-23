package dpd.lab.voting.model;

import dpd.lab.voting.exceptions.TotalVotesExceededException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static dpd.lab.voting.model.assertions.VotingDomainAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ElectionResultTest {

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
    public void shouldKeepElectionResultStillValid() {
        ElectionResult electionResult = new ElectionResult(Votes.instanceOf(100))
                .registerVotesFor(LENI_ROBREDO).with(Votes.instanceOf(30))
                .registerVotesFor(BARACK_OBAMA).with(Votes.instanceOf(20))
                .registerVotesFor(ANTHONY_ALBANESE).with(Votes.instanceOf(20));

        assertThat(electionResult).isNotNull();
    }

    @Test
    public void shouldThrowExceptionWhenCountingVotesExceedTotalVotes() {
        assertThatThrownBy(() -> {
            new ElectionResult(Votes.instanceOf(100))
                    .registerVotesFor(LENI_ROBREDO).with(Votes.instanceOf(30))
                    .registerVotesFor(BARACK_OBAMA).with(Votes.instanceOf(20))
                    .registerVotesFor(ANTHONY_ALBANESE).with(Votes.instanceOf(70));
        }).isInstanceOf(TotalVotesExceededException.class)
        .hasMessage("Total counted votes 120 has exceeded total registered votes 100");
    }

    @Test
    public void shouldHaveWinnerIfSomeoneGotMajority() {
        ElectionResult electionResult = new ElectionResult(Votes.instanceOf(100))
                .registerVotesFor(LENI_ROBREDO).with(Votes.instanceOf(60))
                .registerVotesFor(BARACK_OBAMA).with(Votes.instanceOf(20))
                .registerVotesFor(ANTHONY_ALBANESE).with(Votes.instanceOf(20));

        assertThat(electionResult).winnerIs(LENI_ROBREDO);
    }
}
