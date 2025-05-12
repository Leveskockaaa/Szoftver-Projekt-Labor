package com.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import util.StyledButton;
import util.Colors;

public class CharacterSelector extends JFrame {
    private final List<String> selectedMushroomerTypes = new ArrayList<>();
    private final List<String> selectedInsectologists = new ArrayList<>();
    private final List<Color> selectedColors = new ArrayList<>();
    
    // Mushroom types
    private final String[] mushroomTypes = {"Capulon", "Gilledon", "Hyphara", "Poralia"};

    // Entomolgist colors
    private final Color[] colors = {
        new Color(Colors.RED),
        new Color(Colors.BLUE),
        new Color(Colors.GREEN),
        new Color(Colors.YELLOW),
        new Color(Colors.ORANGE),
        new Color(Colors.PINK)
    };
    private final String[] colorNames = {"Red", "Blue", "Green", "Yellow", "Orange", "Pink"};
    
    // JetBrains Mono font
    String jetBrainsMonoFont = "JetBrains Mono";
    private final Font jetBrainsFont = new Font(jetBrainsMonoFont, Font.BOLD, 30);
    private final Font jetBrainsFontBold = new Font(jetBrainsMonoFont, Font.BOLD, 36);
    private final Font jetBrainsFontTitle = new Font(jetBrainsMonoFont, Font.BOLD, 48);

    private final StyledButton styledButton = new StyledButton();
    
    public CharacterSelector() {
        // Basic window settings
        setTitle("Character Selector Game");
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Set UI font
        setUIFont();
        
        // Show the initial screen
        showStartScreen();
    }
    
    // Set JetBrains Mono font for all UI components
    private void setUIFont() {
        UIManager.put("Button.font", jetBrainsFont);
        UIManager.put("Label.font", jetBrainsFont);
        UIManager.put("TextField.font", jetBrainsFont);
        UIManager.put("ComboBox.font", jetBrainsFont);
        UIManager.put("CheckBox.font", jetBrainsFont);
        UIManager.put("RadioButton.font", jetBrainsFont);
        UIManager.put("TitledBorder.font", jetBrainsFontBold);
    }
    
    private void showStartScreen() {
        // Create panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        // Create title
        JLabel titleLabel = new JLabel("Character Selection", SwingConstants.CENTER);
        titleLabel.setFont(jetBrainsFontTitle);
        panel.add(titleLabel, BorderLayout.CENTER);
        
        // Create start button
        JButton startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(450, 120));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(Colors.BLUE));
        startButton.setUI(styledButton);
        startButton.setFont(jetBrainsFontBold);
        
        // Center the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Button event handler
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedMushroomerTypes.clear();
                selectedInsectologists.clear();
                selectedColors.clear();
                showCharacterSelection();
            }
        });
        
        // Set panel to window
        setContentPane(panel);
        revalidate();
        repaint();
    }
    
    private void showCharacterSelection() {
        // If all characters are selected, start the game
        if (selectedMushroomerTypes.size() >= 2 && selectedInsectologists.size() >= 2) {
            showGameStart();
            return;
        }
        
        JPanel panel = new JPanel(new BorderLayout());
        
        // Header
        String header = selectedMushroomerTypes.size() < 2 ? 
                "Choose a mushroom type for Mycologist " + (selectedMushroomerTypes.size() + 1) : 
                "Choose a color for Entomologist " + (selectedInsectologists.size() + 1);
        
        JLabel titleLabel = new JLabel(header, SwingConstants.CENTER);
        titleLabel.setFont(jetBrainsFontBold);
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Main content panel
        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        if (selectedMushroomerTypes.size() < 2) {
            // Panel for mushroom selection with fixed size
            JPanel selectionPanel = new JPanel();
            selectionPanel.setLayout(new GridLayout(2, 2, 10, 10)); // 2x2 grid with 10px gap
            selectionPanel.setPreferredSize(new Dimension(500, 200)); // Control the overall size
            
            // Display mushroom types
            for (final String type : mushroomTypes) {
                JButton button = new JButton(type);
                button.setForeground(Color.WHITE);
                button.setBackground(new Color(Colors.GREEN));
                button.setUI(styledButton);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedMushroomerTypes.add(type);
                        showCharacterSelection();
                    }
                });
                selectionPanel.add(button);
            }
            contentPanel.add(selectionPanel);
        } else {
            // Panel for color selection
            JPanel selectionPanel = new JPanel();
            selectionPanel.setLayout(new GridLayout(3, 2, 10, 10));
            selectionPanel.setPreferredSize(new Dimension(500, 300));

            // Display colors
            for (int i = 0; i < colors.length; i++) {
                final Color color = colors[i];
                final String colorName = colorNames[i];
                
                JButton button = new JButton(colorName);
                button.setUI(styledButton);
                button.setBackground(color);
                button.setForeground(Color.WHITE);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedInsectologists.add("Entomologist ");
                        selectedColors.add(color);
                        showCharacterSelection();
                    }
                });
                selectionPanel.add(button);
            }
            contentPanel.add(selectionPanel);
        }
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        setContentPane(panel);
        revalidate();
        repaint();
    }
    
    private void showGameStart() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Title
        JLabel titleLabel = new JLabel("Start Game", SwingConstants.CENTER);
        titleLabel.setFont(jetBrainsFontTitle);
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Character info panel
        JPanel infoPanel = new JPanel(new GridLayout(1, 2));
        
        // Mushroomer panel
        JPanel mushroomerPanel = new JPanel(new GridLayout(2, 1));
        mushroomerPanel.setBorder(BorderFactory.createTitledBorder("Mycologists"));
        for (int i = 0; i < selectedMushroomerTypes.size(); i++) {
            JLabel label = new JLabel("Mycologist " + (i+1) + ": " + selectedMushroomerTypes.get(i));
            label.setFont(jetBrainsFont);
            mushroomerPanel.add(label);
        }
        
        // Insectologist panel
        JPanel insectologistPanel = new JPanel(new GridLayout(2, 1));
        insectologistPanel.setBorder(BorderFactory.createTitledBorder("Entomologists"));
        for (int i = 0; i < selectedInsectologists.size(); i++) {
            JPanel insectologistInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel label = new JLabel("Entomologist " + (i+1) + ": ");
            label.setFont(jetBrainsFont);
            
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
        
        // New game button
        JButton startGameButton = new JButton("Start Game");
        startGameButton.setPreferredSize(new Dimension(450, 120));
        startGameButton.setUI(styledButton);
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStartScreen();
            }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startGameButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        setContentPane(panel);
        revalidate();
        repaint();
    }
}