package models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class MyceliumTestClass {
    private boolean canGrow;
    private boolean insectEaten;
    private float growthSpeed;
    private TectonTestClass tecton;
    private MycologistTestClass mycologist;
    private List<MyceliumTestClass> myceliumConnections;

    public MyceliumTestClass(MycologistTestClass mycologist, TectonTestClass tecton) {
        this.canGrow = true;
        this.insectEaten = false;
        this.growthSpeed = 10;
        this.tecton = tecton;
        this.mycologist = mycologist;
        this.myceliumConnections = new ArrayList<>();
    }

    public TectonTestClass getTecton() {
        return tecton;
    }

    public MycologistTestClass getMycologist() {
        return mycologist;
    }

    public List<MyceliumTestClass> getMyceliumConnections() {
        return myceliumConnections;
    }

    public Class<? extends MushroomBodyTestClass> getBodyType() {
        return mycologist.getMushroomBodies().get(0).getClass();
    }

    public boolean canDevelop() {
        int sporeCount = tecton.sporesAvailable().size();
        return sporeCount >= 6 && !tecton.hasMushroomBody();
    }

    public void addMyceliumConnection(MyceliumTestClass mycelium) {
        myceliumConnections.add(mycelium);
    }

    public void developMushroomBody() {
        if (canDevelop()) {
            MushroomBodyTestClass newMushroomBody = new MushroomBodyTestClass(tecton, mycologist);
            tecton.placeMushroomBody(newMushroomBody);
            mycologist.addMushroomBody(newMushroomBody);
        }
    }

    public void enableGrowth() {
        canGrow = true;
    }

    public MyceliumTestClass createNewBranch(TectonTestClass tecton) {
        if (canGrow) {
            if (tecton.canAddMycelium()) {
                MyceliumTestClass newMycelium = new MyceliumTestClass(mycologist, tecton);

                tecton.addMycelium(newMycelium);
                myceliumConnections.add(newMycelium);
                newMycelium.myceliumConnections.add(this);

                return newMycelium;
            } else {
                for (MyceliumTestClass mycelium : tecton.getMycelia()) {
                    if (mycelium.getBodyType() == getBodyType()) {
                        myceliumConnections.add(mycelium);
                        mycelium.myceliumConnections.add(this);
                        break;
                    }
                }
            }
        }

        return null;
    }

    public void addConnection(MyceliumTestClass mycelium) {
        try {
            if (mycelium == null) {
                throw new IllegalArgumentException("Mycelium cannot be null");
            }
            if (mycelium.getMycologist() != mycologist) {
                throw new IllegalArgumentException("Mycelium must belong to the same Mycologist");
            }
            if (mycelium == this) {
                throw new IllegalArgumentException("Cannot add connection to itself");
            }
            if (!mycelium.getTecton().getNeighbors().contains(tecton)) {
                throw new IllegalArgumentException("Mycelium must be on a neighboring Tecton");
            }
            if (myceliumConnections.contains(mycelium)) {
                throw new IllegalArgumentException("Connection already exists");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            throw new IllegalArgumentException(exception.getMessage());
        }

        myceliumConnections.add(mycelium);
        mycelium.myceliumConnections.add(this);
    }

    public void removeConnection(MyceliumTestClass mycelium) {
        try {
            if (mycelium == null) {
                throw new IllegalArgumentException("Mycelium cannot be null");
            }
            if (mycelium.getMycologist() != mycologist) {
                throw new IllegalArgumentException("Mycelium must belong to the same Mycologist");
            }
            if (mycelium == this) {
                throw new IllegalArgumentException("Cannot remove connection to itself");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            throw new IllegalArgumentException(exception.getMessage());
        }

        myceliumConnections.remove(mycelium);
        mycelium.myceliumConnections.remove(this);
    }

    public boolean isConnectedToMushroomBody() {
        HashSet<MyceliumTestClass> visitedMycelia = new HashSet<>();
        LinkedList<MyceliumTestClass> queue = new LinkedList<>();
        queue.add(this);
        visitedMycelia.add(this);

        while (!queue.isEmpty()) {
            MyceliumTestClass current = queue.poll();

            TectonTestClass currentTecton = current.getTecton();
            MycologistTestClass thisMycologist = this.getMycologist();
            for (MushroomBodyTestClass body : thisMycologist.getMushroomBodies()) {
                if (body.getTecton() == currentTecton) {
                    return true;
                }
            }

            for (MyceliumTestClass neighbor : current.getMyceliumConnections()) {
                if (!visitedMycelia.contains(neighbor) && neighbor.getMycologist() == mycologist) {
                    visitedMycelia.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        return false;
    }

    public void speedUpGrowth(float time) {
        try {
            if (time <= 0) {
                throw new IllegalArgumentException("Time cannot be negative or zero");
            }
            if (growthSpeed - time < 0) {
                throw new IllegalArgumentException("Growth speed cannot be negative");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            throw new IllegalArgumentException(exception.getMessage());
        }

        growthSpeed -= time;
    }

    public void resetGrowthSpeed() {
        growthSpeed = 10;
    }

    public void setInsectEaten(boolean insectEaten) {
        this.insectEaten = insectEaten;
    }

    public void eatInsect() {
        try {
            if (insectEaten) {
                throw new IllegalArgumentException("Insect already eaten");
            }
            if (!tecton.hasInsect()) {
                throw new IllegalArgumentException("No insect to eat");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            throw new IllegalArgumentException(exception.getMessage());
        }

        InsectTestClass insect = tecton.getInsect();

        try {
            if (!insect.isParalized()) {
                throw new IllegalArgumentException("Insect is not paralyzed");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            throw new IllegalArgumentException(exception.getMessage());
        }

        tecton.removeInsect();

        try {
            if (tecton.hasMushroomBody()) {
                throw new IllegalArgumentException("Tecton already has a mushroom body");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            throw new IllegalArgumentException(exception.getMessage());
        }

        MushroomBodyTestClass mushroomBody = MushroomBodyTestClass.createRandomMushroomBody(tecton, mycologist);
        tecton.placeMushroomBody(mushroomBody);
        insectEaten = true;
    }
}
