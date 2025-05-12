package com.example.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import util.Colors;
import util.FontStyles;
import util.StyledButton;

public class StartScreen extends JPanel {
    private final transient Object lock = new Object();

    public StartScreen() {
        setupUI();
    }

    public Object getLock() {
        return lock;
    }
    
    private void setupUI() {
        this.setLayout(new BorderLayout());
        
        // Create title
        JLabel titleLabel = new JLabel("Character Selection", SwingConstants.CENTER);
        titleLabel.setFont(new Font(FontStyles.JETBRAINS_MONO_FONT, Font.PLAIN, FontStyles.FONT_SIZE_LARGE));
        this.add(titleLabel, BorderLayout.CENTER);
        
        // Create start button
        JButton startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(450, 120));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(Colors.BLUE));
        startButton.setUI(StyledButton.getInstance());
        
        // Center the button this
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
        
        // Button event handler
        startButton.addActionListener(event -> {
            synchronized (lock) {
                lock.notifyAll();
            }
        });
    }
}
