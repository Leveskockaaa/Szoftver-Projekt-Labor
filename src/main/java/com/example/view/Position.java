package com.example.view;

public class Position {
    public int x;
    public int y;
    public int width;
    public int height;
    public int rotation;

    public Position(int x, int y, int width, int height, int rotation) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 0;
        this.height = 0;
        this.rotation = 0;
    }

    @Override
    public String toString() {
        return "Position{" +
               "x=" + x +
               ", y=" + y +
               ", width=" + width +
               ", height=" + height +
               ", rotation=" + rotation +
               '}';
    }
}
