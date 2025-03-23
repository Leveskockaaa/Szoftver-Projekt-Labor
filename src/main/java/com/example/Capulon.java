package com.example;

/**
 * A Capulon gombafaj gombatestjeinek kezeléséért felelős osztály.
 */
public class Capulon extends MushroomBody{
    /**
     * Capulon osztály konstruktora.
     * @param tecton A tekton amire a gombatest kerül.
     */
    Capulon(Tecton tecton){
        super(tecton);
        sporeSpreadsLeft = 15;
    }

    /**
     * A Capulon verzióját valósítja meg a spóraszórásnak.
     * Capulon spórákat szór.
     */
    @Override
    public void spreadSpores() {
        Skeleton.logFunctionCall(this, "spreadSpores");
        for(Tecton t : tecton.getNeighbors()){
            t.addSpore(new CapulonSpore());
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
     * Egy igaz-hamis érték arról, hogy a Capulon gombatest
     * szupergombává tud-e fejlődni.
     * @return true, ha szupergombává tud fejlődni, false ha nem.
     */
    @Override
    public boolean canEvolve() {
        Skeleton.logFunctionCall(this, "canEvolve");
        int sporeCount = 0;
        for (Spore s : tecton.sporesAvailable()){
            if(s.getClass() == CapulonSpore.class){ //Spore type?
                sporeCount++;
            }
        }
        Mycelium my = new Mycelium(tecton);
        for(Mycelium mycelium : tecton.getMycelia()){
            if(mycelium.getBodyType() == Capulon.class){
                my = mycelium;
            }
        }
        Skeleton.logReturn(this, "canEvolve");
        return sporeCount >= 3 && my.getMyceliumConnections().size() >= 3;
    }
}
