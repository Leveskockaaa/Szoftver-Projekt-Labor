package com.example;

public class Main {
    public static void main(String[] args) {
        Logger.initTestCase("function-call-test");
        Logger.logFunctionCall("user", "moveTo", "transix");
        Logger.logFunctionCall("insect", "placeInsect", "insect");
        Logger.logFunctionCall("transix", "neutralizeTectonEffects");
        Logger.logReturn("transix", "neutralizeTectonEffects");
        Logger.logFunctionCall("transix", "removeInsect");
        Logger.logFunctionCall("myself", "randomIntent");
        Logger.logReturn("myself", "randomIntent");
        Logger.logReturn("transix", "removeInsect");
        Logger.logFunctionCall("transix", "setNutrientMultiplier", "times");
        Logger.logReturn("transix", "setNutrientMultiplier");
        Logger.logReturn("insect", "placeInsect");

        Logger.initTestCase("branch-test");
        Logger.logFunctionCall("me", "ask", "you");
        Logger.logBranch("are you doing well?");
        Logger.logReturn("system", "ask");
    }
}