package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Skeleton {
    private static int lineCounter = 1;
    private static final String INDENT = "\t";
    private static int depth = 1;
    private static final Scanner scanner = new Scanner(System.in);

    private static HashMap<Object, String> nameMap = new HashMap<>();

    /*  Private constructor */
    public Skeleton() { }

    /*  Formats the line number to always have at least two digits. */
    private static String formatLineNumber(int number) {
        return (number < 10 ? "0" : "") + number;
    }

    private static String formatText(String text) {
        int textLength = text.length();
        int maxLength = 54;
        int padding = (maxLength - textLength) / 2;
        String leftPadding = "-".repeat(padding) + " ";
        String rightPadding = " " + "-".repeat(maxLength - textLength - padding);
        return leftPadding + text + rightPadding;
    }

    /*  Starts a new test case, resetting the line counter and indentation depth.
        Prints a header with the test case name. */
    public static void initTestCase(String testCase) {
        lineCounter = 1;
        depth = 1;
        System.out.println();
        System.out.println(formatText(testCase));
    }

    public static void finishTestCase(String testCase) {
        System.out.println(formatText(testCase));
        System.out.println();
    }

    /*  Logs a function call with indentation based on the call depth.
        Allows passing multiple parameters to be displayed inside brackets. */
    public static void logFunctionCall(Object instance, String methodName, Object... params) {
        List<Object> paramLista = Arrays.asList(params);
        List<String> paramNames = paramLista.stream().map(o -> nameMap.getOrDefault(o, o.toString())).collect(Collectors.toList());
        String instanceName = nameMap.getOrDefault(instance, instance.toString());
        String paramList = String.join(", ", paramNames);
        System.out.println(formatLineNumber(lineCounter++) + INDENT.repeat(depth) + instanceName + "." + methodName + "(" + paramList + ")");
        depth++;
    }

    /*  Logs a function return, reducing the indentation depth. */
    public static void logReturn(Object instance, String methodName) {

        String instanceName = nameMap.getOrDefault(instance, instance.toString());
        depth--;
        System.out.println(formatLineNumber(lineCounter++) + INDENT.repeat(depth) + "return " + instanceName + "." + methodName + "()");
    }

    /* Logs a branch decision and asks the user whether the condition should be true or false. */
    public static boolean logBranch(String condition) {
        String result;
        System.out.print(formatLineNumber(lineCounter++) + INDENT.repeat(depth) + "branch " + condition + " [y/n]: ");
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y")) {
                result = "TRUE";
                break;
            }
            if (input.equals("n")) {
                result = "FALSE";
                break;
            }
            System.out.print("Wrong input. Type 'y' or 'n': ");
        }
        System.out.println(formatLineNumber(lineCounter++) + INDENT.repeat(depth) + "branch " + condition + " is " + result);
        if (result.equals("TRUE")) {
            return true;
        } else {
            return false;
        }
    }


    public static void logCreateInstance(Object o, String className, String instanceName) {
        System.out.println(formatLineNumber(lineCounter++) + INDENT.repeat(depth) + className + " " + instanceName + " = new " + className + "()");
        nameMap.put(o, instanceName);
    }

    public static void logCommunicationDiagramStart() {
        System.out.println(formatText("Communication Diagram Elements"));

    }
    public static void logInitializationFunctionCalls() {
        System.out.println(formatText("Initialization"));
    }
    public static void logSequencyDiagramStart() {
        System.out.println(formatText("Sequency Diagram Elements"));
    }

    /*
     * A felhasználó kiadja a parancsot, hogy a rovar egy spórát megegyen.
     * Ekkora azon a tektonon amin a rovar áll a spórák közöl a legrégebben
     * ott lévő megevésre kerül (ebben az esetben ez egy Hyphara spóra), 
     * tehát a tektonról (ami ebben az esetben egy Transix tekton, de ennek
     * nincsen jelentősége lehetne Orogenixen kívül az összes) eltűnik, a
     * rovarász pontjaihoz adódik és a rovar sebességét megnöveli
     * másfélszeresére. 
     */
    public static void insectEatHypahraSpore() {

        initTestCase("Insect eats Hypahra spore ");

        // Create instances
        logCommunicationDiagramStart();
        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");
        
        Entomologist e1 = new Entomologist("e1");
        logCreateInstance(e1, "Entomologist", "e1");
        
        Insect i1 = new Insect(e1);
        logCreateInstance(i1, "Insect", "i1");

        HypharaSpore s1 = new HypharaSpore();
        logCreateInstance(s1, "HypharaSpore", "s1");
    
        logInitializationFunctionCalls();
        i1.enableEating();
        t1.placeInsect(i1);
        t1.addSpore(s1);

        logSequencyDiagramStart();
        // Log function calls
        i1.eatSpore();

        finishTestCase("Insect eats Hypahra spore ");

    }

    /*
     * A felhasználó kiadja a parancsot, amivel visszaállítja
     * a rovar eredeti sebességét.
     */
    public static void hypharaLosesEffect() {
        initTestCase("Hyphara loses effect");

        // Create instances
        logCommunicationDiagramStart();
        Insect i1 = new Insect(new Entomologist("e1"));
        logCreateInstance(i1, "Insect", "i1");

        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");


        // Log init function calls
        logInitializationFunctionCalls();
        i1.setSpeed(0.1f);
        t1.placeInsect(i1);

        // Log sequence function calls
        logSequencyDiagramStart();
        i1.setSpeed(1.0f);

        finishTestCase("Hyphara loses effect");
    }

    /*
     * A felhasználó kiadja a parancsot, hogy a rovar egy spórát megegyen.
     * Ekkora azon a tektonon amin a rovar áll a spórák közöl a legrégebben
     * ott lévő megevésre kerül (ebben az esetben ez egy Gilledon spóra),
     * tehát a tektonról (ami ebben az esetben egy Transix tekton, de
     * ennek nincsen jelentősége lehetne Orogenixen kívül az összes) eltűnik,
     * a rovarász pontjaihoz adódik és a rovar sebességét lecsökkenti kétharmadára
     */
    public static void insectEatsGilledonSpore() {
        initTestCase("Insect eats Gilledon spore");

        // Create instances
        logCommunicationDiagramStart();
        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");

        Entomologist e1 = new Entomologist("e1");
        logCreateInstance(e1, "Entomologist", "e1");

        Insect i1 = new Insect(e1);
        logCreateInstance(i1, "Insect", "i1");

        GilledonSpore s1 = new GilledonSpore();
        logCreateInstance(s1, "GilledonSpore", "s1");

        // Log init function calls
        logInitializationFunctionCalls();

        t1.placeInsect(i1);
        t1.addSpore(s1);
        i1.enableEating();

        // Log sequence function calls
        logSequencyDiagramStart();
        i1.eatSpore();

        finishTestCase("Insect eats Gilledon spore");
    }

    /*
     * A felhasználó kiadja a parancsot, amivel visszaállítja
     * a rovar eredeti sebességét. 
     */
    public static void gilledonLosesEffect() {
        initTestCase("Gilledon loses effect");

        // Create instances
        logCommunicationDiagramStart();
        Insect i1 = new Insect(new Entomologist("e1"));
        logCreateInstance(i1, "Insect", "i1");

        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");


        // Log initislization function calls
        logInitializationFunctionCalls();
        i1.setSpeed(0.1f);
        t1.placeInsect(i1);

        // Log sequence function calls
        logSequencyDiagramStart();
        i1.setSpeed(1.0f);

        finishTestCase("Gilledon loses effect");
    }

    /*
     * A felhasználó kiadja a parancsot, hogy a rovar egy spórát megegyen.
     * Ekkora azon a tektonon amin a rovar áll a spórák közöl a legrégebben
     * ott lévő megevésre kerül (ebben az esetben ez egy Poralia spóra),
     * tehát a tektonról (ami ebben az esetben egy Transix tekton, de ennek
     * nincsen jelentősége lehetne Orogenixen kívül az összes) eltűnik, a
     * rovarász pontjaihoz adódik és a rovart megbénítja. 
     */
    public static void insectEatsPoraliaSpore () {
        initTestCase("Insect eats Poralia spore");

        // Create instances
        logCommunicationDiagramStart();
        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");

        Entomologist e1 = new Entomologist("e1");
        logCreateInstance(e1, "Entomologist", "e1");

        Insect i1 = new Insect(e1);
        logCreateInstance(i1, "Insect", "i1");

        PoraliaSpore s1 = new PoraliaSpore();
        logCreateInstance(s1, "PoraliaSpore", "s1");

        // Log initialization function calls
        logInitializationFunctionCalls();
        i1.enableEating();
        t1.placeInsect(i1);
        t1.addSpore(s1);

        // Log sequence function calls
        logSequencyDiagramStart();
        i1.eatSpore();

        finishTestCase("Insect eats Poralia spore");
    }
    /*
     * A felhasználó kiadja a parancsot, amivel feloldja a rovar bénítását. 
     */
    public static void poraliaLosesEffect () {
        initTestCase("Poralia loses effect");

        // Create instances
        logCommunicationDiagramStart();
        Insect i1 = new Insect(new Entomologist("e1"));
        logCreateInstance(i1, "Insect", "i1");

        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");


        // Log initialization function calls
        logInitializationFunctionCalls();
        i1.paralize();
        t1.placeInsect(i1);

        // Log sequence function calls
        logSequencyDiagramStart();
        i1.unParalized();

        finishTestCase("Poralia loses effect");

    }
    /*
     * A felhasználó kiadja a parancsot, hogy a rovar egy spórát megegyen.
     * Ekkora azon a tektonon amin a rovar áll a spórák közöl a legrégebben
     * ott lévő megevésre kerül (ebben az esetben ez egy Capulon spóra),
     * tehát a tektonról (ami ebben az esetben egy Transix tekton, de ennek
     * nincsen jelentősége lehetne Orogenixen kívül az összes) eltűnik, a
     * rovarász pontjaihoz adódik és a rovarnak elveszi a fonalak elrágásának
     * képességét. 
     */
    public static void insectEatCapulonSpore() {
        initTestCase("Insect eats Capulon spore");

        // Create instances
        logCommunicationDiagramStart();
        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");

        Entomologist e1 = new Entomologist("e1");
        logCreateInstance(e1, "Entomologist", "e1");

        Insect i1 = new Insect(e1);
        logCreateInstance(i1, "Insect", "i1");

        CapulonSpore s1 = new CapulonSpore();
        logCreateInstance(s1, "CapulonSpore", "s1");

        // Log initialization function calls
        logInitializationFunctionCalls();
        i1.enableEating();
        t1.placeInsect(i1);
        t1.addSpore(s1);

        // Log sequence function calls
        logSequencyDiagramStart();
        i1.eatSpore();

        finishTestCase("Insect eats Capulon spore");
    }
    /*
     * A felhasználó kiadja a parancsot, amivel visszaadja a fonalak elrágásának képességét. 
     */
    public static void capulonLosesEffect() {
        initTestCase("Capulon loses effect");

        // Create instances
        logCommunicationDiagramStart();
        Insect i1 = new Insect(new Entomologist("e1"));
        logCreateInstance(i1, "Insect", "i1");

        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");


        // Log initialization function calls
        logInitializationFunctionCalls();
        i1.disableChewMycelium();
        t1.placeInsect(i1);

        // Log sequence function calls
        logSequencyDiagramStart();
        i1.enableEating();

        finishTestCase("Capulon loses effect");
    }
    /*
     * A felhasználó kiadja a parancsot, hogy a rovar egy spórát megegyen miközben
     * a rovar egy Orogenix tektonon tartózkodik. Ekkora azon a tektonon amin a
     * rovar áll a spórák közöl a legrégebben ott lévő megevésre kerül (ebben az
     * esetben ez egy Capulon spóra, de ennek nincsen jelentősége, lehetne bármilyen),
     * tehát a tektonról eltűnik, a rovarász pontjaihoz adódik. 
     */
    public static void insectEatsSporeOnOrogenix() {
        initTestCase("Insect eats spore on Orogenix");

        // Create instances
        logCommunicationDiagramStart();
        Orogenix t1 = new Orogenix();
        logCreateInstance(t1, "Orogenix", "t1");

        Entomologist e1 = new Entomologist("e1");
        logCreateInstance(e1, "Entomologist", "e1");

        Insect i1 = new Insect(e1);
        logCreateInstance(i1, "Insect", "i1");

        CapulonSpore s1 = new CapulonSpore();
        logCreateInstance(s1, "CapulonSpore", "s1");

        // Log initialization function calls
        logInitializationFunctionCalls();
        i1.enableEating();
        t1.placeInsect(i1);
        t1.addSpore(s1);

        // Log sequence function calls
        logSequencyDiagramStart();
        i1.eatSpore();

        finishTestCase("Insect eats spore on Orogenix");
    }

    public static void insectMovesToTransix() {
        initTestCase("Insect can move to Transix");

        // Create instances

        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");

        Transix t2 = new Transix();
        logCreateInstance(t2, "Transix", "t2");

        Mycelium my1 = new Mycelium(t1);
        logCreateInstance(my1, "Mycelium", "my1");

        Mycelium my2 = new Mycelium(t2);
        logCreateInstance(my2, "Mycelium", "my2");

        Entomologist e1 = new Entomologist("e1");
        logCreateInstance(e1, "Entomologist", "e1");

        Insect i1 = new Insect(e1);
        logCreateInstance(i1, "Insect", "i1");

        // Log init function calls

        t1.placeInsect(i1);
        t1.addMycelium(my1);
        t1.addTectonToNeighbors(t2);

        t2.addMycelium(my2);
        t2.addTectonToNeighbors(t1);

        my1.createNewBranch(t2);

        i1.unParalized();

        // Log sequence function calls
        System.out.println("-----------------");

        i1.moveTo(t2);
    }

    public static void insectMovesToMagmox() {
        initTestCase("Insect can move to Magmox");

        // Create instances

        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");

        Magmox t2 = new Magmox();
        logCreateInstance(t2, "Magmox", "t2");

        Mycelium my1 = new Mycelium(t1);
        logCreateInstance(my1, "Mycelium", "my1");

        Mycelium my2 = new Mycelium(t2);
        logCreateInstance(my2, "Mycelium", "my2");

        Entomologist e1 = new Entomologist("e1");
        logCreateInstance(e1, "Entomologist", "e1");

        Insect i1 = new Insect(e1);
        logCreateInstance(i1, "Insect", "i1");

        // Log init function calls

        t1.placeInsect(i1);
        t1.addMycelium(my1);
        t1.addTectonToNeighbors(t2);

        t2.addMycelium(my2);
        t2.addTectonToNeighbors(t1);

        my1.createNewBranch(t2);

        i1.unParalized();

        // Log sequence function calls
        System.out.println("-----------------");

        i1.moveTo(t2);
    }

    public static void insectMovesToMantleon() {
        initTestCase("Insect can move to Mantleon");

        // Create instances

        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");

        Mantleon t2 = new Mantleon();
        logCreateInstance(t2, "Mantleon", "t2");

        Mycelium my1 = new Mycelium(t1);
        logCreateInstance(my1, "Mycelium", "my1");

        Mycelium my2 = new Mycelium(t2);
        logCreateInstance(my2, "Mycelium", "my2");

        Entomologist e1 = new Entomologist("e1");
        logCreateInstance(e1, "Entomologist", "e1");

        Insect i1 = new Insect(e1);
        logCreateInstance(i1, "Insect", "i1");

        // Log init function calls

        t1.placeInsect(i1);
        t1.addMycelium(my1);
        t1.addTectonToNeighbors(t2);

        t2.addMycelium(my2);
        t2.addTectonToNeighbors(t1);

        my1.createNewBranch(t2);

        i1.unParalized();

        // Log sequence function calls
        System.out.println("-----------------");

        i1.moveTo(t2);
    }

    public static void insectMovesToOrogenix() {
        initTestCase("Insect can move to Orogenix");

        // Create instances

        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");

        Orogenix t2 = new Orogenix();
        logCreateInstance(t2, "Orogenix", "t2");

        Mycelium my1 = new Mycelium(t1);
        logCreateInstance(my1, "Mycelium", "my1");

        Mycelium my2 = new Mycelium(t2);
        logCreateInstance(my2, "Mycelium", "my2");

        Entomologist e1 = new Entomologist("e1");
        logCreateInstance(e1, "Entomologist", "e1");

        Insect i1 = new Insect(e1);
        logCreateInstance(i1, "Insect", "i1");

        // Log init function calls

        t1.placeInsect(i1);
        t1.addMycelium(my1);
        t1.addTectonToNeighbors(t2);

        t2.addMycelium(my2);
        t2.addTectonToNeighbors(t1);

        my1.createNewBranch(t2);

        i1.unParalized();

        // Log sequence function calls
        System.out.println("-----------------");

        i1.moveTo(t2);
    }

    public static void insectCantMoveOccupied() {
        initTestCase("Insect can't move due to occupied Tecton");

        // Create instances

        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");

        Transix t2 = new Transix();
        logCreateInstance(t2, "Transix", "t2");

        Mycelium my1 = new Mycelium(t1);
        logCreateInstance(my1, "Mycelium", "my1");

        Mycelium my2 = new Mycelium(t2);
        logCreateInstance(my2, "Mycelium", "my2");

        Entomologist e1 = new Entomologist("e1");
        logCreateInstance(e1, "Entomologist", "e1");

        Entomologist e2 = new Entomologist("e2");
        logCreateInstance(e2, "Entomologist", "e2");

        Insect i1 = new Insect(e1);
        logCreateInstance(i1, "Insect", "i1");

        Insect i2 = new Insect(e2);
        logCreateInstance(i2, "Insect", "i2");

        // Log init function calls

        t1.placeInsect(i1);
        t1.addMycelium(my1);
        t1.addTectonToNeighbors(t2);

        t2.placeInsect(i2);
        t2.addMycelium(my2);
        t2.addTectonToNeighbors(t1);

        my1.createNewBranch(t2);

        i1.unParalized();
        i2.unParalized();

        // Log sequence function calls
        System.out.println("-----------------");

        i1.moveTo(t2);
    }

    public static void insectCantMoveNoConnection() {
        initTestCase("Insect can't move due to no connection");

        // Create instances

        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");

        Transix t2 = new Transix();
        logCreateInstance(t2, "Transix", "t2");

        Mycelium my1 = new Mycelium(t1);
        logCreateInstance(my1, "Mycelium", "my1");

        Mycelium my2 = new Mycelium(t2);
        logCreateInstance(my2, "Mycelium", "my2");

        Entomologist e1 = new Entomologist("e1");
        logCreateInstance(e1, "Entomologist", "e1");

        Insect i1 = new Insect(e1);
        logCreateInstance(i1, "Insect", "i1");

        // Log init function calls

        t1.placeInsect(i1);
        t1.addMycelium(my1);
        t1.addTectonToNeighbors(t2);

        t2.addMycelium(my2);
        t2.addTectonToNeighbors(t1);

        i1.unParalized();

        // Log sequence function calls
        System.out.println("-----------------");

        i1.moveTo(t2);
    }

    public static void insectChewsMycelium() {
        initTestCase("Insect chews mycelium");

        // Create instances

        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");

        Transix t2 = new Transix();
        logCreateInstance(t2, "Transix", "t2");

        Mycelium my1 = new Mycelium(t1);
        logCreateInstance(my1, "Mycelium", "my1");

        Mycelium my2 = new Mycelium(t2);
        logCreateInstance(my2, "Mycelium", "my2");

        Entomologist e1 = new Entomologist("e1");
        logCreateInstance(e1, "Entomologist", "e1");

        Insect i1 = new Insect(e1);
        logCreateInstance(i1, "Insect", "i1");

        // Log init function calls

        t1.placeInsect(i1);
        t1.addMycelium(my1);
        t1.addTectonToNeighbors(t2);

        t2.addMycelium(my2);
        t2.addTectonToNeighbors(t1);

        my1.createNewBranch(t2);

        i1.enableToChewMycelium();

        // Log sequence function calls
        System.out.println("-----------------");

        i1.chewMycelium(my1, my2);
    }

    public static void insectCantChewMycelium() {
        initTestCase("Insect can't chew mycelium");

        // Create instances

        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");

        Transix t2 = new Transix();
        logCreateInstance(t2, "Transix", "t2");

        Mycelium my1 = new Mycelium(t1);
        logCreateInstance(my1, "Mycelium", "my1");

        Mycelium my2 = new Mycelium(t2);
        logCreateInstance(my2, "Mycelium", "my2");

        Entomologist e1 = new Entomologist("e1");
        logCreateInstance(e1, "Entomologist", "e1");

        Insect i1 = new Insect(e1);
        logCreateInstance(i1, "Insect", "i1");

        // Log init function calls

        t1.placeInsect(i1);
        t1.addMycelium(my1);
        t1.addTectonToNeighbors(t2);

        t2.addMycelium(my2);
        t2.addTectonToNeighbors(t1);

        my1.createNewBranch(t2);

        i1.disableChewMycelium();

        // Log sequence function calls
        System.out.println("-----------------");

        i1.chewMycelium(my1, my2);
    }

    public static void initializeStartingTectons() {
        initTestCase("Initialize starting Tectons");
        // Create instances
        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");

        Mantleon t2 = new Mantleon();
        logCreateInstance(t2, "Mantleon", "t2");

        Orogenix t3 = new Orogenix();
        logCreateInstance(t3, "Orogenix", "t3");

        GameTable gt = new GameTable();
        logCreateInstance(gt, "GameTable", "gt");

        // TODO kommunikációs diagram
        gt.setTectons(new ArrayList<>(Arrays.asList(t1, t2, t3)));
        //Szekvencia diagram
        gt.initialize();

        finishTestCase("Initialize starting Tectons");
    }

    public static void breakTectonApartWithoutInsectOrMushroomBody() {
        initTestCase("Break Tecton apart without Insect or Mushroombody");
        // Create instances
        Transix t = new Transix();
        logCreateInstance(t, "Transix", "t");

        Transix neigh1 = new Transix();
        logCreateInstance(neigh1, "Transix", "neigh1");

        Transix neigh2 = new Transix();
        logCreateInstance(neigh2, "Transix", "neigh2");

        //TODO kommunikációs diagram
        t.addTectonToNeighbors(neigh1);
        t.addTectonToNeighbors(neigh2);

        //TODO Szekvencia diagram
        t.breakApart();

        finishTestCase("Break Tecton apart without Insect or Mushroombody");
    }

    public static void breakTectonApartWithInsect() {
        initTestCase("Break Tecton apart with Insect");
        // Create instances
        Transix t = new Transix();
        logCreateInstance(t, "Transix", "t");

        Transix neigh1 = new Transix();
        logCreateInstance(neigh1, "Transix", "neigh1");

        Transix neigh2 = new Transix();
        logCreateInstance(neigh2, "Transix", "neigh2");

        Insect i = new Insect(new Entomologist("e1"));
        logCreateInstance(i, "Insect", "i");

        //TODO kommunikációs diagram
        t.placeInsect(i);
        t.addTectonToNeighbors(neigh1);
        t.addTectonToNeighbors(neigh2);

        //TODO Szekvencia diagram
        t.breakApart();

        finishTestCase("Break Tecton apart with Insect");
    }

    public static void breakTectonApartWithMushroomBody() {
        initTestCase("Break Tecton apart with MushroomBody");
        // Create instances
        Transix t = new Transix();
        logCreateInstance(t, "Transix", "t");

        Transix neigh1 = new Transix();
        logCreateInstance(neigh1, "Transix", "neigh1");

        Transix neigh2 = new Transix();
        logCreateInstance(neigh2, "Transix", "neigh2");

        Hyphara mb = new Hyphara(t);
        logCreateInstance(mb, "Hyphara", "mb");

        Mycelium my = new Mycelium(t);
        logCreateInstance(my, "Mycelium", "my");

        //TODO kommunikációs diagram
        t.placeMushroomBody(mb);
        t.addMycelium(my);

        t.addTectonToNeighbors(neigh1);
        t.addTectonToNeighbors(neigh2);

        //TODO Szekvencia diagram
        t.breakApart();

        finishTestCase("Break Tecton apart with MushroomBody");
    }




    public static void breakTectonApartWithInsectAndMushroomBody() {
        initTestCase("Break Tecton apart with Insect and MushroomBody");
        Hyphara mb = null;
        Mycelium my = null;
        Insect i = null;
        // Create instances
        Transix t = new Transix();
        logCreateInstance(t, "Transix", "t");

        Transix neigh1 = new Transix();
        logCreateInstance(neigh1, "Transix", "neigh1");

        Transix neigh2 = new Transix();
        logCreateInstance(neigh2, "Transix", "neigh2");

        mb = new Hyphara(t);
        logCreateInstance(mb, "Hyphara", "mb");

        my = new Mycelium(t);
        logCreateInstance(my, "Mycelium", "my");

        i = new Insect(new Entomologist("e1"));
        logCreateInstance(i, "Insect", "i");


        //TODO kommunikációs diagram
        t.placeInsect(i);

        t.placeMushroomBody(mb);
        t.addMycelium(my);

        t.addTectonToNeighbors(neigh1);
        t.addTectonToNeighbors(neigh2);

        //TODO Szekvencia diagram
        t.breakApart();

        finishTestCase("Break Tecton apart with Insect and MushroomBody");
    }

    public static void growMycelium() {
        Skeleton.initTestCase("growMycelium");

        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");
        Transix t2 = new Transix();
        logCreateInstance(t2, "Transix", "t2");
        Mycelium my = new Mycelium(t1);
        logCreateInstance(my, "Mycelium", "my");

        t1.addTectonToNeighbors(t2);
        t1.addMycelium(my);
        my.enableGrowth();

        my.createNewBranch(t2);

        Skeleton.finishTestCase("growMycelium");
    }

    public static void cantGrowMycelium() {
        Skeleton.initTestCase("cantGrowMycelium");

        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");
        Transix t2 = new Transix();
        logCreateInstance(t2, "Transix", "t2");
        Mycelium my1 = new Mycelium(t1);
        logCreateInstance(my1, "Mycelium", "my1");
        Mycelium my2 = new Mycelium(t2);
        logCreateInstance(my2, "Mycelium", "my2");
        Mycelium my3 = new Mycelium(t2);
        logCreateInstance(my3, "Mycelium", "my3");

        t1.addTectonToNeighbors(t2);
        t1.addMycelium(my1);
        t2.addMycelium(my2);
        t2.addMycelium(my3);

        my1.createNewBranch(t2);

        Skeleton.finishTestCase("cantGrowMycelium");
    }

    public static void growMushroomBody() {
        Skeleton.initTestCase("growMushroomBody");

        Transix t = new Transix();
        logCreateInstance(t, "Transix", "t");
        Mycelium my = new Mycelium(t);
        logCreateInstance(my, "Mycelium", "my");
        HypharaSpore sp = new HypharaSpore();
        logCreateInstance(sp, "HypharaSpore", "sp");

        t.addMycelium(my);
        for(int n = 0; n < 20; n++){
            t.addSpore(sp);
        }

        my.developMushroomBody();

        Skeleton.finishTestCase("growMushroomBody");
    }

    public static void cantGrowMushroomBody() {
        Skeleton.initTestCase("cantGrowMushroomBody");

        Transix t = new Transix();
        logCreateInstance(t, "Transix", "t");
        Mycelium my = new Mycelium(t);
        logCreateInstance(my, "Mycelium", "my");

        t.addMycelium(my);

        my.developMushroomBody();

        Skeleton.finishTestCase("cantGrowMushroomBody");
    }

    public static void spreadSpores() {
        Skeleton.initTestCase("spreadSpores");

        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");
        Transix t2 = new Transix();
        logCreateInstance(t2, "Transix", "t2");
        Hyphara mb = new Hyphara(t1);
        logCreateInstance(mb, "Hyphara", "mb");
        Mycologist mc = new Mycologist("Player 1");
        logCreateInstance(mc, "Mycologist", "mc");
        mb.setMycologist(mc);

        t1.addTectonToNeighbors(t2);
        t1.placeMushroomBody(mb);

        mb.spreadSpores();

        Skeleton.finishTestCase("spreadSpores");
    }

    public static void spreadSporesWither() {
        Skeleton.initTestCase("spreadSporesWither");

        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");
        Transix t2 = new Transix();
        logCreateInstance(t2, "Transix", "t2");
        Hyphara mb = new Hyphara(t1);
        logCreateInstance(mb, "Hyphara", "mb");
        Mycologist mc = new Mycologist("Player 1");
        logCreateInstance(mc, "Mycologist", "mc");
        mb.setMycologist(mc);

        t1.addTectonToNeighbors(t2);
        t1.placeMushroomBody(mb);
        for(int n = 0; n < 14; n++){
            mb.spreadSpores();
        }

        mb.spreadSpores();

        Skeleton.finishTestCase("spreadSporesWither");
    }

    public static void mushroomBodyEvolves(){ // Communication diagram must be remade
        Skeleton.initTestCase("mushroomBodyEvolves");

        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");
        Transix t2 = new Transix();
        logCreateInstance(t2, "Transix", "t2");
        Transix t3 = new Transix();
        logCreateInstance(t3, "Transix", "t3");
        Transix t4 = new Transix();
        logCreateInstance(t4, "Transix", "t4");
        t1.addTectonToNeighbors(t2);
        t1.addTectonToNeighbors(t3);
        t1.addTectonToNeighbors(t4);

        Mycelium my1 = new Mycelium(t1);
        logCreateInstance(my1, "Mycelium", "my1");
        t1.addMycelium(my1);
        my1.enableGrowth();
        Mycelium my2 = my1.createNewBranch(t2); //return type change
        Mycelium my3 = my1.createNewBranch(t3);
        Mycelium my4 = my1.createNewBranch(t4);

        Hyphara mb1 = new Hyphara(t1);
        logCreateInstance(mb1, "Hyphara", "mb1");

        HypharaSpore sp = new HypharaSpore();
        logCreateInstance(sp, "HypharaSpore", "sp");
        for(int n = 0; n < 20; n++){
            t1.addSpore(sp);
        }

        mb1.evolveSuper();

        Skeleton.finishTestCase("mushroomBodyEvolves");
    }

    public static void mushroomBodyCantEvolve(){
        Skeleton.initTestCase("mushroomBodyCantEvolve");

        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");
        Transix t2 = new Transix();
        logCreateInstance(t2, "Transix", "t2");
        Transix t3 = new Transix();
        logCreateInstance(t3, "Transix", "t3");
        Transix t4 = new Transix();
        logCreateInstance(t4, "Transix", "t4");
        t1.addTectonToNeighbors(t2);
        t1.addTectonToNeighbors(t3);
        t1.addTectonToNeighbors(t4);

        Mycelium my1 = new Mycelium(t1);
        logCreateInstance(my1, "Mycelium", "my1");
        t1.addMycelium(my1);
        my1.enableGrowth();
        Mycelium my2 = my1.createNewBranch(t2);
        Mycelium my3 = my1.createNewBranch(t3);
        Mycelium my4 = my1.createNewBranch(t4);

        Hyphara mb1 = new Hyphara(t1);
        logCreateInstance(mb1, "Hyphara", "mb1");

        mb1.evolveSuper();

        Skeleton.finishTestCase("mushroomBodyCantEvolve");
    }
}
