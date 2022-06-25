package dpd.lab.voting.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class CandidateVotesTest {

    @Test
    public void shouldBeSortedProperly() {
        CandidateVotes leniRobredoVotes =
                new CandidateVotes(new Candidate("Leni Robredo"), Votes.valueOf(300));
        CandidateVotes ramonMagsaysayVotes =
                new CandidateVotes(new Candidate("Ramon Magsaysay"),Votes.valueOf(250));
        CandidateVotes manuelQuezonVotes =
                new CandidateVotes(new Candidate("Manuel Quezon"), Votes.valueOf(260));

        List<CandidateVotes> candidateVotesList = Arrays.asList(
                leniRobredoVotes, ramonMagsaysayVotes, manuelQuezonVotes
        );

        List<CandidateVotes> sortedList = candidateVotesList.stream().sorted().collect(Collectors.toList());
        assertThat(sortedList.get(0)).isEqualTo(ramonMagsaysayVotes);
    }

    @Test
    public void shouldBeSortedProperlyInReverse() {
        CandidateVotes leniRobredoVotes =
                new CandidateVotes(new Candidate("Leni Robredo"), Votes.valueOf(300));
        CandidateVotes ramonMagsaysayVotes =
                new CandidateVotes(new Candidate("Ramon Magsaysay"),Votes.valueOf(250));
        CandidateVotes manuelQuezonVotes =
                new CandidateVotes(new Candidate("Manuel Quezon"), Votes.valueOf(260));

        List<CandidateVotes> candidateVotesList = Arrays.asList(
                leniRobredoVotes, ramonMagsaysayVotes, manuelQuezonVotes
        );

        List<CandidateVotes> sortedList = candidateVotesList.stream()
                .sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        assertThat(sortedList.get(sortedList.size() - 1)).isEqualTo(ramonMagsaysayVotes);
    }
}
