package com.example.model;

import java.awt.Color;
import java.util.List;

import com.example.Controller;
import com.example.view.MainFrame;

public class Main {
    private static MainFrame mainFrame;
    private static Controller controller;

    public static void main(String[] args) {
        mainFrame = new MainFrame();
        controller = new Controller();

        mainFrame.showGameScreen(controller.getGameTable());


    }
}