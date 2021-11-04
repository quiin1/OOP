package w8_interface;

import java.util.ArrayList;
import java.util.List;

public class ShapeUtil {
    /**
     * Info of list shapes has cirles and triangles.
     *
     * @param shapes .
     * @return Circle:\n... Triangle:\n...
     */
    public String printInfo(List<GeometricObject> shapes) {
        StringBuilder info = new StringBuilder();
        int circles = 0;
        int triangles = 0;
        for (GeometricObject shape : shapes) {
            if (shape.getInfo().startsWith("Circle")) {
                if (circles == 0) {
                    info.append("Circle:\n");
                }
                info.append(shape.getInfo()).append("\n");
                circles++;
            }
        }
        for (GeometricObject shape : shapes) {
            if (shape.getInfo().startsWith("Triangle")) {
                if (triangles == 0) {
                    info.append("Triangle:\n");
                }
                info.append(shape.getInfo()).append("\n");
                triangles++;
            }
        }
        return info.toString();
    }

    public static void main(String[] args) {
        List<GeometricObject> shapes = new ArrayList<>();
        Circle c1 = new Circle(new Point(0, 0), 5);
//        Triangle t1 = new Triangle(new Point(0, 0), new Point(0, 0), new Point(0, 0));
//        Triangle t2 = new Triangle(new Point(0, 0), new Point(0.5, 0.5), new Point(1, 1));
//        Triangle t3 = new Triangle(new Point(0, 1), new Point(0, 0), new Point(0, 3));
        Triangle t4 = new Triangle(new Point(0, 0), new Point(1, 1), new Point(3, 0));
        shapes.add(c1);
        shapes.add(t4);

        ShapeUtil shapeUtil = new ShapeUtil();
        System.out.println(shapeUtil.printInfo(shapes));
    }
}
