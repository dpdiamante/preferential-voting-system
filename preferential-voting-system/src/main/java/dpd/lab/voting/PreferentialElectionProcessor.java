package dpd.lab.voting;

import dpd.lab.voting.model.Ballot;
import dpd.lab.voting.model.Candidate;
import dpd.lab.voting.model.ElectionResult;
import dpd.lab.voting.model.Votes;

import java.util.List;
import java.util.Set;

public class PreferentialElectionProcessor {

    public ElectionResult processBallots(List<Ballot> ballots, Set<Candidate> candidates) {
        ballots.removeIf(ballot -> !validBallot(ballot, candidates));

        ElectionResult electionResult = new ElectionResult(Votes.valueOf(ballots.size()));

        return electionResult;
    }

    private boolean validBallot(Ballot ballot, Set<Candidate> candidates) {
        return candidates.stream().anyMatch(candidate -> !ballot.ballotContains(candidate));
    }
}
