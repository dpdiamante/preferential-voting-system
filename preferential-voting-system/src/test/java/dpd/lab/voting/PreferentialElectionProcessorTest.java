package dpd.lab.voting;

import com.google.gson.Gson;
import dpd.lab.voting.model.Ballot;
import dpd.lab.voting.model.Candidate;
import dpd.lab.voting.model.ElectionResult;
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

    private static Candidate leniRobredo;
    private static Candidate ramonMagsaysay;
    private static Candidate manuelQuezon;
    private static Candidate coryAquino;
    private static Candidate fidelRamos;
    private static Set<Candidate> philippinePresidentialCandidates;

    private static Candidate johnWick;
    private static Candidate jackReacher;
    private static Candidate johnMcClane;
    private static Candidate vincent;
    private static Candidate hutchMansell;
    private static Set<Candidate> assassinCandidates;

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

        johnWick = new Candidate("John Wick");
        jackReacher = new Candidate("Jack Reacher");
        johnMcClane = new Candidate("John McClane");
        vincent = new Candidate("Vincent");
        hutchMansell = new Candidate("Hutch Mansell");
        assassinCandidates = new HashSet<>(Arrays.asList(
            johnWick, jackReacher, johnMcClane, vincent
        ));

        /*private static Candidate leniRobredo;
        private static Candidate ramonMagsaysay;
        private static Candidate manuelQuezon;
        private static Candidate coryAquino;
        private static Candidate fidelRamos;
        private static List<Candidate> philippinePresidentialCandidates;*/
        leniRobredo = new Candidate("Leni Robredo");
        ramonMagsaysay = new Candidate("Ramon Magsaysay");
        manuelQuezon = new Candidate("Manuel Quezon");
        coryAquino = new Candidate("Cory Aquino");
        fidelRamos = new Candidate("Fidel Ramos");
        philippinePresidentialCandidates = new HashSet<>(Arrays.asList(
            leniRobredo, ramonMagsaysay, manuelQuezon, coryAquino, fidelRamos
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
        System.out.println(new Gson().toJson(result));
    }

    private List<Ballot> ballotListWithSecondRoundWin() {
        return new ArrayList<>(List.of(
            new Ballot().voteInOrder(johnWick, hutchMansell, jackReacher, vincent, johnMcClane),
            new Ballot().voteInOrder(johnWick, vincent, hutchMansell, johnMcClane, jackReacher),
            new Ballot().voteInOrder(johnWick, jackReacher, vincent, hutchMansell, johnMcClane),
            new Ballot().voteInOrder(johnWick, jackReacher, vincent, hutchMansell, johnMcClane),
            new Ballot().voteInOrder(jackReacher, hutchMansell, johnWick, vincent, johnMcClane),
            new Ballot().voteInOrder(jackReacher, johnWick, hutchMansell, vincent, johnMcClane),
            new Ballot().voteInOrder(hutchMansell, johnWick, jackReacher, vincent, johnMcClane),
            new Ballot().voteInOrder(hutchMansell, jackReacher, johnWick, vincent, johnMcClane),
            new Ballot().voteInOrder(hutchMansell, johnWick, jackReacher, vincent, johnMcClane)
        ));
    }

    private List<Ballot> ballotListWithLowestTieOnFirstRound() {
        return null;
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
