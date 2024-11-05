package com.flexisaf.week8.constant;


public interface Generic {


    // reg expression
    String DIGITS_REG_EXT = "\\d+";

    String PHONE_REG_EX = "\\A[0-9]{11}\\z"; // "^\\+?\\d{1,3}?\\s?[0-9]{6,18}";

    String EMAIL_REG_EX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$";

    String BEARER = "Bearer";

    public static final String SIGNING_KEY = "abjfoijefli2o3ur2839ru02h03f0904j4of0490j094775i75i5ki5";

    public static final String AUTHORITIES_KEY = "scopes";

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";

    static int PASSWORD_LENGTH = 7;
    static int PHONENUMBER_LENGTH = 11;
    static String ALL_ALPHANUMERIC = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static String ALL_LOWER_ALPHABETS = "abcdefghijklmnopqrstuvwxyz";
    static String ALL_CAP_ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static String ALL_NUMERIC = "1234567890";

    public static final int TIMEOUT_TIME = 10;

    String USER_AGENT_NAME = "user-agent";

    String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";

    String CURRENCY_CODE = "NGN";

    String TOKEN_REGEXP = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";

    public static final String FCM_SERVER_KEY = "AAAArMjTf7w:APA91bGJZI2pEsjNv_sTquDwJls9sPQZn7hutrm79cLWeaPgnBPg4zeOrZQGiJTMbsuBWL9g3fqa_ipEgCbxFQA-AAhfi36_Qv7-QpNU95wrIXxSEdyPBdqMB9hm2hnpgNRpx3C7lVPt";

    public static final String FCM_BASE_URL = "https://fcm.googleapis.com";

    public static final String CONTENT_TYPE = "application/json";

}