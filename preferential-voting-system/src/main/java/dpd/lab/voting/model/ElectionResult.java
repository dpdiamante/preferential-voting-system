package dpd.lab.voting.model;

import dpd.lab.voting.exceptions.TotalVotesExceededException;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ElectionResult {

    private ElectionRounds electionRounds;

    private final Votes totalVotes;

    private Map<Candidate, Votes> votePortions;

    public ElectionResult(Votes totalVotes) {
        this.votePortions = new HashMap<>();
        this.totalVotes = totalVotes;
    }

    public ElectionResultCandidateRegister registerVotesFor(Candidate candidate) {
        return new ElectionResultCandidateRegister(this, candidate);
    }

    public void setElectionRounds(ElectionRounds rounds) {
        this.electionRounds = rounds;
    }

    public ElectionRounds getElectionRounds() {
        return this.electionRounds;
    }

    public Votes getTotalVotes() {
        return totalVotes;
    }

    public Optional<Votes> getWinningVotes() {
        int highestVote = votePortions.values().stream()
                .map(Votes::getValue)
                .max(Comparator.naturalOrder()).orElse(0);

        if (highestVote > totalVotes.getValue() / 2) {
            return Optional.of(Votes.valueOf(highestVote));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Candidate> getWinner() {
        Optional<Votes> winningVotes = getWinningVotes();

        if (winningVotes.isEmpty()) {
            return Optional.empty();
        }

        return votePortions.entrySet().stream()
                .filter(candidateVotesEntry -> candidateVotesEntry.getValue().equals(winningVotes.get()))
                .findFirst()
                .map(Map.Entry::getKey);
    }

    public Optional<Votes> getVotesFor(Candidate candidate) {
        return Optional.ofNullable(votePortions.get(candidate));
    }

    public static class ElectionResultCandidateRegister {

        private final ElectionResult electionResult;

        private final Candidate candidate;

        public ElectionResultCandidateRegister(ElectionResult electionResult, Candidate candidate) {
            this.electionResult = electionResult;
            this.candidate = candidate;
        }

        public ElectionResult with(Votes votes) {
            electionResult.votePortions.put(candidate, votes);

            int totalCountedVotes = electionResult.votePortions.values().stream()
                    .mapToInt(TinyType::getValue).sum();

            if (totalCountedVotes > electionResult.totalVotes.getValue()) {
                throw new TotalVotesExceededException(electionResult.totalVotes, totalCountedVotes);
            }

            return electionResult;
        }
    }
}
