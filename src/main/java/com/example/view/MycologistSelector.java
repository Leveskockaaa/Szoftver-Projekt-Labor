package com.example.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.example.model.Main;

import util.Colors;

public class MycologistSelector extends JFrame {
    private final transient Object lock = new Object();
    private String selectedMushroomBodyType;
    private final String[] mushroomTypes = {"Capulon", "Gilledon", "Hyphara", "Poralia"};
    
    public MycologistSelector() {
        // Basic window settings
        setTitle("Mycologist Selection");
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setupUI();
    }
    
    public Object getLock() {
        return lock;
    }

    private void setupUI() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Header
        String header = "Choose a mushroom type for Mycologist";
        JLabel titleLabel = new JLabel(header, SwingConstants.CENTER);
        titleLabel.setFont(Main.getJetBrainsFontBold());
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Main content panel
        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel for mushroom selection with fixed size
        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new GridLayout(2, 2, 10, 10)); // 2x2 grid with 10px gap
        selectionPanel.setPreferredSize(new Dimension(500, 200)); // Control the overall size
        
        // Display mushroom types
        for (final String type : mushroomTypes) {
            var button = new JButton(type);
            button.setForeground(Color.WHITE);
            button.setBackground(new Color(Colors.GREEN));
            button.setUI(Main.getStyledButton());
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedMushroomBodyType = type;

                    synchronized (lock) {
                        lock.notifyAll();
                    }
                    dispose();
                }
            });
            selectionPanel.add(button);
        }
        contentPanel.add(selectionPanel);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        setContentPane(panel);
    }
    
    // public void setSelectedMushrooms(List<String> mushrooms) {
    //     this.selectedMushrooms.clear();
    //     this.selectedMushrooms.addAll(mushrooms);
    //     Main.setSelectedMushrooms(selectedMushrooms);
        
    //     // Update the header text
    //     JPanel contentPane = (JPanel) getContentPane();
    //     JLabel titleLabel = (JLabel) ((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.NORTH);
    //     titleLabel.setText("Choose a mushroom type for Mycologist " + (selectedMushrooms.size() + 1));
    // }

    public String getSelectedMushroomBodyType() {
        return selectedMushroomBodyType;
    }
}

