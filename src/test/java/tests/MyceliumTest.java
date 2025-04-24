package tests;

import models.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.GilledonSpore;
import com.example.MushroomBody;
import com.example.Spore;

class MyceliumTest {
    private MycologistTestClass mycologist1, mycologist2;
    private TectonTestClass tecton1 ,tecton2, tecton3, tecton4;

    private List<MushroomBodyTestClass> mushroomBodies;
    private List<MyceliumTestClass> allMyceliums;

    @BeforeEach
    void setUp() {
        mushroomBodies = new ArrayList<MushroomBodyTestClass>();
        allMyceliums = new ArrayList<MyceliumTestClass>();

        mycologist1 = new MycologistTestClass("Mycologist1");
        mycologist2 = new MycologistTestClass("Mycologist2");
        tecton1 = new TectonTestClass("Tecton1");
        tecton2 = new TectonTestClass("Tecton2");
        tecton3 = new TectonTestClass("Tecton3");
        tecton4 = new TectonTestClass("Tecton4");

        tecton1.addNeighbor(tecton2);
        tecton1.addNeighbor(tecton3);
        tecton2.addNeighbor(tecton4);
        tecton3.addNeighbor(tecton4);
    }

    private MushroomBodyTestClass createMushroomBody(MycologistTestClass mycologist, TectonTestClass tecton) {
        MushroomBodyTestClass body = new GilledonTestClass(tecton, mycologist);
        mycologist.addMushroomBody(body);
        mushroomBodies.add(body);
        return body;
    }
    
    private MyceliumTestClass createMycelium(MycologistTestClass mycologist, TectonTestClass tecton) {
        MyceliumTestClass mycelium = new MyceliumTestClass(mycologist, tecton);
        allMyceliums.add(mycelium);
        return mycelium;
    }
    
    @Test
    void testMyceliumCreation() {
        MyceliumTestClass mycelium1 = createMycelium(mycologist1, tecton1);
        MyceliumTestClass mycelium2 = createMycelium(mycologist2, tecton2);
        
        assertSame(mycelium1.getMycologist(), mycologist1, "mycelium1 should belong to mycologist1");
        assertSame(mycelium2.getMycologist(), mycologist2, "mycelium2 should belong to mycologist2");
    }

    @Test
    void testMushroomBodyOwner() {
        MushroomBodyTestClass body1 = createMushroomBody(mycologist1, tecton1);
        MushroomBodyTestClass body2 = createMushroomBody(mycologist2, tecton2);

        assertSame(body1.getTecton(), tecton1, "body1 should belong to tecton1");
        assertSame(body2.getTecton(), tecton2, "body2 should belong to tecton2");
        assertSame(body1.getMycologist(), mycologist1, "body1 should belong to mycologist1");
        assertSame(body2.getMycologist(), mycologist2, "body2 should belong to mycologist2");
    }

    @Test
    void testAddConnection() {
        MyceliumTestClass mycelium1a = createMycelium(mycologist1, tecton1);
        MyceliumTestClass mycelium1b = createMycelium(mycologist1, tecton2);
        MyceliumTestClass mycelium1c = createMycelium(mycologist1, tecton3);

        mycelium1a.addConnection(mycelium1b);
        mycelium1a.addConnection(mycelium1c);

        assertTrue(mycelium1a.getMyceliumConnections().contains(mycelium1b), "mycelium1a should be connected to mycelium1b");
        assertTrue(mycelium1a.getMyceliumConnections().contains(mycelium1c), "mycelium1b should be connected to mycelium1c");
        assertTrue(mycelium1b.getMyceliumConnections().contains(mycelium1a), "mycelium1b should be connected to mycelium1a");
        assertTrue(mycelium1c.getMyceliumConnections().contains(mycelium1a), "mycelium1c should be connected to mycelium1b");
        assertFalse(mycelium1c.getMyceliumConnections().contains(mycelium1b), "mycelium1a should not be connected to mycelium1c");
        assertFalse(mycelium1b.getMyceliumConnections().contains(mycelium1c), "mycelium1c should not be connected to mycelium1a");
    }

