package ru.stqa.pft.sandbox;

public class DistancePointToPoint {

    public static void main(String[] args) {
        Point p1 = new Point(5, 7);
        Point p2 = new Point(4, 3);

        System.out.println("Растояние между точками " + p1.x + ":" + p1.y + " и " + p2.x + ":" + p2.y +
                " = " + p1.distance(p2));

    }

}
