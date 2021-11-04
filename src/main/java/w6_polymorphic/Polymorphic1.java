package w6_polymorphic;

public class Polymorphic1 {
    public static void main(String[] args) {
        Circle c1 = new Circle(3.5,"red",true);
        Circle c2 = new Circle(3.5,"red",true);
        System.out.println(c1);
        System.out.println(c1.getArea());
        System.out.println(c1.getPerimeter());
        System.out.println(c1.getColor());
        System.out.println(c1.isFilled());
        System.out.println(c1.equals(c2));
//
        Rectangle r2 = new Rectangle(2.5, 3.8,"yellow",true);
        System.out.println(r2);
        System.out.println(r2.getArea());
        System.out.println(r2.getPerimeter());
        System.out.println(r2.getColor());
//        System.out.println(r2.getSide()); // Cannot resolve method 'getSide' in 'Rectangle'
        System.out.println(r2.getLength());

        Square sq1 = new Square(2.3);
        System.out.println(sq1);
        System.out.println(sq1.getArea());
        System.out.println(sq1.getPerimeter());
        System.out.println(sq1.getColor());
        System.out.println(sq1.getSide());
        System.out.println(sq1.getLength());

        Layer l1 = new Layer();
        l1.addShape(c1);
        l1.addShape(c2);
        l1.addShape(r2);
        l1.addShape(sq1);
        System.out.println(l1.getInfo());
        l1.removeDuplicates();
        System.out.println(l1.getInfo());
        l1.removeCircles();
        System.out.println(l1.getInfo());
//        test();
    }

    private static void test() {
        Shape s1 = new Circle(5.5, "RED", false);
        System.out.println(s1);
        System.out.println(s1.getArea());
        System.out.println(s1.getPerimeter());
        System.out.println(s1.getColor());
        System.out.println(s1.isFilled());
//        System.out.println(s1.getRadius()); // Cannot resolve method 'getRadius' in 'Shape'

        Circle c1 = (Circle)s1;
        System.out.println(c1);
        System.out.println(c1.getArea());
        System.out.println(c1.getPerimeter());
        System.out.println(c1.getColor());
        System.out.println(c1.isFilled());
        System.out.println(c1.getRadius());

//        Shape s2 = new Shape(); // 'Shape' is abstract; cannot be instantiated

        Shape s3 = new Rectangle(1.0, 2.0, "RED", false);
        System.out.println(s3);
        System.out.println(s3.getArea());
        System.out.println(s3.getPerimeter());
        System.out.println(s3.getColor());
//        System.out.println(s3.getLength()); // Cannot resolve method 'getLength' in 'Shape'

        Rectangle r1 = (Rectangle)s3;
        System.out.println(r1);
        System.out.println(r1.getArea());
        System.out.println(r1.getColor());
        System.out.println(r1.getLength());

        Shape s4 = new Square(6.6);
        System.out.println(s4);
        System.out.println(s4.getArea());
        System.out.println(s4.getColor());
//        System.out.println(s4.getSide()); // Cannot resolve method 'getSide' in 'Shape'

        Rectangle r2 = (Rectangle)s4;
        System.out.println(r2);
        System.out.println(r2.getArea());
        System.out.println(r2.getColor());
//        System.out.println(r2.getSide()); // Cannot resolve method 'getSide' in 'Rectangle'
        System.out.println(r2.getLength());

        Square sq1 = (Square)r2;
        System.out.println(sq1);
        System.out.println(sq1.getArea());
        System.out.println(sq1.getColor());
        System.out.println(sq1.getSide());
        System.out.println(sq1.getLength());
    }

}
