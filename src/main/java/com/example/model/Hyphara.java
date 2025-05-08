package com.example.model;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * A Hyphara gombafaj gombatestjeinek kezeléséért felelős osztály.
 */
public class Hyphara extends MushroomBody {
    /**
     * Hyphara osztály konstruktora.
     * @param tecton A tekton amire a gombatest kerül.
     */
    public Hyphara(Tecton tecton, Mycologist mycologist, String name) {
        super(tecton, mycologist, name);
        sporeSpreadsLeft = 15;
    }
    
    /**
     * Létrehoz egy új Hyphara típusú gombatestet.
     * 
     * @return Új Hyphara típusú gombatest.
     */
    @Override
    public MushroomBody createMushroomBody(Tecton tecton, Mycologist mycologist, String name) {
        return new Hyphara(tecton, mycologist, name);
    }

    /**
     * A Hyphara verzióját valósítja meg a spóraszórásnak.
     * Hyphara spórákat szór.
     */
    @Override
    public void spreadSpores() {
        if(canSpreadSpores && sporeSpreadsLeft > 0) {
            if(superBody){
                superSpreadSpores();
            }
            else{
                for(Tecton t : tecton.getNeighbors()){
                    t.addSpore(new HypharaSpore(this));
                }
            }

            sporeSpreadsLeft--;
            if(sporeSpreadsLeft == 0){
                dead = true;
                mycologist.collect(this);
                tecton.removeMushroomBody();
            }
        }
    }

    /**
     * Privát függvény amibe ki van szervezve a szupergomba spóra
     * szórásának a logikája. BFS segítségével megy a tektonokon
     * addig amíg a "gráfban" a tektonok mélységi száma el nem éri
     * a kettőt, az ilyen tektonokra már nem lépünk oda.
     */
    private void superSpreadSpores(){
        HashMap<Tecton, Integer> visited = new HashMap<>();
        LinkedList<Tecton> queue = new LinkedList<>();
        queue.add(tecton);
        visited.put(tecton, 0);

        while (!queue.isEmpty()) {
            Tecton current = queue.poll();
            int depth = visited.get(current);
            if(depth != 0) current.addSpore(new HypharaSpore(this));

            for (Tecton neighbor : current.getNeighbors()) {
                if (!visited.containsKey(current) && depth < 2) {
                    visited.put(current, depth + 1);
                    queue.add(neighbor);
                }
            }
        }
    }

    /**
     * Egy igaz-hamis érték arról, hogy a Hyphara gombatest
     * szupergombává tud-e fejlődni.
     * @return true, ha szupergombává tud fejlődni, false ha nem.
     */
    @Override
    public boolean canEvolve() {
        int sporeCount = 0;
        for (Spore s : tecton.sporesAvailable()){
            if(s.getClass() == HypharaSpore.class){ //Spore type?
                sporeCount++;
            }
        }

        Mycelium my = null;
        for(Mycelium mycelium : tecton.getMycelia()){
            if(mycelium.getMycologist() == mycologist){
                my = mycelium;
                break;
            }
        }
        return sporeCount >= 3 && my!= null && my.getMyceliumConnections().size() >= 3;
    }

    /**
     * Megpróbálja szupergombává fejleszteni a gombatestet. Ha a
     * körülmények adottak (a canEvolve() függvény true-val tér vissza) akkor átállítja a
     * superBody attribútumot true-ra, és meghívja a tecton takeSpore függvényét, amellyel
     * eltávolít 3 spórát a saját fajának spórái közül a tektonjáról.
     */
    @Override
    public void evolveSuper() {
        if(canEvolve()){
            tecton.takeSpore(mycologist, 3);
            superBody = true;
        }
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
