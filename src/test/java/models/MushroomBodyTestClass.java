package models;

public abstract class MushroomBodyTestClass {
    protected TectonTestClass tecton;
    protected MycologistTestClass mycologist;

    public MushroomBodyTestClass(TectonTestClass tecton, MycologistTestClass mycologist) {
        this.tecton = tecton;
        this.mycologist = mycologist;
        initializeMycologist();
    }

    private void initializeMycologist() {
        mycologist.addMushroomBody(this);
    }

    public abstract MushroomBodyTestClass createMushroomBody(TectonTestClass tecton, MycologistTestClass mycologist);

    public TectonTestClass getTecton() {
        return tecton;
    }

    public MycologistTestClass getMycologist() {
        return mycologist;
    }
}
