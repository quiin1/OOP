package w8_interface;

public class Triangle implements GeometricObject {
    private Point p1;
    private Point p2;
    private Point p3;

    /**
     * Constructor.
     * @param p1 .
     * @param p2 .
     * @param p3 .
     * @throws RuntimeException if >= 2 same point or 3 point : 1 line.
     */
    public Triangle(Point p1, Point p2, Point p3) throws RuntimeException {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        if (p1.equal(p2) || p2.equal(p3) || p3.equal(p1)
                || p1.vecto(p2).isParallel(p1.vecto(p3))) {
            throw new RuntimeException();
        }
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public Point getP3() {
        return p3;
    }

    @Override
    public double getArea() {
        double p = getPerimeter() / 2;
        return Math.sqrt(p * (p - p1.distance(p2)) * (p - p2.distance(p3)) * (p - p3.distance(p1)));
    }

    @Override
    public double getPerimeter() {
        return p1.distance(p2) + p2.distance(p3) + p3.distance(p1);
    }

    @Override
    public String getInfo() {
        return "Triangle["
                + "(" + String.format("%.2f", p1.getPointX()) + ","
                        + String.format("%.2f", p1.getPointY()) + "),"
                + "(" + String.format("%.2f", p2.getPointX()) + ","
                        + String.format("%.2f", p2.getPointY()) + "),"
                + "(" + String.format("%.2f", p3.getPointX()) + ","
                        + String.format("%.2f", p3.getPointY()) + ")"
                + "]";
    }
}
