package com.example.taskworklife.config;

public class Authority {
    public static final String[] USER_AUTHORITIES = {"user:crud", "images:crud", "reserveringen:crud", "kamer:crud"};
    public static final String[] ADMIN_AUTHORITIES = {"user:manage", "reserveringen:manage", "images:manage",
            "kamer:manage"};
}
