package com.example;

public class Hyphara extends MushroomBody{
    Hyphara(Tecton tecton){
        super(tecton);
        dead = false;
        superBody = false;
        canSpreadSpores = true;
        sporeSpreadsLeft = 15;
    }

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
            tecton.removeMushroomBody(); //Corrected "Spread Spores - Body withers" sequence diagram
        }
        Skeleton.logReturn(this, "spreadSpores");
    }

    @Override
    public boolean canEvolve() {
        Skeleton.logFunctionCall(this, "canEvolve");
        int sporeCount = 0;
        for (Spore s : tecton.sporesAvailable()){
            if(s.getClass() == HypharaSpore.class){ //Spore type?
                sporeCount++;
            }
        }
        Skeleton.logReturn(this, "canEvolve");
        Mycelium my = new Mycelium(tecton);
        for(Mycelium mycelium : tecton.getMyceliums()){
            if(mycelium.getMycologist().getMushroomBodies().getFirst().getClass() == Hyphara.class){
                my = mycelium;
            }
        }
        return sporeCount >= 3 && my.getMyceliumConnections().size() >= 3;
    }
}
