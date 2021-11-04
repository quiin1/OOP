package w6_polymorphic;

public class Layer {
    private java.util.List<Shape> shapes = new java.util.ArrayList<>();

    /**
     * add 1 shape -> list.
     *
     * @param shape .
     */
    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    /**
     * get info.
     *
     * @return info.
     */
    public String getInfo() {
        StringBuilder res = new StringBuilder("Layer of crazy shapes:\n");
        for (int i = 0; i < shapes.size(); i++) {
            res.append(shapes.get(i).toString()).append('\n');
        }
        return res.toString();
    }

    /**
     * remove all circles in list.
     */
    public void removeCircles() {
        for (int i = 0; i < shapes.size(); i++) {
            if (shapes.get(i).type.equals("circle")) {
                shapes.remove(i);
            }
        }
    }

    /**
     * remove duplicate shapes.
     */
    public void removeDuplicates() {
        for (int i = 0; i < shapes.size() - 1; i++) {
            for (int j = i + 1; j < shapes.size(); j++) {
                if (shapes.get(i).equals(shapes.get(j))) {
                    shapes.remove(j);
                }
            }
        }
    }
}
