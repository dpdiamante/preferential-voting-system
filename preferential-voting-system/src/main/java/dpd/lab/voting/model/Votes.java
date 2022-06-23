package dpd.lab.voting.model;

public class Votes extends TinyType<Integer> {

    public Votes(Integer value) {
        super(value);
    }

    public static Votes instanceOf(int value) {
        return new Votes(value);
    }
}
