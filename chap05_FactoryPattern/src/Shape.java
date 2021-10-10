import java.awt.*;

public abstract class Shape {
    String type;
    Point[] points;

    public Shape(String type, Point[] points) {
        this.type = type;
        this.points = points;
    }

    public abstract double calcArea();

    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder();

        answer.append(type).append("\n");
        int i = 0;
        for (Point point : points) {
            answer.append("P").append(i++).append(": ").append(point.toString()).append("\n");
        }
        answer.append("area: ").append(calcArea()).append("\n");
        return answer.toString();
    }
}
