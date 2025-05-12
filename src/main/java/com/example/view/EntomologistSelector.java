package com.example.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.example.model.Main;

import util.Colors;

public class EntomologistSelector extends JFrame {
    private final List<Color> selectedColors = new ArrayList<>();
    private final List<String> selectedEntomologists = new ArrayList<>();
    
    // Entomologist colors
    private final Color[] colors = {
        new Color(Colors.RED),
        new Color(Colors.BLUE),
        new Color(Colors.GREEN),
        new Color(Colors.YELLOW),
        new Color(Colors.ORANGE),
        new Color(Colors.PINK)
    };
    private final String[] colorNames = {"Red", "Blue", "Green", "Yellow", "Orange", "Pink"};
    
    public EntomologistSelector() {
        // Basic window settings
        setTitle("Entomologist Selection");
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setupUI();
    }
    
    private void setupUI() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Header
        String header = "Choose a color for Entomologist " + (selectedColors.size() + 1);
        JLabel titleLabel = new JLabel(header, SwingConstants.CENTER);
        titleLabel.setFont(Main.getJetBrainsFontBold());
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Main content panel
        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel for color selection
        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new GridLayout(3, 2, 10, 10));
        selectionPanel.setPreferredSize(new Dimension(500, 300));

        // Display colors
        for (int i = 0; i < colors.length; i++) {
            final Color color = colors[i];
            final String colorName = colorNames[i];
            
            JButton button = new JButton(colorName);
            button.setUI(Main.getStyledButton());
            button.setBackground(color);
            button.setForeground(Color.WHITE);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedEntomologists.add("Entomologist");
                    selectedColors.add(color);
                    
                    // If we've selected 2 colors, proceed to next screen
                    if (selectedColors.size() >= 2) {
                        dispose(); // Close this window
                        Main.startGameSummary(selectedColors);
                    } else {
                        // Otherwise refresh this screen for next selection
                        dispose();
                        EntomologistSelector nextSelector = new EntomologistSelector();
                        nextSelector.setSelectedColors(selectedColors);
                        nextSelector.setSelectedEntomologists(selectedEntomologists);
                        nextSelector.setVisible(true);
                    }
                }
            });
            selectionPanel.add(button);
        }
        contentPanel.add(selectionPanel);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        setContentPane(panel);
    }
    
    public void setSelectedColors(List<Color> colors) {
        this.selectedColors.clear();
        this.selectedColors.addAll(colors);
        Main.setSelectedInsectColors(selectedColors);
        
        // Update the header text
        JPanel contentPane = (JPanel) getContentPane();
        JLabel titleLabel = (JLabel) ((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.NORTH);
        titleLabel.setText("Choose a color for Entomologist " + (selectedColors.size() + 1));
    }
    
    public void setSelectedEntomologists(List<String> entomologists) {
        this.selectedEntomologists.clear();
        this.selectedEntomologists.addAll(entomologists);
    }
}
