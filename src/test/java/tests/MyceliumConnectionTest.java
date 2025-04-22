package tests;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.MushroomBodyTestClass;
import models.MyceliumTestClass;
import models.MycologistTestClass;
import models.TectonTestClass;

class MyceliumConnectionTest {
    private MycologistTestClass mycologist1, mycologist2, mycologist3;
    private TectonTestClass[] tectons;
    private List<MushroomBodyTestClass> mushroomBodies;
    private List<MyceliumTestClass> allMyceliums;

    @BeforeEach
    void setUp() {
        mycologist1 = new MycologistTestClass("Mycologist1");
        mycologist2 = new MycologistTestClass("Mycologist2");
        mycologist3 = new MycologistTestClass("Mycologist3");
        
        tectons = new TectonTestClass[10];
        for (int i = 0; i < 10; i++) {
            tectons[i] = new TectonTestClass("Tecton" + (i + 1));
        }

        for (int i = 0; i < 10; i++) {
            for (int j = i + 1; j < 10; j++) {
                tectons[i].addNeighbor(tectons[j]);
            }
        }
        
        mushroomBodies = new ArrayList<MushroomBodyTestClass>();
        allMyceliums = new ArrayList<MyceliumTestClass>();
    }

    private MushroomBodyTestClass createMushroomBody(MycologistTestClass mycologist, TectonTestClass tecton) {
        MushroomBodyTestClass body = new MushroomBodyTestClass(tecton, mycologist);
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
    void testMyceliumWithMushroomBodyAndWithout() {
        createMushroomBody(mycologist1, tectons[0]);
        createMushroomBody(mycologist2, tectons[5]);
        createMushroomBody(mycologist3, tectons[9]);

        MyceliumTestClass mycelium1a = createMycelium(mycologist1, tectons[1]);
        MyceliumTestClass mycelium1b = createMycelium(mycologist1, tectons[2]);
        mycelium1a.addConnection(mycelium1b);

        MyceliumTestClass mycelium2a = createMycelium(mycologist2, tectons[5]);
        MyceliumTestClass mycelium2b = createMycelium(mycologist2, tectons[4]);
        mycelium2a.addConnection(mycelium2b);

        MyceliumTestClass mycelium3a = createMycelium(mycologist3, tectons[9]);
        MyceliumTestClass mycelium3b = createMycelium(mycologist3, tectons[0]);
        MyceliumTestClass mycelium3c = createMycelium(mycologist3, tectons[6]);
        MyceliumTestClass mycelium3d = createMycelium(mycologist3, tectons[3]);
        mycelium3a.addConnection(mycelium3b);
        mycelium3a.addConnection(mycelium3c);
        mycelium3c.addConnection(mycelium3d);
        
        assertFalse(mycelium1a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2b.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium3d.isConnectedToMushroomBody(), "Not implemented");
    }
    
    @Test
    void testAddMyceliumConnectionInRuntime() {
        createMushroomBody(mycologist1, tectons[0]);
        createMushroomBody(mycologist2, tectons[5]);

        MyceliumTestClass mycelium1a = createMycelium(mycologist1, tectons[1]);
        MyceliumTestClass mycelium1b = createMycelium(mycologist1, tectons[2]);
        MyceliumTestClass mycelium1c = createMycelium(mycologist1, tectons[3]);
        mycelium1a.addConnection(mycelium1b);
        mycelium1b.addConnection(mycelium1c);
        
        MyceliumTestClass mycelium2a = createMycelium(mycologist2, tectons[5]);
        MyceliumTestClass mycelium2b = createMycelium(mycologist2, tectons[6]);
        MyceliumTestClass mycelium2c = createMycelium(mycologist2, tectons[0]);
        mycelium2a.addConnection(mycelium2b);
        
        assertFalse(mycelium1a.isConnectedToMushroomBody(), "Not implemented");
        assertFalse(mycelium2c.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2a.isConnectedToMushroomBody(), "Not implemented");
        
        mycelium2b.addConnection(mycelium2c);

        MyceliumTestClass mycelium1d = createMycelium(mycologist1, tectons[0]);
        mycelium1a.addConnection(mycelium1d);
        
        assertTrue(mycelium2c.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1c.isConnectedToMushroomBody(), "Not implemented");
    }
    
    @Test
    void testConnectedCirclesAndNotConnected() {
        createMushroomBody(mycologist1, tectons[0]);
        createMushroomBody(mycologist2, tectons[5]);
        createMushroomBody(mycologist3, tectons[9]);
        
        MyceliumTestClass mycelium1a = createMycelium(mycologist1, tectons[0]);
        MyceliumTestClass mycelium1b = createMycelium(mycologist1, tectons[1]);
        MyceliumTestClass mycelium1c = createMycelium(mycologist1, tectons[2]);
        MyceliumTestClass mycelium1d = createMycelium(mycologist1, tectons[3]);
        mycelium1a.addConnection(mycelium1b);
        mycelium1b.addConnection(mycelium1c);
        mycelium1c.addConnection(mycelium1d);
        mycelium1d.addConnection(mycelium1a);
        
        MyceliumTestClass mycelium2a = createMycelium(mycologist2, tectons[5]);
        MyceliumTestClass mycelium2b = createMycelium(mycologist2, tectons[1]);
        MyceliumTestClass mycelium2c = createMycelium(mycologist2, tectons[0]);
        MyceliumTestClass mycelium2d = createMycelium(mycologist2, tectons[4]);
        mycelium2a.addConnection(mycelium2b);
        mycelium2b.addConnection(mycelium2c);
        mycelium2c.addConnection(mycelium2d);
        mycelium2d.addConnection(mycelium2a);
        
        MyceliumTestClass mycelium3a = createMycelium(mycologist3, tectons[3]);
        MyceliumTestClass mycelium3b = createMycelium(mycologist3, tectons[2]);
        MyceliumTestClass mycelium3c = createMycelium(mycologist3, tectons[6]);
        mycelium3a.addConnection(mycelium3b);
        mycelium3b.addConnection(mycelium3c);
        mycelium3c.addConnection(mycelium3a);
    
        assertTrue(mycelium1a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1b.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1c.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1d.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2b.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2c.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2d.isConnectedToMushroomBody(), "Not implemented");
        assertFalse(mycelium3a.isConnectedToMushroomBody(), "Not implemented");
        assertFalse(mycelium3b.isConnectedToMushroomBody(), "Not implemented");
        assertFalse(mycelium3c.isConnectedToMushroomBody(), "Not implemented");

        MyceliumTestClass mycelium3d = createMycelium(mycologist3, tectons[9]);
        mycelium3d.addConnection(mycelium3c);

        assertTrue(mycelium1a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1b.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1c.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1d.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2b.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2c.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2d.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium3a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium3b.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium3c.isConnectedToMushroomBody(), "Not implemented");
    }
    
    @Test
    void testChewConnectionInRuntime() {
        createMushroomBody(mycologist1, tectons[0]);

        MyceliumTestClass mycelium1a = createMycelium(mycologist1, tectons[0]);
        MyceliumTestClass mycelium1b = createMycelium(mycologist1, tectons[1]);
        MyceliumTestClass mycelium1c = createMycelium(mycologist1, tectons[2]);
        MyceliumTestClass mycelium1d = createMycelium(mycologist1, tectons[3]);
        MyceliumTestClass mycelium1e = createMycelium(mycologist1, tectons[4]);
        mycelium1a.addConnection(mycelium1b);
        mycelium1b.addConnection(mycelium1c);
        mycelium1c.addConnection(mycelium1d);
        mycelium1d.addConnection(mycelium1e);

        assertTrue(mycelium1a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1b.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1c.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1d.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1e.isConnectedToMushroomBody(), "Not implemented");
        
        mycelium1c.removeConnection(mycelium1b);

        assertTrue(mycelium1a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1b.isConnectedToMushroomBody(), "Not implemented");
        assertFalse(mycelium1c.isConnectedToMushroomBody(), "Not implemented");
        assertFalse(mycelium1d.isConnectedToMushroomBody(), "Not implemented");
        assertFalse(mycelium1e.isConnectedToMushroomBody(), "Not implemented");

        mycelium1e.addConnection(mycelium1a);

        assertTrue(mycelium1a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1b.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1c.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1d.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1e.isConnectedToMushroomBody(), "Not implemented");
    }
    
    @Test
    void testRealGameplay() {
        createMushroomBody(mycologist1, tectons[0]);
        createMushroomBody(mycologist2, tectons[4]);
        createMushroomBody(mycologist3, tectons[8]);

        // First

        MyceliumTestClass mycelium1a = createMycelium(mycologist1, tectons[0]);
        MyceliumTestClass mycelium1b = createMycelium(mycologist1, tectons[3]);
        mycelium1a.addConnection(mycelium1b);

        MyceliumTestClass mycelium2a = createMycelium(mycologist2, tectons[4]);
        MyceliumTestClass mycelium2b = createMycelium(mycologist2, tectons[5]);
        mycelium2a.addConnection(mycelium2b);

        MyceliumTestClass mycelium3a = createMycelium(mycologist3, tectons[8]);
        MyceliumTestClass mycelium3b = createMycelium(mycologist3, tectons[7]);
        mycelium3a.addConnection(mycelium3b);

        assertTrue(mycelium1a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1b.isConnectedToMushroomBody(), "Not implemented");

        assertTrue(mycelium2a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2b.isConnectedToMushroomBody(), "Not implemented");

        assertTrue(mycelium3a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium3b.isConnectedToMushroomBody(), "Not implemented");

        // Second

        MyceliumTestClass mycelium1c = createMycelium(mycologist1, tectons[4]);
        mycelium1b.addConnection(mycelium1c);

        MyceliumTestClass mycelium2c = createMycelium(mycologist2, tectons[8]);
        mycelium2b.addConnection(mycelium2c);

        MyceliumTestClass mycelium3c = createMycelium(mycologist3, tectons[6]);
        mycelium3b.addConnection(mycelium3c);

        assertTrue(mycelium1a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1b.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1c.isConnectedToMushroomBody(), "Not implemented");

        assertTrue(mycelium2a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2b.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2c.isConnectedToMushroomBody(), "Not implemented");

        assertTrue(mycelium3a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium3b.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium3c.isConnectedToMushroomBody(), "Not implemented");

        // Third

        MyceliumTestClass mycelium1d = createMycelium(mycologist1, tectons[1]);
        mycelium1a.addConnection(mycelium1d);

        MyceliumTestClass mycelium2d = createMycelium(mycologist2, tectons[2]);
        mycelium2b.addConnection(mycelium2d);

        MyceliumTestClass mycelium3d = createMycelium(mycologist3, tectons[3]);
        mycelium3c.addConnection(mycelium3d);

        assertTrue(mycelium1a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1b.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1c.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1d.isConnectedToMushroomBody(), "Not implemented");

        assertTrue(mycelium2a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2b.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2c.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2d.isConnectedToMushroomBody(), "Not implemented");

        assertTrue(mycelium3a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium3b.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium3c.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium3d.isConnectedToMushroomBody(), "Not implemented");

        // Fourth

        mycelium1c.addConnection(mycelium1d);
        mycelium2c.addConnection(mycelium2d);
        mycelium3b.addConnection(mycelium3d);

        assertTrue(mycelium1a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1b.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1c.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1d.isConnectedToMushroomBody(), "Not implemented");

        assertTrue(mycelium2a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2b.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2c.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2d.isConnectedToMushroomBody(), "Not implemented");

        assertTrue(mycelium3a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium3b.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium3c.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium3d.isConnectedToMushroomBody(), "Not implemented");

        // Fifth

        mycelium1a.removeConnection(mycelium1b);
        mycelium1c.removeConnection(mycelium1d);

        mycelium2b.removeConnection(mycelium2c);

        mycelium3a.removeConnection(mycelium3b);
        mycelium3b.removeConnection(mycelium3d);

        assertTrue(mycelium1a.isConnectedToMushroomBody(), "Not implemented");
        assertFalse(mycelium1b.isConnectedToMushroomBody(), "Not implemented");
        assertFalse(mycelium1c.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1d.isConnectedToMushroomBody(), "Not implemented");

        assertTrue(mycelium2a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2b.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2c.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2d.isConnectedToMushroomBody(), "Not implemented");

        assertTrue(mycelium3a.isConnectedToMushroomBody(), "Not implemented");
        assertFalse(mycelium3b.isConnectedToMushroomBody(), "Not implemented");
        assertFalse(mycelium3c.isConnectedToMushroomBody(), "Not implemented");
        assertFalse(mycelium3d.isConnectedToMushroomBody(), "Not implemented");

        // Sixth
        mycelium3a.addConnection(mycelium3b);
        createMushroomBody(mycologist1, tectons[3]);
        createMushroomBody(mycologist2, tectons[2]);
        createMushroomBody(mycologist3, tectons[7]);

        assertTrue(mycelium1a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1b.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1c.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1d.isConnectedToMushroomBody(), "Not implemented");

        assertTrue(mycelium2a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2b.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2c.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2d.isConnectedToMushroomBody(), "Not implemented");

        assertTrue(mycelium3a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium3b.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium3c.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium3d.isConnectedToMushroomBody(), "Not implemented");
    }
}