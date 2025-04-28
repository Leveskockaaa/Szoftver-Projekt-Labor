package models;

public class GilledonTestClass extends MushroomBodyTestClass {
    public GilledonTestClass(TectonTestClass tecton, MycologistTestClass mycologist) {
        super(tecton, mycologist);
    }

    @Override
    public MushroomBodyTestClass createMushroomBody(TectonTestClass tecton, MycologistTestClass mycologist) {
        return new GilledonTestClass(tecton, mycologist);
    }
}
