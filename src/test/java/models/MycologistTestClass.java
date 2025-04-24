package models;

import java.util.ArrayList;
import java.util.List;

public class MycologistTestClass {
    private String name;
    private List<MushroomBodyTestClass> mushroomBodies;
    private List<MyceliumTestClass> myceliums;

    public MycologistTestClass(String name) {
        this.name = name;
        this.mushroomBodies = new ArrayList<>();
        this.myceliums = new ArrayList<>();
    }

    public void addMushroomBody(MushroomBodyTestClass body) {
        mushroomBodies.add(body);
    }

    public void addMycelium(MyceliumTestClass mycelium) {
        myceliums.add(mycelium);
    }

    public List<MushroomBodyTestClass> getMushroomBodies() {
        return mushroomBodies;
    }

    public List<MyceliumTestClass> getMyceliums() {
        return myceliums;
    }
        
    @Override
    public String toString() {
        return name;
    }
}
