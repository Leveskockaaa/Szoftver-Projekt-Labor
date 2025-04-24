package models;

public class PoraliaTestClass extends MushroomBodyTestClass {
    public PoraliaTestClass(TectonTestClass tecton, MycologistTestClass mycologist) {
        super(tecton, mycologist);
    }

    @Override
    public MushroomBodyTestClass createMushroomBody(TectonTestClass tecton, MycologistTestClass mycologist) {
        return new PoraliaTestClass(tecton, mycologist);
    }
}