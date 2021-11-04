package w6_polymorphic;

public class Polymorphic2 {
    public static void main(String[] args) {
        Square square = new Square(new Point(10, 5), 5, "BLUE", true);

        Circle c1 = new Circle(3.5, "red", true);
        Circle c2 = new Circle(3.5, "red", true);
        System.out.println(c1);
        System.out.println(c1.getArea());
        System.out.println(c1.getPerimeter());
        System.out.println(c1.getColor());
        System.out.println(c1.isFilled());
        System.out.println(c1.equals(c2));
//
        Rectangle r2 = new Rectangle(2.5, 2.5, "yellow", true);
        Rectangle r3 = new Rectangle(2.5, 2.5, "yellow", true);
        System.out.println(r2);
        System.out.println(r2.getArea());
        System.out.println(r2.getPerimeter());
        System.out.println(r2.getColor());
//        System.out.println(r2.getSide()); // Cannot resolve method 'getSide' in 'Rectangle'
        System.out.println(r2.getLength());

        Square sq1 = new Square(2.3);
        Square sq2 = new Square(2.3);
        System.out.println(sq1);
        System.out.println(sq1.getArea());
        System.out.println(sq1.getPerimeter());
        System.out.println(sq1.getColor());
        System.out.println(sq1.getSide());
        System.out.println(sq1.getLength());

        Layer l1 = new Layer();
        l1.addShape(square);
        l1.addShape(c1);
        l1.addShape(c2);
        l1.addShape(r2);
        l1.addShape(sq1);
        l1.addShape(r3);
        l1.addShape(sq2);
        System.out.println(l1.getInfo());
        l1.removeDuplicates();
        System.out.println(l1.getInfo());
        l1.removeCircles();
        System.out.println(l1.getInfo());
//        test();
    }
}
