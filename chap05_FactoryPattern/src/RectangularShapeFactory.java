import java.awt.*;

public class RectangularShapeFactory implements ShapeFactory {
    @Override
    public Shape create(String type, Point[] points) {
        if (type.equals(Type.Rectangle.type)) {
            return new Rectangle(type, points);
        } else if (type.equals(Type.Parallelogram.type)) {
            return new Parallelogram(type, points);
        } else if (type.equals(Type.Trapezoid.type)) {
            return new Trapezoid(type, points);
        }
        return null;
    }
}