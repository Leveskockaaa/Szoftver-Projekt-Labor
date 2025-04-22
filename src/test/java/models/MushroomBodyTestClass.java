package models;

import java.util.Random;

public class MushroomBodyTestClass {
    private TectonTestClass tecton;
    private MycologistTestClass mycologist;

    public MushroomBodyTestClass(TectonTestClass tecton, MycologistTestClass mycologist) {
        this.tecton = tecton;
        this.mycologist = mycologist;
    }

    public static MushroomBodyTestClass createRandomMushroomBody(TectonTestClass tecton, MycologistTestClass mycologist) {
        int type = new Random().nextInt(3);
        switch (type) {
            case 0 -> {
                return new CapulonTestClass(tecton, mycologist);
            }
            case 1 -> {
                return new GilledonTestClass(tecton, mycologist);
            }
            case 2 -> {
                return new HypharaTestClass(tecton, mycologist);
            }
            case 3 -> {
                return new PoraliaTestClass(tecton, mycologist);
            }
            default -> throw new AssertionError();
        }
    }

    public TectonTestClass getTecton() {
        return tecton;
    }

    public MycologistTestClass getMycologist() {
        return mycologist;
    }
}
