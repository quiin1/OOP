import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Week8Task2 {
    public void nullPointerEx() {
        String a = null;
        System.out.println(a.toLowerCase());
    }

    /**
     * test 1.
     *
     * @throws NullPointerException .
     */
    public String nullPointerExTest() throws NullPointerException {
        try {
            nullPointerEx();
        } catch (NullPointerException e) {
            return "Lỗi Null Pointer";
        }
        return null;
    }

    public void arrayIndexOutOfBoundsEx() {
        int[] a = new int[10];
        System.out.println(a[10]);
    }

    /**
     * test 2.
     *
     * @throws ArrayIndexOutOfBoundsException .
     */
    public String arrayIndexOutOfBoundsExTest() throws ArrayIndexOutOfBoundsException {
        try {
            arrayIndexOutOfBoundsEx();
        } catch (ArrayIndexOutOfBoundsException e) {
            return "Lỗi Array Index Out of Bounds";
        }
        return null;
    }

    public double arithmeticEx() {
        return 1 / 0;
    }

    /**
     * test 3.
     *
     * @throws ArithmeticException .
     */
    public String arithmeticExTest() throws ArithmeticException {
        try {
            arithmeticEx();
        } catch (ArithmeticException e) {
            return "Lỗi Arithmetic";
        }
        return null;
    }

    public void fileNotFoundEx() throws FileNotFoundException {
        FileReader f = new FileReader("D:\\abc");
        f.getEncoding();
    }

    /**
     * test 4.
     *
     */
    public String fileNotFoundExTest() {
        try {
            fileNotFoundEx();
        } catch (FileNotFoundException e) {
            return "Lỗi File Not Found";
        }
        return null;
    }

    public void ioEx() throws IOException {
        FileReader f = new FileReader("D:\\abc");
        f.read();
    }

    /**
     * test 5.
     *
     */
    public String ioExTest() {
        try {
            ioEx();
        } catch (IOException e) {
            return "Lỗi IO";
        }
        return null;
    }

    /**
     * main.
     * @param args .
     * @throws IOException .
     */
    public static void main(String[] args) throws IOException {
        Week8Task2 test = new Week8Task2();
        System.err.println(test.nullPointerExTest());
        System.err.println(test.arrayIndexOutOfBoundsExTest());
        System.err.println(test.fileNotFoundExTest());
        System.err.println(test.ioExTest());
    }
}
