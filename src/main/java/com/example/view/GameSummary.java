package com.example.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.example.model.Main;

public class GameSummary extends JFrame {
    private final List<String> selectedMushrooms;
    private final List<Color> selectedColors;
    
    public GameSummary(List<String> selectedMushrooms, List<Color> selectedColors) {
        this.selectedMushrooms = selectedMushrooms;
        this.selectedColors = selectedColors;
        
        // Basic window settings
        setTitle("Game Summary");
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setupUI();
    }
    
    private void setupUI() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Title
        JLabel titleLabel = new JLabel("Start Game", SwingConstants.CENTER);
        titleLabel.setFont(Main.getJetBrainsFontTitle());
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Character info panel
        JPanel infoPanel = new JPanel(new GridLayout(1, 2));
        
        // Mushroomer panel
        JPanel mushroomerPanel = new JPanel(new GridLayout(2, 1));
        mushroomerPanel.setBorder(BorderFactory.createTitledBorder("Mycologists"));
        for (int i = 0; i < selectedMushrooms.size(); i++) {
            JLabel label = new JLabel("Mycologist " + (i+1) + ": " + selectedMushrooms.get(i));
            label.setFont(Main.getJetBrainsFont());
            mushroomerPanel.add(label);
        }
        
        // Insectologist panel
        JPanel insectologistPanel = new JPanel(new GridLayout(2, 1));
        insectologistPanel.setBorder(BorderFactory.createTitledBorder("Entomologists"));
        for (int i = 0; i < selectedColors.size(); i++) {
            JPanel insectologistInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel label = new JLabel("Entomologist " + (i+1) + ": ");
            label.setFont(Main.getJetBrainsFont());
            
            JPanel colorPanel = new JPanel();
            colorPanel.setBackground(selectedColors.get(i));
            colorPanel.setPreferredSize(new Dimension(20, 20));
            
            insectologistInfo.add(label);
            insectologistInfo.add(colorPanel);
            insectologistPanel.add(insectologistInfo);
        }
        
        infoPanel.add(mushroomerPanel);
        infoPanel.add(insectologistPanel);
        panel.add(infoPanel, BorderLayout.CENTER);
        
        // Start game button
        JButton startGameButton = new JButton("Start Game");
        startGameButton.setPreferredSize(new Dimension(450, 120));
        startGameButton.setUI(Main.getStyledButton());
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close this window
                // Main.startGame(); // Start the actual game
            }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startGameButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        setContentPane(panel);
    }
}
