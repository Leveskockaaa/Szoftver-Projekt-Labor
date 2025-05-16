package com.example.model;

import java.awt.Color;
import java.util.List;
import com.example.Controller;
import com.example.view.*;

import com.example.view.MainFrame;
import java.io.IOException;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.List;

/**
 * A fő osztály, amely a program belépési pontját tartalmazza.
 */
public class Main {
    private static MainFrame mainFrame = new MainFrame();

    public static void main(String[] args) {
        mainFrame = new MainFrame();

        // BEGIN - Character Selection
        mainFrame.showStartScreen();

        // Get the two types of mushrooms
        String firstMycologist = mainFrame.showMycologistSelector();
        String secondMycologist = mainFrame.showMycologistSelector();
        System.out.println("Selected Mushroom Body Types: " + firstMycologist + ", " + secondMycologist);

        // Get the two colors of insects
        Color firstInsectColor = mainFrame.showEntomologistSelector();
        Color secondInsectColor = mainFrame.showEntomologistSelector();
        System.out.println("Selected Insect Colors: " + firstInsectColor + ", " + secondInsectColor);

        mainFrame.showGameSummary(List.of(firstMycologist, secondMycologist), List.of(firstInsectColor, secondInsectColor));
        // END - Character Selection


    }
}