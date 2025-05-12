package com.example.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.example.model.Main;

import util.Colors;

public class EntomologistSelector extends JPanel {
    private final transient Object lock = new Object();
    private Color selectedColor;
    
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
        setLayout(new BorderLayout());
        setupUI();
    }

    public Object getLock() {
        return lock;
    }
    
    private void setupUI() {
        // Header
        String header = "Choose a color for Entomologist";
        JLabel titleLabel = new JLabel(header, SwingConstants.CENTER);
        titleLabel.setFont(Main.getJetBrainsFontBold());
        this.add(titleLabel, BorderLayout.NORTH);
        
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
            button.addActionListener(event -> {
                selectedColor = color;

                synchronized (lock) {
                    lock.notifyAll();
                }
            });
            selectionPanel.add(button);
        }
        contentPanel.add(selectionPanel);
        
        this.add(contentPanel, BorderLayout.CENTER);
    }
    
    public Color getSelectedColor() {
        return selectedColor;
    }
}
