package com.example;

/**
 * A Gilledon gombafaj gombatestjeinek kezeléséért felelős osztály.
 */
public class Gilledon extends MushroomBody{
    /**
     * Gilledon osztály konstruktora.
     * @param tecton A tekton amire a gombatest kerül.
     */
    Gilledon(Tecton tecton, Mycologist mycologist) {
        super(tecton, mycologist);
        sporeSpreadsLeft = 15;
    }

    /**
     * Létrehoz egy új Gilledon típusú gombatestet.
     * 
     * @return Új Gilledon típusú gombatest.
     */
    @Override
    public MushroomBody createMushroomBody(Tecton tecton, Mycologist mycologist) {
        return new Gilledon(tecton, mycologist);
    }

    /**
     * A Gilledon verzióját valósítja meg a spóraszórásnak.
     * Gilledon spórákat szór.
     */
    @Override
    public void spreadSpores() {
        Skeleton.logFunctionCall(this, "spreadSpores");
        for(Tecton t : tecton.getNeighbors()){
            t.addSpore(new GilledonSpore());
        }
        sporeSpreadsLeft--;
        if(sporeSpreadsLeft == 0){
            dead = true;
            mycologist.collect(this);
            tecton.removeMushroomBody();
        }
        Skeleton.logReturn(this, "spreadSpores");
    }

    /**
     * Egy igaz-hamis érték arról, hogy a Gilledon gombatest
     * szupergombává tud-e fejlődni.
     * @return true, ha szupergombává tud fejlődni, false ha nem.
     */
    @Override
    public boolean canEvolve() {
        Skeleton.logFunctionCall(this, "canEvolve");
        int sporeCount = 0;
        for (Spore s : tecton.sporesAvailable()){
            if(s.getClass() == GilledonSpore.class){ //Spore type?
                sporeCount++;
            }
        }
        Mycelium my = new Mycelium(tecton);
        for(Mycelium mycelium : tecton.getMycelia()){
            if(mycelium.getBodyType() == Gilledon.class){
                my = mycelium;
            }
        }
        Skeleton.logReturn(this, "canEvolve");
        return sporeCount >= 3 && my.getMyceliumConnections().size() >= 3;
    }
}
