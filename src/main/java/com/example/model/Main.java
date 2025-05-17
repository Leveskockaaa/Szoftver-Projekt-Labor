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


        Entomologist entomologist = new Entomologist("e1");
        Insect i1 = new Insect(entomologist);
        Insect i2 = new Insect(entomologist);
       
        i1.setColor(new Color(firstInsectColor.getRGB()));
       
        i2.setColor(secondInsectColor);

        Tecton t5 = gameTable.getTectons().get(4);
        t5.placeInsect(i1);
        t5.placeInsect(i2);


        Mycologist mycologist = new Mycologist("my1");
        mycologist.setType(firstMycologist);
        Mycelium mycelium = new Mycelium(t5, mycologist);
        Mycelium mycelium2 = new Mycelium(t5, mycologist);
        Mycelium mycelium3 = new Mycelium(t5, mycologist);

        switch (firstMycologist) {
            case "Hyphara":
                new Hyphara(t5, mycologist);
                break;
            case "Gilledon":
                new Gilledon(t5, mycologist);
                break;
            case "Poralia":
                new Poralia(t5, mycologist);
                break;
            case "Capulon":
                new Capulon(t5, mycologist);
                break;
            default:
                System.out.println("Invalid Mycologist type");
                break;
        }   
        mainFrame.showGameTable(gameTable);
        // END - Gameplay
    }
}