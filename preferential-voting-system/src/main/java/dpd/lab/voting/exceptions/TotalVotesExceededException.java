package dpd.lab.voting.exceptions;

import dpd.lab.voting.model.Votes;

public class TotalVotesExceededException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Total counted votes %d has exceeded total registered votes %d";

    public TotalVotesExceededException(Votes totalVotes, int countedVotes) {
        super(String.format(ERROR_MESSAGE, countedVotes, totalVotes.getValue()));
    }
}
