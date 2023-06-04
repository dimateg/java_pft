package ru.stqa.pft.sandbox;

public class Point {

    public double x;
    public double y;


    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point pointToCalculate) {
        double a = this.y - pointToCalculate.y;
        double b = this.x - pointToCalculate.x;
        return Math.sqrt((a * a) + (b * b));
    }
}