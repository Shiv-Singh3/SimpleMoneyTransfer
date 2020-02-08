package simpleMoneyTransfer.webServices.response;


import lombok.Getter;
import lombok.Setter;
import simpleMoneyTransfer.exceptions.SimpleMoneyTransferException;

public class ErrorResponse extends BaseResponse{

    private static final Document DOC_TYPE =
            new Document("instance", "simpleMoneyTransfer", "standard.error", "1.0");

    private Error error;

    public ErrorResponse() {}

    public ErrorResponse(SimpleMoneyTransferException e) {
        super(DOC_TYPE);
        this.error = new Error();
        error.setCode(e.getErrorCode());
        error.setDescription(e.getDescription());
        error.setGuid(e.getErrorGUID());
    }

    @Getter
    @Setter
    public static class Error {

        private String guid;

        private String code;

        private String description;
    }
}
