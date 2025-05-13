package com.example;
import com.example.model.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Controller implements KeyListener {
    private static HashMap<Object, String> nameMap = new HashMap<>();
    private List<Timer> timers = new ArrayList<>();
    private GameTable gameTable;
    private Mycologist mycologist1 = new Mycologist("Mycologist1");
    private Mycologist mycologist2 = new Mycologist("Mycologist2");
    private Entomologist entomologist1 = new Entomologist("Entomologist1");
    private Entomologist entomologist2 = new Entomologist("Entomologist2");
    
    private int selectedMyceliumIndexM1 = -1;
    private boolean myceliumSelectionActiveM1 = false;
    private boolean tectonSelectionActiveM1 = false;
    private int selectedTectonIndexM1 = -1;

    private int selectedMyceliumIndexM2 = -1;
    private boolean myceliumSelectionActiveM2 = false;
    private boolean tectonSelectionActiveM2 = false;
    private int selectedTectonIndexM2 = -1;

    private int selectedInsectIndexE1 = -1;
    private Insect selectedInsectE1 = null;

    private boolean movementActiveE1 = false;
    private int selectedTectonToMoveIndexE1 = -1;
    private Tecton moveToTectonE1 = null;
    private boolean chewActiveE1 = false;
    private int selectedTectonForChewIndexE1 = -1;
    private Tecton chewTectonE1 = null;



    public Controller() {
        Tecton t = new Transix("Tecton1");
        mycologist1.addMycelium(new Mycelium(t, mycologist1));
        mycologist1.addMycelium(new Mycelium(t, mycologist1));
        mycologist1.addMycelium(new Mycelium(t, mycologist1));
        MushroomBody mushroomBody = new Hyphara(t, mycologist1);

        mycologist2.addMycelium(new Mycelium(t, mycologist2));
        mycologist2.addMycelium(new Mycelium(t, mycologist2));

        Tecton t2 = new Transix("Tecton2");
        Tecton t3 = new Transix("Tecton3");
        t.addTectonToNeighbors(t2);
        t.addTectonToNeighbors(t3);

        Insect i = new Insect(entomologist1);
        t.placeInsect(i);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Spóra szórás mycologist1-nek
        System.out.println("Key pressed: " + e.getKeyChar());
        if (e.getKeyCode() == KeyEvent.VK_A) {
            for (MushroomBody mushroomBody : mycologist1.getMushroomBodies()) {
                boolean success = mushroomBody.spreadSpores();
                if (success) {
                    mushroomBody.setSporeSpread(false);
                    timers.add(new Timer(15, () -> mushroomBody.setSporeSpread(true)));
                }
            }
        }

        // Supergomba fejlesztés mycologist1-nek
        if (e.getKeyCode() == KeyEvent.VK_S) {
            for (MushroomBody mushroomBody : mycologist1.getMushroomBodies()) {
                mushroomBody.evolveSuper();
            }
        }

        // Fonalból test növesztés mycologist1-nek
        if (e.getKeyCode() == KeyEvent.VK_D) {
            for (Mycelium mycelium : mycologist1.getMycelia()) {
                mycelium.developMushroomBody();
            }
        }

        // Fonal növesztés mycologist1-nek
        if (e.getKeyCode() == KeyEvent.VK_W) {
            List<Mycelium> mycelia = mycologist1.getMycelia();
            if (!myceliumSelectionActiveM1 && !tectonSelectionActiveM1) {
                // Start mycelium selection
                myceliumSelectionActiveM1 = true;
                selectedMyceliumIndexM1 = 0;
                if (!mycelia.isEmpty()) {
                    System.out.println("Selected mycelium for mycologist1 index: " + selectedMyceliumIndexM1);
                }
            } else if (myceliumSelectionActiveM1) {
                // Finalize mycelium selection, start tecton selection
                System.out.println("Finalized mycelium for mycologist1 index: " + selectedMyceliumIndexM1);
                myceliumSelectionActiveM1 = false;
                tectonSelectionActiveM1 = true;
                selectedTectonIndexM1 = 0;
                List<Tecton> neighbors = mycelia.get(selectedMyceliumIndexM1).getTecton().getNeighbors();
                if (!neighbors.isEmpty()) {
                    System.out.println("Selected tecton for mycologist1 index: " + selectedTectonIndexM1);
                }
            } else if (tectonSelectionActiveM1) {
                // Finalize tecton selection
                List<Tecton> neighbors = mycelia.get(selectedMyceliumIndexM1).getTecton().getNeighbors();
                if (!neighbors.isEmpty()) {
                    System.out.println("Finalized tecton for mycologist1 index: " + selectedTectonIndexM1);
                    mycelia.get(selectedMyceliumIndexM1).createNewBranch(neighbors.get(selectedTectonIndexM1));
                    System.out.println("Created new branch for mycologist1 at tecton index: " + selectedTectonIndexM1);
                    mycelia.get(selectedMyceliumIndexM1).disableGrowth();
                    int myceliumIndex = selectedMyceliumIndexM1; // capture current index
                    timers.add(new Timer(10, new Runnable() {
                        @Override
                        public void run() {
                            mycologist1.getMycelia().get(myceliumIndex).enableGrowth();
                        }
                    }));
                }
                tectonSelectionActiveM1 = false;
                selectedTectonIndexM1 = -1;
                selectedMyceliumIndexM1 = -1;
            }
        }

        // Step through mycelia
        if (e.getKeyCode() == KeyEvent.VK_E && myceliumSelectionActiveM1) {
            List<Mycelium> mycelia = mycologist1.getMycelia();
            if (!mycelia.isEmpty()) {
                selectedMyceliumIndexM1 = (selectedMyceliumIndexM1 + 1) % mycelia.size();
                System.out.println("Selected mycelium for mycologist1 index: " + selectedMyceliumIndexM1);
            }
        }

        // Step through tecton neighbors
        if (e.getKeyCode() == KeyEvent.VK_E && tectonSelectionActiveM1) {
            List<Mycelium> mycelia = mycologist1.getMycelia();
            List<Tecton> neighbors = mycelia.get(selectedMyceliumIndexM1).getTecton().getNeighbors();
            if (!neighbors.isEmpty()) {
                selectedTectonIndexM1 = (selectedTectonIndexM1 + 1) % neighbors.size();
                System.out.println("Selected tecton for mycologist1 index: " + selectedTectonIndexM1);
            }
        }

        // Spóra szórás mycologist2-nek
        if (e.getKeyCode() == KeyEvent.VK_F) {
            for (MushroomBody mushroomBody : mycologist2.getMushroomBodies()) {
                boolean success = mushroomBody.spreadSpores();
                if (success) {
                    mushroomBody.setSporeSpread(false);
                    timers.add(new Timer(15, () -> mushroomBody.setSporeSpread(true)));
                }
            }
        }

        // Supergomba fejlesztés mycologist2-nek
        if (e.getKeyCode() == KeyEvent.VK_G) {
            for (MushroomBody mushroomBody : mycologist2.getMushroomBodies()) {
                mushroomBody.evolveSuper();
            }
        }

        // Fonalból test növesztés mycologist2-nek
        if (e.getKeyCode() == KeyEvent.VK_H) {
            for (Mycelium mycelium : mycologist2.getMycelia()) {
                mycelium.developMushroomBody();
            }
        }

        // Fonal növesztés mycologist2-nek
        if (e.getKeyCode() == KeyEvent.VK_T) {
            List<Mycelium> mycelia = mycologist2.getMycelia();
            if (!myceliumSelectionActiveM2 && !tectonSelectionActiveM2) {
                // Start mycelium selection
                myceliumSelectionActiveM2 = true;
                selectedMyceliumIndexM2 = 0;
                if (!mycelia.isEmpty()) {
                    System.out.println("Selected mycelium for mycologist2 index: " + selectedMyceliumIndexM2);
                }
            } else if (myceliumSelectionActiveM2) {
                // Finalize mycelium selection, start tecton selection
                System.out.println("Finalized mycelium for mycologist2 index: " + selectedMyceliumIndexM2);
                myceliumSelectionActiveM2 = false;
                tectonSelectionActiveM2 = true;
                selectedTectonIndexM2 = 0;
                List<Tecton> neighbors = mycelia.get(selectedMyceliumIndexM2).getTecton().getNeighbors();
                if (!neighbors.isEmpty()) {
                    System.out.println("Selected tecton for mycologist2 index: " + selectedTectonIndexM2);
                }
            } else if (tectonSelectionActiveM2) {
                // Finalize tecton selection
                List<Tecton> neighbors = mycelia.get(selectedMyceliumIndexM2).getTecton().getNeighbors();
                if (!neighbors.isEmpty()) {
                    System.out.println("Finalized tecton for mycologist2 index: " + selectedTectonIndexM2);
                    mycelia.get(selectedMyceliumIndexM2).createNewBranch(neighbors.get(selectedTectonIndexM2));
                    System.out.println("Created new branch for mycologist2 at tecton index: " + selectedTectonIndexM2);
                    mycelia.get(selectedMyceliumIndexM2).disableGrowth();
                    int myceliumIndex = selectedMyceliumIndexM2; // capture current index
                    timers.add(new Timer(10, new Runnable() {
                        @Override
                        public void run() {
                            mycologist2.getMycelia().get(myceliumIndex).enableGrowth();
                        }
                    }));
                }
                tectonSelectionActiveM2 = false;
                selectedTectonIndexM2 = -1;
                selectedMyceliumIndexM2 = -1;
            }
        }

        // Step through mycelia
        if (e.getKeyCode() == KeyEvent.VK_Z && myceliumSelectionActiveM2) {
            List<Mycelium> mycelia = mycologist2.getMycelia();
            if (!mycelia.isEmpty()) {
                selectedMyceliumIndexM2 = (selectedMyceliumIndexM2 + 1) % mycelia.size();
                System.out.println("Selected mycelium for mycologist2 index: " + selectedMyceliumIndexM2);
            }
        }

        // Step through tecton neighbors
        if (e.getKeyCode() == KeyEvent.VK_Z && tectonSelectionActiveM2) {
            List<Mycelium> mycelia = mycologist2.getMycelia();
            List<Tecton> neighbors = mycelia.get(selectedMyceliumIndexM2).getTecton().getNeighbors();
            if (!neighbors.isEmpty()) {
                selectedTectonIndexM2 = (selectedTectonIndexM2 + 1) % neighbors.size();
                System.out.println("Selected tecton for mycologist2 index: " + selectedTectonIndexM2);
            }
        }

        // Entomologist1 actions
        if (e.getKeyCode() == KeyEvent.VK_K) {
            if (selectedInsectIndexE1 == -1) {
                selectedInsectIndexE1 = 0;
                selectedInsectE1 = entomologist1.getInsects().get(selectedInsectIndexE1);
            }
            selectedInsectE1.eatSpore();
            selectedInsectE1.disableEating();
            timers.add(new Timer(5, () -> selectedInsectE1.enableEating()));
        }

        if (e.getKeyCode() == KeyEvent.VK_L) {
            if (selectedInsectIndexE1 == -1) {
                selectedInsectIndexE1 = 0;
            } else {
                selectedInsectIndexE1 = (selectedInsectIndexE1 + 1) % entomologist1.getInsects().size();
            }
            System.out.println("Selected insect for entomologist1 index: " + selectedInsectIndexE1);
            selectedInsectE1 = entomologist1.getInsects().get(selectedInsectIndexE1);
        }

        if (e.getKeyCode() == KeyEvent.VK_I) {
            List<Tecton> locations = new ArrayList<>();
            List<Mycelium> mycelia = selectedInsectE1.getTecton().getMycelia();
            for (Mycelium mycelium : mycelia) {
                mycelium.getMyceliumConnections().forEach(myceliumConnection -> locations.add(myceliumConnection.getTecton()));
            }
            if (!movementActiveE1) {
                // Start movement selection
                movementActiveE1 = true;
                selectedTectonToMoveIndexE1 = 0;
                if (!locations.isEmpty()) {
                    moveToTectonE1 = locations.get(selectedTectonToMoveIndexE1);
                    System.out.println("Selected tecton for movement for entomilogist1 index: " + selectedTectonToMoveIndexE1);
                }
            } else if (movementActiveE1) {
                // Finalize movement selection
                System.out.println("Finalized tecton for movement for entomilogist1 index: " + selectedTectonToMoveIndexE1);
                selectedInsectE1.moveTo(moveToTectonE1);
                moveToTectonE1 = null;
                movementActiveE1 = false;
                selectedTectonToMoveIndexE1 = -1;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_J) {
            List<Tecton> connections = new ArrayList<>();
            List<Mycelium> mycelia = selectedInsectE1.getTecton().getMycelia();
            for (Mycelium mycelium : mycelia) {
                mycelium.getMyceliumConnections().forEach(myceliumConnection -> connections.add(myceliumConnection.getTecton()));
            }
            if (!chewActiveE1) {
                // Start chew selection
                chewActiveE1 = true;
                selectedTectonForChewIndexE1 = 0;
                if (!connections.isEmpty()) {
                    chewTectonE1 = connections.get(selectedTectonForChewIndexE1);
                    System.out.println("Selected tecton for movement for entomilogist1 index: " + selectedTectonForChewIndexE1);
                }
            } else if (chewActiveE1) {
                // Finalize movement selection
                System.out.println("Finalized tecton for movement for entomilogist1 index: " + selectedTectonForChewIndexE1);
                List<Mycelium> myceliums1 = chewTectonE1.getMycelia();
                List<Mycelium> myceliums2 = selectedInsectE1.getTecton().getMycelia();
                List<Mycelium> myceliums = new ArrayList<>();
                for (Mycelium mycelium : myceliums2) {
                    myceliums.addAll(mycelium.getMyceliumConnections());
                }
                for (Mycelium mycelium : myceliums1) {
                    if (myceliums.contains(mycelium)) {
                        System.out.println("Chewing mycelium: " + mycelium.getName());
                        selectedInsectE1.chewMycelium(mycelium);
                        selectedInsectE1.disableChewMycelium();
                        timers.add(new Timer(10, () -> selectedInsectE1.enableToChewMycelium()));
                    }
                }
                chewTectonE1 = null;
                chewActiveE1 = false;
                selectedTectonForChewIndexE1 = -1;
            }
        }

        // Step through chew locations
        if (e.getKeyCode() == KeyEvent.VK_O && chewActiveE1) {
            List<Tecton> connections = new ArrayList<>();
            List<Mycelium> mycelia = selectedInsectE1.getTecton().getMycelia();
            for (Mycelium mycelium : mycelia) {
                mycelium.getMyceliumConnections().forEach(myceliumConnection -> connections.add(myceliumConnection.getTecton()));
            }
            if (!connections.isEmpty()) {
                selectedTectonForChewIndexE1 = (selectedTectonForChewIndexE1 + 1) % connections.size();
                System.out.println("Selected tecton for movement for entomilogist1 index: " + selectedTectonForChewIndexE1);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        ;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        ;
    }

    public static boolean isRandomOn() {
        return true;
    }

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
     * Végrehajt egy parancsot a megadott string alapján.
     * A parancsot szóközök mentén részekre bontja, azonosítja a parancs nevét,
     * és a megfelelő metódust hívja meg a végrehajtáshoz.
     * A végrehajtott parancsot naplózza. Hiba esetén hibaüzenetet ír ki.
     *
     * @param command A végrehajtandó parancs string formátumban.
     */
//    public void runCommand() {
//        try {
//            switch (keyPressed) {
//                case "MOVE"              -> move(commandParts);
//                case "EATSPORE"          -> eatSpore(commandParts);
//                case "CHEWMYCELIUM"      -> chewMycelium(commandParts);
//                case "SPREADSPORES"      -> spreadSpores(commandParts);
//                case "EVOLVESUPER"       -> evolveSuper(commandParts);
//                case "GROWTO"            -> growTo(commandParts);
//                case "GROWBODY"          -> growBody(commandParts);
//                case "DEVOUR"            -> devour(commandParts);
//                default                  -> throw new AssertionError();
//            }
//
//        } catch (Exception exception) {
//            System.out.println("[ERROR] Exception has been thrown while executing command: " + command + "\n" +
//                    "Exception message: " + exception.getMessage());
//            exception.printStackTrace();
//        }
//    }

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
                        case "hyphara":  Hyphara h = new Hyphara(null, mycologist); mycologist.setMushroomBodyType(h); break;
                        case "gilledon": Gilledon g = new Gilledon(null, mycologist); mycologist.setMushroomBodyType(g); break;
                        case "poralia": Poralia p = new Poralia(null, mycologist); mycologist.setMushroomBodyType(p); break;
                        case "capulon": Capulon c = new Capulon(null, mycologist); mycologist.setMushroomBodyType(c); break;
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
        Insect insect = new Insect(entomologist);
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
            case "hyphara" -> mushroomBody = new Hyphara(tecton, mycologist);
            case "gilledon" -> mushroomBody = new Gilledon(tecton, mycologist);
            case "poralia" -> mushroomBody = new Poralia(tecton, mycologist);
            case "capulon" -> mushroomBody = new Capulon(tecton, mycologist);
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

        Mycelium mycelium = new Mycelium(tecton, mycologist);
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

        Mycelium newMycelium = mycelium.createNewBranch(tecton);
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

        mycelium.developMushroomBody();
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
