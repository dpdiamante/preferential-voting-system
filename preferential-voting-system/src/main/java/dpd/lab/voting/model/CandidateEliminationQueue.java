package dpd.lab.voting.model;

import dpd.lab.voting.exceptions.VotingException;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CandidateEliminationQueue {

    private List<CandidateVotes> sortedCandidateVotesList;

    public Candidate element() {
        return sortedCandidateVotesList.remove(sortedCandidateVotesList.size() - 1).getCandidate();
    }

    public void update(List<CandidateVotes> candidateVotes) {
        List<CandidateVotes> sorted = candidateVotes.stream()
                .sorted(Comparator.reverseOrder()).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(sortedCandidateVotesList)) {
            sortedCandidateVotesList = sorted;
        } else {
            for (int i = 0; i < sorted.size() - 1; i++) {
                /**
                 * If there's a tie within the sorted list argument, check with existing sorted list and arrange
                 * appropriately
                 */
                CandidateVotes current = sorted.get(i);
                CandidateVotes next = sorted.get(i + 1);

                if (next.getVotes().equals(current.getVotes())) {
                    int untilWhatIndex = i + 1;
                    for (int j = i + 2; j < sorted.size(); j++) {
                        CandidateVotes afterNext = sorted.get(j);

                        if (afterNext.getVotes().equals(current.getVotes())) {
                            untilWhatIndex = j + 1;
                        } else {
                            break;
                        }
                    }

                    List<CandidateVotes> tiedCandidates = sorted.subList(i, untilWhatIndex + 1);
                    List<CandidateVotes> rearrangeCandidates = rearrangeCandidates(tiedCandidates);

                    for (CandidateVotes candidateToMove : rearrangeCandidates) {
                        sorted.remove(i);
                        sorted.add(i, candidateToMove);
                        i++;
                    }
                }
            }

            sortedCandidateVotesList = sorted;
        }
    }

    private List<CandidateVotes> rearrangeCandidates(List<CandidateVotes> tiedCandidates) {
        List<Candidate> arrangedCandidates = sortedCandidateVotesList.stream().filter(candidateVotes ->
            tiedCandidates.stream()
                .map(CandidateVotes::getCandidate)
                .anyMatch(candidate -> candidateVotes.getCandidate().equals(candidate))
        ).collect(Collectors.toList()).stream().sorted(Comparator.reverseOrder())
                .map(CandidateVotes::getCandidate)
                .collect(Collectors.toList());

        List<CandidateVotes> rearrangedCandidateVotes = new ArrayList<>();

        arrangedCandidates.forEach(candidate -> {
            CandidateVotes retrievedCandidateVotes = tiedCandidates.stream()
                    .filter(candidateVotes -> candidateVotes.getCandidate().equals(candidate))
                    .findFirst().orElseThrow(() -> new VotingException("Something went wrong with rearranging"));

            rearrangedCandidateVotes.add(retrievedCandidateVotes);
        });

        return rearrangedCandidateVotes;
    }

}
