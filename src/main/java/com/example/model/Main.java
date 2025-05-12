package com.example.model;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.UIManager;

import com.example.view.MainFrame;

import util.StyledButton;

/**
 * A fő osztály, amely a program belépési pontját tartalmazza.
 */
public class Main {
    private static MainFrame mainFrame = new MainFrame();

    // Fonts and UI elements that will be shared across screens
    private static final String jetBrainsMonoFont = "JetBrains Mono";
    private static final Font jetBrainsFont = new Font(jetBrainsMonoFont, Font.BOLD, 30);
    private static final Font jetBrainsFontBold = new Font(jetBrainsMonoFont, Font.BOLD, 36);
    private static final Font jetBrainsFontTitle = new Font(jetBrainsMonoFont, Font.BOLD, 48);
    private static final StyledButton styledButton = new StyledButton();

    public static void main(String[] args) {
        // Set UI font for the entire application
        setUIFont();

        mainFrame = new MainFrame();
        mainFrame.showStartScreen();

        String firstMycologist = mainFrame.showMycologistSelector();
        String secondMycologist = mainFrame.showMycologistSelector();
        System.out.println("Selected Mushroom Body Types: " + firstMycologist + ", " + secondMycologist);

        Color firstInsectColor = mainFrame.showEntomologistSelector();
        Color secondInsectColor = mainFrame.showEntomologistSelector();
        System.out.println("Selected Insect Colors: " + firstInsectColor + ", " + secondInsectColor);

        mainFrame.showGameSummary(List.of(firstMycologist, secondMycologist), List.of(firstInsectColor, secondInsectColor));
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