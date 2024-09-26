package com.myfreeit.passwordgenerator;

/*
 * Copyright (c) 2024, Denis Odesskiy. All rights reserved.
 *
 * This software is the confidential and proprietary information of Denis Odesskiy
 * ("Confidential Information"). You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the license agreement you
 * entered into with Denis Odesskiy.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The View class represents the graphical user interface (GUI) for the password generator application.
 *
 * <p>This class manages the layout of the components such as a spinner for password length,
 * checkboxes, buttons, and a slider for adjusting the password length.
 * It handles interactions between the user and the application.</p>
 *
 * <p>It provides methods for adding listeners to handle user input and actions, and also includes
 * functionality for updating the UI based on the selected locale.</p>
 *
 * @author Denis Odesskiy
 * @since 2024
 */
public class View extends JFrame {
    private transient ResourceBundle bundle;
    private final JSpinner lengthSpinner;
    private final JSlider lengthSlider;
    private final JCheckBox specialCharsCheckbox;
    private final JButton generateButton;
    private final JTextArea resultArea;
    private final JLabel messageLabel;
    private final JLabel passwordLengthLabel;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 64;
    private String lengthLabelText;
    private final JComboBox<String> box = new JComboBox<>(new String[]{"EN", "RU"});

    /**
     * Initializes the View, setting up the window and all of its components.
     * The default locale is English, but the user can switch between other languages.
     */
    public View() {
        bundle = ResourceBundle.getBundle("i18n/messages", Locale.ENGLISH);

        setTitle(bundle.getString("app.title"));
        setSize(360, 320);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize spinner and slider with synchronized values.
        lengthSpinner = new JSpinner(new SpinnerNumberModel(MIN_PASSWORD_LENGTH, MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH, 1)); // Spinner for password length
        lengthSlider = new JSlider(SwingConstants.HORIZONTAL, MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH, MIN_PASSWORD_LENGTH); // Slider for password length
        lengthSlider.setMajorTickSpacing(8);
        lengthSlider.setMinorTickSpacing(1);
        lengthSlider.setPaintTicks(true);
        lengthSlider.setPaintLabels(true);

        lengthLabelText = bundle.getString("label.length");
        lengthLabelText = MessageFormat.format(lengthLabelText, MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH);
        passwordLengthLabel = new JLabel(lengthLabelText);
        specialCharsCheckbox = new JCheckBox(bundle.getString("label.specialChars"));
        generateButton = new JButton(bundle.getString("button.generate"));
        generateButton.setPreferredSize(new Dimension(140, 40));
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        messageLabel = new JLabel();

        // Sync slider and spinner values.
        lengthSlider.addChangeListener(e -> lengthSpinner.setValue(lengthSlider.getValue()));
        lengthSpinner.addChangeListener(e -> lengthSlider.setValue((int) lengthSpinner.getValue()));

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Add language selection icon.
        ImageIcon choiceLanguageIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("icons/globe.png")));
        JLabel choiceLanguageLabel = new JLabel(choiceLanguageIcon);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(choiceLanguageLabel, gbc);

        // Add the language selection combo box.
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;
        gbc.insets = new Insets(5, 10, 5, 10);
        box.setPreferredSize(new Dimension(50, box.getPreferredSize().height));
        panel.add(box, gbc);

        // Add password length label and spinner.
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.WEST;
        passwordLengthLabel.setPreferredSize(new Dimension(220, passwordLengthLabel.getPreferredSize().height));
        panel.add(passwordLengthLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        lengthSpinner.setPreferredSize(box.getPreferredSize());
        panel.add(lengthSpinner, gbc);

        // Add slider.
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(lengthSlider, gbc);

        // Add special characters checkbox.
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(specialCharsCheckbox, gbc);

        // Add generate button.
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(generateButton, gbc);

        // Add result area (scrollable).
        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
        add(messageLabel, BorderLayout.SOUTH);

        box.setSelectedIndex(0); // Default language is English.
    }

    /**
     * Adds an ActionListener to the "Generate" button.
     *
     * @param listener The ActionListener to be added.
     */
    public void addGenerateButtonListener(ActionListener listener) {
        generateButton.addActionListener(listener);
    }

    /**
     * Adds a MouseAdapter to the result area to handle mouse events.
     *
     * @param listener The MouseAdapter to be added.
     */
    public void addResultAreaMouseListener(MouseAdapter listener) {
        resultArea.addMouseListener(listener);
    }

    /**
     * Adds an ItemListener to the language selection box.
     *
     * @param listener The ItemListener to be added.
     */
    public void addLanguageChangeListener(ItemListener listener) {
        box.addItemListener(listener);
    }

    /**
     * Returns the currently selected locale based on the selected language in the combo box.
     *
     * @return The selected Locale, either English or the default locale.
     */
    public Locale getSelectedLocale() {
        return box.getSelectedIndex() == 0 ? Locale.ENGLISH : Locale.getDefault();
    }

    /**
     * Gets the text currently displayed in the result area.
     *
     * @return The result area text.
     */
    public String getResultAreaText() {
        return resultArea.getText();
    }

    /**
     * Sets the current resource bundle to update the UI texts.
     *
     * @param bundle The new ResourceBundle.
     */
    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    /**
     * Gets the current ResourceBundle used by the view.
     *
     * @return The current ResourceBundle.
     */
    public ResourceBundle getBundle() {
        return bundle;
    }

    /**
     * Gets the current password length from the spinner.
     *
     * @return The length of the password as an integer.
     */
    public int getPasswordLength() {
        return (int) lengthSpinner.getValue();
    }

    /**
     * Checks if the user has selected to use special characters in the password.
     *
     * @return true if the special characters checkbox is selected, false otherwise.
     */
    public boolean isUseSpecialChars() {
        return specialCharsCheckbox.isSelected();
    }

    /**
     * Sets the generated password to be displayed in the result area.
     *
     * @param password The generated password.
     */
    public void setGeneratedPassword(String password) {
        resultArea.setText(password);
        messageLabel.setText("");
    }

    /**
     * Updates all UI texts according to the currently selected language.
     */
    public void updateUITexts() {
        if (bundle != null) {
            setTitle(bundle.getString("app.title"));
            specialCharsCheckbox.setText(bundle.getString("label.specialChars"));

            lengthLabelText = bundle.getString("label.length");
            lengthLabelText = MessageFormat.format(lengthLabelText, MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH);
            passwordLengthLabel.setText(lengthLabelText);

            generateButton.setText(bundle.getString("button.generate"));
            messageLabel.setText("");
            revalidate();
            repaint();
        }
    }

    /**
     * Triggers a visual animation on the result area when the user copies the password to the clipboard.
     */
    public void showCopyBorderAnimation() {
        resultArea.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 250), 2));
        Timer borderAnimationTimer = new Timer(300, e1 -> {
            resultArea.setBorder(BorderFactory.createEmptyBorder());
            ((Timer) e1.getSource()).stop();
        });
        borderAnimationTimer.start();
    }

    /**
     * Requests focus on the password length input field.
     */
    public void requestFocusOnLengthField() {
        lengthSpinner.requestFocus();
    }

    /**
     * Displays a success message in the UI.
     *
     * @param message The success message to display.
     */
    public void showSuccess(String message) {
        showMessage(message, MessageStatus.SUCCESS);
    }

    /**
     * Displays an error message in the UI.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        showMessage(message, MessageStatus.ERROR);
    }

    /**
     * Displays a notification message in the UI.
     *
     * @param message The notification message to display.
     */
    public void showNotification(String message) {
        showMessage(message, MessageStatus.NOTIFICATION);
    }

    /**
     * Internal method for showing a message in the UI with a specific status.
     *
     * @param message The message to display.
     * @param status  The status of the message (error, notification, success).
     */
    private void showMessage(String message, MessageStatus status) {
        Color messageColor = getMessageColor(status);
        messageLabel.setForeground(messageColor);
        messageLabel.setText(message);
        // Trigger notification animation
        showNotification(message, messageColor);
    }

    /**
     * Returns the color associated with the message status.
     *
     * @param status The status of the message.
     * @return The corresponding color.
     */
    private Color getMessageColor(MessageStatus status) {
        return switch (status) {
            case ERROR -> Color.RED;
            case NOTIFICATION -> new Color(50, 150, 250);
            case SUCCESS -> new Color(3, 110, 40);
        };
    }

    /**
     * Shows a custom notification animation in the result area.
     *
     * @param message      The message to display.
     * @param messageColor The color of the message.
     */
    private void showNotification(String message, Color messageColor) {
        NotificationAnimation notification = new NotificationAnimation(message, messageColor);
        resultArea.setLayout(null);
        resultArea.add(notification);
        notification.setBounds((resultArea.getWidth() - 360) / 2, (resultArea.getHeight() - 300) / 2, 360, 320);
        notification.startAnimation();
    }
}
