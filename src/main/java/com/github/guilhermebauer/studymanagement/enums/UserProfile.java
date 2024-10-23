package com.github.guilhermebauer.studymanagement.enums;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserProfile {
    @JsonProperty("user")
    USER("USER"),
    @JsonProperty("admin")
    ADMIN("ADMIN");

    private final String profile;

    UserProfile(String userProfile) {
        this.profile = userProfile;
    }

    public String getProfile() {
        return profile;
    }

    public static UserProfile fromString(String userProfile) {
        for (UserProfile profile : UserProfile.values()) {
            if (profile.profile.equalsIgnoreCase(userProfile)) {
                return profile;
            }
        }
        throw new IllegalArgumentException("Unknown user profile: " + userProfile);
    }
}
