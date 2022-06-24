package dpd.lab.voting.model;

public class Candidate {

    private final String name;

    public Candidate(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Preference voteWithPreference(PriorityPreference priorityPreference) {
        return new Preference(this, priorityPreference);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }

        if (that == null || getClass() != that.getClass()) {
            return false;
        }

        Candidate thatCandidate = (Candidate) that;

        return name.equals(thatCandidate.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
