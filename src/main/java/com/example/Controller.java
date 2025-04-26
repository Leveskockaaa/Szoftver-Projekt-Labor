package com.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Controller {
    private static List<String> commandsList = new ArrayList<>();
    private static final List<String> commandLog = new ArrayList<>();
    public static HashMap<Object, String> nameMap = new HashMap<>();
    private static int seconds;
    private static boolean isRandomOn;
    private static List<String> testCases = new ArrayList<>();
    private static File folder = new File("src/test");
    private static boolean isTestMode = false;
    private static String logFilePath = "";


    public static Object getFromNameMap(String name) {
        for (Object object : nameMap.keySet()) {
            if (nameMap.get(object).equals(name)) {
                return object;
            }
        }
        System.out.println("[ERROR] Object not found in name map: " + name);
        return null;
    }

    public static void putToNameMap(Object object, String name) {
        nameMap.put(object, name);
    }

    public static boolean isRandomOn() {
        return isRandomOn;
    }

    public static void setTestMode(boolean mode) {
        isTestMode = mode;
    }

    public void runCommand(String command) {
//        boolean isCommandValid = validateCommand(command);
//        if (!isCommandValid) {
//            System.out.println("Invalid command: " + command);
//            return;
//        }
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

            commandLog.add(command);
            System.out.println("[INFO] Command executed successfuly: " + command);
        } catch (Exception exception) {
            System.out.println("[ERROR] Exception has been thrown while executing command: " + command);
            exception.printStackTrace();
        }
    }

    public List<String> initTests(String filePath) {
        try {
            testCases = Files.readAllLines(new File(filePath).toPath());
        } catch (IOException exception) {
            System.out.println("[ERROR] Error while loading test cases: " + exception.getMessage());
        }
        return testCases;
    }

    public int chooseTest() {
        return 0;
    }

    public void runTest(int testNumber) {
        File[] matchingDirectories = folder.listFiles(file ->
                file.isDirectory() && file.getName().contains(testNumber + "")
        );
        if (matchingDirectories != null && matchingDirectories.length == 1) {
            logFilePath = "src/test/" + matchingDirectories[0].getName() + "/test-output.txt";
            File logFile = new File(logFilePath);
            if (logFile.exists()) {
                logFile.delete();
            }
            File testFile = new File(matchingDirectories[0].getAbsolutePath() + "/input.txt");
            try {
                List<String> lines = Files.readAllLines(testFile.toPath());
                for (String line : lines) {
                    runCommand(line);
                }
                System.out.println("[INFO] Test case executed successfully: " + testFile.getName());
            } catch (IOException exception) {
                System.out.println("[ERROR] Error while executing test case: " + exception.getMessage());
            }
        } else {
            System.out.println("[ERROR] No matching test files found for test number: " + testNumber);
        }
    }

    private boolean validateCommand(String command) {
        return commandsList.contains(command);
    }

    private void devour(String[] commandParts) {
        if (commandParts.length != 2) {
            System.out.println("[ERROR] Invalid command usage: " + commandParts[0] + " <myceliumName>");
            return;
        }
        String myceliumName = commandParts[1];

        Mycelium mycelium = (Mycelium) getFromNameMap(myceliumName);
        if (mycelium == null) return;
        mycelium.eatInsect();
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

    private void log(String message, Path path) {
        if (isTestMode) {
            try {
                Files.write(path, (message + System.lineSeparator()).getBytes(),
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException exception) {
                System.out.println("[ERROR] Error while writing to log file: " + exception.getMessage());
            }
        } else {
            System.out.println(message);
        }
    }

    public void status(String[] commandParts) {
        if (commandParts.length != 2) {
            System.out.println("[ERROR] Invalid command usage: " + commandParts[0] + " <objectName>");
            return;
        }
        String Indent = "    ";
        if (commandParts[1].equalsIgnoreCase("random")) {
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
            log(tecton.printName() + ":", Paths.get(logFilePath));
            log(Indent + "Type: " + tecton.printType(), Paths.get(logFilePath));
            log(Indent + "Size: " + tecton.printSize(), Paths.get(logFilePath));
            log(Indent + "maxMycelia: " + tecton.printMaxMycelia(), Paths.get(logFilePath));
            log(Indent + "Neighbors: " + tecton.printNeighbors(), Paths.get(logFilePath));
            log(Indent + "MushroomBody: " + tecton.printMushroomBody(), Paths.get(logFilePath));
            log(Indent + "Mycelia: " + tecton.printMycelia(), Paths.get(logFilePath));
            log(Indent + "Spores: " + tecton.printSpores(), Paths.get(logFilePath));
            log(Indent + "Insects: " + tecton.printInsects(), Paths.get(logFilePath));

        } else if (object instanceof Mycelium mycelium) {
            log(mycelium.printName() + ":", Paths.get(logFilePath));
            log(Indent + "canGrow: " + mycelium.printCanGrow(), Paths.get(logFilePath));
            log(Indent + "growthSpeed: " + mycelium.printGrowthSpeed(), Paths.get(logFilePath));
            log(Indent + "connectedTo: " + mycelium.printConnections(), Paths.get(logFilePath));
            log(Indent + "MushroomBodys: " + mycelium.printMushroomBodys(), Paths.get(logFilePath));

        } else if (object instanceof Insect insect) {
            log(insect.printName() + ":", Paths.get(logFilePath));
            log(Indent + "collectedNutrientPoints: " + insect.printCollectedNutrientPoints(), Paths.get(logFilePath));
            log(Indent + "nutrientMultiplier: " + insect.printNutrientMultiplier(), Paths.get(logFilePath));
            log(Indent + "canChewMycelium: " + insect.printCanChewMycelium(), Paths.get(logFilePath));
            log(Indent + "Speed: " + insect.printSpeed(), Paths.get(logFilePath));
            log(Indent + "isParalized: " + insect.printIsParalized(), Paths.get(logFilePath));
            log(Indent + "canEat: " + insect.printCanEat(), Paths.get(logFilePath));
            log(Indent + "Tecton: " + insect.printTecton(), Paths.get(logFilePath));

        } else if (object instanceof MushroomBody mushroomBody) {
            log(mushroomBody.printName() + ":", Paths.get(logFilePath));
            log(Indent + "Type: " + mushroomBody.printType(), Paths.get(logFilePath));
            log(Indent + "Level: " + mushroomBody.printLevel(), Paths.get(logFilePath));
            log(Indent + "State: " + mushroomBody.printState(), Paths.get(logFilePath));
            log(Indent + "canSpreadSpores: " + mushroomBody.printSporeSpread(), Paths.get(logFilePath));
            log(Indent + "sporeSpreadsLeft: " + mushroomBody.printSporeSpreadsLeft(), Paths.get(logFilePath));

        } else if (object instanceof Player player) {
            log(player.printName() + ":", Paths.get(logFilePath));
            log(Indent + "Type: " + player.printType(), Paths.get(logFilePath));
            log(Indent + "Score: " + player.printScore(), Paths.get(logFilePath));
            log(Indent + "isWinner: " + player.printIsWinner(), Paths.get(logFilePath));
            if (object instanceof Mycologist mycologist) {
                log(Indent + "Species: " + mycologist.printSpecies(), Paths.get(logFilePath));
                log(Indent + "MushroomBodys: " + mycologist.printMushroomBodies(), Paths.get(logFilePath));
                log(Indent + "Bag: " + mycologist.printBag(), Paths.get(logFilePath));
                log(Indent + "Mycelia: " + mycologist.printMycelia(), Paths.get(logFilePath));
            } else if (object instanceof Entomologist entomologist) {
                log(Indent + "Insects: " + entomologist.printInsects(), Paths.get(logFilePath));
            }

        } else if (object instanceof GameTable gameTable) {
            log(gameTable.printName() + ":", Paths.get(logFilePath));
            log(Indent + "Tectons: " + gameTable.printTectons(), Paths.get(logFilePath));
            log(Indent + "Players: " + gameTable.printPlayers(), Paths.get(logFilePath));
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
        if (commandParts.length != 4 && commandParts.length != 5) {
            System.out.println("[ERROR] Invalid command usage: " + commandParts[0] + " <name> <type> <gametableName> <gombatestFaj>");
            return;
        }
        String name = commandParts[1];
        String type = commandParts[2].toLowerCase();
        String gameTableName = commandParts[3];

        switch (type) {
            case "mycologist": {
                Mycologist mycologist = new Mycologist(name);
                if (commandParts.length == 5) {
                    String gombatestFaj = commandParts[4];
                    switch (gombatestFaj.toLowerCase()) {
                        case "hyphara" -> mycologist.addMushroomBody(new Hyphara(null, mycologist, "hyphara_minta"));
                        case "gilledon" -> mycologist.addMushroomBody(new Gilledon(null, mycologist, "gilledon_minta"));
                        case "poralia" -> mycologist.addMushroomBody(new Poralia(null, mycologist, "poralia_minta"));
                        case "capulon" -> mycologist.addMushroomBody(new Capulon(null, mycologist, "capulon_minta"));
                        default -> System.out.println("Invalid mushroom body type: " + gombatestFaj);
                    }
                } else {
                    System.out.println("[ERROR] Mycologist must have a mushroom body type.");
                    return;
                }
                GameTable gameTable = (GameTable) getFromNameMap(gameTableName);
                if (gameTable == null) return;
                gameTable.addPlayer(mycologist);
                nameMap.put(mycologist, name);
                break;
            }
            case "entomologist": {
                Entomologist entomologist = new Entomologist(name);
                GameTable gameTable = (GameTable) getFromNameMap(gameTableName);
                if (gameTable == null) return;
                gameTable.addPlayer(entomologist);
                nameMap.put(entomologist, name);
                break;
            }
            default: {
                System.out.println("Invalid player type: " + type);
                break;
            }
        }
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

        try {
            firstTecton.addTectonToNeighbors(secondTecton);
        } catch (Exception exception) {
            throw new RuntimeException("Error while adding tecton to neighbors", exception);
        }
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
        List<Mycologist> mycologistWinner = new ArrayList<>();
        List<Entomologist> entomologistWinner = new ArrayList<>();
        for (Object obj : nameMap.keySet()) {
            if (obj instanceof Mycologist mycologist) {
                if (mycologist.printIsWinner().equals("Yes")) {
                    mycologistWinner.add(mycologist);
                }
            } else if (obj instanceof Entomologist entomologist) {
                if (entomologist.printIsWinner().equals("Yes")) {
                    entomologistWinner.add(entomologist);
                }
            }
        }
        if (mycologistWinner.isEmpty()) {
            System.out.println(Indent + "Mycologist: No winner");
        } else if (mycologistWinner.size() == 1) {
            System.out.println(Indent + "Mycologist: " + mycologistWinner.get(0).printName());
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Indent).append("Mycologist: ");
            stringBuilder.append(mycologistWinner.get(0).printName()).append(", ");
            stringBuilder.append(mycologistWinner.get(1).printName());
        }
        if (entomologistWinner.isEmpty()) {
            System.out.println(Indent + "Entomologist: No winner");
        } else if (entomologistWinner.size() == 1) {
            System.out.println(Indent + "Entomologist: " + entomologistWinner.get(0).printName());
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Indent).append("Entomologist: ");
            stringBuilder.append(entomologistWinner.get(0).printName()).append(", ");
            stringBuilder.append(entomologistWinner.get(1).printName());
        }
    }

    private void load(String[] commandParts) {
        if (commandParts.length != 2) {
            System.out.println("[ERROR] Invalid command usage: " + commandParts[0] + " <path>");
            return;
        }
        String fileName = commandParts[1];
        File file = new File("src/main/resources/" + fileName);
        if (!file.exists()) {
            System.out.println("[ERROR] File not found: " + fileName);
            return;
        }
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            for (String line : lines) {
                runCommand(line);
            }
            System.out.println("[INFO] Loaded successfully");
        } catch (IOException exception) {
            System.out.println("[ERROR] Error while loading game: " + exception.getMessage());
        }
    }

    private void save(String[] commandParts) {
        if (commandParts.length != 2) {
            System.out.println("[ERROR] Invalid command usage: " + commandParts[0] + " <filePath>");
            return;
        }
        String fileName = commandParts[1];
        File file = new File("src/main/resources/" + fileName);
        if (!file.exists()) {
            System.out.println("[ERROR] File not found: " + fileName);
            return;
        }
        try {
            Files.write(file.toPath(), commandLog);
            System.out.println("[INFO] Saved successfully");
        } catch (IOException exception) {
            System.out.println("[ERROR] Error while saving game: " + exception.getMessage());
        }
    }

    private void exit(String[] commandParts) {
        System.out.println("[INFO] Exiting game");
        System.exit(0);
    }
}
