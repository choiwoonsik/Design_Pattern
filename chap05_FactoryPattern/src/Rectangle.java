import java.awt.*;

public class Rectangle extends Shape {
    public Rectangle(String type, Point[] points) {
        super(type, points);
    }

    @Override
    public double calcArea() {
        return Math.abs(points[0].y - points[1].y) * Math.abs(points[0].x - points[1].x);
    }
}
