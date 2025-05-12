package com.example.view;

import java.awt.Color;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Game");
        setSize(1600, 900);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
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
}
