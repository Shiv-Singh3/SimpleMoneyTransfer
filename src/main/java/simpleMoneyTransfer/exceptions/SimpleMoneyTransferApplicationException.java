package simpleMoneyTransfer.exceptions;

import simpleMoneyTransfer.constants.Errors;

public class SimpleMoneyTransferApplicationException extends SimpleMoneyTransferException {

    public SimpleMoneyTransferApplicationException(Errors error) {
        super(error);
    }

    public SimpleMoneyTransferApplicationException(Errors error, String description) {
        super(error, description);
    }

    public SimpleMoneyTransferApplicationException(Errors error, String description, String uuid) {
        super(error, description, uuid);
    }

    public SimpleMoneyTransferApplicationException(Errors error, String description, Throwable e) {
        super(error, description, e);
    }

    public SimpleMoneyTransferApplicationException(Errors error, String description, String errorGUID, Throwable e) {
        super(error, description, errorGUID, e);
    }
}
