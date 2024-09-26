package com.myfreeit.passwordgenerator.ui;

/*
 * Copyright (c) 2024, Denis Odesskiy. All rights reserved.
 *
 * This software is the confidential and proprietary information of Denis Odesskiy
 * ("Confidential Information"). You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the license agreement you
 * entered into with Denis Odesskiy.
 */

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.myfreeit.passwordgenerator.View;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Utility class responsible for managing the application's theme (dark or light mode).
 * It determines the system's current theme, applies the corresponding look and feel,
 * and continuously monitors changes to the system's theme.
 *
 * <p>The class interacts with the {@link View} to apply theme updates and custom UI styles
 * using the FlatLaf library.</p>
 *
 * <p>This class is not meant to be instantiated, as it contains only static methods
 * for theme management.</p>
 *
 * @author Denis Odesskiy
 * @since 2024
 */
public class ThemeManager {

    private ThemeManager() {
        throw new IllegalStateException("Utility class");
    }

    private static final String FONT_NAME = "Segoe UI";

    // Flag to track whether dark mode is enabled.
    private static boolean isDarkModeEnabled = false;

    /**
     * Initializes the theme manager, setting the look and feel based on the system's theme,
     * and starts a timer to monitor theme changes.
     *
     * @param view the {@link View} instance whose UI will be updated based on the theme.
     */
    public static void initialize(View view) {
        try {
            isDarkModeEnabled = checkDarkMode(); // Check if dark mode is enabled.
            setLookAndFeel(isDarkModeEnabled);   // Set the appropriate look and feel.
            startThemeCheckTimer(view);          // Start monitoring theme changes.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Applies custom styles to the UI components (e.g., buttons, labels).
     * Customization includes colors, fonts, and arcs for rounded corners.
     */
    public static void applyCustomStyles() {
        UIManager.put("Button.arc", 20);
        UIManager.put("Button.background", new Color(50, 150, 250));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", new Font(FONT_NAME, Font.BOLD, 14));
        UIManager.put("Label.font", new Font(FONT_NAME, Font.PLAIN, 12));
    }

    /**
     * Sets the look and feel of the application based on the current theme.
     * If dark mode is enabled, the FlatDarkLaf theme is applied; otherwise, FlatLightLaf is used.
     *
     * @param darkMode true if dark mode is enabled, false otherwise.
     * @throws UnsupportedLookAndFeelException if the look and feel is unsupported.
     */
    public static void setLookAndFeel(boolean darkMode) throws UnsupportedLookAndFeelException {
        if (darkMode) {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } else {
            UIManager.setLookAndFeel(new FlatLightLaf());
        }
        applyCustomStyles();
    }

    /**
     * Starts a timer that continuously checks for system theme changes and applies updates to the UI.
     *
     * @param view the {@link View} instance whose theme needs to be updated.
     */
    private static void startThemeCheckTimer(View view) {
        java.util.Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    boolean darkModeEnabled = checkDarkMode();
                    if (darkModeEnabled != isDarkModeEnabled) {
                        isDarkModeEnabled = darkModeEnabled;
                        setLookAndFeel(darkModeEnabled);
                        SwingUtilities.invokeLater(() -> {
                            SwingUtilities.updateComponentTreeUI(view);
                            view.repaint();
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1000);    // Timer checks for theme change every second.
    }

    /**
     * Checks whether the system's dark mode is enabled by querying the Windows registry.
     *
     * @return true if dark mode is enabled, false otherwise.
     */
    public static boolean checkDarkMode() {
        try {
            // Query the registry for the current theme setting (specific to Windows).
            Process process = Runtime.getRuntime().exec("reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize\" /v AppsUseLightTheme");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("0x1")) {
                    return false;   // Light theme enabled.
                } else if (line.contains("0x0")) {
                    return true;    // Dark theme enabled.
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;   // Default to light mode if an error occurs.
    }
}