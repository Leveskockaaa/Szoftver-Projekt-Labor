package com.example;
import com.example.model.*;

import com.example.model.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Controller {
    private static List<String> commandsList = new ArrayList<>();
    private static final List<String> commandLog = new ArrayList<>();
    private static HashMap<Object, String> nameMap = new HashMap<>();
    private static int seconds;
    private static boolean isRandomOn;
    private static List<String> testCases = new ArrayList<>();
    private static File folder = new File("src/test");
    private static boolean isTestMode = false;
    private static String logFilePath = "";
    private static Scanner scanner;

    /**
     * Visszaadja a megadott névhez tartozó objektumot a név-objektum leképezésből.
     * Ha az objektum nem található, hibaüzenetet ír ki és null-t ad vissza.
     *
     * @param name A keresett objektum neve.
     * @return A névhez tartozó objektum, vagy null, ha nem található.
     */
    public static Object getFromNameMap(String name) {
        for (Object object : nameMap.keySet()) {
            if (nameMap.get(object).equals(name)) {
                return object;
            }
        }
        System.out.println("[ERROR] Object not found in name map: " + name);
        return null;
    }

    /**
     * Hozzáad egy objektumot és a hozzá tartozó nevet a név-objektum leképezéshez.
     * Ha a név már létezik a leképezésben, hibaüzenetet ír ki.
     *
     * @param object A hozzáadandó objektum.
     * @param name Az objektumhoz társítandó név.
     */
    public static void putToNameMap(Object object, String name) {
        if (nameMap.containsValue(name)) {
            System.out.println("[ERROR] Name already exists in name map: " + getFromNameMap(name).toString() + " Please try again with a different name.");
        } else {
            nameMap.put(object, name);
        }
    }

    /**
     * Törli a név-objektum leképezést.
     */
    public static void clearNameMap() {
        nameMap.clear();
    }

    /**
     * Visszaadja, hogy a véletlenszerűség be van-e kapcsolva.
     * @return true, ha a véletlenszerűség be van kapcsolva, egyébként false.
     */
    public static boolean isRandomOn() {
        return isRandomOn;
    }

    /**
     * Beállítja a teszt módot.
     * @param mode A teszt mód új állapota.
     */
    public static void setTestMode(boolean mode) {
        isTestMode = mode;
    }

    /**
     * Beállítja a véletlenszerűség állapotát.
     * @param rand A véletlenszerűség új állapota.
     */
    public static void setIsRandomOn(boolean rand) {
        isRandomOn = rand;
    }

    /**
     * Beállítja a bemeneti szkennert.
     * @param s A használni kívánt Scanner objektum.
     */
    public static void setScanner(Scanner s) {
        scanner = s;
    }

    /**
     * Végrehajt egy parancsot a megadott string alapján.
     * A parancsot szóközök mentén részekre bontja, azonosítja a parancs nevét,
     * és a megfelelő metódust hívja meg a végrehajtáshoz.
     * A végrehajtott parancsot naplózza. Hiba esetén hibaüzenetet ír ki.
     *
     * @param command A végrehajtandó parancs string formátumban.
     */
    public void runCommand(String command) {
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
        } catch (Exception exception) {
            System.out.println("[ERROR] Exception has been thrown while executing command: " + command + "\n" +
                    "Exception message: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    /**
     * Inicializálja a tesztelési környezetet a megadott fájlpath alapján.
     * A mappában található teszt esetek listáját beolvassa és visszaadja.
     *
     * @param filePath A fájl elérési útja, amely a teszt eseteket tartalmazza.
     * @return A teszt esetek listája.
     */

    public List<String> initTests(String filePath) {
        try {
            testCases = Files.readAllLines(new File(filePath).toPath());
        } catch (IOException exception) {
            System.out.println("[ERROR] Error while loading test cases: " + exception.getMessage());
        }
        return testCases;
    }

    /**
     * Futtat egy tesztesetet a megadott tesztszám alapján.
     * Megkeresi a megfelelő tesztkönyvtárat, törli a korábbi kimeneti fájlt,
     * beolvassa a bemeneti parancsokat az `input.txt` fájlból, és végrehajtja azokat.
     * A kimenetet a `test-output.txt` fájlba írja.
     * Hiba esetén hibaüzenetet ír ki a konzolra.
     *
     * @param testNumber A futtatandó teszteset sorszáma.
     */
    public void runTest(int testNumber) {
        File[] matchingDirectories = folder.listFiles(file ->
                file.isDirectory() && file.getName().startsWith(testNumber + "-")
        );
        if (matchingDirectories != null && matchingDirectories.length == 1) {
            logFilePath = "src/test/" + matchingDirectories[0].getName() + "/test-output.txt";
            nameMap.clear();
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
                System.out.println("[INFO] Test case executed successfully");
            } catch (IOException exception) {
                System.out.println("[ERROR] Error while executing test case: " + exception.getMessage());
            }

        } else {
            System.out.println("[ERROR] No matching test files found for test number: " + testNumber);
        }

    }

    /**
     * Egy micélium megeszi a megadott rovar objektumot.
     * A parancs paraméterei: <myceliumName> <insectName>.
     * Először megkeresi a névtérben a micéliumot és a rovart, majd
     * megbénítja a rovart, végül a micélium megeszi azt.
     *
     * @param commandParts A parancs argumentumai: [parancs, micélium neve, rovar neve]
     * @throws RuntimeException ha a paraméterek száma hibás, vagy ha a micélium vagy rovar nem található
     */
    private void devour(String[] commandParts) {
        if (commandParts.length != 3) {
            throw new RuntimeException("[ERROR] Invalid command usage: " + commandParts[0] + " <myceliumName> <insectName>");
        }
        String myceliumName = commandParts[1];
        String insectName = commandParts[2];

        Mycelium mycelium = (Mycelium) getFromNameMap(myceliumName);
        if (mycelium == null) throw new RuntimeException("Mycelium not found: " + myceliumName);
        Insect insect = (Insect) getFromNameMap(insectName);
        if (insect == null) throw new RuntimeException("Insect not found: " + insectName);

        insect.paralize();
        mycelium.eatInsect();
    }

    /**
     * Inicializálja a megadott nevű játéktáblát.
     * Ha nem teszt módban fut, akkor bekéri a szerepköröket a felhasználótól.
     *
     * @param commandParts A parancs argumentumai: [parancs, játéktábla neve]
     * @throws RuntimeException ha a parancs hibás vagy a játéktábla nem található
     */
    private void initialize(String[] commandParts) {
        if (commandParts.length != 2) {
            throw new RuntimeException("[ERROR] Invalid command usage: " + commandParts[0] + " <gametableName>");
        }
        String gametableName = commandParts[1];

        GameTable gameTable = (GameTable) getFromNameMap(gametableName);
        if (gameTable == null) throw new RuntimeException("GameTable not found: " + gametableName);
        gameTable.initialize();
        if (!isTestMode) {
            gameTable.roleChooser(scanner);
        }
    }

    /**
     * Naplóz egy üzenetet a megadott fájlba, ha teszt módban fut a program.
     * Ha nem teszt módban fut, akkor a konzolra írja ki az üzenetet.
     * A fájlba írás után biztosítja, hogy az adatok ténylegesen le legyenek mentve a lemezre.
     *
     * @param message A naplózandó üzenet.
     * @param path A naplófájl elérési útja.
     */
    private void log(String message, Path path) {
        if (isTestMode) {
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                writer.write(message + System.lineSeparator());
                writer.flush();
                try (var channel = FileChannel.open(path, StandardOpenOption.WRITE)) {
                    channel.force(true);
                }
            } catch (IOException exception) {
                System.out.println("[ERROR] Error while writing to log file: " + exception.getMessage());
            }
        } else {
            System.out.println(message);
        }
    }

    /**
     * Kiírja vagy naplózza a megadott objektum vagy állapot adatait.
     * Ha a parancs argumentuma "random", akkor a véletlenszerűség állapotát írja ki.
     * Egyébként a névtérből kikeresi az objektumot, és annak típusától függően részletes információkat naplóz.
     * Hibát dob, ha az argumentumok száma nem megfelelő vagy az objektum nem található.
     *
     * @param commandParts A parancs argumentumai: [parancs, objektum neve vagy "random"]
     * @throws RuntimeException ha hibás a parancs vagy az objektum nem található
     */
    public void status(String[] commandParts) {
        if (commandParts.length != 2) {
            throw new RuntimeException("[ERROR] Invalid command usage: " + commandParts[0] + " <objectName>");
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
        if (object == null) throw new RuntimeException("Object not found: " + commandParts[1]);
        if (object instanceof Tecton tecton) {
            log(tecton.printName() + ":", Paths.get(logFilePath));
            log(Indent + "Type: " + tecton.printType(), Paths.get(logFilePath));
            log(Indent + "Size: " + tecton.printSize(), Paths.get(logFilePath));
            log(Indent + "maxMycelia: " + tecton.printMaxMycelia(), Paths.get(logFilePath));
            log(Indent + "Neighbours: " + tecton.printNeighbors(), Paths.get(logFilePath));
            log(Indent + "MushroomBody: " + tecton.printMushroomBody(), Paths.get(logFilePath));
            log(Indent + "Mycelia: " + tecton.printMycelia(), Paths.get(logFilePath));
            log(Indent + "Spores: " + tecton.printSpores(), Paths.get(logFilePath));
            log(Indent + "Insects: " + tecton.printInsects(), Paths.get(logFilePath));

        } else if (object instanceof Mycelium mycelium) {
            log(mycelium.printName() + ":", Paths.get(logFilePath));
            log(Indent + "canGrow: " + mycelium.printCanGrow(), Paths.get(logFilePath));
            log(Indent + "growthSpeed: " + mycelium.printGrowthSpeed(), Paths.get(logFilePath));
            log(Indent + "connectedTo: " + mycelium.printConnections(), Paths.get(logFilePath));

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

    /**
     * Létrehoz egy új játéktáblát a megadott névvel, és hozzáadja a névtérhez.
     *
     * @param commandParts A parancs argumentumai: [parancs, játéktábla neve]
     * @throws RuntimeException ha a parancs argumentumainak száma nem megfelelő
     */
    private void createGameTable(String[] commandParts) {
        if (commandParts.length != 2) {
            throw new RuntimeException("[ERROR] Invalid command usage: " + commandParts[0] + " <name>");
        }
        GameTable gameTable = new GameTable(commandParts[1]);
        putToNameMap(gameTable, commandParts[1]);
    }

    /**
     * Létrehoz egy új Tecton objektumot a megadott paraméterek alapján, és hozzáadja a játéktáblához.
     * A parancs argumentumai: [parancs, név, típus, játéktábla neve].
     * Hibát dob, ha a parancs argumentumainak száma nem megfelelő, vagy ha a típus vagy játéktábla érvénytelen.
     *
     * @param commandParts A parancs argumentumai: [parancs, név, típus, játéktábla neve]
     * @throws RuntimeException ha a parancs hibás, a típus ismeretlen, vagy a játéktábla nem található
     */
    private void createTecton(String[] commandParts) {
        if (commandParts.length != 4) {
            throw new RuntimeException("[ERROR] Invalid command usage: " + commandParts[0] + " <name> <type> <gametableName>");
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
            default -> throw new RuntimeException("[ERROR] Invalid tecton type: " + type);
        }
        if (tecton == null) throw new RuntimeException("[ERROR] Failed to initialize tecton");
        GameTable gameTable = (GameTable) getFromNameMap(gametableName);
        if (gameTable == null) return;
        gameTable.addTecton(tecton);
        tecton.setGameTable(gameTable);
        putToNameMap(tecton, name);
    }

    /**
     * Létrehoz egy új játékost a megadott paraméterek alapján, és hozzáadja a megfelelő játéktáblához.
     * A parancs argumentumai: [parancs, név, típus, játéktábla neve, (opcionális) gombatestFaj].
     * Hibát dob, ha a parancs argumentumainak száma nem megfelelő, vagy ha a típus, játéktábla vagy gombatestFaj érvénytelen.
     *
     * @param commandParts A parancs argumentumai: [parancs, név, típus, játéktábla neve, (opcionális) gombatestFaj]
     * @throws RuntimeException ha a parancs hibás, a típus vagy a játéktábla nem található, vagy a gombatestFaj érvénytelen
     */
    private void createPlayer(String[] commandParts) {
        if (commandParts.length != 4 && commandParts.length != 5) {
            throw new RuntimeException("Invalid command usage: " + commandParts[0] + " <name> <type> <gametableName> <gombatestFaj>");
        }
        String name = commandParts[1];
        String type = commandParts[2].toLowerCase();
        String gameTableName = commandParts[3];

        switch (type) {
            case "mycologist": {
                Mycologist mycologist = new Mycologist(name);
                if (commandParts.length == 5) {
                    String gombatestFaj = commandParts[4].toLowerCase();
                    switch (gombatestFaj) {
                        case "hyphara":  Hyphara h = new Hyphara(null, mycologist, "hyphara_minta"); mycologist.setMushroomBodyType(h); break;
                        case "gilledon": Gilledon g = new Gilledon(null, mycologist, "gilledon_minta"); mycologist.setMushroomBodyType(g); break;
                        case "poralia": Poralia p = new Poralia(null, mycologist, "poralia_minta"); mycologist.setMushroomBodyType(p); break;
                        case "capulon": Capulon c = new Capulon(null, mycologist, "capulon_minta"); mycologist.setMushroomBodyType(c); break;
                        default: throw new RuntimeException("Invalid gombatestFaj: " + gombatestFaj);
                    }
                }
                else {
                    throw new RuntimeException("Mycologist must have a mushroom body type.");
                }
                GameTable gameTable = (GameTable) getFromNameMap(gameTableName);
                if (gameTable == null) throw new RuntimeException("GameTable not found: " + gameTableName);
                gameTable.addPlayer(mycologist);
                putToNameMap(mycologist, name);
                break;
            }
            case "entomologist": {
                Entomologist entomologist = new Entomologist(name);
                GameTable gameTable = (GameTable) getFromNameMap(gameTableName);
                if (gameTable == null) throw new RuntimeException("GameTable not found: " + gameTableName);
                gameTable.addPlayer(entomologist);
                putToNameMap(entomologist, name);
                break;
            }
            default: {
                throw new RuntimeException("Invalid player type: " + type);
            }
        }
    }

    /**
     * Létrehoz egy új rovar objektumot a megadott paraméterek alapján, és hozzáadja a megfelelő entomológushoz és tektonhoz.
     * A parancs argumentumai: [parancs, név, entomológus neve, tekton neve].
     * Hibát dob, ha a parancs argumentumainak száma nem megfelelő, vagy ha az entomológus vagy tekton nem található.
     *
     * @param commandParts A parancs argumentumai: [parancs, név, entomológus neve, tekton neve]
     * @throws RuntimeException ha a parancs hibás, az entomológus vagy a tekton nem található
     */
    private void createInsect(String[] commandParts) {
        if (commandParts.length != 4) {
            throw new RuntimeException("[ERROR] Invalid command usage: " + commandParts[0] + " <name> <playerName> <tectonName>");
        }
        String name = commandParts[1];
        String player = commandParts[2];
        String tectonName = commandParts[3];

        Entomologist entomologist = (Entomologist) getFromNameMap(player);
        if (entomologist == null) throw new RuntimeException("Entomologist not found: " + player);
        Insect insect = new Insect(entomologist, name);
        entomologist.addInsect(insect);
        Tecton tecton = (Tecton) getFromNameMap(tectonName);
        if (tecton == null) throw new RuntimeException("Tecton not found: " + tectonName);
        tecton.placeInsect(insect);
        putToNameMap(insect, name);
    }

    /**
     * Létrehoz egy új MushroomBody objektumot a megadott paraméterek alapján,
     * hozzáadja a megfelelő Tectonhoz és Mycologisthoz, majd elmenti a névtérbe.
     * Ha a Mycologist-nek csak egy "_minta" nevű gombateste van, azt eltávolítja és csökkenti a pontszámát.
     *
     * @param commandParts A parancs argumentumai: [parancs, név, típus, tecton neve, mycologist neve]
     * @throws RuntimeException ha a parancs hibás, a Tecton vagy Mycologist nem található, vagy a típus érvénytelen
     */
    private void createMushroomBody(String[] commandParts) {
        if (commandParts.length != 5) {
            throw new RuntimeException("[ERROR] Invalid command usage: " + commandParts[0] + " <name> <type> <tectonName> <mycologistName>");
        }
        String name = commandParts[1];
        String type = commandParts[2].toLowerCase();
        String tectonName = commandParts[3];
        String mycologistName = commandParts[4];

        MushroomBody mushroomBody = null;
        Tecton tecton = (Tecton) getFromNameMap(tectonName);
        if (tecton == null) throw new RuntimeException("Tecton not found: " + tectonName);
        Mycologist mycologist = (Mycologist) getFromNameMap(mycologistName);
        if (mycologist == null) throw new RuntimeException("Mycologist not found: " + mycologistName);

        if (mycologist.getMushroomBodies().size() == 1 && mycologist.getMushroomBodies().get(0).printName().contains("_minta")) {
            mycologist.getMushroomBodies().remove(0);
            mycologist.setScore(mycologist.getScore() - 1);
        }
        switch (type) {
            case "hyphara" -> mushroomBody = new Hyphara(tecton, mycologist, name);
            case "gilledon" -> mushroomBody = new Gilledon(tecton, mycologist, name);
            case "poralia" -> mushroomBody = new Poralia(tecton, mycologist, name);
            case "capulon" -> mushroomBody = new Capulon(tecton, mycologist, name);
            default -> System.out.println("Invalid mushroom body type: " + type);
        }
        if (mushroomBody == null) throw new RuntimeException("Failed to initialize mushroom body");
        tecton.placeMushroomBody(mushroomBody);
        putToNameMap(mushroomBody, name);
    }

    /**
     * Létrehoz egy vagy több spórát a megadott típus, tekton és darabszám alapján,
     * majd hozzáadja azokat a megfelelő Tecton objektumhoz.
     * Hibát dob, ha a parancs argumentumainak száma nem megfelelő, vagy ha a típus vagy tekton érvénytelen.
     *
     * @param commandParts A parancs argumentumai: [parancs, típus, tecton neve, darabszám]
     * @throws RuntimeException ha a parancs hibás, a típus vagy a tecton nem található
     */
    private void createSpore(String[] commandParts) {
        if (commandParts.length != 5) {
            throw new RuntimeException("[ERROR] Invalid command usage: " + commandParts[0] + " <type> <tectonName> <number> <mushroomBodyName>");
        }
        String type = commandParts[1];
        String tectonName = commandParts[2];
        String mushroomBodyName = commandParts[4];
        int number = Integer.parseInt(commandParts[3]);

        Tecton tecton = (Tecton) getFromNameMap(tectonName);
        if (tecton == null) throw new RuntimeException("Tecton not found: " + tectonName);
        MushroomBody mushroomBody = (MushroomBody) getFromNameMap(mushroomBodyName);
        if (mushroomBody == null) throw new RuntimeException("MushroomBody not found: " + mushroomBodyName);
        switch (type.toLowerCase()) {
            case "hyphara" -> {
                for (int i = 0; i < number; i++) {
                    HypharaSpore spore = new HypharaSpore(mushroomBody);
                    tecton.addSpore(spore);
                }
            }
            case "gilledon" -> {
                for (int i = 0; i < number; i++) {
                    GilledonSpore spore = new GilledonSpore(mushroomBody);
                    tecton.addSpore(spore);
                }
            }
            case "capulon" -> {
                for (int i = 0; i < number; i++) {
                    CapulonSpore spore = new CapulonSpore(mushroomBody);
                    tecton.addSpore(spore);
                }
            }
            case "poralia" -> {
                for (int i = 0; i < number; i++) {
                    PoraliaSpore spore = new PoraliaSpore(mushroomBody);
                    tecton.addSpore(spore);
                }
            }
            default -> throw new RuntimeException("Invalid spore type: " + type);
        }
    }

    /**
     * Létrehoz egy új micéliumot a megadott paraméterek alapján,
     * hozzáadja a megfelelő Tectonhoz és Mycologisthoz, majd elmenti a névtérbe.
     * Hibát dob, ha a parancs argumentumainak száma nem megfelelő, vagy ha a Mycologist vagy Tecton nem található.
     *
     * @param commandParts A parancs argumentumai: [parancs, micélium neve, mycologist neve, tecton neve]
     * @throws RuntimeException ha a parancs hibás, a Mycologist vagy a Tecton nem található
     */
    private void createMycelium(String[] commandParts) {
        if (commandParts.length != 4) {
            throw new RuntimeException("[ERROR] Invalid command usage: " + commandParts[0] + " <name> <mycologistName> <tectonName>");
        }
        String myceliumName = commandParts[1];
        String mycologistName = commandParts[2];
        String tectonName = commandParts[3];

        Mycologist mycologist = (Mycologist) getFromNameMap(mycologistName);
        if (mycologist == null) throw new RuntimeException("Mycologist not found: " + mycologistName);
        Tecton tecton = (Tecton) getFromNameMap(tectonName);
        if (tecton == null) throw new RuntimeException("Tecton not found: " + tectonName);

        Mycelium mycelium = new Mycelium(tecton, mycologist, myceliumName);
        tecton.addMycelium(mycelium);
        mycologist.addMycelium(mycelium);
        putToNameMap(mycelium, myceliumName);
    }

    /**
     * Egy rovart áthelyez egy másik tektonra a megadott paraméterek alapján.
     * A parancs argumentumai: [parancs, rovar neve, tekton neve].
     * Hibát dob, ha a parancs argumentumainak száma nem megfelelő, vagy ha a rovar vagy tekton nem található.
     *
     * @param commandParts A parancs argumentumai: [parancs, rovar neve, tekton neve]
     * @throws RuntimeException ha a parancs hibás, a rovar vagy a tekton nem található
     */
    private void move(String[] commandParts) {
        if (commandParts.length != 3) {
            throw new RuntimeException("[ERROR] Invalid command usage: " + commandParts[0] + " <insectName> <tectonName>");
        }
        String insectName = commandParts[1];
        String tectonName = commandParts[2];

        Insect insect = (Insect) getFromNameMap(insectName);
        if (insect == null) throw new RuntimeException("Insect not found: " + insectName);
        Tecton tecton = (Tecton) getFromNameMap(tectonName);
        if (tecton == null) throw new RuntimeException("Tecton not found: " + tectonName);

        insect.moveTo(tecton);
    }

    /**
     * Feldarabol egy meglévő Tecton objektumot két új Tecton-ra a megadott nevekkel.
     * A parancs argumentumai: [parancs, eredetiTectonNeve, újTectonNév1, újTectonNév2].
     * Hibát dob, ha a parancs argumentumainak száma nem megfelelő, vagy ha az eredeti Tecton nem található,
     * vagy ha a feldarabolás sikertelen.
     *
     * @param commandParts A parancs argumentumai: [parancs, eredetiTectonNeve, újTectonNév1, újTectonNév2]
     * @throws RuntimeException ha hibás a parancs, az eredeti Tecton nem található, vagy a feldarabolás sikertelen
     */
    private void breakCommand(String[] commandParts) {
        if (commandParts.length != 4) {
            throw new RuntimeException("[ERROR] Invalid command usage: " + commandParts[0] + " <tectonName> <newTectonName1> <newTectonName2>");
        }
        String tectonName = commandParts[1];
        String newTectonName1 = commandParts[2];
        String newTectonName2 = commandParts[3];

        Tecton tecton = (Tecton) getFromNameMap(tectonName);
        if (tecton == null) throw new RuntimeException("[ERROR] Tecton not found: " + tectonName);

        List<Tecton> newtectons = tecton.breakApart(newTectonName1, newTectonName2);
        if (newtectons == null) throw new RuntimeException("[ERROR] Failed to break tecton");

    }

    /**
     * Beállítja a véletlenszerűség állapotát a parancs argumentuma alapján.
     * A parancs argumentumai: [parancs, "true"/"false"].
     * Hibát dob, ha az argumentum nem "true" vagy "false".
     *
     * @param commandParts A parancs argumentumai: [parancs, "true"/"false"]
     * @throws RuntimeException ha az argumentum hibás
     */
    private static void random(String[] commandParts) {
        if (commandParts.length != 2) {
            throw new RuntimeException("[ERROR] Invalid command usage: " + commandParts[0] + " <true/false>");
        }
        String random = commandParts[1];
        if (random.equalsIgnoreCase("true")) {
            isRandomOn = true;
        } else if (random.equalsIgnoreCase("false")) {
            isRandomOn = false;
        } else {
            throw new RuntimeException("Invalid random value: " + random);
        }
    }

    /**
     * Egy rovar megeszik egy spórát a megadott név alapján.
     * A parancs argumentuma: [parancs, rovar neve].
     * Hibát dob, ha a parancs argumentumainak száma nem megfelelő, vagy ha a rovar nem található.
     *
     * @param commandParts A parancs argumentumai: [parancs, rovar neve]
     * @throws RuntimeException ha a parancs hibás vagy a rovar nem található
     */
    private void eatSpore(String[] commandParts) {
        if (commandParts.length != 2) {
            throw new RuntimeException("[ERROR] Invalid command usage: " + commandParts[0] + " <insectName>");
        }
        String insectName = commandParts[1];

        Insect insect = (Insect) getFromNameMap(insectName);
        if (insect == null) throw new RuntimeException("Insect not found: " + insectName);

        insect.eatSpore();
    }

    /**
     * Egy rovar elrág egy micéliumot a megadott paraméterek alapján.
     * A parancs argumentumai: [parancs, rovar neve, micélium neve].
     * Hibát dob, ha a parancs argumentumainak száma nem megfelelő, vagy ha a rovar vagy micélium nem található.
     *
     * @param commandParts A parancs argumentumai: [parancs, rovar neve, micélium neve]
     * @throws RuntimeException ha a parancs hibás, a rovar vagy a micélium nem található
     */
    private void chewMycelium(String[] commandParts) {
        if (commandParts.length != 3) {
            throw new RuntimeException("[ERROR] Invalid command usage: " + commandParts[0] + " <insectName> <myceliumName>");
        }
        String insectName = commandParts[1];
        String myceliumName = commandParts[2];

        Insect insect = (Insect) getFromNameMap(insectName);
        if (insect == null) throw new RuntimeException("Insect not found: " + insectName);
        Mycelium mycelium = (Mycelium) getFromNameMap(myceliumName);
        if (mycelium == null) throw new RuntimeException("Mycelium not found: " + myceliumName);

        insect.chewMycelium(mycelium);
    }

    /**
     * Egy gombatest szórja a spóráit a megadott név alapján.
     * A parancs argumentuma: [parancs, gombatest neve].
     * Hibát dob, ha a parancs argumentumainak száma nem megfelelő, vagy ha a gombatest nem található.
     *
     * @param commandParts A parancs argumentumai: [parancs, gombatest neve]
     * @throws RuntimeException ha a parancs hibás vagy a gombatest nem található
     */
    private void spreadSpores(String[] commandParts) {
        if (commandParts.length != 2) {
            throw new RuntimeException("[ERROR] Invalid command usage: " + commandParts[0] + " <mushroomBodyName>");
        }
        String mushroomBodyName = commandParts[1];

        MushroomBody mushroomBody = (MushroomBody) getFromNameMap(mushroomBodyName);
        if (mushroomBody == null) throw new RuntimeException("MushroomBody not found: " + mushroomBodyName);

        mushroomBody.spreadSpores();
    }

    /**
     * Egy gombatest szuper gombatesté fejlődik a megadott név alapján.
     * A parancs argumentuma: [parancs, gombatest neve].
     * Hibát dob, ha a parancs argumentumainak száma nem megfelelő, vagy ha a gombatest nem található.
     *
     * @param commandParts A parancs argumentumai: [parancs, gombatest neve]
     * @throws RuntimeException ha a parancs hibás vagy a gombatest nem található
     */
    private void evolveSuper(String[] commandParts) {
        if (commandParts.length != 2) {
            throw new RuntimeException("[ERROR] Invalid command usage: " + commandParts[0] + " <mushroomBodyName>");
        }
        String mushroomBodyName = commandParts[1];

        MushroomBody mushroomBody = (MushroomBody) getFromNameMap(mushroomBodyName);
        if (mushroomBody == null) throw new RuntimeException("MushroomBody not found: " + mushroomBodyName);

        mushroomBody.evolveSuper();
    }

    /**
     * Két Tecton objektumot szomszédossá tesz egymással a megadott neveik alapján.
     * A parancs argumentumai: [parancs, elsőTectonNeve, másodikTectonNeve].
     * Hibát dob, ha a parancs argumentumainak száma nem megfelelő, vagy ha bármelyik Tecton nem található.
     *
     * @param commandParts A parancs argumentumai: [parancs, elsőTectonNeve, másodikTectonNeve]
     * @throws RuntimeException ha hibás a parancs vagy a Tecton objektumok nem találhatók
     */
    private void neighbors(String[] commandParts) {
        if (commandParts.length != 3) {
            throw new RuntimeException("[ERROR] Invalid command usage: " + commandParts[0] + " <firstTectonName> <secondTectonName>");
        }
        String firstTectonName = commandParts[1];
        String secondTectonName = commandParts[2];

        Tecton firstTecton = (Tecton) getFromNameMap(firstTectonName);
        if (firstTecton == null) throw new RuntimeException("First Tecton not found: " + firstTectonName);
        Tecton secondTecton = (Tecton) getFromNameMap(secondTectonName);
        if (secondTecton == null) throw new RuntimeException("Second Tecton not found: " + secondTectonName);

        try {
            firstTecton.addTectonToNeighbors(secondTecton);
        } catch (Exception exception) {
            throw new RuntimeException("Error while adding tecton to neighbors", exception);
        }
    }

    /**
     * Egy meglévő micéliumot egy másik tektonra növeszt, új micéliumot hozva létre a megadott névvel.
     * A parancs argumentumai: [parancs, micélium neve, tecton neve, új micélium neve].
     * Hibát dob, ha a parancs argumentumainak száma nem megfelelő, vagy ha a micélium vagy tecton nem található,
     * vagy ha az új micélium ág létrehozása sikertelen.
     *
     * @param commandParts A parancs argumentumai: [parancs, micélium neve, tecton neve, új micélium neve]
     * @throws RuntimeException ha hibás a parancs, a micélium vagy a tecton nem található, vagy az új ág létrehozása sikertelen
     */
    private void growTo(String[] commandParts) {
        if (commandParts.length != 4) {
            throw new RuntimeException("[ERROR] Invalid command usage: " + commandParts[0] + " <myceliumName> <tectonName> <newMyceliumName>");
        }
        String myceliumName = commandParts[1];
        String tectonName = commandParts[2];
        String newMyceliumName = commandParts[3];

        Mycelium mycelium = (Mycelium) getFromNameMap(myceliumName);
        if (mycelium == null) throw new RuntimeException("Mycelium not found: " + myceliumName);
        Tecton tecton = (Tecton) getFromNameMap(tectonName);
        if (tecton == null) throw new RuntimeException("Tecton not found: " + tectonName);

        Mycelium newMycelium = mycelium.createNewBranch(tecton, newMyceliumName);
        if (newMycelium == null) throw new RuntimeException("Failed to create new mycelium branch");
        putToNameMap(newMycelium, newMyceliumName);
    }

    /**
     * Egy meglévő micéliumhoz új gombatestet növeszt a megadott névvel.
     * A parancs argumentumai: [parancs, micélium neve, új gombatest neve].
     * Hibát dob, ha a parancs argumentumainak száma nem megfelelő, vagy ha a micélium nem található.
     *
     * @param commandParts A parancs argumentumai: [parancs, micélium neve, új gombatest neve]
     * @throws RuntimeException ha hibás a parancs vagy a micélium nem található
     */
    private void growBody(String[] commandParts) {
        if (commandParts.length != 3) {
            throw new RuntimeException("[ERROR] Invalid command usage: " + commandParts[0] + " <myceliumName> <newMushroomBodyName>");
        }
        String myceliumName = commandParts[1];
        String newMushroomBodyName = commandParts[2];

        Mycelium mycelium = (Mycelium) getFromNameMap(myceliumName);
        if (mycelium == null) throw new RuntimeException("Mycelium not found: " + myceliumName);

        mycelium.developMushroomBody(newMushroomBodyName);
    }

    /**
     * Növeli a játékban eltelt másodpercek számát a megadott értékkel.
     * A parancs argumentumai: [parancs, másodpercek száma].
     * Hibát dob, ha a parancs argumentumainak száma nem megfelelő.
     *
     * @param commandParts A parancs argumentumai: [parancs, másodpercek száma]
     * @throws RuntimeException ha hibás a parancs argumentumainak száma
     */
    private void delay(String[] commandParts) {
        if (commandParts.length != 2) {
            throw new RuntimeException("[ERROR] Invalid command usage: " + commandParts[0] + " <seconds>");

        }
        seconds += Integer.parseInt(commandParts[1]);
    }

    /**
     * Lezárja a játékot a megadott játéktábla alapján.
     * Kiírja a győzteseket a konzolra, külön a gombászok és rovarászok között.
     * Hibát dob, ha a parancs argumentumainak száma nem megfelelő vagy a játéktábla nem található.
     *
     * @param commandParts A parancs argumentumai: [parancs, játéktábla neve]
     * @throws RuntimeException ha hibás a parancs vagy a játéktábla nem található
     */
    private void endGame(String[] commandParts) {
        if (commandParts.length != 2) {
            throw new RuntimeException("[ERROR] Invalid command usage: " + commandParts[0] + " <gametableName>");
        }
        GameTable gameTable = (GameTable) getFromNameMap(commandParts[1]);
        if (gameTable == null) throw new RuntimeException("GameTable not found: " + commandParts[1]);
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

    /**
     * Betölti a játék állapotát a megadott fájlból, és végrehajtja a benne található parancsokat.
     * Ha a fájl nem található vagy hiba történik a beolvasás során, hibaüzenetet ír ki.
     *
     * @param commandParts A parancs argumentumai: [parancs, fájl neve]
     * @throws RuntimeException ha a parancs argumentumainak száma nem megfelelő
     */
    private void load(String[] commandParts) {
        if (commandParts.length != 2) {
            throw new RuntimeException("[ERROR] Invalid command usage: " + commandParts[0] + " <path>");
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

    /**
     * Elmenti a játék parancsainak naplóját a megadott fájlba.
     * Ha a fájl nem létezik, hibaüzenetet ír ki.
     * Sikeres mentés esetén információs üzenetet jelenít meg.
     *
     * @param commandParts A parancs argumentumai: [parancs, fájl neve]
     * @throws RuntimeException ha a parancs argumentumainak száma nem megfelelő
     */
    private void save(String[] commandParts) {
        if (commandParts.length != 2) {
            throw new RuntimeException("[ERROR] Invalid command usage: " + commandParts[0] + " <filePath>");
        }
        String fileName = commandParts[1];
        File file = new File("src/main/resources/" + fileName);
        try {
            Files.write(file.toPath(), commandLog);
            System.out.println("[INFO] Saved successfully");
        } catch (IOException exception) {
            System.out.println("[ERROR] Error while saving game: " + exception.getMessage());
        }
    }

    /**
     * Kilép a játékból.
     * Kiír egy információs üzenetet, majd leállítja a programot.
     *
     * @param commandParts A parancs argumentumai (nem használt).
     */
    private void exit(String[] commandParts) {
        System.out.println("[INFO] Exiting game");
        System.exit(0);
    }
}
