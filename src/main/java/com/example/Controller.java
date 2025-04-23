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
        String[] commandArgs = new String[commandParts.length - 1];
        switch (commandName) {
            case "STATUS":            status(commandArgs);            break;
            case "CREATEGAMETABLE":   createGameTable(commandArgs);   break;
            case "CREATETECTON":      createTecton(commandArgs);      break;
            case "CREATEPLAYER":      createPlayer(commandArgs);      break;
            case "CREATEINSECT":      createInsect(commandArgs);      break;
            case "CREATEMUSHROOMBODY":createMushroomBody(commandArgs);break;
            case "CREATESPORE":       createSpore(commandArgs);       break;
            case "CREATEMYCELIUM":    createMycelium(commandArgs);    break;
            case "MOVE":              move(commandArgs);              break;
            case "BREAK":             breakCommand(commandArgs);      break;
            case "RANDOM":            random(commandArgs);            break;
            case "EATSPORE":          eatSpore(commandArgs);          break;
            case "CHEWMYCELIUM":      chewMycelium(commandArgs);      break;
            case "SPREADSPORES":      spreadSpores(commandArgs);      break;
            case "EVOLVESUPER":       evolveSuper(commandArgs);       break;
            case "NEIGHBORS":         neighbors(commandArgs);         break;
            case "GROWTO":            growTo(commandArgs);            break;
            case "GROWBODY":          growBody(commandArgs);          break;
            case "DELAY":             delay(commandArgs);             break;
            case "ENDGAME":           endGame(commandArgs);           break;
            case "LOAD":              load(commandArgs);              break;
            case "SAVE":              save(commandArgs);              break;
            case "EXIT":              exit(commandArgs);              break;

            default:
            throw new AssertionError();
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


    public void status(String[] commandArgs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void createGameTable(String[] commandArgs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void createTecton(String[] commandArgs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void createPlayer(String[] commandArgs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void createInsect(String[] commandArgs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void createMushroomBody(String[] commandArgs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void createSpore(String[] commandArgs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void createMycelium(String[] commandArgs) {
        String myceliumName = commandArgs[0];
        String mycologistName = commandArgs[1];
        String tectonName = commandArgs[2];

        Mycologist mycologist = (Mycologist) getFromNameMap(mycologistName);
        Tecton tecton = (Tecton) getFromNameMap(tectonName);

        if (mycologist == null || tecton == null) return;

        Mycelium mycelium = new Mycelium(tecton, mycologist);
        nameMap.put(mycelium, myceliumName);
    }

    private void move(String[] commandArgs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void breakCommand(String[] commandArgs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void random(String[] commandArgs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void eatSpore(String[] commandArgs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void chewMycelium(String[] commandArgs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void spreadSpores(String[] commandArgs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void evolveSuper(String[] commandArgs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void neighbors(String[] commandArgs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void growTo(String[] commandArgs) {
        String myceliumName = commandArgs[0];
        String tectonName = commandArgs[1];
        String newMyceliumName = commandArgs[2];

        Mycelium mycelium = (Mycelium) getFromNameMap(myceliumName);
        Tecton tecton = (Tecton) getFromNameMap(tectonName);

        if (mycelium == null || tecton == null) return;

        Mycelium newMycelium = mycelium.createNewBranch(tecton);
        if (newMycelium == null) return;
        nameMap.put(newMycelium, newMyceliumName);
    }

    private void growBody(String[] commandArgs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void delay(String[] commandArgs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void endGame(String[] commandArgs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void load(String[] commandArgs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void save(String[] commandArgs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void exit(String[] commandArgs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
