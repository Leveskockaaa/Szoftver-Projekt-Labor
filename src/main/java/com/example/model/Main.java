package com.example.model;

import java.awt.Color;
import java.util.List;

import com.example.view.MainFrame;

/**
 * A fő osztály, amely a program belépési pontját tartalmazza.
 */
public class Main {
    private static MainFrame mainFrame = new MainFrame();

    public static void main(String[] args) {
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
}