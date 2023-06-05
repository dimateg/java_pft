package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

    @Test
    public void testDistance() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(5, 0);

        Assert.assertEquals(p1.distance(p2), 5.0);
    }

    @Test
    public void testDistance2() {
        Point p1 = new Point(5, 3);
        Point p2 = new Point(8, 7);

        Assert.assertEquals(p1.distance(p2), 5.0);
    }

    @Test
    public void testDistance3() {
        Point p1 = new Point(19, 5);
        Point p2 = new Point(3, 27);

        Assert.assertEquals(p1.distance(p2), 27.202941017470888);
    }
}
