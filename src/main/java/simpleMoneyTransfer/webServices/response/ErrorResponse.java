package simpleMoneyTransfer.webServices.response;

import simpleMoneyTransfer.exceptions.SimpleMoneyTransferException;

public class ErrorResponse extends BaseResponse {

    private static final Document DOC_TYPE =
            new Document("instance", "simpleMoneyTransfer", "standard.error", "1.0");

    private Error error;

    public ErrorResponse(SimpleMoneyTransferException e) {
        super(DOC_TYPE);
        this.error = new Error();
        error.setCode(e.getErrorCode());
        error.setDescription(e.getDescription());
        error.setGuid(e.getErrorGUID());
    }

    public ErrorResponse() {}

    public static class Error {

        private String guid;

        private String code;

        private String description;

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

}