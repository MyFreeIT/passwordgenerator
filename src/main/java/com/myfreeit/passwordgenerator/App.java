package com.myfreeit.passwordgenerator;

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
import com.myfreeit.passwordgenerator.ui.ThemeManager;

import javax.swing.*;

/**
 * The main application class responsible for launching the password generator.
 * It sets up the application's look and feel based on the system's theme (dark or light mode),
 * initializes the {@link Model}, {@link View}, and {@link Controller}, and applies custom UI styles.
 *
 * <p>This class uses the FlatLaf library to manage the themes and {@link ThemeManager}
 * to handle custom styling and theme initialization.</p>
 *
 * <p>Upon launching, the app checks for the current system's theme preference (dark or light mode)
 * and applies the corresponding look and feel. The {@link ThemeManager} is then used to apply
 * custom styles to the application UI components.</p>
 *
 * @author Denis Odesskiy
 * @since 2024
 */
public class App {

    /**
     * The entry point of the application.
     *
     * <p>Sets the look and feel of the UI based on system preferences, initializes the MVC components,
     * and applies custom styles to the UI.</p>
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                boolean isDarkModeEnabled = ThemeManager.checkDarkMode();
                if (isDarkModeEnabled) {
                    UIManager.setLookAndFeel(new FlatDarkLaf());
                } else {
                    UIManager.setLookAndFeel(new FlatLightLaf());
                }
                ThemeManager.applyCustomStyles();
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }

            Model model = new Model();
            View view = new View();
            new Controller(model, view);

            ThemeManager.initialize(view);
            SwingUtilities.updateComponentTreeUI(view);
            view.setVisible(true);
        });
    }
}