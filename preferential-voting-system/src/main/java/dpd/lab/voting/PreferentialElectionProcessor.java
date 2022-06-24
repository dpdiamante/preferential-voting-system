package dpd.lab.voting;

import dpd.lab.voting.exceptions.VotingException;
import dpd.lab.voting.model.Ballot;
import dpd.lab.voting.model.Candidate;
import dpd.lab.voting.model.ElectionResult;
import dpd.lab.voting.model.ElectionRounds;
import dpd.lab.voting.model.Preference;
import dpd.lab.voting.model.PriorityPreference;
import dpd.lab.voting.model.Votes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PreferentialElectionProcessor {

    public ElectionResult processBallots(List<Ballot> ballots, Set<Candidate> candidates) {
        ballots.removeIf(ballot -> invalidBallot(ballot, candidates));
        ElectionResult electionResult = new ElectionResult(Votes.valueOf(ballots.size()));
        Map<Integer, Set<Candidate>> lowestPerRound = new HashMap<>();

        for (int i = 0; i < candidates.size(); i++) {
            runRound(electionResult, ballots, i + 1);

            if (electionResult.getWinner().isPresent()) {
                break;
            }
        }

        return electionResult;
    }

    private void runRound(ElectionResult result, List<Ballot> ballots, int round) {
        ballots.forEach(ballot -> {
            Candidate candidate = ballot.withPreference(PriorityPreference.of(round))
                .orElseThrow(() -> new VotingException("A ballot seems to be invalid"));

            result.addVoteFor(candidate);
            result.setElectionRounds(ElectionRounds.valueOf(round));
        });
    }

    private boolean invalidBallot(Ballot ballot, Set<Candidate> candidates) {
        Set<Candidate> ballotCandidates = ballot.getVotes().stream()
                .map(Preference::getCandidate).collect(Collectors.toSet());

        return !ballotCandidates.equals(candidates);
    }
}
