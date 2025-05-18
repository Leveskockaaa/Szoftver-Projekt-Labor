package com.example.view;

import com.example.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A countdown timer that displays the remaining time in minutes:seconds format.
 * Starts from a specified duration and counts down to zero.
 */
public class GameCountdownTimer {
    private final JLabel timerLabel;
    private javax.swing.Timer timer;
    private int remainingSeconds;
    private final int initialMinutes;

    /**
     * Creates a new countdown timer with the specified initial duration.
     *
     * @param initialMinutes The initial duration in minutes
     */
    public GameCountdownTimer(int initialMinutes) {
        this.initialMinutes = initialMinutes;
        this.remainingSeconds = initialMinutes * 60;

        // Create the timer label with the initial time
        timerLabel = new JLabel();
        timerLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 18));
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setHorizontalAlignment(SwingConstants.LEFT);
        updateTimerText();

        // Create the timer that ticks every second
        timer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
    }

    /**
     * Updates the timer by reducing one second and updating the display text.
     * Stops the timer when it reaches zero.
     */
    private void tick() {
        if (remainingSeconds > 0) {
            remainingSeconds--;
            updateTimerText();
        } else {
            timer.stop();
            // When timer reaches zero, we could trigger a game end event here
        }
    }

    /**
     * Updates the timer label text with the current time in MM:SS format.
     */
    private void updateTimerText() {
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
    }

    /**
     * Starts the countdown timer.
     */
    public void start() {
        timer.start();
    }

    /**
     * Stops the countdown timer.
     */
    public void stop() {
        timer.stop();
    }

    /**
     * Resets the timer to the initial duration.
     */
    public void reset() {
        stop();
        remainingSeconds = initialMinutes * 60;
        updateTimerText();
    }

    /**
     * Gets the timer display component.
     *
     * @return The JLabel displaying the timer.
     */
    public JLabel getTimerLabel() {
        return timerLabel;
    }

    /**
     * Gets the remaining time in seconds.
     *
     * @return The remaining time in seconds.
     */
    public int getRemainingSeconds() {
        return remainingSeconds;
    }
}
