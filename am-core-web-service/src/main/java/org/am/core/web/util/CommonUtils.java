package org.am.core.web.util;

import java.security.SecureRandom;

public final class CommonUtils {
    private CommonUtils() {}

    public static String getFullName(String firstName, String lastName, String secondLastName) {
        StringBuilder fullName = new StringBuilder();
        fullName.append(firstName)
                .append(" ")
                .append(lastName);
        if (secondLastName != null && !secondLastName.isEmpty()) {
            fullName.append(" ")
                    .append(secondLastName);
        }
        return fullName.toString();
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 8;

    public static String generateRandomCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(randomIndex));
        }
        return code.toString();
    }
}
