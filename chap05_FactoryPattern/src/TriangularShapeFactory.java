import java.awt.*;

public class TriangularShapeFactory implements ShapeFactory {
    @Override
    public Shape create(String type, Point[] points) {
        if (type.equals(Type.Triangle.type)) {
            return new Triangle(type, points);
        } else if (type.equals(Type.RightTriangle.type)) {
            return new RightTriangle(type, points);
        }
        return null;
    }
}
