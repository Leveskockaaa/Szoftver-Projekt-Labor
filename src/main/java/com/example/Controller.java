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

    public static boolean isRandomOn() {
        return isRandomOn;
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
        Object object = getFromNameMap(commandParts[0]);
        String Indent = "    ";
        if (object == null) {
            System.out.println("Object not found in name map.");
            return;
        }
        if (commandParts[0].equalsIgnoreCase("random")) {
            if (isRandomOn) {
                System.out.println("Random: ON");
            } else {
                System.out.println("Random: OFF");
            }
            return;
        } else if (commandParts[0].equalsIgnoreCase("endgame")) {
            System.out.println("The game has ended!");
            System.out.println("Winners:");
            Mycologist mycologistWinner = null;
            Entomologist entomologistWinner = null;
            for (Object obj : nameMap.keySet()) {
                if (obj instanceof Mycologist mycologist) {
                    if (mycologist.printIsWinner().equals("Yes")) {
                        mycologistWinner = mycologist;
                    }
                } else if (obj instanceof Entomologist entomologist) {
                    if (entomologist.printIsWinner().equals("Yes")) {
                        entomologistWinner = entomologist;
                    }
                }
            }
            if (mycologistWinner == null) {
                System.out.println(Indent + "Mycologist: No winner");
            } else {
                System.out.println(Indent + "Mycologist: " + mycologistWinner.printName());
            }
            if (entomologistWinner == null) {
                System.out.println(Indent + "Entomologist: No winner");
            } else {
                System.out.println(Indent + "Entomologist: " + entomologistWinner.printName());
            }

        }
        if (object instanceof Tecton tecton) {
            System.out.println(tecton.printName() + ":");
            System.out.println(Indent + "Type: " + tecton.printType());
            System.out.println(Indent + "Size: " + tecton.printSize());
            System.out.println(Indent + "maxMycelia: " + tecton.printMaxMycelia());
            System.out.println(Indent + "Neighbors: " + tecton.printNeighbors());
            System.out.println(Indent + "MushroomBody: " + tecton.printMushroomBody());
            System.out.println(Indent + "Mycelia: " + tecton.printMycelia());
            System.out.println(Indent + "Spores: " + tecton.printSpores());
            System.out.println(Indent + "Insects: " + tecton.printInsects());

        } else if (object instanceof Mycelium mycelium) {
            System.out.println(mycelium.printName() + ":");
            System.out.println(Indent + "canGrow: " + mycelium.printCanGrow());
            System.out.println(Indent + "growthSpeed: " + mycelium.printGrowthSpeed());
            System.out.println(Indent + "connectedTo: " + mycelium.printConnections());
            System.out.println(Indent + "MushroomBodys: " + mycelium.printMushroomBodys());

        } else if (object instanceof Insect insect) {
            System.out.println(insect.printName() + ":");
            System.out.println(Indent + "collectedNutrientPoints: " + insect.printCollectedNutrientPoints());
            System.out.println(Indent + "nutrientMultiplier: " + insect.printNutrientMultiplier());
            System.out.println(Indent + "canChewMycelium: " + insect.printCanChewMycelium());
            System.out.println(Indent + "Speed: " + insect.printSpeed());
            System.out.println(Indent + "isParalized: " + insect.printIsParalized());
            System.out.println(Indent + "canEat: " + insect.printCanEat());
            System.out.println(Indent + "Tecton: " + insect.printTecton());

        } else if (object instanceof MushroomBody mushroomBody) {
            System.out.println(mushroomBody.printName() + ":");
            System.out.println(Indent + "Type: " + mushroomBody.printType());
            System.out.println(Indent + "Level: " + mushroomBody.printLevel());
            System.out.println(Indent + "State: " + mushroomBody.printState());
            System.out.println(Indent + "canSpreadSpores: " + mushroomBody.printSporeSpread());
            System.out.println(Indent + "sporeSpreadsLeft: " + mushroomBody.printSporeSpreadsLeft());

        } else if (object instanceof Player player) {
            System.out.println(player.printName() + ":");
            System.out.println(Indent + "Type: " + player.printType());
            System.out.println(Indent + "Score: " + player.printScore());
            System.out.println(Indent + "isWinner: " + player.printIsWinner());
            if (object instanceof Mycologist mycologist) {
                System.out.println(Indent + "Species: " + mycologist.printSpecies());
                System.out.println(Indent + "MushroomBodys: " + mycologist.printMushroomBodies());
                System.out.println(Indent + "Bag: " + mycologist.printBag());
                System.out.println(Indent + "Mycelia: " + mycologist.printMycelia());
            } else if (object instanceof Entomologist entomologist) {
                System.out.println(Indent + "Insects: " + entomologist.printInsects());
            }

        } else if (object instanceof GameTable gameTable) {
            System.out.println(gameTable.printName() + ":");
            System.out.println(Indent + "Tectons: " + gameTable.printTectons());
            System.out.println(Indent + "Players: " + gameTable.printPlayers());
        }
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
