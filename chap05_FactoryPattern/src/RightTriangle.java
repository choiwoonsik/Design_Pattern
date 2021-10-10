import java.awt.*;

public class RightTriangle extends Shape {
    public RightTriangle(String type, Point[] points) {
        super(type, points);
    }

    @Override
    public double calcArea() {
        return (double) Math.abs(points[2].x - points[1].x) * Math.abs(points[0].y - points[2].y) / 2;
    }
}
