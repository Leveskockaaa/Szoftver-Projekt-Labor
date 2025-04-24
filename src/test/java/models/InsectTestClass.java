package models;

public class InsectTestClass {
    private final String name;
    private TectonTestClass tecton;
    private boolean isParalized;

    public InsectTestClass(String name, TectonTestClass tecton) {
        this.name = name;
        this.tecton = tecton;
        this.isParalized = false;
    }
    
    public boolean isParalized() {
        return isParalized;
    }

    public void paralize() {
        this.isParalized = true;
    }

    public void unParalized() {
        this.isParalized = false;
    }

    public TectonTestClass getTecton() {
        return tecton;
    }

    @Override
    public String toString() {
        return name;
    }
}
