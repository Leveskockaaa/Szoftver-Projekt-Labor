package models;

public class CapulonTestClass extends MushroomBodyTestClass {
    public CapulonTestClass(TectonTestClass tecton, MycologistTestClass mycologist) {
        super(tecton, mycologist);
    }

    @Override
    public MushroomBodyTestClass createMushroomBody(TectonTestClass tecton, MycologistTestClass mycologist) {
        return new CapulonTestClass(tecton, mycologist);
    }
}
