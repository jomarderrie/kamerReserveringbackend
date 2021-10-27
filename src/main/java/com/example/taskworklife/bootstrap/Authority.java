package com.example.taskworklife.bootstrap;

public class Authority {
    public static final String[] USER_AUTHORITIES = { "user:read", "kamer:read" };
    public static final String[] ADMIN_AUTHORITIES = { "user:read", "userAdmin:read","user:create", "user:update", "kamer:read", "kamer:write", "kamer:update", "kamer:delete", "kamerAdmin:write" };
}
