package w8_interface;

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

    public double distance(Point other) {
        return Math.sqrt((pointX - other.pointX) * (pointX - other.pointX)
                + (pointY - other.pointY) * (pointY - other.pointY));
    }

    protected double length() {
        return distance(new Point(0, 0));
    }

    protected boolean equal(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o instanceof Point) {
            Point other = (Point) o;
            return pointX == other.pointX && pointY == other.pointY;
        }
        return false;
    }

    protected Point vecto(Point other) {
        return new Point(other.pointX - pointX, other.pointY - pointY);
    }

    protected boolean isParallel(Point other) {
        if (pointX == 0 && other.pointX == 0 || pointY == 0 && other.pointY == 0) {
            return true;
        }
        return pointX / other.pointX == pointY / other.pointY;
    }
}
