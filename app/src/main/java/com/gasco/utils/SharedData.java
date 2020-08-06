package com.gasco.utils;

import com.gasco.Models.User;

import java.util.Date;

public class SharedData {
    public static Date LAST_LOGIN_TIME = null;
    public static User USER = new User();
    public static String USER_TOKEN;

    public static void CLEAR_DATA() {
        SharedData.USER = null;
        SharedData.USER_TOKEN = "";
        SharedData.LAST_LOGIN_TIME = null;
    }
}
