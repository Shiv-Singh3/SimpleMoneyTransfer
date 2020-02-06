package simpleMoneyTransfer.utils;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.security.SecureRandom;
import java.util.Currency;
import java.util.Random;

public final class CommonUtils {

    private static final int RAND_STRING_LEN = 16;

    private static final String SEED = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ123456789";

    private static final Random RANDOM = new SecureRandom();

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
}
