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

/**
 * A JPanel component that displays an animated notification message with a font-size
 * and opacity transition effect.
 *
 * <p>This class is responsible for creating and displaying a notification message that
 * animates by gradually increasing the font size and making the message fade in.
 * The animation stops once the message reaches full size and opacity, and then
 * the component removes itself from the UI.</p>
 *
 * <p>Typically used in conjunction with {@link View} to display temporary notifications
 * to the user.</p>
 *
 * <ul>
 *   <li>The animation smoothly transitions the font size up to a maximum of 16 points.</li>
 *   <li>The opacity of the message increases gradually from 0 to 1 during the animation.</li>
 * </ul>
 *
 * @author Denis Odesskiy
 * @since 2024
 */
public class NotificationAnimation extends JPanel {
    private final JLabel notificationLabel;
    private final Timer animationTimer;
    private int fontSize = 0;
    private float opacity = 0f;

    /**
     * Constructs a {@code NotificationAnimation} with the specified message and color.
     *
     * @param message      The text message to be displayed during the animation.
     * @param messageColor The color of the message text.
     */
    public NotificationAnimation(String message, Color messageColor) {
        setOpaque(false);
        notificationLabel = new JLabel(message);
        notificationLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
        notificationLabel.setForeground(new Color(messageColor.getRed(), messageColor.getGreen(), messageColor.getBlue(), 0));  // Initial opacity set to 0.
        setLayout(new GridBagLayout());
        add(notificationLabel);

        // Initializes the timer that drives the animation.
        animationTimer = new Timer(80, e -> animateNotification());
    }

    /**
     * Starts the notification animation by incrementing the font size and opacity.
     *
     * <p>The animation runs until the font size reaches 16 and the opacity is fully visible,
     * after which the notification will disappear from the UI.</p>
     */
    public void startAnimation() {
        animationTimer.start();
    }

    /**
     * Handles the animation logic for gradually increasing the font size and opacity of the notification message.
     *
     * <p>This method is called at regular intervals by the {@code Timer}, updating the label's appearance until
     * the animation is complete. When finished, the component is removed from its parent.</p>
     */
    private void animateNotification() {
        int maxFontSize = 16;

        // Gradually increase font size.
        if (fontSize < maxFontSize) {
            fontSize++;
            notificationLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
        }

        // Gradually increase opacity.
        if (opacity < 1) {
            opacity += 0.05f;
            notificationLabel.setForeground(new Color(notificationLabel.getForeground().getRed(),
                    notificationLabel.getForeground().getGreen(),
                    notificationLabel.getForeground().getBlue(), (int) (opacity * 255)));
        }

        // Stop the animation once it reaches full size and opacity, then remove the component.
        if (opacity >= 1 || fontSize >= maxFontSize) {
            animationTimer.stop();
            SwingUtilities.invokeLater(() -> getParent().remove(this));
            getParent().revalidate();
            getParent().repaint();
            notificationLabel.setText("");
        }
    }
}