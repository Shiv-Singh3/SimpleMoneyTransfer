package simpleMoneyTransfer.exceptions;

import simpleMoneyTransfer.constants.Errors;

public class SimpleMoneyTransferValidationException extends SimpleMoneyTransferException {

    public SimpleMoneyTransferValidationException(Errors error) {
        super(error);
    }

    public SimpleMoneyTransferValidationException(Errors error, String description) {
        super(error, description);
    }

    public SimpleMoneyTransferValidationException(Errors error, String description, String uuid) {
        super(error, description, uuid);
    }

    public SimpleMoneyTransferValidationException(Errors error, String description, Throwable e) {
        super(error, description, e);
    }

    public SimpleMoneyTransferValidationException(Errors error, String description, String errorGUID, Throwable e) {
        super(error, description, errorGUID, e);
    }
}
