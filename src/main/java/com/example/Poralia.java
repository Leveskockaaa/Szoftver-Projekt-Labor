package com.example;

/**
 * A Poralia gombafaj gombatestjeinek kezeléséért felelős osztály.
 */
public class Poralia extends MushroomBody{
    /**
     * Poralia osztály konstruktora.
     * @param tecton A tekton amire a gombatest kerül.
     */
    Poralia(Tecton tecton, Mycologist mycologist) {
        super(tecton, mycologist);
        sporeSpreadsLeft = 15;
    }

    /**
     * A Poralia verzióját valósítja meg a spóraszórásnak.
     * Poralia spórákat szór.
     */
    @Override
    public void spreadSpores() {
        Skeleton.logFunctionCall(this, "spreadSpores");
        for(Tecton t : tecton.getNeighbors()){
            t.addSpore(new PoraliaSpore());
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
     * Egy igaz-hamis érték arról, hogy a Poralia gombatest
     * szupergombává tud-e fejlődni.
     * @return true, ha szupergombává tud fejlődni, false ha nem.
     */
    @Override
    public boolean canEvolve() {
        Skeleton.logFunctionCall(this, "canEvolve");
        int sporeCount = 0;
        for (Spore s : tecton.sporesAvailable()){
            if(s.getClass() == PoraliaSpore.class){ //Spore type?
                sporeCount++;
            }
        }
        Mycelium my = new Mycelium(tecton);
        for(Mycelium mycelium : tecton.getMycelia()){
            if(mycelium.getBodyType() == Poralia.class){
                my = mycelium;
            }
        }
        Skeleton.logReturn(this, "canEvolve");
        return sporeCount >= 3 && my.getMyceliumConnections().size() >= 3;
    }

    /*
    =============================================================================================
    Teszteléshez kiíró metódusok
    =============================================================================================
     */
    @Override
    public String printType() {
        return this.getClass().getSimpleName();
    }
}
