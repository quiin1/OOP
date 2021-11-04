package w6_polymorphic;

public abstract class Shape {
    protected String color;
    protected boolean filled;
    String type;

    public Shape() {
    }

    /**
     * Constructor 2.
     *
     * @param color  .
     * @param filled .
     */
    public Shape(String color, boolean filled) {
        this.color = color;
        this.filled = filled;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public abstract double getArea();

    public abstract double getPerimeter();

    /**
     * to string.
     *
     * @return .
     */
    public String toString() {
        return "Shape["
                + "color=" + color
                + ",filled=" + filled
                + ']';
    }

    public boolean isSameType(Shape otherSh) {
        return this.type.equals(otherSh.type);
    }
}
