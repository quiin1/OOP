package w6_polymorphic;

public class Square extends Rectangle {
    final String type = "square";

    /**
     * Constructor 1.
     */
    public Square() {
    }

    /**
     * Constructor 2.
     *
     * @param side .
     */
    public Square(double side) {
        super(side, side);
    }

    /**
     * Constructor 3.
     *
     * @param side   .
     * @param color  .
     * @param filled .
     */
    public Square(double side, String color, boolean filled) {
        super(side, side, color, filled);
    }

    /**
     * Constructor 4.
     *
     * @param topLeft .
     * @param side    .
     * @param color   .
     * @param filled  .
     */
    public Square(Point topLeft, double side, String color, boolean filled) {
        super(topLeft, side, side, color, filled);
    }

    public double getSide() {
        return super.getWidth();
    }

    public void setSide(double side) {
        setWidth(side);
        setLength(side);
    }

    @Override
    public void setWidth(double side) {
        super.setWidth(side);
    }

    @Override
    public void setLength(double side) {
        super.setLength(side);
    }

    @Override
    public String toString() {
        return "Square["
                + "topLeft=" + getTopLeft().toString()
                + ",side=" + getSide()
                + ",color=" + color
                + ",filled=" + filled
                + ']';
    }

    @Override
    public boolean equals(Object o) {
        if (String.valueOf(o).equals("circle")) {
            return false;
        }
        return super.equals(o);
    }
}
