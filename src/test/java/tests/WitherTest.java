package tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.CapulonTestClass;
import models.MushroomBodyTestClass;
import models.MyceliumTestClass;
import models.MycologistTestClass;
import models.TectonTestClass;

class WitherTest {
    TectonTestClass tecton1;
    TectonTestClass tecton2;
    MycologistTestClass mycologist;
    MyceliumTestClass mycelium1;
    MyceliumTestClass mycelium2;

    @BeforeEach
    void setUp() {
        mycologist = new MycologistTestClass("Mycologist");
        tecton1 = new TectonTestClass("Tecton1");
        tecton2 = new TectonTestClass("Tecton2");
        mycelium1 = new MyceliumTestClass(mycologist, tecton1);
        mycelium2 = new MyceliumTestClass(mycologist, tecton2);
        tecton1.addMycelium(mycelium1);
        tecton2.addMycelium(mycelium2);

        tecton1.addNeighbor(tecton2);
        mycelium1.addConnection(mycelium2);
    }

    @Test
    void testWitherOnSingleMycelium() {
        assertFalse(tecton1.getMycelia().isEmpty(), "Mycelium should not be empty before withering");

        mycelium1.wither();

        try {
            Thread.sleep(1100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertTrue(tecton1.getMycelia().isEmpty(), "Mycelium should be removed from the Tecton after withering");
    }

    @Test
    void testWitherOnMultipleMycelia() {
        assertFalse(tecton1.getMycelia().isEmpty(), "Mycelium should not be empty before withering");
        assertFalse(tecton2.getMycelia().isEmpty(), "Mycelium should not be empty before withering");

        mycelium1.wither();
        mycelium2.wither();

        try {
            Thread.sleep(900);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertTrue(mycelium1.isWithering(), "Mycelium1 should be withering");
        assertTrue(mycelium2.isWithering(), "Mycelium2 should be withering");

        MushroomBodyTestClass mushroomBody1 = new CapulonTestClass(tecton1, mycologist);
        tecton1.placeMushroomBody(mushroomBody1);
        if (mycelium1.isConnectedToMushroomBody()) mycelium1.cancelWither();
        if (mycelium2.isConnectedToMushroomBody()) mycelium2.cancelWither();

        assertFalse(mycelium1.isWithering(), "Mycelium1 should not be withering");
        assertFalse(mycelium2.isWithering(), "Mycelium2 should not be withering");

        assertFalse(tecton1.getMycelia().isEmpty(), "Mycelium should not be removed from the Tecton after withering");
        assertFalse(tecton2.getMycelia().isEmpty(), "Mycelium should not be removed from the Tecton after withering");
    }
}
