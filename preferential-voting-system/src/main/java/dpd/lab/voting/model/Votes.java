package dpd.lab.voting.model;

public class Votes extends TinyType<Integer> {

    public Votes(Integer value) {
        super(value);
    }

    public Votes increment() {
        return new Votes(this.getValue() + 1);
    }

    public static Votes valueOf(int value) {
        return new Votes(value);
    }
}
