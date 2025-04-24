package com.example;

/**
 * A Hyphara gombafaj gombatestjeinek kezeléséért felelős osztály.
 */
public class Hyphara extends MushroomBody {
    /**
     * Hyphara osztály konstruktora.
     * @param tecton A tekton amire a gombatest kerül.
     */
    Hyphara(Tecton tecton, Mycologist mycologist) {
        super(tecton, mycologist);
        sporeSpreadsLeft = 15;
    }
    
    /**
     * Létrehoz egy új Hyphara típusú gombatestet.
     * 
     * @return Új Hyphara típusú gombatest.
     */
    @Override
    public MushroomBody createMushroomBody(Tecton tecton, Mycologist mycologist) {
        return new Hyphara(tecton, mycologist);
    }

    /**
     * A Hyphara verzióját valósítja meg a spóraszórásnak.
     * Hyphara spórákat szór.
     */
    @Override
    public void spreadSpores() {
        Skeleton.logFunctionCall(this, "spreadSpores");
        for(Tecton t : tecton.getNeighbors()){
            t.addSpore(new HypharaSpore());
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
     * Egy igaz-hamis érték arról, hogy a Hyphara gombatest
     * szupergombává tud-e fejlődni.
     * @return true, ha szupergombává tud fejlődni, false ha nem.
     */
    @Override
    public boolean canEvolve() {
        Skeleton.logFunctionCall(this, "canEvolve");
        int sporeCount = 0;
        for (Spore s : tecton.sporesAvailable()){
            if(s.getClass() == HypharaSpore.class){ //Spore type?
                sporeCount++;
            }
        }
        Mycelium my = new Mycelium(tecton);
        for(Mycelium mycelium : tecton.getMycelia()){
            if(mycelium.getBodyType() == Hyphara.class){
                my = mycelium;
            }
        }
        Skeleton.logReturn(this, "canEvolve");
        return sporeCount >= 3 && my.getMyceliumConnections().size() >= 3;
    }
}