    @Test
    void testAddConnectionExceptions() {
        MyceliumTestClass mycelium1a = createMycelium(mycologist1, tecton1);
        MyceliumTestClass mycelium1b = createMycelium(mycologist1, tecton2);
        MyceliumTestClass mycelium2a = createMycelium(mycologist2, tecton3);
        MyceliumTestClass mycelium2b = createMycelium(mycologist2, tecton4);

        assertDoesNotThrow(() -> mycelium1a.addConnection(mycelium1b), "Should not throw exception when adding connection between same mycologist");
        assertDoesNotThrow(() -> mycelium2a.addConnection(mycelium2b), "Should not throw exception when adding connection between same mycologist");
        
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> mycelium1a.addConnection(mycelium2a), "Should throw exception when adding connection between different mycologists");
        assertSame("Mycelium must belong to the same Mycologist", exception1.getMessage(), "Exception message should match");

        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> mycelium1a.addConnection(mycelium1a), "Should throw exception when adding connection to itself");
        assertSame("Cannot add connection to itself", exception2.getMessage(), "Exception message should match");

        IllegalArgumentException exception3 = assertThrows(IllegalArgumentException.class, () -> mycelium1a.addConnection(null), "Should throw exception when adding null connection");
        assertSame("Mycelium cannot be null", exception3.getMessage(), "Exception message should match");

        IllegalArgumentException exception4 = assertThrows(IllegalArgumentException.class, () -> mycelium1a.addConnection(mycelium1b), "Should throw exception when connection already exists");
        assertSame("Connection already exists", exception4.getMessage(), "Exception message should match");

        MyceliumTestClass mycelium1c = createMycelium(mycologist1, tecton3);
        MyceliumTestClass mycelium2c = createMycelium(mycologist2, tecton2);

        assertDoesNotThrow(() -> mycelium1a.addConnection(mycelium1c), "Should not throw exception when adding connection between same mycologist");
        assertDoesNotThrow(() -> mycelium2b.addConnection(mycelium2c), "Should not throw exception when adding connection between same mycologist");

        IllegalArgumentException exception5 = assertThrows(IllegalArgumentException.class, () -> mycelium1b.addConnection(mycelium1c), "Should throw exception when adding connection between different mycologists");
        assertSame("Mycelium must be on a neighboring Tecton", exception5.getMessage(), "Exception message should match");

        IllegalArgumentException exception6 = assertThrows(IllegalArgumentException.class, () -> mycelium2a.addConnection(mycelium2c), "Should throw exception when adding connection between different mycologists");
        assertSame("Mycelium must be on a neighboring Tecton", exception6.getMessage(), "Exception message should match");
    }

    @Test
    void testSpeedUpGrowthExceptions() {
        MyceliumTestClass mycelium = createMycelium(mycologist1, tecton1);

        assertDoesNotThrow(() -> mycelium.speedUpGrowth(5), "Should not throw exception when time is positive");
        
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> mycelium.speedUpGrowth(-5), "Should throw exception when time is negative");
        assertSame("Time cannot be negative or zero", exception1.getMessage(), "Exception message should match");

        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> mycelium.speedUpGrowth(10), "Should throw exception when time is negative");
        assertSame("Growth speed cannot be negative", exception2.getMessage(), "Exception message should match");

        IllegalArgumentException exception3 = assertThrows(IllegalArgumentException.class, () -> mycelium.speedUpGrowth(20), "Should throw exception when growth speed becomes negative");
        assertSame("Growth speed cannot be negative", exception3.getMessage(), "Exception message should match");

        mycelium.resetGrowthSpeed();

        assertDoesNotThrow(() -> mycelium.speedUpGrowth(5), "Should not throw exception when time is positive after reset");
    }

    @Test
    void testEatInsect() {
        MyceliumTestClass mycelium = createMycelium(mycologist1, tecton1);
        InsectTestClass insect = new InsectTestClass("Insect1", tecton1);

        assertFalse(tecton1.hasMushroomBody(), "Tecton1 should not have a mushroom body before eating insect");

        insect.paralize();
        tecton1.addInsect(insect);
        mycelium.eatInsect();

        assertTrue(tecton1.hasMushroomBody(), "Tecton1 should have a mushroom body after eating insect");
    }

    @Test
    void testEatInsectExceptions() {
        MyceliumTestClass mycelium = createMycelium(mycologist1, tecton1);
        mycelium.setInsectEaten(true);

        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> mycelium.eatInsect(), "Should throw exception when insect already eaten");
        assertSame("Insect already eaten", exception1.getMessage(), "Exception message should match");

        mycelium.setInsectEaten(false);

        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> mycelium.eatInsect(), "Should throw exception when no insect to eat");
        assertSame("No insect to eat", exception2.getMessage(), "Exception message should match");

        InsectTestClass insect = new InsectTestClass("Insect1", tecton1);
        tecton1.addInsect(insect);
        IllegalArgumentException exception3 = assertThrows(IllegalArgumentException.class, () -> mycelium.eatInsect(), "Should throw exception when insect is not paralyzed");
        assertSame("Insect is not paralyzed", exception3.getMessage(), "Exception message should match");

        insect.paralize();

        MushroomBodyTestClass body = createMushroomBody(mycologist1, tecton1);
        tecton1.placeMushroomBody(body);
        IllegalArgumentException exception4 = assertThrows(IllegalArgumentException.class, () -> mycelium.eatInsect(), "Should throw exception when Tecton already has a mushroom body");
        assertSame("Tecton already has a mushroom body", exception4.getMessage(), "Exception message should match");
    }

    @Test
    void testCanDevelop() {
        MushroomBodyTestClass gilledonMushroomBody = new GilledonTestClass(tecton1, mycologist1);
        MyceliumTestClass mycelium = createMycelium(mycologist1, tecton1);
        
        for (int i = 0; i < 5; i++) {
            tecton1.addSpore(new SporeTestClass(gilledonMushroomBody));
        }

        assertFalse(mycelium.canDevelop(), "The ");

        MushroomBodyTestClass capulonMushroomBody = new CapulonTestClass(tecton1, mycologist2);
        tecton1.addSpore(new SporeTestClass(capulonMushroomBody));

        assertFalse(mycelium.canDevelop(), "The mycelium can't develop, there is not enough spores");

        tecton1.addSpore(new SporeTestClass(gilledonMushroomBody));

        assertTrue(mycelium.canDevelop(), "The mycelium can develop, there is enough spores");
    }
}