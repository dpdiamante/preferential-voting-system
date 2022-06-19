package dpd.lab.voting.model;

public abstract class TinyType<T> {

    private final T value;

    public TinyType(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }

        if (that == null || getClass() != that.getClass()) {
            return false;
        }

        TinyType<?> thatValue = (TinyType<?>) that;

        return value.equals(thatValue.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
