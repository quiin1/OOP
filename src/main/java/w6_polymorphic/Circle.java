package w6_polymorphic;

import static java.lang.Math.PI;

public class Circle extends Shape {
    protected double radius;
    protected Point center = new Point(0, 0);
    final String type = "circle";

    /**
     * Constructor 1.
     */
    public Circle() {
        super.type = type;
    }

    /**
     * Constructor 2.
     *
     * @param radius .
     */
    public Circle(double radius) {
        this.radius = radius;
        super.type = type;
    }

    /**
     * Constructor 3.
     *
     * @param radius .
     * @param color  .
     * @param filled .
     */
    public Circle(double radius, String color, boolean filled) {
        super(color, filled);
        this.radius = radius;
        super.type = type;
    }

    /**
     * Constructor 4.
     *
     * @param color  .
     * @param filled .
     * @param radius .
     * @param center .
     */
    public Circle(Point center, double radius, String color, boolean filled) {
        super(color, filled);
        this.radius = radius;
        this.center = center;
        super.type = type;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2.0 * PI * radius;
    }

    /**
     * .
     *
     * @param o .
     * @return .
     */
    public boolean equals(Object o) {
        if (!super.isSameType((Shape) o)) {
            return false;
        }
        if (String.valueOf(o).equals(this.type)) {
            return true;
        }
        Circle otherCir = (Circle) o;
        return center.equals(otherCir.center)
                && Math.abs(radius - otherCir.radius) <= 0.001;
    }

    public int hashCode() {

        return 0;
    }

    @Override
    public String toString() {
        return "Circle["
                + "center=" + getCenter().toString()
                + ",radius=" + radius
                + ",color=" + color
                + ",filled=" + filled
                + ']';
    }


}
