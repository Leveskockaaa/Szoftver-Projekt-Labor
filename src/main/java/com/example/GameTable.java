package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * A játéktér/pálya irányításáért felel. Ő inicializálja a játék kezdő állapotát, indítja el a játékot és
 * hirdeti ki a győzteseket. A játékelemek kezelését végzi (Tecton, Player).
 */
public class GameTable {

    private String name;

    /**
     * Tecton osztállyal való kapcsolat. A kapcsolat célja,
     * hogy a GameTable kezelni tudja a játéktéren elhelyezkedő tektonokat, amiket egy
     * listában tart nyílván az attribútumai között. Ha a játéktér megsemmisül, akkor a
     * tektonok is. Legalább 10 tekton tartozik a GameTable-hez (kezdő állapot), de ezek
     * száma nőhet.
     */
    private List<Tecton> tectons;

    private List<Player> players;

    private int sizeX;

    private int sizeY;

    public GameTable(){
        sizeX = 0;
        sizeY = 0;
        tectons = null;
        players = null;
    }

    public GameTable(int x, int y){
        sizeX = x;
        sizeY = y;
        tectons = new ArrayList<Tecton>();
        players = new ArrayList<Player>();
    }

    /**
     * Beállítja a tektonok listáját.
     *
     * @param tectons A tektonok listája, amelyet be kell állítani.
     */
    public void setTectons(List<Tecton> tectons) {
        this.tectons = tectons;
    }

    /**
     * Inicializálja a játéktáblát. A tektonok listáján végighaladva
     * beállítja a szomszédos tektonokat.
     */
    public void initialize() {
        Skeleton.logFunctionCall(this, "initialize");

        if (Controller.isRandomOn()){
            Random random = new Random();
            for(int i = 0; i < 10; i++){
                int randNum = random.nextInt(4);
                System.out.println(randNum);
                Tecton t = switch (randNum) {
                    case 0 -> new Transix();
                    case 1 -> new Mantleon();
                    case 2 -> new Magmox();
                    default -> new Orogenix();
                };
                tectons.add(t);
                System.out.println(t);
            }
        } else {
            Tecton t1 = new Transix();
            Tecton t2 = new Transix();
            Tecton t3 = new Transix();
            Tecton t4 = new Mantleon();
            Tecton t5 = new Mantleon();
            Tecton t6 = new Magmox();
            Tecton t7 = new Magmox();
            Tecton t8 = new Orogenix();
            Tecton t9 = new Orogenix();
            Tecton t10 = new Orogenix();

            tectons.add(t1);
            tectons.add(t2);
            tectons.add(t3);
            tectons.add(t4);
            tectons.add(t5);
            tectons.add(t6);
            tectons.add(t7);
            tectons.add(t8);
            tectons.add(t9);
            tectons.add(t10);
        }

        for (int i = 0; i < tectons.size() - 1; i++) {
            tectons.get(i).addTectonToNeighbors(tectons.get(i + 1));
        }
        tectons.get(tectons.size() - 1).addTectonToNeighbors(tectons.get(0));

        for (int i = 0; i < tectons.size() - 1; i++){
            tectons.get(i).addTectonToNeighbors(tectons.get(tectons.size() - 1));
        }
        Skeleton.logReturn(this, "initialize");
    }

    private MushroomBody chooseType(Tecton tecton, String type, Mycologist m){
        MushroomBody mBody = switch (type) {
            case "Hyphara" -> new Hyphara(tecton, m);
            case "Gilledon" -> new Gilledon(tecton, m);
            case "Poralia" -> new Poralia(tecton, m);
            default -> new Capulon(tecton, m);
        };

        return mBody;
    }

    public void roleChooser() {
        Scanner roleInput = new Scanner(System.in);

        System.out.println("Az első gombász adja meg a nevét!");
        String name = roleInput.nextLine();
        Mycologist m1 = new Mycologist(name);

        System.out.println("A második gombász adja meg a nevét!");
        name = roleInput.nextLine();
        Mycologist m2 = new Mycologist(name);

        System.out.println("Az első rovarász adja meg a nevét!");
        name = roleInput.nextLine();
        Entomologist e1 = new Entomologist(name);

        System.out.println("A második rovarász adja meg a nevét!");
        name = roleInput.nextLine();
        Entomologist e2 = new Entomologist(name);

        players.add(m1);
        players.add(m2);
        players.add(e1);
        players.add(e2);

        System.out.println("Hanyadik tektonra kerüljön a gombatest a listából? (1-10)");
        int initialTecton = roleInput.nextInt();
        String mushroomType = roleInput.nextLine();
        m1.addMushroomBody(chooseType(tectons.get(initialTecton), mushroomType, m1));
        Mycelium my1 = new Mycelium(tectons.get(initialTecton), m1);
        m1.addMycelium(my1);
        tectons.get(initialTecton).addMycelium(my1);

        System.out.println("Hanyadik tektonra kerüljön a gombatest a listából? (1-10)");
        initialTecton = roleInput.nextInt();
        mushroomType = roleInput.nextLine();
        m2.addMushroomBody(chooseType(tectons.get(initialTecton), mushroomType, m2));
        Mycelium my2 = new Mycelium(tectons.get(initialTecton), m2);
        m2.addMycelium(my2);
        tectons.get(initialTecton).addMycelium(my2);

        System.out.println("Hanyadik tektonra kerüljön a rovar a listából? (1-10)");
        initialTecton = roleInput.nextInt();
        Insect i1 = new Insect(e1);
        e1.addInsect(i1);
        e1.placeInitial(tectons.get(initialTecton));

        System.out.println("Hanyadik tektonra kerüljön a rovar a listából? (1-10)");
        initialTecton = roleInput.nextInt();
        Insect i2 = new Insect(e2);
        e2.addInsect(i2);
        e2.placeInitial(tectons.get(initialTecton));

        roleInput.close();
    }

    public void startGame(){
        //TODO
    }

    public void endGame(){
        if(players.get(0).getScore() > players.get(1).getScore()){
            players.get(0).setAsWinner();
        } else if (players.get(0).getScore() < players.get(1).getScore()){
            players.get(1).setAsWinner();
        } else {
            players.get(0).setAsWinner();
            players.get(1).setAsWinner();
        }

        if(players.get(2).getScore() > players.get(3).getScore()){
            players.get(2).setAsWinner();
        } else if (players.get(2).getScore() < players.get(3).getScore()){
            players.get(3).setAsWinner();
        } else {
            players.get(2).setAsWinner();
            players.get(3).setAsWinner();
        }

        //TODO timer leállás

        announceWinners();
    }

    public void announceWinners(){
        for (Player player : players) {
            if (player.getIsWinner()) {
                System.out.println(player.getName() + " " + player.getScore());
            }
        }
    }

    public void removeTecton(Tecton t) {
        tectons.remove(t);
    }

    public void addTecton(Tecton t){
        tectons.add(t);
    }

    public List<Tecton> getTectons() { return tectons; }

    public List<Player> getPlayers() { return players; }

    /*
    =============================================================================================
    Teszteléshez kiíró metódusok
    =============================================================================================
     */

    public String printName() {
        return this.name;
    }

    public String printTectons() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Tecton t : tectons) {
            sb.append(t.printName()).append(": ").append(t.printType()).append(", ");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("]");
        return sb.toString();
    }

    public String printPlayers() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Player p : players) {
            sb.append(p.printName()).append(": ").append(p.printType()).append(", ");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("]");
        return sb.toString();
    }
}
