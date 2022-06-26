package dpd.lab.voting;

import dpd.lab.voting.exceptions.VotingException;
import dpd.lab.voting.model.Ballot;
import dpd.lab.voting.model.Candidate;
import dpd.lab.voting.model.CandidateEliminationQueue;
import dpd.lab.voting.model.ElectionResult;
import dpd.lab.voting.model.ElectionRounds;
import dpd.lab.voting.model.Preference;
import dpd.lab.voting.model.PriorityPreference;
import dpd.lab.voting.model.Votes;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class PreferentialElectionProcessor {

    public ElectionResult processBallots(List<Ballot> ballots, Set<Candidate> candidates) {
        ballots.removeIf(ballot -> invalidBallot(ballot, candidates));
        ElectionResult electionResult = new ElectionResult(Votes.valueOf(ballots.size()));
        CandidateEliminationQueue eliminationQueue = new CandidateEliminationQueue();

        List<Ballot> ballotsToProcess = ballots;

        for (int i = 0; i < candidates.size(); i++) {
            if (!eliminationQueue.isEmpty()) {
                Candidate toBeRemoved = eliminationQueue.element();

                ballotsToProcess = ballots.stream()
                    .filter(ballot ->
                            ballot.getActiveVote().isPresent() && ballot.getActiveVote().get().equals(toBeRemoved)
                    ).collect(Collectors.toList());
                ballotsToProcess.forEach(Ballot::movePriority);
                electionResult.registerVotesFor(toBeRemoved).with(Votes.valueOf(0));
                ballots.forEach(ballot -> ballot.remove(toBeRemoved));
            }

            runRound(electionResult, ballotsToProcess);

            if (electionResult.getWinner().isPresent()) {
                electionResult.setElectionRounds(ElectionRounds.valueOf(i + 1));
                break;
            }

            eliminationQueue.update(electionResult.getCandidateVotes());
            /*electionResult.getLowestVotes().stream()
                    .filter(losingCandidate -> !losersQueue.contains(losingCandidate))
                    .forEach(losersQueue::add);*/
        }

        return electionResult;
    }

    private void runRound(ElectionResult result, List<Ballot> ballots) {
        ballots.forEach(ballot -> {
            Candidate candidate = ballot.getActiveVote()
                .orElseThrow(() -> new VotingException("A ballot seems to be invalid"));

            result.addVoteFor(candidate);
        });
    }

    private boolean invalidBallot(Ballot ballot, Set<Candidate> candidates) {
        Set<Candidate> ballotCandidates = ballot.getVotes().stream()
                .map(Preference::getCandidate).collect(Collectors.toSet());

        return !ballotCandidates.equals(candidates);
    }
}
