package com.example.taskworklife.config;

public class Authority {
    public static final String[] USER_AUTHORITIES = {"user:user", "user:images", "user:reserveringen", "user:kamer"};
    public static final String[] ADMIN_AUTHORITIES = {"userAdmin:manageUser", "userAdmin:manageReserveringen", "userAdmin:manageImages",
            "userAdmin:kamerManage"};
}
