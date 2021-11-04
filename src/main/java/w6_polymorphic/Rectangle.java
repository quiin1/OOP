package w6_polymorphic;

public class Rectangle extends Shape {
    protected Point topLeft = new Point(0, 0);
    protected double width;
    protected double length;
    final String type = "rectangle";

    public Rectangle() {
        super.type = type;
    }

    /**
     * Constructor 2.
     *
     * @param width  .
     * @param length .
     */
    public Rectangle(double width, double length) {
        this.width = width;
        this.length = length;
        super.type = type;
    }

    /**
     * Constructor 3.
     *
     * @param width  .
     * @param length .
     * @param color  .
     * @param filled .
     */
    public Rectangle(double width, double length, String color, boolean filled) {
        super(color, filled);
        this.width = width;
        this.length = length;
        super.type = type;
    }

    /**
     * Constructor 4.
     *
     * @param topLeft .
     * @param width   .
     * @param length  .
     * @param color   .
     * @param filled  .
     */
    public Rectangle(Point topLeft, double width, double length, String color, boolean filled) {
        super(color, filled);
        this.topLeft = topLeft;
        this.width = width;
        this.length = length;
        super.type = type;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public double getArea() {
        return width * length;
    }

    @Override
    public double getPerimeter() {
        return 2.0 * (width + length);
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    /**
     * .
     *
     * @param o shape.
     * @return .
     */
    public boolean equals(Object o) {
        if (!super.isSameType((Shape) o)) {
            return false;
        }

        if (super.type.equals("square")) {
            Square otherSq = (Square) o;
            return topLeft.equals(otherSq.topLeft)
                    && Math.abs(width - otherSq.width) <= 0.001
                    && Math.abs(length - otherSq.length) <= 0.001;
        }

        Rectangle otherRec = (Rectangle) o;
        return topLeft.equals(otherRec.topLeft)
                && Math.abs(width - otherRec.width) <= 0.001
                && Math.abs(length - otherRec.length) <= 0.001;
    }

    public int hashCode() {

        return 0;
    }

    @Override
    public String toString() {
        return "Rectangle["
                + "topLeft=" + getTopLeft().toString()
                + ",width=" + width
                + ",length=" + length
                + ",color=" + color
                + ",filled=" + filled
                + ']';
    }
}
