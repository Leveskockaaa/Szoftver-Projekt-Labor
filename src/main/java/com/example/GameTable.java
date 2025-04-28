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

    public GameTable(String name) {
        this.name = name;
        sizeX = 0;
        sizeY = 0;
        tectons = new ArrayList<>();
        players = new ArrayList<>();
    }

    public GameTable(int x, int y, String name) {
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
     * Inicializálja a játéktáblát. Létrehozza a tektonokat és a tektonok listáján végighaladva
     * beállítja a szomszédos tektonokat. A tektonokat random módban véletlenszerűen generálja (típus),
     * random kikapcsolása esetén pedig egy megadott módon.
     */
    public void initialize() {
        if (tectons.size() > 0) {
            tectons.clear();
            Controller.clearNameMap();
        }
        if (Controller.isRandomOn()){
            Random random = new Random();
            for(int i = 0; i < 10; i++){
                int randNum = random.nextInt(4);
                Tecton t = switch (randNum) {
                    case 0 -> new Transix(TectonSize.GIANT, "t" + i);
                    case 1 -> new Mantleon(TectonSize.GIANT, "t" + i);
                    case 2 -> new Magmox(TectonSize.GIANT, "t" + i);
                    default -> new Orogenix(TectonSize.GIANT, "t" + i);
                };
                tectons.add(t);
                Controller.putToNameMap(t, t.getName());
            }
        } else {
            Tecton t1 = new Transix(TectonSize.GIANT, "t1");
            Tecton t2 = new Transix(TectonSize.GIANT, "t2");
            Tecton t3 = new Transix(TectonSize.GIANT, "t3");
            Tecton t4 = new Mantleon(TectonSize.GIANT, "t4");
            Tecton t5 = new Mantleon(TectonSize.GIANT, "t5");
            Tecton t6 = new Magmox(TectonSize.GIANT, "t6");
            Tecton t7 = new Magmox(TectonSize.GIANT, "t7");
            Tecton t8 = new Orogenix(TectonSize.GIANT, "t8");
            Tecton t9 = new Orogenix(TectonSize.GIANT, "t9");
            Tecton t10 = new Orogenix(TectonSize.GIANT, "t10");

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

            for (Tecton t : tectons) {
                Controller.putToNameMap(t, t.getName());
            }
        }

        for (int i = 0; i < tectons.size() - 2; i++) {
            tectons.get(i).addTectonToNeighbors(tectons.get(i + 1));
        }
        tectons.get(tectons.size() - 2).addTectonToNeighbors(tectons.get(0));

        for (int i = 0; i < tectons.size() - 1; i++){
            tectons.get(i).addTectonToNeighbors(tectons.get(tectons.size() - 1));
        }
    }

    /**
     * Választ egy gombatestet a megadott típus alapján, és létrehozza azt.
     *
     * @param tecton A tekton, amelyhez a gombatestet hozzárendeljük.
     * @param type   A gombatest típusa (Hyphara, Gilledon, Poralia, Capulon).
     * @param m      A mycologist, aki létrehozza a gombatestet.
     * @param name   A gombatest neve.
     * @return A létrehozott gombatest.
     */
    private MushroomBody chooseType(Tecton tecton, String type, Mycologist m, String name){
        MushroomBody mBody = switch (type.toLowerCase()) {
            case "hyphara" -> new Hyphara(tecton, m, name);
            case "gilledon" -> new Gilledon(tecton, m, name);
            case "poralia" -> new Poralia(tecton, m, name);
            case "capulon" -> new Capulon(tecton, m, name);
            default -> {
                System.out.println("[ERROR] Nincs ilyen gombatest típus!");
                yield null;
            }
        };
        Controller.putToNameMap(mBody, mBody.getName());
        return mBody;
    }

    /**
     * A játék szerepválasztását végzi. Két gombászt és két rovarászt kér be a felhasználótól. Ezeket létrehozza és a kért helyre
     * helyezi a tektonok listáján. Valamint a gombászok fajt is választhatnak.
     *
     * @param roleInput Beolvasást végző Scanner objektum.
     */
    public void roleChooser(Scanner roleInput) {
        if (players.size() > 0) {
            players.clear();
        }
        System.out.println("Az első gombász adja meg a nevét!");
        String mycologist1 = roleInput.nextLine();
        Mycologist m1 = new Mycologist(mycologist1);
        Controller.putToNameMap(m1, m1.getName());

        System.out.println("A második gombász adja meg a nevét!");
        String mycologist2 = roleInput.nextLine();
        Mycologist m2 = new Mycologist(mycologist2);
        Controller.putToNameMap(m2, m2.getName());

        System.out.println("Az első rovarász adja meg a nevét!");
        String entomologist1 = roleInput.nextLine();
        Entomologist e1 = new Entomologist(entomologist1);
        Controller.putToNameMap(e1, e1.getName());

        System.out.println("A második rovarász adja meg a nevét!");
        String entomologist2 = roleInput.nextLine();
        Entomologist e2 = new Entomologist(entomologist2);
        Controller.putToNameMap(e2, e2.getName());

        players.add(m1);
        players.add(m2);
        players.add(e1);
        players.add(e2);

        System.out.println("Hanyadik tektonra kerüljön a gombatest a listából? (1-10)");
        int initialTecton = roleInput.nextInt();
        roleInput.nextLine();
        System.out.println("Adja meg a gombatest típusát! (Hyphara, Gilledon, Poralia, Capulon)");
        String mushroomType = roleInput.nextLine();
        System.out.println("Adja meg a gombatest nevét!");
        String mushroomName = roleInput.nextLine();
        chooseType(tectons.get(initialTecton), mushroomType, m1, mushroomName);
        Mycelium my1 = new Mycelium(tectons.get(initialTecton), m1, m1.getName() + "_my_" + (m1.getMycelia().size() + 1));
        Controller.putToNameMap(my1, my1.getName());
        m1.addMycelium(my1);
        tectons.get(initialTecton).addMycelium(my1);
        m1.placeInitial(tectons.get(initialTecton));

        System.out.println("Hanyadik tektonra kerüljön a gombatest a listából? (1-10)");
        initialTecton = roleInput.nextInt();
        roleInput.nextLine();
        System.out.println("Adja meg a gombatest típusát! (Hyphara, Gilledon, Poralia, Capulon)");
        mushroomType = roleInput.nextLine();
        System.out.println("Adja meg a gombatest nevét!");
        mushroomName = roleInput.nextLine();
        chooseType(tectons.get(initialTecton), mushroomType, m2, mushroomName);
        Mycelium my2 = new Mycelium(tectons.get(initialTecton), m2, m2.getName() + "_my_" + (m2.getMycelia().size() + 1));
        Controller.putToNameMap(my2, my2.getName());
        m2.addMycelium(my2);
        tectons.get(initialTecton).addMycelium(my2);
        m2.placeInitial(tectons.get(initialTecton));

        System.out.println("Hanyadik tektonra kerüljön a rovar a listából? (1-10)");
        initialTecton = roleInput.nextInt();
        roleInput.nextLine();
        System.out.println("Adja meg a rovar nevét!");
        String insectName = roleInput.nextLine();
        Insect i1 = new Insect(e1, insectName);
        Controller.putToNameMap(i1, i1.getName());
        e1.addInsect(i1);
        e1.placeInitial(tectons.get(initialTecton));

        System.out.println("Hanyadik tektonra kerüljön a rovar a listából? (1-10)");
        initialTecton = roleInput.nextInt();
        roleInput.nextLine();
        System.out.println("Adja meg a rovar nevét!");
        insectName = roleInput.nextLine();
        Insect i2 = new Insect(e2, insectName);
        Controller.putToNameMap(i2, i2.getName());
        e2.addInsect(i2);
        e2.placeInitial(tectons.get(initialTecton));

    }

    public void startGame(){
        //TODO
    }

    /**
     * A játék végén hirdeti ki a győzteseket. A győztesek nevét és pontszámát kiírja a konzolra.
     * A győztesek a játékosok közül kerülnek ki, akiknek a pontszáma a legmagasabb.
     */
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

    /**
     * Kihirdeti a győzteseket a játék végén. A győztesek nevét és pontszámát kiírja a konzolra.
     */
    public void announceWinners(){
        String Indent = "    ";

        System.out.println("The game has ended!");
        System.out.println("Winners:");
        for (Player player : players) {
            if (player.getIsWinner()) {
                System.out.println(Indent + player.getName() + ": " + player.getScore());
            }
        }
    }

    public void removeTecton(Tecton t) {
        tectons.remove(t);
    }

    public void addTecton(Tecton t){
        tectons.add(t);
    }

    public void addPlayer(Player p){
        players.add(p);
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
