package com.example.model;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;

import com.example.view.EntomologistSelector;
import com.example.view.GameSummary;
import com.example.view.MycologistSelector;
import com.example.view.StartScreen;

import util.StyledButton;

/**
 * A fő osztály, amely a program belépési pontját tartalmazza.
 */
public class Main {
    // These lists will store the selected mushroom types and insect colors
    private static List<String> selectedMushroomBodyTypes = new ArrayList<>();
    private static List<Color> selectedInsectColors = new ArrayList<>();
    
    // Fonts and UI elements that will be shared across screens
    private static final String jetBrainsMonoFont = "JetBrains Mono";
    private static final Font jetBrainsFont = new Font(jetBrainsMonoFont, Font.BOLD, 30);
    private static final Font jetBrainsFontBold = new Font(jetBrainsMonoFont, Font.BOLD, 36);
    private static final Font jetBrainsFontTitle = new Font(jetBrainsMonoFont, Font.BOLD, 48);
    private static final StyledButton styledButton = new StyledButton();
    
    public static void main(String[] args) {
        // Set UI font for the entire application
        setUIFont();
        
        StartScreen startScreen = new StartScreen();
        startScreen.setVisible(true);

        try {
            synchronized (startScreen.getLock()) {
                startScreen.getLock().wait();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }


        MycologistSelector firstMycologistSelector = new MycologistSelector();
        firstMycologistSelector.setVisible(true);

        try {
            synchronized (firstMycologistSelector.getLock()) {
                firstMycologistSelector.getLock().wait();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

        String firstMushroomBodyType = firstMycologistSelector.getSelectedMushroomBodyType();

        MycologistSelector secondMycologistSelector = new MycologistSelector();
        secondMycologistSelector.setVisible(true);

        try {
            synchronized (secondMycologistSelector.getLock()) {
                secondMycologistSelector.getLock().wait();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

        String secondMushroomBodyType = firstMycologistSelector.getSelectedMushroomBodyType();

        System.out.println("Selected Mushroom Body Types: " + firstMushroomBodyType + ", " + secondMushroomBodyType);
    }
    
    // Set JetBrains Mono font for all UI components
    private static void setUIFont() {
        UIManager.put("Button.font", jetBrainsFont);
        UIManager.put("Label.font", jetBrainsFont);
        UIManager.put("TextField.font", jetBrainsFont);
        UIManager.put("ComboBox.font", jetBrainsFont);
        UIManager.put("CheckBox.font", jetBrainsFont);
        UIManager.put("RadioButton.font", jetBrainsFont);
        UIManager.put("TitledBorder.font", jetBrainsFontBold);
    }
    
    // Method to proceed to mycologist selection
    public static void startMycologistSelection() {
        // Clear previous selections when starting a new game
        selectedMushroomBodyTypes.clear();
        selectedInsectColors.clear();
        
        MycologistSelector mycologistSelector = new MycologistSelector();
        mycologistSelector.setVisible(true);
    }

    public static void setSelectedMushrooms(List<String> mushrooms) {
        selectedMushroomBodyTypes.addAll(mushrooms);
    }
    
    // Method to proceed to entomologist selection with mushroom selections
    public static void startEntomologistSelection(List<String> mushroomTypes) {
        // Store the selected mushroom types
        selectedMushroomBodyTypes.addAll(mushroomTypes);
        
        EntomologistSelector entomologistSelector = new EntomologistSelector();
        entomologistSelector.setVisible(true);
    }

    public static void setSelectedInsectColors(List<Color> insectColors) {
        selectedInsectColors.addAll(insectColors);
    }
    
    // Method to proceed to game summary with color selections
    public static void startGameSummary(List<Color> insectColors) {
        // Store the selected insect colors
        selectedInsectColors.addAll(insectColors);
        
        GameSummary gameSummary = new GameSummary(selectedMushroomBodyTypes, selectedInsectColors);
        gameSummary.setVisible(true);
    }
    
    // Method to start the actual game after summary
    public static void startGame() {
        // This is where you would start the actual game using the selected characters
        // For now, we just go back to the start screen
        StartScreen startScreen = new StartScreen();
        startScreen.setVisible(true);
    }
    
    // Getter methods to access selections from other parts of the application
    public static List<String> getSelectedMushroomBodyTypes() {
        return selectedMushroomBodyTypes;
    }
    
    public static List<Color> getSelectedInsectColors() {
        return selectedInsectColors;
    }
    
    // Method to get shared UI components
    public static Font getJetBrainsFont() {
        return jetBrainsFont;
    }
    
    public static Font getJetBrainsFontBold() {
        return jetBrainsFontBold;
    }
    
    public static Font getJetBrainsFontTitle() {
        return jetBrainsFontTitle;
    }
    
    public static StyledButton getStyledButton() {
        return styledButton;
    }
}