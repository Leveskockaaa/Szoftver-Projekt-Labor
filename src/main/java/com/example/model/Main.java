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

        // START - Gameplay
        GameTable gameTable = new GameTable(mainFrame.getWidth(), mainFrame.getHeight(), "gt");
        gameTable.initialize();

        Tecton toBeBroken = gameTable.getTectons().get(0);
        for(Tecton tecton : toBeBroken.breakApart("b1", "b2")){
            gameTable.addTecton(tecton);
        }
        gameTable.removeTecton(toBeBroken);

//        toBeBroken = gameTable.getTectons().get(0);
//        for(Tecton tecton : toBeBroken.breakApart("b3", "b4")){
//            gameTable.addTecton(tecton);
//        }
//        gameTable.removeTecton(toBeBroken);

        Tecton t0 = gameTable.getTectons().get(0);
        Tecton t1 = gameTable.getTectons().get(1);
        Mycologist mc = new Mycologist("mc1");
        t0.getMycelia().add(new Mycelium(t0,mc));
        t0.getMycelia().get(0).createNewBranch(t1);
//        t1.getMycelia().add(new Mycelium(t0,mc,"my2"));

        Entomologist entomologist = new Entomologist("e1");
        Insect i1 = new Insect(entomologist);
        i1.setColor(firstInsectColor);
        Tecton t5 = gameTable.getTectons().get(4);
        t5.placeInsect(i1);


        mainFrame.showGameTable(gameTable);
        // END - Gameplay
    }
}