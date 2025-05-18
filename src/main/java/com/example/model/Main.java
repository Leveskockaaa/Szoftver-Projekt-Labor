package com.example.model;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import com.example.Controller;
import com.example.view.MainFrame;

/**
 * A fő osztály, amely a program belépési pontját tartalmazza.
 */
public class Main {
    private static MainFrame mainFrame;

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

        mainFrame.showGameSummary(List.of(firstMycologist, secondMycologist), List.of(firstInsectColor, secondInsectColor));
        // END - Character Selection

        Controller controller = new Controller(Arrays.asList(firstMycologist, secondMycologist), Arrays.asList(firstInsectColor, secondInsectColor));
        mainFrame.setController(controller);

        mainFrame.showGameTable(controller.getGameTable());


        // TODO: Implement proper threading so the window only updates when something has actually changed
        // while (true) {
        //     System.out.println("Game Running...");
        //     mainFrame.showGameTable(controller.getGameTable());
        //     try {
        //         Thread.sleep(1000); // Sleep for 1 second
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        // }


    }
}