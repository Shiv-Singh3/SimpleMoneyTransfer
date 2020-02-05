package simpleMoneyTransfer.constants;

public class ConfigConstants {

    public static final String URL_PATTERN = "/*";

    //REST EASY MODULE CONSTANTS
    public static final String REST_EASY_SERVLET_MAPPING_PREFIX = "resteasy.servlet.mapping.prefix";

    //API ORIGIN FILTER CONSTANTS
    public static final String HEADER_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    public static final String HEADER_ALLOW_ORIGIN_VALUE = "*";
    public static final String HEADER_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    public static final String HEADER_ALLOW_HEADERS_VALUE = "Content-Type";
    public static final String HEADER_ALLOW_METHODS = "Access-Control-Allow-Methods";
    public static final String HEADER_ALLOW_METHODS_VALUE = "GET, POST, DELETE, PUT";

    //SWAGGER SERVLET CONTEXT LISTENER CONSTANTS
    public static final String ATTRIBUTE_READER = "reader";
    public static final String ATTRIBUTE_SWAGGER = "swagger";
    public static final String ATTRIBUTE_SCANNER = "scanner";

    //SWAGGER SERVLET BEAN CONFIG CONSTANTS
    public static final String BEAN_CONFIG_VERSION = "1.0.0";
    public static final String BEAN_CONFIG_PROTOCOL = "http";
    public static final String BEAN_CONFIG_HOST = "localhost";
    public static final String BEAN_CONFIG_PORT = "8080";
    public static final String BEAN_CONFIG_BASE_PATH = "/api";
    public static final String BEAN_CONFIG_TITLE = "Simple Money Transfer";
    public static final String BEAN_CONFIG_DESCRIPTION = "RestFul APIs for transferring money among accounts";
    public static final boolean BEAN_CONFIG_SET_SCAN = true;

    //MAIN CONSTANTS
    public static final String APPLICATION_PATH = "/api";
    public static final String CONTEXT_ROOT = "/";
    public static final int PORT = 8080;
}
