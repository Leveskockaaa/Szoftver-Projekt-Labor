package com.example.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.example.model.Main;

public class GameSummary extends JFrame {
    private final transient Object lock = new Object();
    private final List<String> selectedMycologists;
    private final List<Color> selectedInsectColors;

    public GameSummary(List<String> selectedMycologists, List<Color> selectedInsectColors) {
        this.selectedMycologists = selectedMycologists;
        this.selectedInsectColors = selectedInsectColors;

        // Basic window settings
        setTitle("Game Summary");
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setupUI();
    }

    public Object getLock() {
        return lock;
    }

    private void setupUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);

        // Title at the top
        JLabel titleLabel = new JLabel("Start Game", SwingConstants.CENTER);
        titleLabel.setFont(Main.getJetBrainsFontTitle());
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Central panel for character selection (2x2 grid)
        JPanel characterPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        characterPanel.setBackground(Color.WHITE);

        // Left column: Mycologists
        JPanel mycologistsColumn = new JPanel(new GridLayout(2, 1, 10, 10));
        mycologistsColumn.setBackground(Color.WHITE);
        
        JLabel firstMycologistLabel = new JLabel("1st Mycologist", SwingConstants.CENTER);
        firstMycologistLabel.setFont(Main.getJetBrainsFont());
        mycologistsColumn.add(firstMycologistLabel);
        
        JButton firstMycologistButton = new JButton(selectedMycologists.get(0));
        firstMycologistButton.setMaximumSize(new Dimension(500, 200));
        firstMycologistButton.setUI(Main.getStyledButton());
        mycologistsColumn.add(firstMycologistButton);
        
        characterPanel.add(mycologistsColumn);

        // Right column: Insects
        JPanel insectsColumn = new JPanel(new GridLayout(2, 1, 10, 10));
        insectsColumn.setBackground(Color.WHITE);
        
        JLabel firstInsectLabel = new JLabel("1st Insect", SwingConstants.CENTER);
        firstInsectLabel.setFont(Main.getJetBrainsFont());
        insectsColumn.add(firstInsectLabel);
        
        JButton firstInsectButton = new JButton();
        firstInsectButton.setBackground(selectedInsectColors.get(0));
        firstInsectButton.setMaximumSize(new Dimension(500, 200));
        firstInsectButton.setUI(Main.getStyledButton());
        insectsColumn.add(firstInsectButton);
        
        characterPanel.add(insectsColumn);

        // Left column: 2nd Mycologist
        JPanel secondMycologistsColumn = new JPanel(new GridLayout(2, 1, 10, 10));
        secondMycologistsColumn.setBackground(Color.WHITE);
        
        JLabel secondMycologistLabel = new JLabel("2nd Mycologist", SwingConstants.CENTER);
        secondMycologistLabel.setFont(Main.getJetBrainsFont());
        secondMycologistsColumn.add(secondMycologistLabel);
        
        JButton secondMycologistButton = new JButton(selectedMycologists.get(1));
        secondMycologistButton.setMaximumSize(new Dimension(500, 200));
        secondMycologistButton.setUI(Main.getStyledButton());
        secondMycologistsColumn.add(secondMycologistButton);
        
        characterPanel.add(secondMycologistsColumn);

        // Right column: 2nd Insect
        JPanel secondInsectsColumn = new JPanel(new GridLayout(2, 1, 10, 10));
        secondInsectsColumn.setBackground(Color.WHITE);
        
        JLabel secondInsectLabel = new JLabel("2nd Insect", SwingConstants.CENTER);
        secondInsectLabel.setFont(Main.getJetBrainsFont());
        secondInsectsColumn.add(secondInsectLabel);
        
        JButton secondInsectButton = new JButton();
        secondInsectButton.setBackground(selectedInsectColors.get(1));
        secondInsectButton.setMaximumSize(new Dimension(500, 200));
        secondInsectButton.setUI(Main.getStyledButton());
        secondInsectsColumn.add(secondInsectButton);
        
        characterPanel.add(secondInsectsColumn);

        mainPanel.add(characterPanel, BorderLayout.CENTER);

        // Start Game button at the bottom
        JButton startGameButton = new JButton("Start Game");
        startGameButton.setPreferredSize(new Dimension(450, 120));
        startGameButton.setUI(Main.getStyledButton());
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (lock) {
                    lock.notifyAll();
                }
                dispose();
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(startGameButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }
}