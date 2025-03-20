package com.example;

import java.util.Scanner;

public class Logger {
    private static int lineCounter = 1;
    private static final String INDENT = "\t";
    private static int depth = 1;
    private static final Scanner scanner = new Scanner(System.in);

    /*  Private constructor */
    private Logger() { }

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

    /*  Logs a function call with indentation based on the call depth.
        Allows passing multiple parameters to be displayed inside brackets. */
    public static void logFunctionCall(String className, String methodName, String... params) {
        String paramList = String.join(", ", params);
        System.out.println(formatLineNumber(lineCounter++) + INDENT.repeat(depth) + className + "." + methodName + "(" + paramList + ")");
        depth++;
    }

    /*  Logs a function return, reducing the indentation depth. */
    public static void logReturn(String className, String methodName) {
        depth--;
        System.out.println(formatLineNumber(lineCounter++) + INDENT.repeat(depth) + "return " + className + "." + methodName + "()");
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
}
