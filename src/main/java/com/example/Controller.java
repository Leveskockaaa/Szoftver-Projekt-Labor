package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Controller {
    private static List<String> commandsList = new ArrayList<>();
    private static HashMap<Object, String> nameMap = new HashMap<>();
    private static int seconds;
    private static boolean isRandomOn;



    public static Object getFromNameMap(String name) {
        for (Object object : nameMap.keySet()) {
            if (nameMap.get(object).equals(name)) {
                return object;
            }
        }
        return null;
    }

    public void runCommand(String command) {

    }

    public void saveState(String filePath) {

    }

    public void loadState(String filePath) {

    }

    void delay(int seconds) {
        this.seconds += seconds;
    }

    int chooseTest() {

    }

    void runTest(int testNumber) {

    }
}
