package com.example.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import com.example.model.GameTable;

import util.FontStyles;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Game");
        setSize(1600, 900);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        setUIFont();
    }

    public void setUIFont() {
        UIManager.put("Button.font", FontStyles.JETBRAINS_MONO_FONT);
        UIManager.put("Label.font", FontStyles.JETBRAINS_MONO_FONT);
        UIManager.put("TextField.font", FontStyles.JETBRAINS_MONO_FONT);
        UIManager.put("ComboBox.font", FontStyles.JETBRAINS_MONO_FONT);
        UIManager.put("CheckBox.font", FontStyles.JETBRAINS_MONO_FONT);
        UIManager.put("RadioButton.font", FontStyles.JETBRAINS_MONO_FONT);
        UIManager.put("TitledBorder.font", FontStyles.JETBRAINS_MONO_BOLD_FONT);
    }

    public void showStartScreen() {
        StartScreen startScreen = new StartScreen();
        setContentPane(startScreen);
        revalidate();
        repaint();

        synchronized (startScreen.getLock()) {
            try {
                startScreen.getLock().wait();
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                System.err.println("An error occurred while waiting for the start screen lock: " + exception.getMessage());
            }
        }
    }

    public Color showEntomologistSelector() {
        EntomologistSelector entomologistSelector = new EntomologistSelector();
        setContentPane(entomologistSelector);
        revalidate();
        repaint();

        synchronized (entomologistSelector.getLock()) {
            try {
                entomologistSelector.getLock().wait();
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                System.err.println("An error occurred while waiting for the start screen lock: " + exception.getMessage());
            }
        }
        System.out.println("Selected color: " + entomologistSelector.getSelectedColor());
        return entomologistSelector.getSelectedColor();
    }

    public String showMycologistSelector() {
        MycologistSelector mycologistSelector = new MycologistSelector();
        setContentPane(mycologistSelector);
        revalidate();
        repaint();

        synchronized (mycologistSelector.getLock()) {
            try {
                mycologistSelector.getLock().wait();
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                System.err.println("An error occurred while waiting for the start screen lock: " + exception.getMessage());
            }
        }

        return mycologistSelector.getSelectedMushroomBodyType();
    }

//    public void showGameScreen(GameTable gameTable) {
//        gameTable.initialize();
//        GameTableView gameScreen = new GameTableView(gameTable);
//        this.add(gameScreen.layeredPane, BorderLayout.CENTER);
//        revalidate();
//        repaint();
//
//
//    }

    public void showGameSummary(List<String> mycologists, List<Color> insectColors) {
        GameSummary gameSummary = new GameSummary(mycologists, insectColors);
        setContentPane(gameSummary);
        revalidate();
        repaint();

        synchronized (gameSummary.getLock()) {
            try {
                gameSummary.getLock().wait();
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                System.err.println("An error occurred while waiting for the start screen lock: " + exception.getMessage());
            }
        }
    }

    public void showGameTable(GameTable gameTable) {
        GameTableView gameTableView = new GameTableView(gameTable);
        this.add(gameTableView, BorderLayout.CENTER);
        setContentPane(gameTableView);
        revalidate();
        repaint();
    }
}
