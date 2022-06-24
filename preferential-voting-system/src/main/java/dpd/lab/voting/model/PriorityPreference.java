package dpd.lab.voting.model;

public class PriorityPreference extends TinyType<Integer> {

    public PriorityPreference(Integer priority) {
        super(priority);
    }

    public PriorityPreference increment() {
        return new PriorityPreference(this.getValue() + 1);
    }

    public static PriorityPreference of(int priority) {
        return new PriorityPreference(priority);
    }
}
