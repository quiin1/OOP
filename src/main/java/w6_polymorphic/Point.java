package w6_polymorphic;

public class Point {
    private double pointX;
    private double pointY;

    public Point(double pointX, double pointY) {
        this.pointX = pointX;
        this.pointY = pointY;
    }

    public double getPointX() {
        return pointX;
    }

    public void setPointX(double pointX) {
        this.pointX = pointX;
    }

    public double getPointY() {
        return pointY;
    }

    public void setPointY(double pointY) {
        this.pointY = pointY;
    }

    public double distance(Point p) {
        return Math.sqrt((pointX - p.pointX) * (pointX - p.pointX)
                + (pointY - p.pointY) * (pointY - p.pointY));
    }

    /**
     * .
     *
     * @param o .
     * @return .
     */
    public boolean equals(Object o) {
        Point otherP = (Point) o;
        return pointX == otherP.getPointX() && pointY == otherP.getPointY();
    }

    public int hashCode() {

        return 0;
    }

    /**
     * .
     *
     * @return .
     */
    public String toString() {
        return "(" + pointX + "," + pointY + ')';
    }
}
