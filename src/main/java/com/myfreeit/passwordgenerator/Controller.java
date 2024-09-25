package com.myfreeit.passwordgenerator;

/*
 * Copyright (c) 2024, Denis Odesskiy. All rights reserved.
 *
 * This software is the confidential and proprietary information of Denis Odesskiy
 * ("Confidential Information"). You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the license agreement you
 * entered into with Denis Odesskiy.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.Toolkit;


/**
 * Controller class in the MVC architecture for the Password Generator application.
 * It handles user actions and updates the model and view accordingly.
 * <p>
 * The Controller listens to user actions, such as button clicks and language selection changes,
 * and interacts with the {@link Model} to generate passwords and update the {@link View}.
 * </p>
 *
 * <p><b>Author:</b> Denis Odesskiy</p>
 * <p><b>Since:</b> 2024</p>
 */
public class Controller {
    private final Model model;
    private final View view;

    /**
     * Constructs a new Controller.
     *
     * @param model the Model instance for generating passwords
     * @param view  the View instance for interacting with the user
     */
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        this.view.addGenerateButtonListener(new GenerateButtonListener());
        this.view.addResultAreaMouseListener(new ResultAreaMouseListener());
        this.view.addLanguageChangeListener(new LanguageChangeListener());
    }

    /**
     * ActionListener class for handling the password generation button click.
     */
    class GenerateButtonListener implements ActionListener {
        /**
         * Invoked when the generate button is clicked. It retrieves user input,
         * generates a password using the {@link Model}, and updates the {@link View}.
         *
         * @param e the action event triggered by the button click
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int length = view.getPasswordLength();
                boolean useSpecialChars = view.isUseSpecialChars();
                String password = model.generatePassword(length, useSpecialChars);
                view.setGeneratedPassword(password);
                view.showSuccess(view.getBundle().getString("message.success"));
            } catch (Exception ex) {
                view.showError(String.format("%s: %s", view.getBundle().getString("message.unexpected"), ex.getMessage()));
            }
            view.requestFocusOnLengthField();
        }
    }

    /**
     * MouseAdapter class for handling mouse clicks on the result area.
     * Copies the generated password to the clipboard when clicked.
     */
    class ResultAreaMouseListener extends MouseAdapter {
        /**
         * Invoked when the result area is clicked. Copies the text to the clipboard
         * and shows a notification in the view.
         *
         * @param e the mouse event triggered by the click
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            String resultText = view.getResultAreaText();
            if (resultText != null && !resultText.isBlank()) {
                copyToClipboard(resultText);
                view.showCopyBorderAnimation();
                view.showNotification(view.getBundle().getString("message.copiedToClipboard"));
            }
            view.requestFocusOnLengthField();
        }

        /**
         * Copies the provided text to the system clipboard.
         *
         * @param text the text to be copied to the clipboard
         */
        private void copyToClipboard(String text) {
            if (text != null && !text.isEmpty()) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection selection = new StringSelection(text);
                clipboard.setContents(selection, null);
            }
        }
    }

    /**
     * ItemListener class for handling changes in language selection.
     * Updates the view with the selected language's locale.
     */
    class LanguageChangeListener implements ItemListener {
        /**
         * Invoked when the language selection is changed. Updates the view's locale
         * and reloads the UI texts.
         *
         * @param e the item event triggered by the language selection
         */
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Locale locale = view.getSelectedLocale();
                view.setBundle(ResourceBundle.getBundle("i18n/messages", locale));
                view.updateUITexts();
            }
        }
    }
}
