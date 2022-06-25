package dpd.lab.voting.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CandidateEliminationQueueTest {

    private static Candidate leniRobredo;
    private static Candidate ramonMagsaysay;
    private static Candidate coryAquino;
    private static Candidate manuelQuezon;
    private static Candidate fidelRamos;
    private static Candidate ninoyAquino;

    @BeforeAll
    public static void setUp() {
        leniRobredo = new Candidate("Leni Robredo");
        ramonMagsaysay = new Candidate("Ramon Magsaysay");
        coryAquino = new Candidate("Cory Aquino");
        manuelQuezon = new Candidate("Manuel Quezon");
        fidelRamos = new Candidate("Fidel Ramos");
        ninoyAquino = new Candidate("Ninoy Aquino");
    }

    @Test
    public void shouldGiveCorrectEliminationCandidateOnFirstRound() {
        List<CandidateVotes> candidateVotes = new ArrayList<>(Arrays.asList(
                new CandidateVotes(coryAquino, Votes.valueOf(80)),
                new CandidateVotes(leniRobredo, Votes.valueOf(100)),
                new CandidateVotes(ramonMagsaysay, Votes.valueOf(90)),
                new CandidateVotes(manuelQuezon, Votes.valueOf(70)),
                new CandidateVotes(fidelRamos, Votes.valueOf(60)),
                new CandidateVotes(ninoyAquino, Votes.valueOf(50))
        ));
        CandidateEliminationQueue queue = new CandidateEliminationQueue();
        queue.update(candidateVotes);

        assertThat(queue.element()).isEqualTo(ninoyAquino);
    }

    @Test
    public void shouldGiveCorrectEliminationCandidateOnSecondRound() {
        List<CandidateVotes> candidateVotes = new ArrayList<>(Arrays.asList(
                new CandidateVotes(coryAquino, Votes.valueOf(80)),
                new CandidateVotes(leniRobredo, Votes.valueOf(100)),
                new CandidateVotes(ramonMagsaysay, Votes.valueOf(90)),
                new CandidateVotes(manuelQuezon, Votes.valueOf(70)),
                new CandidateVotes(fidelRamos, Votes.valueOf(60)),
                new CandidateVotes(ninoyAquino, Votes.valueOf(50))
        ));
        CandidateEliminationQueue queue = new CandidateEliminationQueue();
        queue.update(candidateVotes);
        queue.element();

        List<CandidateVotes> secondRoundCandidateVotes = new ArrayList<>(Arrays.asList(
                new CandidateVotes(coryAquino, Votes.valueOf(95)),
                new CandidateVotes(leniRobredo, Votes.valueOf(100)),
                new CandidateVotes(ramonMagsaysay, Votes.valueOf(90)),
                new CandidateVotes(manuelQuezon, Votes.valueOf(70)),
                new CandidateVotes(fidelRamos, Votes.valueOf(72))
        ));
        queue.update(secondRoundCandidateVotes);
        assertThat(queue.element()).isEqualTo(manuelQuezon);
    }

    @Test
    public void shouldGiveCorrectEliminationCandidateOnThirdRoundWithTie() {
        List<CandidateVotes> candidateVotes = new ArrayList<>(Arrays.asList(
                new CandidateVotes(coryAquino, Votes.valueOf(80)),
                new CandidateVotes(leniRobredo, Votes.valueOf(100)),
                new CandidateVotes(ramonMagsaysay, Votes.valueOf(90)),
                new CandidateVotes(manuelQuezon, Votes.valueOf(70)),
                new CandidateVotes(fidelRamos, Votes.valueOf(60)),
                new CandidateVotes(ninoyAquino, Votes.valueOf(50))
        ));
        CandidateEliminationQueue queue = new CandidateEliminationQueue();
        queue.update(candidateVotes);
        queue.element();

        List<CandidateVotes> secondRoundCandidateVotes = new ArrayList<>(Arrays.asList(
                new CandidateVotes(coryAquino, Votes.valueOf(95)),
                new CandidateVotes(leniRobredo, Votes.valueOf(100)),
                new CandidateVotes(ramonMagsaysay, Votes.valueOf(90)),
                new CandidateVotes(manuelQuezon, Votes.valueOf(70)),
                new CandidateVotes(fidelRamos, Votes.valueOf(72))
        ));
        queue.update(secondRoundCandidateVotes);
        assertThat(queue.element()).isEqualTo(manuelQuezon);

        List<CandidateVotes> thirdRoundCandidateVotes = new ArrayList<>(Arrays.asList(
                new CandidateVotes(coryAquino, Votes.valueOf(96)),
                new CandidateVotes(leniRobredo, Votes.valueOf(100)),
                new CandidateVotes(ramonMagsaysay, Votes.valueOf(96)),
                new CandidateVotes(fidelRamos, Votes.valueOf(99))
        ));

        queue.update(thirdRoundCandidateVotes);
        assertThat(queue.element()).isEqualTo(ramonMagsaysay);
    }
}
