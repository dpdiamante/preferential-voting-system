package dpd.lab.voting.exceptions;

import dpd.lab.voting.model.PriorityPreference;

public class PreferencePriorityTakenException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Priority %d already taken";

    public PreferencePriorityTakenException(PriorityPreference priority) {
        super(String.format(ERROR_MESSAGE, priority.getValue()));
    }
}
