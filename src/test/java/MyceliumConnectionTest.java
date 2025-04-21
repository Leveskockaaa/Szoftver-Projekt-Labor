import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MyceliumConnectionTest {
    static class Tecton {
        private String name;
        public Tecton(String name) {
            this.name = name;
        }
        @Override
        public String toString() {
            return "Tecton{" + name + '}';
        }
    }

    static class MushroomBody {
        private Tecton tecton;
        public MushroomBody(Tecton tecton) {
            this.tecton = tecton;
        }
        public Tecton getTecton() {
            return tecton;
        }
    }

    static class Mycologist {
        private String name;
        private List<MushroomBody> mushroomBodies;
        private List<Mycelium> myceliums;

        public Mycologist(String name) {
            this.name = name;
            this.mushroomBodies = new ArrayList<>();
            this.myceliums = new ArrayList<>();
        }

        public void addMushroomBody(MushroomBody body) {
            mushroomBodies.add(body);
        }

        public void addMycelium(Mycelium mycelium) {
            myceliums.add(mycelium);
        }

        public List<MushroomBody> getMushroomBodies() {
            return mushroomBodies;
        }

        public List<Mycelium> getMyceliums() {
            return myceliums;
        }
        
        @Override
        public String toString() {
            return "Mycologist{" + name + '}';
        }
    }

    static class Mycelium {
        private Tecton tecton;
        private List<Mycelium> myceliumConnections;
        private Mycologist mycologist;

        public Mycelium(Mycologist mycologist, Tecton tecton) {
            this.mycologist = mycologist;
            this.tecton = tecton;
            this.myceliumConnections = new ArrayList<>();
            if (mycologist != null) {
                mycologist.addMycelium(this);
            }
        }

        public void addConnection(Mycelium mycelium) {
            if (!myceliumConnections.contains(mycelium)) {
                myceliumConnections.add(mycelium);
                mycelium.myceliumConnections.add(this);
            }
        }

        public void removeConnection(Mycelium mycelium) {
            myceliumConnections.remove(mycelium);
            mycelium.myceliumConnections.remove(this);
        }

        public Tecton getTecton() {
            return tecton;
        }

        public List<Mycelium> getMyceliumConnections() {
            return myceliumConnections;
        }

        public Mycologist getMycologist() {
            return mycologist;
        }

        public boolean isConnectedToMushroomBody() {
            HashSet<Mycelium> visitedMycelia = new HashSet<Mycelium>();
            LinkedList<Mycelium> queue = new LinkedList<Mycelium>();
            queue.add(this);
            visitedMycelia.add(this);
            
            while (!queue.isEmpty()) {
                Mycelium current = queue.poll();
                
                Tecton currentTecton = current.getTecton();
                Mycologist thisMycologist = this.getMycologist();
                for (MushroomBody body : thisMycologist.getMushroomBodies()) {
                    if (body.getTecton() == currentTecton) {
                        return true;
                    }
                }
                
                for (Mycelium neighbor : current.getMyceliumConnections()) {
                    if (!visitedMycelia.contains(neighbor) && neighbor.getMycologist() == mycologist) {
                        visitedMycelia.add(neighbor);
                        queue.add(neighbor);
                    }
                }
            }

            return false;
        }
    }

    private Mycologist mycologist1, mycologist2, mycologist3;
    private Tecton[] tectons;
    private List<MushroomBody> mushroomBodies;
    private List<Mycelium> allMyceliums;

    @BeforeEach
    void setUp() {
        mycologist1 = new Mycologist("Mycologist1");
        mycologist2 = new Mycologist("Mycologist2");
        mycologist3 = new Mycologist("Mycologist3");
        
        tectons = new Tecton[10];
        for (int i = 0; i < 10; i++) {
            tectons[i] = new Tecton("Tecton" + (i + 1));
        }
        
        mushroomBodies = new ArrayList<MushroomBody>();
        allMyceliums = new ArrayList<Mycelium>();
    }

    private MushroomBody createMushroomBody(Mycologist mycologist, Tecton tecton) {
        MushroomBody body = new MushroomBody(tecton);
        mycologist.addMushroomBody(body);
        mushroomBodies.add(body);
        return body;
    }
    
    private Mycelium createMycelium(Mycologist mycologist, Tecton tecton) {
        Mycelium mycelium = new Mycelium(mycologist, tecton);
        allMyceliums.add(mycelium);
        return mycelium;
    }

    @Test
    void testMyceliumWithMushroomBodyAndWithout() {
        createMushroomBody(mycologist1, tectons[0]);
        createMushroomBody(mycologist2, tectons[5]);
        createMushroomBody(mycologist3, tectons[9]);

        Mycelium mycelium1a = createMycelium(mycologist1, tectons[1]);
        Mycelium mycelium1b = createMycelium(mycologist1, tectons[2]);
        mycelium1a.addConnection(mycelium1b);

        Mycelium mycelium2a = createMycelium(mycologist2, tectons[5]);
        Mycelium mycelium2b = createMycelium(mycologist2, tectons[4]);
        mycelium2a.addConnection(mycelium2b);

        Mycelium mycelium3a = createMycelium(mycologist3, tectons[9]);
        Mycelium mycelium3b = createMycelium(mycologist3, tectons[0]);
        Mycelium mycelium3c = createMycelium(mycologist3, tectons[6]);
        Mycelium mycelium3d = createMycelium(mycologist3, tectons[3]);
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

        Mycelium mycelium1a = createMycelium(mycologist1, tectons[1]);
        Mycelium mycelium1b = createMycelium(mycologist1, tectons[2]);
        Mycelium mycelium1c = createMycelium(mycologist1, tectons[3]);
        mycelium1a.addConnection(mycelium1b);
        mycelium1b.addConnection(mycelium1c);
        
        Mycelium mycelium2a = createMycelium(mycologist2, tectons[5]);
        Mycelium mycelium2b = createMycelium(mycologist2, tectons[6]);
        Mycelium mycelium2c = createMycelium(mycologist2, tectons[0]);
        mycelium2a.addConnection(mycelium2b);
        
        assertFalse(mycelium1a.isConnectedToMushroomBody(), "Not implemented");
        assertFalse(mycelium2c.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2a.isConnectedToMushroomBody(), "Not implemented");
        
        mycelium2b.addConnection(mycelium2c);

        Mycelium mycelium1d = createMycelium(mycologist1, tectons[0]);
        mycelium1a.addConnection(mycelium1d);
        
        assertTrue(mycelium2c.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1c.isConnectedToMushroomBody(), "Not implemented");
    }
    
    @Test
    void testConnectedCirclesAndNotConnected() {
        createMushroomBody(mycologist1, tectons[0]);
        createMushroomBody(mycologist2, tectons[5]);
        createMushroomBody(mycologist3, tectons[9]);
        
        Mycelium mycelium1a = createMycelium(mycologist1, tectons[0]);
        Mycelium mycelium1b = createMycelium(mycologist1, tectons[1]);
        Mycelium mycelium1c = createMycelium(mycologist1, tectons[2]);
        Mycelium mycelium1d = createMycelium(mycologist1, tectons[3]);
        mycelium1a.addConnection(mycelium1b);
        mycelium1b.addConnection(mycelium1c);
        mycelium1c.addConnection(mycelium1d);
        mycelium1d.addConnection(mycelium1a);
        
        Mycelium mycelium2a = createMycelium(mycologist2, tectons[5]);
        Mycelium mycelium2b = createMycelium(mycologist2, tectons[1]);
        Mycelium mycelium2c = createMycelium(mycologist2, tectons[0]);
        Mycelium mycelium2d = createMycelium(mycologist2, tectons[4]);
        mycelium2a.addConnection(mycelium2b);
        mycelium2b.addConnection(mycelium2c);
        mycelium2c.addConnection(mycelium2d);
        mycelium2d.addConnection(mycelium2a);
        
        Mycelium mycelium3a = createMycelium(mycologist3, tectons[3]);
        Mycelium mycelium3b = createMycelium(mycologist3, tectons[2]);
        Mycelium mycelium3c = createMycelium(mycologist3, tectons[6]);
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

        Mycelium mycelium3d = createMycelium(mycologist3, tectons[9]);
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

        Mycelium mycelium1a = createMycelium(mycologist1, tectons[0]);
        Mycelium mycelium1b = createMycelium(mycologist1, tectons[1]);
        Mycelium mycelium1c = createMycelium(mycologist1, tectons[2]);
        Mycelium mycelium1d = createMycelium(mycologist1, tectons[3]);
        Mycelium mycelium1e = createMycelium(mycologist1, tectons[4]);
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

        Mycelium mycelium1a = createMycelium(mycologist1, tectons[0]);
        Mycelium mycelium1b = createMycelium(mycologist1, tectons[3]);
        mycelium1a.addConnection(mycelium1b);

        Mycelium mycelium2a = createMycelium(mycologist2, tectons[4]);
        Mycelium mycelium2b = createMycelium(mycologist2, tectons[5]);
        mycelium2a.addConnection(mycelium2b);

        Mycelium mycelium3a = createMycelium(mycologist3, tectons[8]);
        Mycelium mycelium3b = createMycelium(mycologist3, tectons[7]);
        mycelium3a.addConnection(mycelium3b);

        assertTrue(mycelium1a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium1b.isConnectedToMushroomBody(), "Not implemented");

        assertTrue(mycelium2a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium2b.isConnectedToMushroomBody(), "Not implemented");

        assertTrue(mycelium3a.isConnectedToMushroomBody(), "Not implemented");
        assertTrue(mycelium3b.isConnectedToMushroomBody(), "Not implemented");

        // Second

        Mycelium mycelium1c = createMycelium(mycologist1, tectons[4]);
        mycelium1b.addConnection(mycelium1c);

        Mycelium mycelium2c = createMycelium(mycologist2, tectons[8]);
        mycelium2b.addConnection(mycelium2c);

        Mycelium mycelium3c = createMycelium(mycologist3, tectons[6]);
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

        Mycelium mycelium1d = createMycelium(mycologist1, tectons[1]);
        mycelium1a.addConnection(mycelium1d);

        Mycelium mycelium2d = createMycelium(mycologist2, tectons[2]);
        mycelium2b.addConnection(mycelium2d);

        Mycelium mycelium3d = createMycelium(mycologist3, tectons[3]);
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