package com.example;
import com.example.model.*;
import com.example.view.MainFrame;
import com.example.view.Position;

import java.awt.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

public class Controller implements KeyListener {
    private MainFrame mainFrame;

    private static HashMap<Object, String> nameMap = new HashMap<>();
    private static List<Timer> timers = new ArrayList<>();
    private static GameTable gameTable;
    private Mycologist mycologist1 = new Mycologist();
    private Mycologist mycologist2 = new Mycologist();
    private Entomologist entomologist1 = new Entomologist();
    private Entomologist entomologist2 = new Entomologist();
    
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

    private int selectedInsectIndexE2 = -1;
    private Insect selectedInsectE2 = null;

    private boolean movementActiveE2 = false;
    private int selectedTectonToMoveIndexE2 = -1;
    private Tecton moveToTectonE2 = null;
    private boolean chewActiveE2 = false;
    private int selectedTectonForChewIndexE2 = -1;
    private Tecton chewTectonE2 = null;


    public Controller(List<String> mycologists, List<Color> entomologists) {
        switch (mycologists.get(0).toLowerCase()) {
            case "hyphara" -> mycologist1.setMushroomBodyType(new Hyphara(new Transix(), mycologist1));
            case "poralia" -> mycologist1.setMushroomBodyType(new Poralia(new Transix(), mycologist1));
            case "capulon" -> mycologist1.setMushroomBodyType(new Capulon(new Transix(), mycologist1));
            case "gilledon" -> mycologist1.setMushroomBodyType(new Gilledon(new Transix(), mycologist1));
        }
        switch (mycologists.get(1).toLowerCase()) {
            case "hyphara" -> mycologist2.setMushroomBodyType(new Hyphara(new Transix(), mycologist2));
            case "poralia" -> mycologist2.setMushroomBodyType(new Poralia(new Transix(), mycologist2));
            case "capulon" -> mycologist2.setMushroomBodyType(new Capulon(new Transix(), mycologist2));
            case "gilledon" -> mycologist2.setMushroomBodyType(new Gilledon(new Transix(), mycologist2));
        }
        entomologist1.setColor(entomologists.get(0));
        entomologist2.setColor(entomologists.get(1));

        gameTable = new GameTable(Arrays.asList(mycologist1, mycologist2, entomologist1, entomologist2));
        gameTable.initialize();
        List<Tecton> tectons = gameTable.getTectons();

//        Random random = new Random();
//        for (Tecton tecton : tectons) {
//            int time = random.nextInt(3, 6);
//            Timer timer = new Timer(time, () -> {
//                List<Tecton> ret = tecton.breakApart();
//                gameTable.removeTecton(tecton);
//                gameTable.addTecton(ret.get(0));
//                gameTable.addTecton(ret.get(1));
//                // itt megy végbe a tektonok ketttétörése?
//                repaintFrame();
//            });
//            timers.add(timer);
//        }
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void repaintFrame() {
        if (mainFrame != null) {
            mainFrame.updateGameTable();
        }
    }

    public GameTable getGameTable() {
        return gameTable;
    }

    public static void addTimer(Timer timer) {
        timers.add(timer);
    }

    public static void removeTecton(Tecton tecton) {
        gameTable.removeTecton(tecton);
    }

    public static void addTecton(Tecton tecton) {
        gameTable.addTecton(tecton);
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
            MushroomBody res = null;
            Mycelium removable = null;
            for (Mycelium mycelium : mycologist1.getMycelia()) {
                res = mycelium.developMushroomBody();
                removable = mycelium;
                if (res != null) break;
            }
            if (res != null) {
                gameTable.getView().addNewMushroomBody(res);
                gameTable.getView().removeMycelium(removable);
            }
            gameTable.checkMyceliumConnections();
            repaintFrame();
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
                mycologist1.getMycelia().get(selectedMyceliumIndexM1).getView().setHighlighted(false);
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
                    Mycelium newMycelium = mycelia.get(selectedMyceliumIndexM1).createNewBranch(neighbors.get(selectedTectonIndexM1));
                    System.out.println("Created new branch for mycologist1 at tecton index: " + selectedTectonIndexM1);

                    // Add new mycelium view to the GameTableView if a new mycelium was created
                    if (newMycelium != null) {
                        gameTable.getView().addNewMycelium(newMycelium);
                        mycologist1.addMycelium(newMycelium);
                        newMycelium.disableGrowth();
                    }

                    mycelia.get(selectedMyceliumIndexM1).disableGrowth();
                    int myceliumIndex = selectedMyceliumIndexM1; // capture current index
                    timers.add(new Timer(10, new Runnable() {
                        @Override
                        public void run() {
                            mycologist1.getMycelia().get(myceliumIndex).enableGrowth();
                            newMycelium.enableGrowth();
                        }
                    }));
                }
                tectonSelectionActiveM1 = false;
                neighbors.get(selectedTectonIndexM1).getView().setIsHighlighted(false);
                gameTable.getView().repaint();
                selectedTectonIndexM1 = -1;
                selectedMyceliumIndexM1 = -1;
            }
            gameTable.checkMyceliumConnections();
            repaintFrame();
        }

        // Step through mycelia
        if (e.getKeyCode() == KeyEvent.VK_E && myceliumSelectionActiveM1) {
            List<Mycelium> mycelia = mycologist1.getMycelia();
            if (!mycelia.isEmpty()) {
                mycologist1.getMycelia().get(selectedMyceliumIndexM1).getView().setHighlighted(false);
                selectedMyceliumIndexM1 = (selectedMyceliumIndexM1 + 1) % mycelia.size();
                mycologist1.getMycelia().get(selectedMyceliumIndexM1).getView().setHighlighted(true);
                gameTable.getView().repaint();
                mycologist1.getMycelia().get(selectedMyceliumIndexM1).getView().repaint();
                System.out.println("Selected mycelium for mycologist1 index: " + selectedMyceliumIndexM1);
            }
        }

        // Step through tecton neighbors
        if (e.getKeyCode() == KeyEvent.VK_E && tectonSelectionActiveM1) {
            List<Mycelium> mycelia = mycologist1.getMycelia();
            List<Tecton> neighbors = mycelia.get(selectedMyceliumIndexM1).getTecton().getNeighbors();
            if (!neighbors.isEmpty()) {
                neighbors.get(selectedTectonIndexM1).getView().setIsHighlighted(false);
                selectedTectonIndexM1 = (selectedTectonIndexM1 + 1) % neighbors.size();
                neighbors.get(selectedTectonIndexM1).getView().setIsHighlighted(true);
                gameTable.getView().repaint();
                System.out.println("Selected tecton for mycologist1 index: " + selectedTectonIndexM1);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_Q) {
            for (Mycelium mycelium : mycologist1.getMycelia()) {
                Insect tmp = mycelium.eatInsect();
                if (tmp != null) {
                    MushroomBody mb = switch (mycologist1.getBodies().get(0).printType()) {
                        case "Hyphara" -> new Hyphara(mycelium.getTecton(), mycologist1);
                        case "Poralia" -> new Poralia(mycelium.getTecton(), mycologist1);
                        case "Capulon" -> new Capulon(mycelium.getTecton(), mycologist1);
                        case "Gilledon" -> new Gilledon(mycelium.getTecton(), mycologist1);
                        default -> null;
                    };
                    if (mb != null) gameTable.getView().addNewMushroomBody(mb);
                    gameTable.getView().removeInsect(tmp);
                    break;
                }
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
            MushroomBody res = null;
            Mycelium removable = null;
            for (Mycelium mycelium : mycologist2.getMycelia()) {
                res = mycelium.developMushroomBody();
                removable = mycelium;
                if (res != null) break;
            }
            if (res != null) {
                gameTable.getView().addNewMushroomBody(res);
                gameTable.getView().removeMycelium(removable);
            }
            gameTable.checkMyceliumConnections();
            repaintFrame();
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
                mycologist2.getMycelia().get(selectedMyceliumIndexM2).getView().setHighlighted(false);
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
                    Mycelium newMycelium = mycelia.get(selectedMyceliumIndexM2).createNewBranch(neighbors.get(selectedTectonIndexM2));
                    System.out.println("Created new branch for mycologist2 at tecton index: " + selectedTectonIndexM2);

                    if (newMycelium != null) {
                        gameTable.getView().addNewMycelium(newMycelium);
                        mycologist2.addMycelium(newMycelium);
                        newMycelium.disableGrowth();
                    }

                    mycelia.get(selectedMyceliumIndexM2).disableGrowth();
                    int myceliumIndex = selectedMyceliumIndexM2; // capture current index
                    timers.add(new Timer(10, new Runnable() {
                        @Override
                        public void run() {
                            mycologist2.getMycelia().get(myceliumIndex).enableGrowth();
                            newMycelium.enableGrowth();
                        }
                    }));
                }
                tectonSelectionActiveM2 = false;
                neighbors.get(selectedTectonIndexM2).getView().setIsHighlighted(false);
                selectedTectonIndexM2 = -1;
                selectedMyceliumIndexM2 = -1;
            }
            gameTable.checkMyceliumConnections();
            repaintFrame();
        }

        // Step through mycelia
        if (e.getKeyCode() == KeyEvent.VK_Z && myceliumSelectionActiveM2) {
            List<Mycelium> mycelia = mycologist2.getMycelia();
            if (!mycelia.isEmpty()) {
                mycologist2.getMycelia().get(selectedMyceliumIndexM2).getView().setHighlighted(false);
                selectedMyceliumIndexM2 = (selectedMyceliumIndexM2 + 1) % mycelia.size();
                mycologist2.getMycelia().get(selectedMyceliumIndexM2).getView().setHighlighted(true);
                gameTable.getView().repaint();
                System.out.println("Selected mycelium for mycologist2 index: " + selectedMyceliumIndexM2);
            }
        }

        // Step through tecton neighbors
        if (e.getKeyCode() == KeyEvent.VK_Z && tectonSelectionActiveM2) {
            List<Mycelium> mycelia = mycologist2.getMycelia();
            List<Tecton> neighbors = mycelia.get(selectedMyceliumIndexM2).getTecton().getNeighbors();
            if (!neighbors.isEmpty()) {
                neighbors.get(selectedTectonIndexM2).getView().setIsHighlighted(false);
                selectedTectonIndexM2 = (selectedTectonIndexM2 + 1) % neighbors.size();
                neighbors.get(selectedTectonIndexM2).getView().setIsHighlighted(true);
                gameTable.getView().repaint();
                System.out.println("Selected tecton for mycologist2 index: " + selectedTectonIndexM2);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_R) {
            for (Mycelium mycelium : mycologist2.getMycelia()) {
                Insect tmp = mycelium.eatInsect();
                if (tmp != null) {
                    MushroomBody mb = switch (mycologist2.getBodies().get(0).printType()) {
                        case "Hyphara" -> new Hyphara(mycelium.getTecton(), mycologist2);
                        case "Poralia" -> new Poralia(mycelium.getTecton(), mycologist2);
                        case "Capulon" -> new Capulon(mycelium.getTecton(), mycologist2);
                        case "Gilledon" -> new Gilledon(mycelium.getTecton(), mycologist2);
                        default -> null;
                    };
                    if (mb != null) gameTable.getView().addNewMushroomBody(mb);
                    gameTable.getView().removeInsect(tmp);
                    break;
                }
            }
        }

        // Entomologist1 actions
        // Eat
        if (e.getKeyCode() == KeyEvent.VK_K) {
            if (selectedInsectIndexE1 == -1) {
                selectedInsectIndexE1 = 0;
                selectedInsectE1 = entomologist1.getInsects().get(selectedInsectIndexE1);
            }
            selectedInsectE1.eatSpore();
            selectedInsectE1.disableEating();
            timers.add(new Timer(5, () -> selectedInsectE1.enableEating()));
        }

        // Insect select
        if (e.getKeyCode() == KeyEvent.VK_L) {
            if (selectedInsectIndexE1 == -1) {
                selectedInsectIndexE1 = 0;
            } else {
                selectedInsectE1.getView().setHighlighted(false);
                selectedInsectIndexE1 = (selectedInsectIndexE1 + 1) % entomologist1.getInsects().size();
                selectedInsectE1.getView().setHighlighted(true);
            }
            System.out.println("Selected insect for entomologist1 index: " + selectedInsectIndexE1);
            selectedInsectE1 = entomologist1.getInsects().get(selectedInsectIndexE1);
        }

        //Movement selection
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
                moveToTectonE1 = locations.get(selectedTectonToMoveIndexE1);
                selectedInsectE1.moveTo(moveToTectonE1);
                System.out.println("Moved insect to tecton: " + moveToTectonE1);
                moveToTectonE1.getView().setIsHighlighted(false);
                gameTable.getView().updateInsect(selectedInsectE1);
                moveToTectonE1 = null;
                movementActiveE1 = false;
                selectedTectonToMoveIndexE1 = -1;
            }
        }

        // Chew
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
                chewTectonE1.getView().setIsHighlighted(false);
                chewTectonE1 = null;
                chewActiveE1 = false;
                selectedTectonForChewIndexE1 = -1;
                gameTable.checkMyceliumConnections();
                repaintFrame();
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
                connections.get(selectedTectonForChewIndexE1).getView().setIsHighlighted(false);
                selectedTectonForChewIndexE1 = (selectedTectonForChewIndexE1 + 1) % connections.size();
                connections.get(selectedTectonForChewIndexE1).getView().setIsHighlighted(true);
                System.out.println("Selected tecton for chew for entomilogist1 index: " + selectedTectonForChewIndexE1);
            }
        }

        // Step through move locations
        if (e.getKeyCode() == KeyEvent.VK_O && movementActiveE1) {
            List<Tecton> connections = new ArrayList<>();
            List<Mycelium> mycelia = selectedInsectE1.getTecton().getMycelia();
            for (Mycelium mycelium : mycelia) {
                mycelium.getMyceliumConnections().forEach(myceliumConnection -> connections.add(myceliumConnection.getTecton()));
            }
            if (!connections.isEmpty()) {
                connections.get(selectedTectonToMoveIndexE1).getView().setIsHighlighted(false);
                selectedTectonToMoveIndexE1 = (selectedTectonToMoveIndexE1 + 1) % connections.size();
                connections.get(selectedTectonToMoveIndexE1).getView().setIsHighlighted(true);
                System.out.println("Selected tecton for movement for entomilogist1 index: " + selectedTectonToMoveIndexE1);
            }
        }

        // Entomologist2 actions
        if (e.getKeyCode() == KeyEvent.VK_OPEN_BRACKET) {
            if (selectedInsectIndexE2 == -1) {
                selectedInsectIndexE2 = 0;
                selectedInsectE2 = entomologist2.getInsects().get(selectedInsectIndexE2);
            }
            selectedInsectE2.eatSpore();
            selectedInsectE2.disableEating();
            timers.add(new Timer(5, () -> selectedInsectE2.enableEating()));
        }

        //Insect select
        if (e.getKeyCode() == KeyEvent.VK_CLOSE_BRACKET) {
            if (selectedInsectIndexE2 == -1) {
                selectedInsectIndexE2 = 0;
            } else {
                selectedInsectE2.getView().setHighlighted(false);
                selectedInsectIndexE2 = (selectedInsectIndexE2 + 1) % entomologist2.getInsects().size();
                selectedInsectE2.getView().setHighlighted(true);
            }
            System.out.println("Selected insect for entomologist2 index: " + selectedInsectIndexE2);
            selectedInsectE2 = entomologist2.getInsects().get(selectedInsectIndexE2);
        }

        //Movement selection
        if (e.getKeyCode() == KeyEvent.VK_MINUS) {
            List<Tecton> locations = new ArrayList<>();
            List<Mycelium> mycelia = selectedInsectE2.getTecton().getMycelia();
            for (Mycelium mycelium : mycelia) {
                mycelium.getMyceliumConnections().forEach(myceliumConnection -> locations.add(myceliumConnection.getTecton()));
            }
            if (!movementActiveE2) {
                // Start movement selection
                movementActiveE2 = true;
                selectedTectonToMoveIndexE2 = 0;
                if (!locations.isEmpty()) {
                    moveToTectonE2 = locations.get(selectedTectonToMoveIndexE2);
                    System.out.println("Selected tecton for movement for entomilogist2 index: " + selectedTectonToMoveIndexE2);
                }
            } else if (movementActiveE2) {
                // Finalize movement selection
                System.out.println("Finalized tecton for movement for entomilogist2 index: " + selectedTectonToMoveIndexE2);
                moveToTectonE2 = locations.get(selectedTectonToMoveIndexE2);
                selectedInsectE2.moveTo(moveToTectonE2);
                for (Tecton tecton : gameTable.getTectons()) {
                    System.out.println("Insects🐛: " + tecton.getInsects());
                }
                moveToTectonE2.getView().setIsHighlighted(false);
                gameTable.getView().updateInsect(selectedInsectE2);
                moveToTectonE2 = null;
                movementActiveE2 = false;
                selectedTectonToMoveIndexE2 = -1;
            }
        }

        // Chew
        if (e.getKeyCode() == KeyEvent.VK_P) {
            List<Tecton> connections = new ArrayList<>();
            List<Mycelium> mycelia = selectedInsectE2.getTecton().getMycelia();
            for (Mycelium mycelium : mycelia) {
                mycelium.getMyceliumConnections().forEach(myceliumConnection -> connections.add(myceliumConnection.getTecton()));
            }
            if (!chewActiveE2) {
                // Start chew selection
                chewActiveE2 = true;
                selectedTectonForChewIndexE2 = 0;
                if (!connections.isEmpty()) {
                    chewTectonE2 = connections.get(selectedTectonForChewIndexE2);
                    System.out.println("Selected tecton for movement for entomilogist2 index: " + selectedTectonForChewIndexE2);
                }
            } else if (chewActiveE2) {
                // Finalize movement selection
                System.out.println("Finalized tecton for movement for entomilogist2 index: " + selectedTectonForChewIndexE2);
                List<Mycelium> myceliums1 = chewTectonE2.getMycelia();
                List<Mycelium> myceliums2 = selectedInsectE2.getTecton().getMycelia();
                List<Mycelium> myceliums = new ArrayList<>();
                for (Mycelium mycelium : myceliums2) {
                    myceliums.addAll(mycelium.getMyceliumConnections());
                }
                for (Mycelium mycelium : myceliums1) {
                    if (myceliums.contains(mycelium)) {
                        System.out.println("Chewing mycelium: " + mycelium.getName());
                        selectedInsectE2.chewMycelium(mycelium);
                        selectedInsectE2.disableChewMycelium();
                        timers.add(new Timer(10, () -> selectedInsectE2.enableToChewMycelium()));
                    }
                }
                chewTectonE2.getView().setIsHighlighted(false);
                chewTectonE2 = null;
                chewActiveE2 = false;
                selectedTectonForChewIndexE2 = -1;
                gameTable.checkMyceliumConnections();
                repaintFrame();
            }
        }

        // Step through chew locations
        if (e.getKeyCode() == KeyEvent.VK_EQUALS && chewActiveE2) {
            List<Tecton> connections = new ArrayList<>();
            List<Mycelium> mycelia = selectedInsectE2.getTecton().getMycelia();
            for (Mycelium mycelium : mycelia) {
                mycelium.getMyceliumConnections().forEach(myceliumConnection -> connections.add(myceliumConnection.getTecton()));
            }
            if (!connections.isEmpty()) {
                connections.get(selectedTectonForChewIndexE2).getView().setIsHighlighted(false);
                selectedTectonForChewIndexE2 = (selectedTectonForChewIndexE2 + 1) % connections.size();
                connections.get(selectedTectonForChewIndexE2).getView().setIsHighlighted(true);
                System.out.println("Selected tecton for movement for entomilogist2 index: " + selectedTectonForChewIndexE2);
            }
        }

        // Step through move locations
        if (e.getKeyCode() == KeyEvent.VK_EQUALS && movementActiveE2) {
            List<Tecton> connections = new ArrayList<>();
            List<Mycelium> mycelia = selectedInsectE2.getTecton().getMycelia();
            for (Mycelium mycelium : mycelia) {
                mycelium.getMyceliumConnections().forEach(myceliumConnection -> connections.add(myceliumConnection.getTecton()));
            }
            if (!connections.isEmpty()) {
                connections.get(selectedTectonToMoveIndexE2).getView().setIsHighlighted(false);
                selectedTectonToMoveIndexE2 = (selectedTectonToMoveIndexE2 + 1) % connections.size();
                connections.get(selectedTectonToMoveIndexE2).getView().setIsHighlighted(true);
                System.out.println("Selected tecton for movement for entomilogist2 index: " + selectedTectonToMoveIndexE2);
            }
        }
        gameTable.getView().repaint();
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

        if (gameTable == null) throw new RuntimeException("GameTable not found: " + gametableName);
        gameTable.initialize();
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
