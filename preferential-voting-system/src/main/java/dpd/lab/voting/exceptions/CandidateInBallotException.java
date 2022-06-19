package dpd.lab.voting.exceptions;

import dpd.lab.voting.model.Candidate;

public class CandidateInBallotException extends RuntimeException {

    public CandidateInBallotException(Candidate candidate) {
        super(candidate.getName());
    }
}
