package com.example;

import java.util.*;
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

    /*  Starts a new test case, resetting the line counter and indentation depth.
        Prints a header with the test case name. */
    public static void initTestCase(String testCase) {
        lineCounter = 1;
        depth = 1;
        System.out.println();
        System.out.println("---------- " + testCase + " ----------");
    }

    public static void finishTestCase(String testCase) {
        System.out.println("---------- " + testCase + " ----------");
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


    public static void insectEatHypahraSpore() {

        initTestCase("Insect eats Hypahra spore ");

        // Create instances
        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");
        
        Entomologist e1 = new Entomologist("e1");
        logCreateInstance(e1, "Entomologist", "e1");
        
        Insect i1 = new Insect(e1);
        logCreateInstance(i1, "Insect", "i1");

        HypharaSpore s1 = new HypharaSpore();
        logCreateInstance(s1, "HypharaSpore", "s1");
    
        i1.enableEating();
        t1.placeInsect(i1);
        t1.addSpore(s1);


        // Log function calls
        i1.eatSpore();

        finishTestCase("Insect eats Hypahra spore ");

    }

    public static void hypharaLosesEffect() {
        initTestCase("Hyphara loses effect");

        // Create instances


        Insect i1 = new Insect(new Entomologist("e1"));
        logCreateInstance(i1, "Insect", "i1");

        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");


        // Log function calls
        i1.setSpeed(0.1f);
        t1.placeInsect(i1);

        i1.setSpeed(1.0f);

        finishTestCase("Hyphara loses effect");
    }

    public static void insectEatsGilledonSpore() {
        initTestCase("Insect eats Gilledon spore");

        // Create instances
        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");

        Entomologist e1 = new Entomologist("e1");
        logCreateInstance(e1, "Entomologist", "e1");

        Insect i1 = new Insect(e1);
        logCreateInstance(i1, "Insect", "i1");

        GilledonSpore s1 = new GilledonSpore();
        logCreateInstance(s1, "GilledonSpore", "s1");


        // Log function calls

        t1.placeInsect(i1);
        t1.addSpore(s1);
        i1.enableEating();

        i1.eatSpore();


        finishTestCase("Insect eats Gilledon spore");


    }
    public static void gilledonLosesEffect() {
        initTestCase("Gilledon loses effect");

        // Create instances
        Insect i1 = new Insect(new Entomologist("e1"));
        logCreateInstance(i1, "Insect", "i1");

        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");


        // Log function calls
        i1.setSpeed(0.1f);
        t1.placeInsect(i1);

        i1.setSpeed(1.0f);

        finishTestCase("Gilledon loses effect");
    }
    public static void insectEatsPoraliaSpore () {
        initTestCase("Insect eats Poralia spore");

        // Create instances
        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");

        Entomologist e1 = new Entomologist("e1");
        logCreateInstance(e1, "Entomologist", "e1");

        Insect i1 = new Insect(e1);
        logCreateInstance(i1, "Insect", "i1");

        PoraliaSpore s1 = new PoraliaSpore();
        logCreateInstance(s1, "PoraliaSpore", "s1");

        // Log function calls

        i1.enableEating();
        t1.placeInsect(i1);
        t1.addSpore(s1);

        i1.eatSpore();

        finishTestCase("Insect eats Poralia spore");
    }
    public static void poraliaLosesEffect () {
        initTestCase("Poralia loses effect");

        // Create instances
        Insect i1 = new Insect(new Entomologist("e1"));
        logCreateInstance(i1, "Insect", "i1");

        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");


        // Log function calls
        i1.paralize();
        t1.placeInsect(i1);

        i1.unParalized();

        finishTestCase("Poralia loses effect");

    }
    public static void insectEatCapulonSpore() {
        initTestCase("Insect eats Capulon spore");

        // Create instances
        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");

        Entomologist e1 = new Entomologist("e1");
        logCreateInstance(e1, "Entomologist", "e1");

        Insect i1 = new Insect(e1);
        logCreateInstance(i1, "Insect", "i1");

        CapulonSpore s1 = new CapulonSpore();
        logCreateInstance(s1, "CapulonSpore", "s1");

        // Log function calls

        i1.enableEating();
        t1.placeInsect(i1);
        t1.addSpore(s1);

        i1.eatSpore();

        finishTestCase("Insect eats Capulon spore");
    }
    public static void capulonLosesEffect() {
        initTestCase("Capulon loses effect");

        // Create instances
        Insect i1 = new Insect(new Entomologist("e1"));
        logCreateInstance(i1, "Insect", "i1");

        Transix t1 = new Transix();
        logCreateInstance(t1, "Transix", "t1");


        // Log function calls
        i1.disableChewMycelium();
        t1.placeInsect(i1);

        i1.enableEating();

        finishTestCase("Capulon loses effect");
    }
    public static void insectEatsSporeOnOrogenix() {
        initTestCase("Insect eats spore on Orogenix");

        // Create instances

        Orogenix t1 = new Orogenix();
        logCreateInstance(t1, "Orogenix", "t1");

        Entomologist e1 = new Entomologist("e1");
        logCreateInstance(e1, "Entomologist", "e1");

        Insect i1 = new Insect(e1);
        logCreateInstance(i1, "Insect", "i1");

        CapulonSpore s1 = new CapulonSpore();
        logCreateInstance(s1, "CapulonSpore", "s1");

        // Log function calls

        i1.enableEating();
        t1.placeInsect(i1);
        t1.addSpore(s1);

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

        Mycelium my1 = new Mycelium();
        logCreateInstance(my1, "Mycelium", "my1");

        Mycelium my2 = new Mycelium();
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

        Mycelium my1 = new Mycelium();
        logCreateInstance(my1, "Mycelium", "my1");

        Mycelium my2 = new Mycelium();
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

        Mycelium my1 = new Mycelium();
        logCreateInstance(my1, "Mycelium", "my1");

        Mycelium my2 = new Mycelium();
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

        Mycelium my1 = new Mycelium();
        logCreateInstance(my1, "Mycelium", "my1");

        Mycelium my2 = new Mycelium();
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

        Mycelium my1 = new Mycelium();
        logCreateInstance(my1, "Mycelium", "my1");

        Mycelium my2 = new Mycelium();
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

        Mycelium my1 = new Mycelium();
        logCreateInstance(my1, "Mycelium", "my1");

        Mycelium my2 = new Mycelium();
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

        Mycelium my1 = new Mycelium();
        logCreateInstance(my1, "Mycelium", "my1");

        Mycelium my2 = new Mycelium();
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

        Mycelium my1 = new Mycelium();
        logCreateInstance(my1, "Mycelium", "my1");

        Mycelium my2 = new Mycelium();
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

        // Log function call
        //Szekvencia diagram
        //gametable.init();
        t1.addTectonToNeighbors(t2);
        //t2.addTectonToNeighbors(t3);
        //t3.addTectonToNeighbors(t1);

        finishTestCase("Initialize starting Tectons");
    }

    public static void breakTectonApart() {
        initTestCase("Break Tecton apart");
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

        boolean hasMushroomBody = logBranch("Legyen a tektonon gomba test?");
        if (hasMushroomBody) {
            mb = new Hyphara();
            logCreateInstance(mb, "Hyphara", "mb");

            my = new Mycelium();
            logCreateInstance(my, "Mycelium", "my");
        }

        boolean hasInsect = logBranch("Legyen a tektonon rovar?");
        if (hasInsect) {
            i = new Insect(new Entomologist("e1"));
            logCreateInstance(i, "Insect", "i");
        }


        //TODO kommunikációs diagram
        if (hasInsect) {
            t.placeInsect(i);
        }
        if (hasMushroomBody) {
            t.placeMushroomBody(mb);
            t.addMycelium(my);
        }
        t.addTectonToNeighbors(neigh1);
        t.addTectonToNeighbors(neigh2);

        //TODO Szekvencia diagram
        t.breakApart();

        finishTestCase("Break Tecton apart");
    }
}
