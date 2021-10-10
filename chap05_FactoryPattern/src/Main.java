import java.awt.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        final Point[] RECTANGLE_POINTS = {
                new Point(100, 150),
                new Point(150, 100)
        };
        final Point[] TRAPEZOID_POINTS = {
                new Point(200, 150),
                new Point(280, 100),
                new Point(170, 100),
                new Point(250, 150)
        };
        final Point[] PARALLELOGRAM_POINTS = {
                new Point(330, 150),
                new Point(400, 100),
                new Point(300, 100),
                new Point(430, 150)
        };
        final Point[] TRIANGLE_POINTS = {
                new Point(225, 300),
                new Point(200, 250),
                new Point(250, 250)
        };
        final Point[] RIGHT_TRIANGLE_POINTS = {
                new Point(350, 300),
                new Point(300, 250),
                new Point(350, 250)
        };

        ArrayList<Shape> shapeList = new ArrayList<>();
        ShapeFactory factory;

        factory = new RectangularShapeFactory();
        shapeList.add(factory.create(Type.Rectangle.type, RECTANGLE_POINTS));
        shapeList.add(factory.create(Type.Trapezoid.type, TRAPEZOID_POINTS));
        shapeList.add(factory.create(Type.Parallelogram.type, PARALLELOGRAM_POINTS));

        factory = new TriangularShapeFactory();
        shapeList.add(factory.create(Type.Triangle.type, TRIANGLE_POINTS));
        shapeList.add(factory.create(Type.RightTriangle.type, RIGHT_TRIANGLE_POINTS));

        for (Shape s : shapeList) {
            System.out.println(s);
        }
    }
}

