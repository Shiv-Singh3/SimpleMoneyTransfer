package simpleMoneyTransfer.constants;

public class CommonConstants {

    public static final String BACK_SLASH = "/";
    public static final String COLON = ":";

    //Account DTO
    public static final String NAME = "name";
    public static final String ACCOUNT_NUMBER = "accountNumber";
    public static final String BALANCE = "balance";
    public static final String CURRENCY = "currency";

    //Transfer STO
    public static final String SOURCE_ACCOUNT_NUM = "sourceAccountNumber";
    public static final String DESTINATION_ACCOUNT_NUM = "destinationAccountNumber";
    public static final String TRANSFER_AMOUNT = "amount";

    public static final int ZERO = 0;

    //SWAGGER RESPONSE STATUS CODES
    public static final int HTTP_STATUS_OK = 200;
    public static final int HTTP_STATUS_NO_CONTENT = 204;
    public static final int HTTP_STATUS_CREATED = 201;
    public static final int HTTP_STATUS_CONFLICT = 409;
    public static final int HTTP_STATUS_NOT_FOUND = 404;
    public static final int HTTP_STATUS_BAD_REQUEST = 400;
    public static final int HTTP_STATUS_INTERNAL_SERVER_ERROR = 500;
}
