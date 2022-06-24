package dpd.lab.voting;

import com.google.gson.Gson;
import dpd.lab.voting.model.Ballot;
import dpd.lab.voting.model.Candidate;
import dpd.lab.voting.model.ElectionResult;
import dpd.lab.voting.model.PriorityPreference;
import dpd.lab.voting.model.Votes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static dpd.lab.voting.model.assertions.VotingDomainAssertions.assertThat;

public class PreferentialElectionProcessorTest {

    private static Candidate BARACK_OBAMA;
    private static Candidate BILL_CLINTON;
    private static Candidate ABRAHAM_LINCOLN;
    private static Candidate GEORGE_WASHINGTON;

    private static Candidate LENI_ROBREDO;
    private static Candidate RAMON_MAGSAYSAY;
    private static Candidate MANUEL_QUEZON;
    private static Candidate CORY_AQUINO;
    private static Candidate FIDEL_RAMOS;

    private static Candidate JOHN_WICK;
    private static Candidate JACK_REACHER;
    private static Candidate JOHN_MCCLANE;
    private static Candidate VINCENT;

    private static Candidate michaelKeaton;
    private static Candidate valKilmer;
    private static Candidate adamWest;
    private static Candidate christianBale;
    private static Candidate robertPattinson;
    private static Candidate benAffleck;
    private static Candidate legoBatman;

    private static Candidate georgeClooney;
    private static Set<Candidate> batmanCandidates;

    private PreferentialElectionProcessor electionProcessor;

    @BeforeAll
    public static void globalSetUp() {
        michaelKeaton = new Candidate("Michael Keaton");
        valKilmer = new Candidate("Val Kilmer");
        adamWest = new Candidate("Adam West");
        christianBale = new Candidate("Christian Bale");
        robertPattinson = new Candidate("Robert Pattinson");
        benAffleck = new Candidate("Ben Affleck");
        legoBatman = new Candidate("Lego Batman");
        georgeClooney = new Candidate("Goerge Clooney");

        batmanCandidates = new HashSet<>(Arrays.asList(
            michaelKeaton, valKilmer, adamWest, christianBale, robertPattinson, benAffleck, legoBatman, georgeClooney
        ));
    }

    @BeforeEach
    public void setUp() {
        electionProcessor = new PreferentialElectionProcessor();
    }

    @Test
    public void shouldHaveCorrectNumberOfTotalVotesAndWinner() {
        ElectionResult result = electionProcessor.processBallots(ballotListWithFirstRoundWin(), batmanCandidates);

        assertThat(result).hasTotalVotes(Votes.valueOf(9)).winnerIs(christianBale);
        System.out.println(new Gson().toJson(result));
    }

    @Test
    public void shouldHaveCorrectNumberOfTotalVotesWhenSomeBallotsAreInvalid() {
        List<Ballot> ballots = ballotListWithFirstRoundWin();
        ballots.get(3).remove(adamWest);
        ElectionResult result = electionProcessor.processBallots(ballots, batmanCandidates);

        assertThat(result).hasTotalVotes(Votes.valueOf(8));
    }

    private List<Ballot> ballotListWithFirstRoundWin() {
        Ballot firstAnonymousBallot = new Ballot().voteInOrder(
            christianBale, robertPattinson, michaelKeaton, benAffleck, valKilmer, legoBatman, georgeClooney, adamWest
        );
        Ballot secondAnonymousBallot = new Ballot().voteInOrder(
            benAffleck, michaelKeaton, christianBale, valKilmer, robertPattinson, georgeClooney, legoBatman, adamWest
        );
        Ballot thirdAnonymousBallot = new Ballot().voteInOrder(
            robertPattinson, christianBale, michaelKeaton, benAffleck, valKilmer, legoBatman, georgeClooney, adamWest
        );
        Ballot fourthAnonymousBallot = new Ballot().voteInOrder(
            christianBale, michaelKeaton, robertPattinson, benAffleck, valKilmer, legoBatman, georgeClooney, adamWest
        );
        Ballot fifthAnonymousBallot = new Ballot().voteInOrder(
            robertPattinson, christianBale, benAffleck, michaelKeaton, valKilmer, legoBatman, georgeClooney, adamWest
        );
        Ballot sixthAnonymousBallot = new Ballot().voteInOrder(
            michaelKeaton, christianBale, robertPattinson, benAffleck, valKilmer, legoBatman, georgeClooney, adamWest
        );
        Ballot seventhAnonymousBallot = new Ballot().voteInOrder(
            christianBale, robertPattinson, benAffleck, michaelKeaton, valKilmer, legoBatman, georgeClooney, adamWest
        );
        Ballot eighthAnonymousBallot = new Ballot().voteInOrder(
            christianBale, robertPattinson, michaelKeaton, benAffleck, valKilmer, legoBatman, georgeClooney, adamWest
        );
        Ballot ninthAnonymousBallot = new Ballot().voteInOrder(
            christianBale, robertPattinson, benAffleck, michaelKeaton, valKilmer, legoBatman, georgeClooney, adamWest
        );

        return new ArrayList<>(List.of(
            firstAnonymousBallot, secondAnonymousBallot, thirdAnonymousBallot, fourthAnonymousBallot,
            fifthAnonymousBallot, sixthAnonymousBallot, seventhAnonymousBallot, eighthAnonymousBallot,
            ninthAnonymousBallot
        ));
    }
}
