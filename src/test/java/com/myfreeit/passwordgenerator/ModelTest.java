package com.myfreeit.passwordgenerator;

/*
 * Copyright (c) 2025, Denis Odesskiy. All rights reserved.
 *
 * This software is the confidential and proprietary information of Denis Odesskiy
 * ("Confidential Information"). You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the license agreement you
 * entered into with Denis Odesskiy.
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The ModelTest class contains unit tests for the {@link Model} class.
 *
 * <p>This class verifies the correctness of password generation logic, including
 * length, character composition, and avoidance of sequential characters.</p>
 *
 * <p>Each test checks a specific aspect of the generated password to ensure
 * compliance with security and formatting requirements.</p>
 *
 * @author Denis Odesskiy
 * @since 2025
 */

class ModelTest {

    private Model model;

    /**
     * Initializes the {@link Model} instance before each test.
     */
    @BeforeEach
    void setUp() {
        model = new Model();
    }

    /**
     * Verifies that the generated password has the correct length.
     */
    @Test
    void testPasswordLength() {
        String password = model.generatePassword(12, false);
        assertEquals(12, password.length(), "Password length should be 12 characters");
    }

    /**
     * Verifies that the password contains at least one uppercase letter.
     */
    @Test
    void testContainsUppercase() {
        String password = model.generatePassword(12, false);
        assertTrue(password.chars().anyMatch(Character::isUpperCase),
                "Password should contain at least one uppercase letter");
    }

    /**
     * Verifies that the password contains at least one lowercase letter.
     */
    @Test
    void testContainsLowercase() {
        String password = model.generatePassword(12, false);
        assertTrue(password.chars().anyMatch(Character::isLowerCase),
                "Password should contain at least one lowercase letter");
    }

    /**
     * Verifies that the password contains at least one digit.
     */
    @Test
    void testContainsDigit() {
        String password = model.generatePassword(12, false);
        assertTrue(password.chars().anyMatch(Character::isDigit),
                "Password should contain at least one digit");
    }

    /**
     * Verifies that the password contains at least one special character when enabled.
     */
    @Test
    void testContainsSpecialCharacterIfEnabled() {
        String password = model.generatePassword(12, true);
        assertTrue(password.chars().anyMatch(c -> "!@#$%^&*()_-+=<>?".indexOf(c) >= 0),
                "Password should contain at least one special character when enabled");
    }

    /**
     * Verifies that the password does not contain sequential characters.
     */
    @Test
    void testNoSequentialCharacters() {
        String password = model.generatePassword(20, true);
        for (int i = 1; i < password.length(); i++) {
            char prev = password.charAt(i - 1);
            char curr = password.charAt(i);
            assertNotEquals(1, Math.abs(prev - curr), "Password should not contain sequential characters");
        }
    }
}
