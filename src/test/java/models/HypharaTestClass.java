package models;

public class HypharaTestClass extends MushroomBodyTestClass {
    public HypharaTestClass(TectonTestClass tecton, MycologistTestClass mycologist) {
        super(tecton, mycologist);
    }

    @Override
    public MushroomBodyTestClass createMushroomBody(TectonTestClass tecton, MycologistTestClass mycologist) {
        return new HypharaTestClass(tecton, mycologist);
    }
}
