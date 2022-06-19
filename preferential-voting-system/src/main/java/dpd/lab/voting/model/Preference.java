package dpd.lab.voting.model;

public class Preference {

    private final PriorityPreference priorityPreference;

    private final Candidate candidate;

    public Preference(Candidate candidate, PriorityPreference priorityPreference) {
        this.candidate = candidate;
        this.priorityPreference = priorityPreference;
    }

    public PriorityPreference getPriorityPreference() {
        return priorityPreference;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    @Override
    public boolean equals(Object that) {

        if (this == that) {
            return true;
        }

        if (that == null || getClass() != that.getClass()) {
            return false;
        }

        Preference thatPreference = (Preference) that;

        if (!priorityPreference.equals(thatPreference.priorityPreference)) {
            return false;
        }

        return candidate.equals(thatPreference.candidate);
    }

    @Override
    public int hashCode() {
        int result = priorityPreference.getValue();
        result = 31 * result + candidate.hashCode();
        return result;
    }
}
