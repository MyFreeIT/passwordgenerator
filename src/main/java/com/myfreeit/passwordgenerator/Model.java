package com.myfreeit.passwordgenerator;

/*
 * Copyright (c) 2024, Denis Odesskiy. All rights reserved.
 *
 * This software is the confidential and proprietary information of Denis Odesskiy
 * ("Confidential Information"). You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the license agreement you
 * entered into with Denis Odesskiy.
 */

import java.security.SecureRandom;

/**
 * The Model class provides functionality for generating secure random passwords
 * based on a combination of uppercase letters, lowercase letters, digits, and
 * optional special characters.
 *
 * <p>The class uses {@link SecureRandom} to ensure high entropy in password
 * generation and includes logic to avoid sequential characters.</p>
 *
 * <p>It supports generating passwords with or without special characters
 * depending on the user input.</p>
 *
 * @author Denis Odesskiy
 * @since 2024
 */
public class Model {

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()_-+=<>?";

    /**
     * Generates a random password with the specified length.
     *
     * <p>The password includes at least one uppercase letter, one lowercase letter,
     * and one digit. If special characters are enabled, the password will also
     * include at least one special character.</p>
     *
     * @param length          The desired length of the password.
     * @param useSpecialChars If true, includes special characters in the password.
     * @return A secure random password as a {@link String}.
     */
    public String generatePassword(int length, boolean useSpecialChars) {
        StringBuilder password = new StringBuilder();
        String characters = UPPERCASE + LOWERCASE + DIGITS;
        if (useSpecialChars) {
            characters += SPECIAL;
        }

        SecureRandom random = new SecureRandom();

        // Ensure at least one of each required character type is included
        password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        if (useSpecialChars) {
            password.append(SPECIAL.charAt(random.nextInt(SPECIAL.length())));
        }

        // Fill the rest of the password length
        while (password.length() < length) {
            char nextChar = characters.charAt(random.nextInt(characters.length()));
            if (!isSequential(password.charAt(password.length() - 1), nextChar)) {
                password.append(nextChar);
            }
        }

        return password.toString();
    }

    /**
     * Checks if two characters are sequential based on their ASCII values.
     *
     * @param prev    The previous character in the sequence.
     * @param current The current character being considered.
     * @return true if the characters are sequential; false otherwise.
     */
    private boolean isSequential(char prev, char current) {
        return Math.abs(prev - current) == 1;
    }
}
