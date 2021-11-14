package com.example.taskworklife.config;

public class Authority {
    public static final String[] USER_AUTHORITIES = { "user:read", "kamer:read","images:read" };
    public static final String[] ADMIN_AUTHORITIES = { "user:read", "userAdmin:read","user:create", "user:update", "kamer:read", "kamer:write", "kamer:update", "kamer:delete", "kamerAdmin:write", "images:read", "images:write", "images:update", "images:delete"};
}
