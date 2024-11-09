package com.ceos20_instagram.domain.member.entity;

public enum UserRole {
    USER, ADMIN;

    public static UserRole fromString(String role) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.name()
                        .equalsIgnoreCase(role)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("Unexpected role: " + role);
    }
}