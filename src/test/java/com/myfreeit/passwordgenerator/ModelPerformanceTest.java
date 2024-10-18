//package com.myfreeit.passwordgenerator;
//
///*
// * Copyright (c) 2024, Denis Odesskiy. All rights reserved.
// *
// * This software is the confidential and proprietary information of Denis Odesskiy
// * ("Confidential Information"). You shall not disclose such Confidential Information
// * and shall use it only in accordance with the terms of the license agreement you
// * entered into with Denis Odesskiy.
// */
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
///**
// * The ModelPerformanceTest class contains performance tests for the {@link Model} class.
// *
// * <p>This class measures the execution time of the password generation process
// * over a large number of iterations to ensure that the algorithm performs efficiently.</p>
// *
// * @author Denis Odesskiy
// * @since 2024
// */
//class ModelPerformanceTest {
//
//    /**
//     * Tests the execution time of the password generation method.
//     *
//     * <p>This method generates 500,000 passwords and measures how long it takes to complete
//     * the process. It asserts that the execution time is below a specific threshold, ensuring
//     * that the generation is efficient even with a large number of iterations.</p>
//     */
//    @Test
//    void testPasswordGenerationPerformance() {
//        Model model = new Model();
//        int iterations = 500_000;
//        int passwordLength = 8;
//        boolean useSpecialChars = false;
//
//        // Start measuring time
//        long startTime = System.nanoTime();
//
//        for (int i = 0; i < iterations; i++) {
//            model.generatePassword(passwordLength, useSpecialChars);
//        }
//
//        // Stop measuring time
//        long endTime = System.nanoTime();
//        long duration = endTime - startTime;
//
//        // Convert to seconds
//        double durationInSeconds = duration / 1_000_000_000.0;
//
//        System.out.println("Time taken: " + durationInSeconds + " seconds");
//
//        // Set an acceptable threshold (e.g., 15 seconds)
//        double acceptableTimeInSeconds = 15.0;
//        Assertions.assertTrue(durationInSeconds < acceptableTimeInSeconds,
//                "Password generation took too long: " + durationInSeconds + " seconds");
//    }
//}
package com.myfreeit.passwordgenerator;

/*
 * Copyright (c) 2024, Denis Odesskiy. All rights reserved.
 *
 * This software is the confidential and proprietary information of Denis Odesskiy
 * ("Confidential Information"). You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the license agreement you
 * entered into with Denis Odesskiy.
 */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The ModelPerformanceTest class contains performance tests for the {@link Model} class.
 *
 * <p>This class measures the execution time of the password generation process
 * over a large number of iterations to ensure that the algorithm performs efficiently
 * under different conditions such as password length and inclusion of special characters.</p>
 *
 * <p>Each test generates 500,000 passwords with varying configurations and asserts that
 * the execution time stays below an acceptable threshold.</p>
 *
 * @author Denis Odesskiy
 * @since 2024
 */
class ModelPerformanceTest {

    private final Model model = new Model();

    /**
     * Measures the execution time for generating 500,000 passwords.
     *
     * <p>This method is a utility to avoid duplication in each test. It takes password length,
     * the flag for special characters, and the time limit to assert that the generation completes
     * within a reasonable time.</p>
     *
     * @param passwordLength      The length of the passwords to be generated.
     * @param useSpecialChars     Flag to include special characters in the password.
     * @param acceptableTimeLimit The maximum acceptable time in seconds for the generation.
     */
    private void measurePerformance(int passwordLength, boolean useSpecialChars, double acceptableTimeLimit) {
        long startTime = System.nanoTime();

        for (int i = 0; i < 500_000; i++) {
            model.generatePassword(passwordLength, useSpecialChars);
        }

        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;

        System.out.println("Time taken: " + durationInSeconds + " seconds for " +
                passwordLength + " characters, useSpecialChars = " + useSpecialChars);

        Assertions.assertTrue(durationInSeconds < acceptableTimeLimit,
                "Password generation took too long: " + durationInSeconds + " seconds");
    }

    /**
     * Measures the performance of password generation for 8 characters without special characters.
     *
     * <p>This test generates 500,000 passwords and measures the time taken to complete
     * the process, asserting that the execution time is below an acceptable threshold.</p>
     */
    @Test
    void testPasswordPerformance_8_NoSpecialChars() {
        measurePerformance(8, false, 15.0);  // Threshold of 15 seconds.
    }

    /**
     * Measures the performance of password generation for 8 characters with special characters.
     *
     * <p>This test generates 500,000 passwords and measures the time taken to complete
     * the process, asserting that the execution time is below an acceptable threshold.</p>
     */
    @Test
    void testPasswordPerformance_8_WithSpecialChars() {
        measurePerformance(8, true, 15.0);  // Threshold of 15 seconds.
    }

    /**
     * Measures the performance of password generation for 64 characters without special characters.
     *
     * <p>This test generates 500,000 passwords and measures the time taken to complete
     * the process, asserting that the execution time is below an acceptable threshold.</p>
     */
    @Test
    void testPasswordPerformance_64_NoSpecialChars() {
        measurePerformance(64, false, 60.0);  // Threshold of 60 seconds.
    }

    /**
     * Measures the performance of password generation for 64 characters with special characters.
     *
     * <p>This test generates 500,000 passwords and measures the time taken to complete
     * the process, asserting that the execution time is below an acceptable threshold.</p>
     */
    @Test
    void testPasswordPerformance_64_WithSpecialChars() {
        measurePerformance(64, true, 60.0);  // Threshold of 60 seconds.
    }
}
