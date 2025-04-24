package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Controller {
    private static List<String> commandsList = new ArrayList<>();
    public static HashMap<Object, String> nameMap = new HashMap<>();
    private static int seconds;
    private static boolean isRandomOn;



    public static Object getFromNameMap(String name) {
        for (Object object : nameMap.keySet()) {
            if (nameMap.get(object).equals(name)) {
                return object;
            }
        }
        System.out.println("[ERROR] Object not found in name map: " + name);
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
                case "INIT"              -> initialize(commandParts);
                case "DEVOUR"            -> devour(commandParts);
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

    private void devour(String[] commandParts) {;
    }

    private void initialize(String[] commandParts) {
        if (commandParts.length != 2) {
            System.out.println("[ERROR] Invalid command usage: " + commandParts[0] + " <gametableName>");
            return;
        }
        String gametableName = commandParts[1];

        GameTable gameTable = (GameTable) getFromNameMap(gametableName);
        if (gameTable == null) {
            return;
        }
        gameTable.initialize();
    }

    public void status(String[] commandParts) {
        if (commandParts.length != 2) {
            System.out.println("[ERROR] Invalid command usage: " + commandParts[0] + " <objectName>");
            return;
        }
        String Indent = "    ";
        if (commandParts[0].equalsIgnoreCase("random")) {
            if (isRandomOn) {
                System.out.println("Random: ON");
            } else {
                System.out.println("Random: OFF");
            }
            return;
        }
        Object object = getFromNameMap(commandParts[1]);
        if (object == null) {
            return;
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
        if (commandParts.length != 2) {
            System.out.println("[ERROR] Invalid command usage: " + commandParts[0] + " <name>");
            return;
        }
        GameTable gameTable = new GameTable(commandParts[1]);
        nameMap.put(gameTable, commandParts[1]);
    }

    private void createTecton(String[] commandParts) {
        if (commandParts.length != 4) {
            System.out.println("[ERROR] Invalid command usage: " + commandParts[0] + " <name> <type> <gametableName>");
            return;
        }
        String name = commandParts[1];
        String type = commandParts[2];
        String gametableName = commandParts[3];

        Tecton tecton = null;
        switch (type.toLowerCase()) {
            case "magmox" -> tecton = new Magmox(name);
            case "mantleon" -> tecton = new Mantleon(name);
            case "orogenix" -> tecton = new Orogenix(name);
            case "transix" -> tecton = new Transix(name);
            default -> System.out.println("Invalid tecton type: " + type);
        }
        if (tecton == null) return;
        GameTable gameTable = (GameTable) getFromNameMap(gametableName);
        if (gameTable == null) return;
        gameTable.addTecton(tecton);
        nameMap.put(tecton, name);
    }

    private void createPlayer(String[] commandParts) {
        if (commandParts.length != 5) {
            System.out.println("[ERROR] Invalid command usage: " + commandParts[0] + " <name> <type> <gametableName> <gombatestFaj>");
            return;
        }
        String name = commandParts[1];
        String type = commandParts[2];
        String gameTableName = commandParts[3];

        Player player = null;
        //TODO: meg kell akkor adni a mycologistnak a gombatestet?
        switch (type.toLowerCase()) {
            case "mycologist" -> player = new Mycologist(name);
            case "entomologist" -> player = new Entomologist(name);
            default -> System.out.println("Invalid player type: " + type);
        }
        if (player == null) return;
        GameTable gameTable = (GameTable) getFromNameMap(gameTableName);
        if (gameTable == null) return;
        gameTable.addPlayer(player);
        nameMap.put(player, name);
    }

    private void createInsect(String[] commandParts) {
        if (commandParts.length != 4) {
            System.out.println("[ERROR] Invalid command usage: " + commandParts[0] + " <name> <playerName> <tectonName>");
            return;
        }
        String name = commandParts[1];
        String player = commandParts[2];
        String tectonName = commandParts[3];

        Entomologist entomologist = (Entomologist) getFromNameMap(player);
        Insect insect = new Insect(entomologist, name);
        Tecton tecton = (Tecton) getFromNameMap(tectonName);
        if (tecton == null) return;
        tecton.placeInsect(insect);
        nameMap.put(insect, name);
    }

    private void createMushroomBody(String[] commandParts) {
        if (commandParts.length != 5) {
            System.out.println("[ERROR] Invalid command usage: " + commandParts[0] + " <name> <type> <tectonName> <mycologistName>");
            return;
        }
        String name = commandParts[1];
        String type = commandParts[2];
        String tectonName = commandParts[3];
        String mycologistName = commandParts[4];

        MushroomBody mushroomBody = null;
        Tecton tecton = (Tecton) getFromNameMap(tectonName);
        Mycologist mycologist = (Mycologist) getFromNameMap(mycologistName);
        switch (type.toLowerCase()) {
            case "hyphara" -> mushroomBody = new Hyphara(tecton, mycologist, name);
            case "gilledon" -> mushroomBody = new Gilledon(tecton, mycologist, name);
            case "poralia" -> mushroomBody = new Poralia(tecton, mycologist, name);
            case "capulon" -> mushroomBody = new Capulon(tecton, mycologist, name);
            default -> System.out.println("Invalid mushroom body type: " + type);
        }
        if (mushroomBody == null) return;
        assert tecton != null;
        tecton.placeMushroomBody(mushroomBody);
        nameMap.put(mushroomBody, name);
    }

    private void createSpore(String[] commandParts) {
        if (commandParts.length != 4) {
            System.out.println("[ERROR] Invalid command usage: " + commandParts[0] + " <type> <tectonName> <number>");
            return;
        }
        String type = commandParts[1];
        String tectonName = commandParts[2];
        int number = Integer.parseInt(commandParts[3]);

        Tecton tecton = (Tecton) getFromNameMap(tectonName);
        if (tecton == null) return;
        switch (type.toLowerCase()) {
            case "hyphara" -> {
                for (int i = 0; i < number; i++) {
                    HypharaSpore spore = new HypharaSpore();
                    tecton.addSpore(spore);
                }
            }
            case "gilledon" -> {
                for (int i = 0; i < number; i++) {
                    GilledonSpore spore = new GilledonSpore();
                    tecton.addSpore(spore);
                }
            }
            case "capulon" -> {
                for (int i = 0; i < number; i++) {
                    CapulonSpore spore = new CapulonSpore();
                    tecton.addSpore(spore);
                }
            }
            case "poralia" -> {
                for (int i = 0; i < number; i++) {
                    PoraliaSpore spore = new PoraliaSpore();
                    tecton.addSpore(spore);
                }
            }
            default -> System.out.println("Invalid spore type: " + type);
        }
    }

    private void createMycelium(String[] commandParts) {
        if (commandParts.length != 4) {
            System.out.println("[ERROR] Invalid command usage: " + commandParts[0] + " <name> <mycologistName> <tectonName>");
            return;
        }
        String myceliumName = commandParts[1];
        String mycologistName = commandParts[2];
        String tectonName = commandParts[3];

        Mycologist mycologist = (Mycologist) getFromNameMap(mycologistName);
        Tecton tecton = (Tecton) getFromNameMap(tectonName);

        if (mycologist == null || tecton == null) return;

        Mycelium mycelium = new Mycelium(tecton, mycologist);
        tecton.addMycelium(mycelium);
        mycologist.addMycelium(mycelium);
        nameMap.put(mycelium, myceliumName);
    }

    private void move(String[] commandParts) {
        if (commandParts.length != 3) {
            System.out.println("[ERROR] Invalid command usage: " + commandParts[0] + " <insectName> <tectonName>");
            return;
        }
        String insectName = commandParts[1];
        String tectonName = commandParts[2];

        Insect insect = (Insect) getFromNameMap(insectName);
        Tecton tecton = (Tecton) getFromNameMap(tectonName);

        if (insect == null || tecton == null) return;

        insect.moveTo(tecton);
    }

    private void breakCommand(String[] commandParts) {
        if (commandParts.length != 4) {
            System.out.println("[ERROR] Invalid command usage: " + commandParts[0] + " <tectonName> <newTectonName1> <newTectonName2>");
            return;
        }
        String tectonName = commandParts[1];
        String newTectonName1 = commandParts[2];
        String newTectonName2 = commandParts[3];

        Tecton tecton = (Tecton) getFromNameMap(tectonName);
        if (tecton == null) return;
        tecton.breakApart(newTectonName1, newTectonName2);
    }

    private static void random(String[] commandParts) {
        if (commandParts.length != 2) {
            System.out.println("[ERROR] Invalid command usage: " + commandParts[0] + " <true/false>");
            return;
        }
        String random = commandParts[1];
        if (random.equalsIgnoreCase("true")) {
            isRandomOn = true;
        } else if (random.equalsIgnoreCase("false")) {
            isRandomOn = false;
        } else {
            System.out.println("Invalid random value: " + random);
        }
    }

    private void eatSpore(String[] commandParts) {
        if (commandParts.length != 2) {
            System.out.println("[ERROR] Invalid command usage: " + commandParts[0] + " <insectName>");
            return;
        }
        String insectName = commandParts[1];

        Insect insect = (Insect) getFromNameMap(insectName);

        if (insect == null) return;

        insect.eatSpore();
    }

    private void chewMycelium(String[] commandParts) {
        if (commandParts.length != 3) {
            System.out.println("[ERROR] Invalid command usage: " + commandParts[0] + " <insectName> <myceliumName>");
            return;
        }
        String insectName = commandParts[1];
        String myceliumName = commandParts[2];

        Insect insect = (Insect) getFromNameMap(insectName);
        Mycelium mycelium = (Mycelium) getFromNameMap(myceliumName);

        if (insect == null || mycelium == null) return;

        insect.chewMycelium(mycelium);
    }

    private void spreadSpores(String[] commandParts) {
        if (commandParts.length != 2) {
            System.out.println("[ERROR] Invalid command usage: " + commandParts[0] + " <mushroomBodyName>");
            return;
        }
        String mushroomBodyName = commandParts[1];

        MushroomBody mushroomBody = (MushroomBody) getFromNameMap(mushroomBodyName);

        if (mushroomBody == null) return;

        mushroomBody.spreadSpores();
    }

    private void evolveSuper(String[] commandParts) {
        if (commandParts.length != 2) {
            System.out.println("[ERROR] Invalid command usage: " + commandParts[0] + " <mushroomBodyName>");
            return;
        }
        String mushroomBodyName = commandParts[1];

        MushroomBody mushroomBody = (MushroomBody) getFromNameMap(mushroomBodyName);

        if (mushroomBody == null) return;

        mushroomBody.evolveSuper();
    }

    private void neighbors(String[] commandParts) {
        if (commandParts.length != 3) {
            System.out.println("[ERROR] Invalid command usage: " + commandParts[0] + " <firstTectonName> <secondTectonName>");
            return;
        }
        String firstTectonName = commandParts[1];
        String secondTectonName = commandParts[2];

        Tecton firstTecton = (Tecton) getFromNameMap(firstTectonName);
        Tecton secondTecton = (Tecton) getFromNameMap(secondTectonName);

        if (firstTecton == null || secondTecton == null) return;

        firstTecton.addTectonToNeighbors(secondTecton);
    }

    private void growTo(String[] commandParts) {
        if (commandParts.length != 4) {
            System.out.println("[ERROR] Invalid command usage: " + commandParts[0] + " <myceliumName> <tectonName> <newMyceliumName>");
            return;
        }
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
        if (commandParts.length != 3) {
            System.out.println("[ERROR] Invalid command usage: " + commandParts[0] + " <myceliumName> <newMushroomBodyName>");
            return;
        }
        String myceliumName = commandParts[1];
        String newMushroomBodyName = commandParts[2];

        Mycelium mycelium = (Mycelium) getFromNameMap(myceliumName);

        if (mycelium == null) return;


        mycelium.developMushroomBody(newMushroomBodyName);
    }

    private void delay(String[] commandParts) {
        if (commandParts.length != 2) {
            System.out.println("[ERROR] Invalid command usage: " + commandParts[0] + " <seconds>");
            return;
        }
        seconds += Integer.parseInt(commandParts[1]);
    }

    private void endGame(String[] commandParts) {
        if (commandParts.length != 1) {
            System.out.println("[ERROR] Invalid command usage: " + commandParts[0] + " <gametableName>");
            return;
        }
        GameTable gameTable = (GameTable) getFromNameMap(commandParts[0]);
        if (gameTable == null) return;
        gameTable.endGame();
        String Indent = "    ";

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
