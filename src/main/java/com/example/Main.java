package com.example;

public class Main {
    public static void main(String[] args) {
        /* 
        Skeleton.initTestCase("function-call-test");
        Skeleton.logFunctionCall("user", "moveTo", "transix");
        Skeleton.logFunctionCall("insect", "placeInsect", "insect");
        Skeleton.logFunctionCall("transix", "neutralizeTectonEffects");
        Skeleton.logReturn("transix", "neutralizeTectonEffects");
        Skeleton.logFunctionCall("transix", "removeInsect");
        Skeleton.logFunctionCall("myself", "randomIntent");
        Skeleton.logReturn("myself", "randomIntent");
        Skeleton.logReturn("transix", "removeInsect");
        Skeleton.logFunctionCall("transix", "setNutrientMultiplier", "times");
        Skeleton.logReturn("transix", "setNutrientMultiplier");
        Skeleton.logReturn("insect", "placeInsect");

        Skeleton.initTestCase("branch-test");
        Skeleton.logFunctionCall("me", "ask", "you");
        Skeleton.logBranch("are you doing well?");
        Skeleton.logReturn("system", "ask");
*/



        Skeleton.insectEatHypahraSpore();
        Skeleton.hypharaLosesEffect();

        Skeleton.insectEatsGilledonSpore();
        Skeleton.gilledonLosesEffect();

        Skeleton.insectEatCapulonSpore();
        Skeleton.capulonLosesEffect();

        Skeleton.insectEatsPoraliaSpore();
        Skeleton.poraliaLosesEffect();
        
        Skeleton.insectEatsSporeOnOrogenix();

        Skeleton.insectMovesToTransix();
        Skeleton.insectMovesToMagmox();
        Skeleton.insectMovesToMantleon();
        Skeleton.insectMovesToOrogenix();
        Skeleton.insectCantMoveOccupied();
        Skeleton.insectCantMoveNoConnection();

        Skeleton.insectChewsMycelium();
        Skeleton.insectCantChewMycelium();
    }
}