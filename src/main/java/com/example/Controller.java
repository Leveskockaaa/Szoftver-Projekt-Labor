package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Controller {
    private static List<String> commandsList = new ArrayList<>();
    private static HashMap<Object, String> nameMap = new HashMap<>();
    private static int seconds;
    private static boolean isRandomOn;



    public static Object getFromNameMap(String name) {
        for (Object object : nameMap.keySet()) {
            if (nameMap.get(object).equals(name)) {
                return object;
            }
        }
        return null;
    }

    public void runCommand(String command) {
        boolean isCommandValid = validateCommand(command);
        if (!isCommandValid) {
            System.out.println("Invalid command: " + command);
            return;
        }

        String[] commandParts = command.split(" ");
        String commandName = commandParts[0].toUpperCase();
        try {
            switch (commandName) {
                case "STATUS"            -> status(commandParts);
                case "CREATEGAMETABLE"   -> createGameTable(commandParts);
                case "CREATETECTON"      -> createTecton(commandParts);
                case "CREATEPLAYER"      -> createPlayer(commandParts);
                case "CREATEINSECT"      -> createInsect(commandParts);
                case "CREATEMUSHROOMBODY"-> createMushroomBody(commandParts);
                case "CREATESPORE"       -> createSpore(commandParts);
                case "CREATEMYCELIUM"    -> createMycelium(commandParts);
                case "MOVE"              -> move(commandParts);
                case "BREAK"             -> breakCommand(commandParts);
                case "RANDOM"            -> random(commandParts);
                case "EATSPORE"          -> eatSpore(commandParts);
                case "CHEWMYCELIUM"      -> chewMycelium(commandParts);
                case "SPREADSPORES"      -> spreadSpores(commandParts);
                case "EVOLVESUPER"       -> evolveSuper(commandParts);
                case "NEIGHBORS"         -> neighbors(commandParts);
                case "GROWTO"            -> growTo(commandParts);
                case "GROWBODY"          -> growBody(commandParts);
                case "DELAY"             -> delay(commandParts);
                case "ENDGAME"           -> endGame(commandParts);
                case "LOAD"              -> load(commandParts);
                case "SAVE"              -> save(commandParts);
                case "EXIT"              -> exit(commandParts);
                default                  -> throw new AssertionError();
            }

            commandsList.add(command);
            System.out.println("[INFO] Command executed successfuly: " + command);
        } catch (Exception exception) {
            System.out.println("[ERROR] Exception has been thrown while executing command: " + command);
            exception.printStackTrace();
        }
    }

    public int chooseTest() {
        return 0;
    }

    public void runTest(int testNumber) {

    }

    private boolean validateCommand(String command) {
        return commandsList.contains(command);
    }

    public void status(String[] commandParts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void createGameTable(String[] commandParts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void createTecton(String[] commandParts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void createPlayer(String[] commandParts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void createInsect(String[] commandParts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void createMushroomBody(String[] commandParts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void createSpore(String[] commandParts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void createMycelium(String[] commandParts) {
        String myceliumName = commandParts[1];
        String mycologistName = commandParts[2];
        String tectonName = commandParts[3];

        Mycologist mycologist = (Mycologist) getFromNameMap(mycologistName);
        Tecton tecton = (Tecton) getFromNameMap(tectonName);

        if (mycologist == null || tecton == null) return;

        Mycelium mycelium = new Mycelium(tecton, mycologist);
        nameMap.put(mycelium, myceliumName);
    }

    private void move(String[] commandParts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void breakCommand(String[] commandParts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static void random(String[] commandParts) {
        isRandomOn = !isRandomOn;
    }

    private void eatSpore(String[] commandParts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void chewMycelium(String[] commandParts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void spreadSpores(String[] commandParts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void evolveSuper(String[] commandParts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void neighbors(String[] commandParts) {
        String firstTectonName = commandParts[1];
        String secondTectonName = commandParts[2];

        Tecton firstTecton = (Tecton) getFromNameMap(firstTectonName);
        Tecton secondTecton = (Tecton) getFromNameMap(secondTectonName);

        if (firstTecton == null || secondTecton == null) return;

        firstTecton.addTectonToNeighbors(secondTecton);
    }

    private void growTo(String[] commandParts) {
        String myceliumName = commandParts[1];
        String tectonName = commandParts[2];
        String newMyceliumName = commandParts[3];

        Mycelium mycelium = (Mycelium) getFromNameMap(myceliumName);
        Tecton tecton = (Tecton) getFromNameMap(tectonName);

        if (mycelium == null || tecton == null) return;

        Mycelium newMycelium = mycelium.createNewBranch(tecton);
        if (newMycelium == null) return;
        nameMap.put(newMycelium, newMyceliumName);
    }

    private void growBody(String[] commandParts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void delay(String[] commandParts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void endGame(String[] commandParts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void load(String[] commandParts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void save(String[] commandParts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void exit(String[] commandParts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
