package com.example.model;

import java.awt.Color;
import java.util.List;

import com.example.Controller;
import com.example.view.MainFrame;

/**
 * A fő osztály, amely a program belépési pontját tartalmazza.
 */
public class Main {
    private static MainFrame mainFrame;
    private static Controller controller;

    public static void main(String[] args) {
        mainFrame = new MainFrame();
        controller = new Controller();

        mainFrame.showGameScreen(controller.getGameTable());

        // START - Gameplay
        GameTable gameTable = new GameTable(mainFrame.getWidth(), mainFrame.getHeight(), "gt");
        gameTable.initialize();

        Tecton toBeBroken = gameTable.getTectons().get(0);
        for(Tecton tecton : toBeBroken.breakApart("b1", "b2")){
            gameTable.addTecton(tecton);
        }
        gameTable.removeTecton(toBeBroken);

        toBeBroken = gameTable.getTectons().get(0);
        for(Tecton tecton : toBeBroken.breakApart("b3", "b4")){
            gameTable.addTecton(tecton);
        }
        gameTable.removeTecton(toBeBroken);

        Tecton t0 = gameTable.getTectons().get(0);
        Tecton t1 = gameTable.getTectons().get(1);
        Mycologist mc = new Mycologist("mc1");
        t0.getMycelia().add(new Mycelium(t0,mc,"my1"));
        t0.getMycelia().get(0).createNewBranch(t1, "my2");
//        t1.getMycelia().add(new Mycelium(t0,mc,"my2"));

        mainFrame.showGameTable(gameTable);
        // END - Gameplay
    }
}