package simpleMoneyTransfer.exceptions;

import lombok.Getter;
import simpleMoneyTransfer.constants.Errors;
import simpleMoneyTransfer.utils.CommonUtils;
import javax.ws.rs.core.Response;

@Getter
public abstract class SimpleMoneyTransferException extends RuntimeException {

    private static final long serialVersionUID = 1141714652063670250L;

    private String errorCode;

    private String description;

    private String errorGUID;

    public SimpleMoneyTransferException(Errors error) {
        this(error, null, CommonUtils.getGUID(),null);
    }

    public SimpleMoneyTransferException(Errors error, String description){
        this(error, description,CommonUtils.getGUID(),null);
    }

    public SimpleMoneyTransferException(Errors error, String description,String uuid){
        this(error, description,uuid,null);
    }

    public SimpleMoneyTransferException(Errors error, String description,Throwable e){
        this(error, description,CommonUtils.getGUID(),e);
    }

    public SimpleMoneyTransferException(Errors error, String description, String errorGUID, Throwable e) {
        super(error.toString() + '|' + description + "|GUID:" + errorGUID, e);
        this.errorCode = error.getCode();
        this.description = error.getDescription();
        this.errorGUID = errorGUID;
    }

    public Response.Status getHttpStatus() {
        return Response.Status.INTERNAL_SERVER_ERROR;
    }
}
