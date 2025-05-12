package com.example.model;

import java.awt.Color;
import java.awt.Font;
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
    // Fonts and UI elements that will be shared across screens
    private static final String jetBrainsMonoFont = "JetBrains Mono";
    private static final Font jetBrainsFont = new Font(jetBrainsMonoFont, Font.BOLD, 30);
    private static final Font jetBrainsFontBold = new Font(jetBrainsMonoFont, Font.BOLD, 36);
    private static final Font jetBrainsFontTitle = new Font(jetBrainsMonoFont, Font.BOLD, 48);
    private static final StyledButton styledButton = new StyledButton();
    public static void main(String[] args) {
        // Set UI font for the entire application
        setUIFont();

        showStartScreen();

        String firstMycologist = selectMycologistType();
        String secondMycologist = selectMycologistType();
        System.out.println("Selected Mushroom Body Types: " + firstMycologist + ", " + secondMycologist);

        Color firstInsectColor = selectInsectColor();
        Color secondInsectColor = selectInsectColor();
        System.out.println("Selected Insect Colors: " + firstInsectColor + ", " + secondInsectColor);

        showGameSummary(List.of(firstMycologist, secondMycologist), List.of(firstInsectColor, secondInsectColor));
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

    private static void showStartScreen() {
        StartScreen startScreen = new StartScreen();
        startScreen.setVisible(true);

        try {
            synchronized (startScreen.getLock()) {
                startScreen.getLock().wait();
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            System.err.println("An error occurred while waiting for the start screen lock: " + exception.getMessage());
        }
    }

    private static String selectMycologistType() {
        MycologistSelector mycologistSelector = new MycologistSelector();
        mycologistSelector.setVisible(true);

        try {
            synchronized (mycologistSelector.getLock()) {
                mycologistSelector.getLock().wait();
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            System.err.println("An error occurred while waiting for the start screen lock: " + exception.getMessage());
        }

        return mycologistSelector.getSelectedMushroomBodyType();
    }

    private static Color selectInsectColor() {
        EntomologistSelector entomologistSelector = new EntomologistSelector();
        entomologistSelector.setVisible(true);

        try {
            synchronized (entomologistSelector.getLock()) {
                entomologistSelector.getLock().wait();
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            System.err.println("An error occurred while waiting for the start screen lock: " + exception.getMessage());
        }

        return entomologistSelector.getSelectedColor();
    }

    private static void showGameSummary(List<String> selectedMycologists, List<Color> selectedInsectColors) {
        GameSummary gameSummary = new GameSummary(selectedMycologists, selectedInsectColors);
        gameSummary.setVisible(true);

        try {
            synchronized (gameSummary.getLock()) {
                gameSummary.getLock().wait();
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            System.err.println("An error occurred while waiting for the start screen lock: " + exception.getMessage());
        }
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