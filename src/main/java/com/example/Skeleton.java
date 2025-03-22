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
        // String paramList = String.join(", ", params);
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
    public static void logBranch(String condition) {
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
    }


    public static void logCreateInstance(Object o, String className, String instanceName) {
        System.out.println(formatLineNumber(lineCounter++) + INDENT.repeat(depth) + className + " " + instanceName + " = new " + className + "()");
        nameMap.put(o, instanceName);
    }


    public static void insectEatHypahraSpore() {
        initTestCase("Insect eat HypharaSpore");
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
        t1.addTectonToNeighbors(t2);
        t2.addTectonToNeighbors(t3);
        t3.addTectonToNeighbors(t1);

        finishTestCase("Initialize starting Tectons");
    }

    public static void breakTectonApartWithoutInsectOrMushroomBody1() {
        initTestCase("Break Tecton apart without Insect or MushroomBody1");
        // Create instances
        Transix t = new Transix();
        logCreateInstance(t, "Transix", "t");

        Hyphara mb = new Hyphara();
        logCreateInstance(mb, "Hyphara", "mb");

        Mycelium my = new Mycelium();
        logCreateInstance(my, "Mycelium", "my");

        Transix neigh1 = new Transix();
        logCreateInstance(neigh1, "Transix", "neigh1");

        Transix neigh2 = new Transix();
        logCreateInstance(neigh2, "Transix", "neigh2");

        // TODO kommunikációs diagram
        t.placeMushroomBody(mb);
        t.addMycelium(my);
        t.addTectonToNeighbors(neigh1);
        t.addTectonToNeighbors(neigh2);

        //TODO Szekvencia diagram
        t.breakApart();




        finishTestCase("Break Tecton apart without Insect or MushroomBody1");
    }





}
