package dpd.lab.voting.exceptions;

import dpd.lab.voting.model.Candidate;

public class CandidateNotInBallotException extends RuntimeException {

    public CandidateNotInBallotException(Candidate candidate) {
        super(candidate.getName());
    }
}
