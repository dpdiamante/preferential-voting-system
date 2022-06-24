package dpd.lab.voting.model;

public class ElectionRounds extends TinyType<Integer> {

    public ElectionRounds(Integer value) {
        super(value);
    }

    public static ElectionRounds valueOf(int value) {
        return new ElectionRounds(value);
    }
}
