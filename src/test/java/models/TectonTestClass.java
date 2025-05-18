package models;

import java.util.ArrayList;
import java.util.List;

public class TectonTestClass {
    private String name;
    private List<SporeTestClass> spores;
    private List<MyceliumTestClass> mycelia;
    private MushroomBodyTestClass mushroomBody;
    private List<TectonTestClass> neighbors;
    private InsectTestClass insect;

    public TectonTestClass(String name) {
        this.name = name;
        this.spores = new ArrayList<>();
        this.mushroomBody = null;
        this.mycelia = new ArrayList<>();
        this.neighbors = new ArrayList<>();
        this.insect = null;
    }

    public void addNeighbor(TectonTestClass neighbor) {
        neighbors.add(neighbor);
        neighbor.neighbors.add(this);
    }

    public List<TectonTestClass> getNeighbors() {
        return neighbors;
    }

    public List<MyceliumTestClass> getMycelia() {
        return mycelia;
    }

    public List<SporeTestClass> sporesAvailable() {
        return spores;
    }

    public void addSpore(SporeTestClass spore) {
        spores.add(spore);
    }

    public InsectTestClass getInsect() {
        return insect;
    }

    public void addInsect(InsectTestClass insect) {
        this.insect = insect;
    }

    public boolean hasInsect() {
        return insect != null;
    }

    public void removeInsect() {
        this.insect = null;
    }

    public boolean hasMushroomBody() {
        return mushroomBody != null;
    }

    public void placeMushroomBody(MushroomBodyTestClass mushroomBody) {
        this.mushroomBody = mushroomBody;
    }

    public boolean canAddMycelium() {
        return true;
    }

    public void addMycelium(MyceliumTestClass mycelium) {
        mycelia.add(mycelium);
    }

    public void removeMycelium(MyceliumTestClass mycelium) {
        mycelia.remove(mycelium);
    }

    @Override
    public String toString() {
        return name;
    }
}
