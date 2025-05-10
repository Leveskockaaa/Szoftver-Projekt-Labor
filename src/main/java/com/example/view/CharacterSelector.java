package com.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CharacterSelector extends JFrame {
    private final List<String> selectedMushroomerTypes = new ArrayList<>();
    private final List<String> selectedInsectologists = new ArrayList<>();
    private final List<Color> selectedColors = new ArrayList<>();
    
    // Mushroom types
    private final String[] mushroomTypes = {"Capulon", "Gilledon", "Hyphara", "Poralia"};
    
    // Used Catpuccin#Mocha colors
    private static final int RED = 0xf38ba8;
    private static final int BLUE = 0x89b4fa;
    private static final int GREEN = 0xa6e3a1;
    private static final int YELLOW = 0xf9e2af;
    private static final int ORANGE = 0xfab387;
    private static final int PINK = 0xf5c2e7;

    // Entomolgist colors
    private final Color[] colors = {
        new Color(RED),
        new Color(BLUE),
        new Color(GREEN),
        new Color(YELLOW),
        new Color(ORANGE),
        new Color(PINK)
    };
    private final String[] colorNames = {"Red", "Blue", "Green", "Yellow", "Orange", "Pink"};
    
    // JetBrains Mono font
    String jetBrainsMonoFont = "JetBrains Mono";
    private final Font jetBrainsFont = new Font(jetBrainsMonoFont, Font.PLAIN, 14);
    private final Font jetBrainsFontBold = new Font(jetBrainsMonoFont, Font.BOLD, 18);
    private final Font jetBrainsFontTitle = new Font(jetBrainsMonoFont, Font.BOLD, 24);

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
        UIManager.put("Button.font", jetBrainsFontBold);
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
        startButton.setPreferredSize(new Dimension(400, 100));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(BLUE));
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
                "Choose a mushroom type for Mushroomer " + (selectedMushroomerTypes.size() + 1) : 
                "Choose a color for Insectologist " + (selectedInsectologists.size() + 1);
        
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
            selectionPanel.setPreferredSize(new Dimension(420, 120)); // Control the overall size
            
            // Display mushroom types
            for (final String type : mushroomTypes) {
                JButton button = new JButton(type);
                button.setForeground(Color.WHITE);
                button.setBackground(new Color(GREEN));
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
                        selectedInsectologists.add("Insectologist");
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
        mushroomerPanel.setBorder(BorderFactory.createTitledBorder("Mushroomers"));
        for (int i = 0; i < selectedMushroomerTypes.size(); i++) {
            JLabel label = new JLabel("Mushroomer " + (i+1) + ": " + selectedMushroomerTypes.get(i));
            label.setFont(jetBrainsFont);
            mushroomerPanel.add(label);
        }
        
        // Insectologist panel
        JPanel insectologistPanel = new JPanel(new GridLayout(2, 1));
        insectologistPanel.setBorder(BorderFactory.createTitledBorder("Insectologists"));
        for (int i = 0; i < selectedInsectologists.size(); i++) {
            JPanel insectologistInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel label = new JLabel("Insectologist " + (i+1) + ": ");
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
        JButton newGameButton = new JButton("New Game");
        newGameButton.setUI(styledButton);
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStartScreen();
            }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(newGameButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        setContentPane(panel);
        revalidate();
        repaint();
    }
    
    public static void main(String[] args) {
        // Run Swing application on EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CharacterSelector().setVisible(true);
            }
        });
    }
}