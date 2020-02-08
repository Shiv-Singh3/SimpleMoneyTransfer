package simpleMoneyTransfer.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simpleMoneyTransfer.exceptions.SimpleMoneyTransferException;
import simpleMoneyTransfer.webServices.response.ErrorResponse;
import org.apache.commons.lang3.StringUtils;
import javax.ws.rs.core.Response;
import java.security.SecureRandom;
import java.util.Currency;
import java.util.Random;

public final class CommonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);

    private static final int RAND_STRING_LEN = 16;

    private static final String SEED = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ123456789";

    private static final Random RANDOM = new SecureRandom();

    private static final ObjectWriter JSON_WRITER = new ObjectMapper().writer();

    private static final String JSON_ERROR = "error creating JSON document";

    public static Object getObjectFromJson(JSONObject jsonObject, String key) {
        try {
            if (jsonObject.has(key)) {
                return jsonObject.get(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    //todo implementation
    public static Integer generateUniqueAccountNumber() {
        return 0;
    }

    //todo implementation
    public static Double getDefaultBalance() {
        return 0d;
    }

    //todo implementation
    public static Currency getDefaultCurrency() {
        return Currency.getInstance("USD");
    }

    public static String getGUID() {
        StringBuilder rand = new StringBuilder(RAND_STRING_LEN);
        for (int i = 0; i < RAND_STRING_LEN; i++) {
            int index = (int) (RANDOM.nextDouble() * SEED.length());
            rand.append(SEED.charAt(index));
        }
        return rand.toString();
    }

    public static Response createWebServiceErrorResponse(SimpleMoneyTransferException e) {
        ErrorResponse errorResponse = new ErrorResponse(e);
        String responseString = serializeToJsonStringSilently(errorResponse);
        return Response.status(e.getHttpStatus()).entity(responseString).build();
    }

    public static String serializeToJsonStringSilently(Object obj) {
        String response = StringUtils.EMPTY;
        try {
            response = JSON_WRITER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error(JSON_ERROR, e);
        }
        return response;
    }
}
