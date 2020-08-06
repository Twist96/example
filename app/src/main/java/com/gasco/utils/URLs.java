package com.gasco.utils;

public class URLs {
    public static String BASE_URL = "https://demo.claritae.net:8000/";

    public static String LOGIN = URLs.BASE_URL + "api/v1/login";
    public static String FILL_LOGS = URLs.BASE_URL + "/api/v1/skids/fillLogs";
    public static final String DISPENSE_LOG = URLs.BASE_URL + "api/v1/skids/logs/%s?expand=%b";
    public static String DISPENSE_LOGS_LIST = URLs.BASE_URL + "api/v1/skids/fillLogs?expand=%b&type=%s"; //"/api/v1/dispenselogs?limit=%d&offset=%d&assigned=%b&expand=%b";
    public static final String DISPENSE_LOG_CHECKS = URLs.BASE_URL + "/api/v1/skids/logs/%s/checks";
    public static final String LOGS_CHECK_LIST = URLs.BASE_URL + "api/v1/skids/logs/checklist?expand=%b&type=%s";
    public static final String SKIDS = URLs.BASE_URL + "api/v1/skids?expand=%b";
}
