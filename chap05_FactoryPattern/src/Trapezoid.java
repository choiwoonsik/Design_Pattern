import java.awt.*;

public class Trapezoid extends Shape {
    public Trapezoid(String type, Point[] points) {
        super(type, points);
    }

    @Override
    public double calcArea() {
        int upLen = Math.abs(points[3].x - points[0].x);
        int downLen = Math.abs(points[1].x - points[2].x);
        int height = Math.abs(points[0].y - points[1].y);
        return (double) (upLen + downLen) * height / 2;
    }
}
