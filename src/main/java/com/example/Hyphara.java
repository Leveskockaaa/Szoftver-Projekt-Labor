package com.example;

public class Hyphara extends MushroomBody{
    Hyphara(Tecton tecton){
        super(tecton);
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
