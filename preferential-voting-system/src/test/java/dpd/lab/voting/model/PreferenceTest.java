package dpd.lab.voting.model;

import org.junit.jupiter.api.Test;

import static dpd.lab.voting.model.assertions.VotingDomainAssertions.assertThat;

public class PreferenceTest {

    @Test
    public void shouldBuildPreference() {
        Candidate leniRobredo = new Candidate("Leni Robredo");
        Preference preference = new Preference(leniRobredo, PriorityPreference.of(1));

        assertThat(preference).hasCandidate(leniRobredo).hasPreferenceVote(PriorityPreference.of(1));
    }

    @Test
    public void shouldHaveEqualPreferences() {
        Candidate leniRobredo = new Candidate("Leni Robredo");
        Preference preference = new Preference(leniRobredo, PriorityPreference.of(1));

        Candidate leniRobredoCopy = new Candidate("Leni Robredo");
        Preference anotherPreferenceVote = new Preference(leniRobredoCopy, PriorityPreference.of(1));

        assertThat(preference).isEqualTo(anotherPreferenceVote);
    }
}
