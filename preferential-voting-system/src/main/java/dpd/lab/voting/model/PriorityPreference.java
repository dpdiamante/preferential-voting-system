package dpd.lab.voting.model;

public class PriorityPreference extends TinyType<Integer> {

    public PriorityPreference(Integer priority) {
        super(priority);
    }

    public static PriorityPreference of(int priority) {
        return new PriorityPreference(priority);
    }
}
