package com.example.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.example.model.Main;

import util.Colors;

public class StartScreen extends JFrame {
    private final transient Object lock = new Object();

    public StartScreen() {
        // Basic window settings
        setTitle("Character Selector Game");
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setupUI();
    }

    public Object getLock() {
        return lock;
    }
    
    private void setupUI() {
        // Create panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        // Create title
        JLabel titleLabel = new JLabel("Character Selection", SwingConstants.CENTER);
        titleLabel.setFont(Main.getJetBrainsFontTitle());
        panel.add(titleLabel, BorderLayout.CENTER);
        
        // Create start button
        JButton startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(450, 120));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(Colors.BLUE));
        startButton.setUI(Main.getStyledButton());
        startButton.setFont(Main.getJetBrainsFontBold());
        
        // Center the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Button event handler
        startButton.addActionListener(event -> {
            synchronized (lock) {
                lock.notifyAll();
            }
            dispose();
        });
        
        // Set panel to window
        setContentPane(panel);
    }
}
