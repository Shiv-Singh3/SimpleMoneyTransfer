package simpleMoneyTransfer.utils;

import simpleMoneyTransfer.constants.CommonConstants;

public final class ConfigUtils {

    public static boolean preConditionStartCheck(String path) {
        return path.length() == CommonConstants.ZERO || path.startsWith(CommonConstants.BACK_SLASH);
    }

    public static boolean preConditionEndCheck(String path) {
        return !path.endsWith(CommonConstants.BACK_SLASH);
    }
}
