package com.example.httptraining;

import com.example.httptraining.Pojo.User;

import java.util.List;

public class Constans {
    public static List<User> LIST_RESPONSE = null;

    public static final String KEY_USER_ID = "id";
    public static final String KEY_USER_NAME = "name";
    public static final String KEY_USER_NICK = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_STREET = "street";
    public static final String KEY_SUITE= "suite";
    public static final String KEY_CITY = "city";
    public static final String KEY_ZIPCODE = "zipcode";
    public static final String KEY_COMPANY = "company";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_WEBSITE = "website";
    public static final String KEY_COMPANY_NAME = "name";
    public static final String KEY_PHRASE = "catchPhrase";
    public static final String KEY_BS = "bs";

    public static class URL {
        private static final String HOST = "https://jsonplaceholder.typicode.com/";
        public static final String GET_USERS = HOST + "users";
    }
}
