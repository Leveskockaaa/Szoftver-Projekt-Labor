package models;

public class SporeTestClass {
    protected MushroomBodyTestClass mushroomBody;

    public SporeTestClass(MushroomBodyTestClass mushroomBody) {
        this.mushroomBody = mushroomBody;
    }

    public MushroomBodyTestClass getMushroomBody() {
        return mushroomBody;
    }
}