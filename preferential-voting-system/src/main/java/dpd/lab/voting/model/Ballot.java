package dpd.lab.voting.model;

import dpd.lab.voting.exceptions.CandidateInBallotException;
import dpd.lab.voting.exceptions.CandidateNotInBallotException;
import dpd.lab.voting.exceptions.PreferencePriorityTakenException;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class Ballot {

    private Set<Preference> votes;

    public Ballot() {
        votes = new HashSet<>();
    }

    public Set<Preference> getVotes() {
        return Collections.unmodifiableSet(votes);
    }

    public Ballot swapPreferences(Candidate candidate, Candidate otherCandidate) {
        Preference candidatePreference = votes.stream()
                .filter(preference -> preference.getCandidate().equals(candidate)).findFirst()
                .orElseThrow(() -> new CandidateNotInBallotException(candidate));

        Preference otherCandidatePreference = votes.stream()
                .filter(preference -> preference.getCandidate().equals(otherCandidate)).findFirst()
                .orElseThrow(() -> new CandidateNotInBallotException(otherCandidate));

        votes.remove(candidatePreference);
        votes.remove(otherCandidatePreference);

        Preference modifiedCandidatePreference =
                new Preference(candidate, otherCandidatePreference.getPriorityPreference());
        Preference modifiedOtherCandidatePreference =
                new Preference(otherCandidate, candidatePreference.getPriorityPreference());

        votes.add(modifiedCandidatePreference);
        votes.add(modifiedOtherCandidatePreference);

        return this;
    }

    public PreferenceBuilder vote(Candidate candidate) {
        return new PreferenceBuilder(this, candidate);
    }

    public PreferenceBuilder changeCandidatePreference(Candidate candidate) {

        if (!votes.removeIf(preference -> preference.getCandidate().equals(candidate))) {
            throw new CandidateNotInBallotException(candidate);
        }

        return new PreferenceBuilder(this, candidate);
    }

    public boolean ballotContains(Candidate candidate) {
        return votes.stream().map(Preference::getCandidate).anyMatch(candidate::equals);
    }

    public boolean preferencePriorityTaken(PriorityPreference priority) {
        return votes.stream().map(Preference::getPriorityPreference).anyMatch(priority::equals);
    }

    public void remove(Candidate candidate) {
        votes.removeIf(preference -> preference.getCandidate().equals(candidate));
    }

    private void addPreference(Preference preference) {

        if (ballotContains(preference.getCandidate())) {
            throw new CandidateInBallotException(preference.getCandidate());
        }

        if (preferencePriorityTaken(preference.getPriorityPreference())) {
            throw new PreferencePriorityTakenException(preference.getPriorityPreference());
        }

        votes.add(preference);
    }

    public Ballot voteInOrder(Candidate... candidates) {
        IntStream.range(0, candidates.length)
                .mapToObj(i -> new Preference(candidates[i], PriorityPreference.of(i + 1)))
                .forEach(this::addPreference);

        return this;
    }

    public static class PreferenceBuilder {

        private final Candidate candidate;

        private final Ballot ballot;

        private PreferenceBuilder(Ballot ballot, Candidate candidate) {
            this.candidate = candidate;
            this.ballot = ballot;
        }

        public Ballot withPreference(int priority) {
            ballot.addPreference(candidate.voteWithPreference(PriorityPreference.of(priority)));

            return ballot;
        }
    }

}
