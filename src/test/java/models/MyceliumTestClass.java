package models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyceliumTestClass {

    private boolean canGrow;
    private boolean insectEaten;
    private float growthSpeed;
    private final TectonTestClass tecton;
    private final MycologistTestClass mycologist;
    private List<MyceliumTestClass> myceliumConnections;
    private Timer timer;

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

    public boolean canDevelop() {
        int sporeCount = 0;
        for (SporeTestClass spore : tecton.sporesAvailable()) {
            if (spore.getMushroomBody().getMycologist() == mycologist) {
                sporeCount++;
            }
        }

        return sporeCount >= 6 && !tecton.hasMushroomBody();
    }

    public void addMyceliumConnection(MyceliumTestClass mycelium) {
        myceliumConnections.add(mycelium);
    }

    public void developMushroomBody() {
        try {
            if (!canDevelop()) {
                throw new IllegalArgumentException("Cannot develop mushroom body");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            throw new IllegalArgumentException(exception.getMessage());
        }

        try {
            if (tecton.hasMushroomBody()) {
                throw new IllegalArgumentException("Tecton already has a mushroom body");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            throw new IllegalArgumentException(exception.getMessage());
        }

        MushroomBodyTestClass mushroomBody = mycologist.getMushroomBodies().getFirst().createMushroomBody(tecton, mycologist);
        tecton.placeMushroomBody(mushroomBody);
    }

    public void enableGrowth() {
        canGrow = true;
    }

    public MyceliumTestClass createNewBranch(TectonTestClass tecton) {
        try {
            if (!canGrow) {
                throw new IllegalArgumentException("Cannot grow");
            }
            for (MyceliumTestClass mycelium : myceliumConnections) {
                if (mycelium.getTecton() == tecton) {
                    throw new IllegalArgumentException("Already connected to this tecton");
                }
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            throw new IllegalArgumentException(exception.getMessage());
        }

        if (tecton.canAddMycelium()) {
            MyceliumTestClass newMycelium = new MyceliumTestClass(mycologist, tecton);

            try {
                tecton.addMycelium(newMycelium);
                this.addConnection(newMycelium);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
                throw new IllegalArgumentException(exception.getMessage());
            }

            return newMycelium;
        } else {
            for (MyceliumTestClass mycelium : tecton.getMycelia()) {
                if (mycelium.getMycologist() == mycologist) {
                    try {
                        this.addConnection(mycelium);
                    } catch (IllegalArgumentException exception) {
                        System.out.println(exception.getMessage());
                        throw new IllegalArgumentException(exception.getMessage());
                    }
                    return null;
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

        MushroomBodyTestClass mushroomBody = mycologist.getMushroomBodies().getFirst().createMushroomBody(tecton, mycologist);
        tecton.placeMushroomBody(mushroomBody);
        insectEaten = true;
    }

        public void wither() {
        if (isWithering()) return;

        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                tecton.removeMycelium(MyceliumTestClass.this);
                timer = null;
            }
        };
        timer.schedule(task, 1000);
    }

    public boolean isWithering() {
        return timer != null;
    }

    public void cancelWither() {
        if (timer == null) return;
        timer.cancel();
        timer.purge();
        timer = null;
    }
}
