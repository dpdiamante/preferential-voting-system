package dpd.lab.voting.model;

public class CandidateVotes implements Comparable<CandidateVotes> {

    private final Candidate candidate;

    private final Votes votes;

    public CandidateVotes(Candidate candidate, Votes votes) {
        this.candidate = candidate;
        this.votes = votes;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public Votes getVotes() {
        return votes;
    }

    @Override
    public int compareTo(CandidateVotes thatCandidateVotes) {
        return votes.getValue().compareTo(thatCandidateVotes.getVotes().getValue());
    }
}
