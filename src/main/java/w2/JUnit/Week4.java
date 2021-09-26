package w2.JUnit;

public class Week4 {
    /**
     * @param a .
     * @param b .
     * @return max of $a and $b.
     */
    public static int max2Int(int a, int b) {
        int max2 = (a > b ? a : b);
        return max2;
    }

    /**
     * @param arr .
     * @return min of array $arr.
     */
    public static int minArray(int[] arr) {
        int min2 = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (min2 > arr[i]) {
                min2 = arr[i];
            }
        }
        return min2;
    }

    /**
     * @param weight in kg.
     * @param height in m.
     * @return BMI index.
     */
    public static String calculateBMI(double weight, double height) {
        double BMI = weight / (height * height);
        BMI = (double) Math.round((BMI * 10) / 10);
        if (BMI < 18.5) {
            return "Thiếu cân";
        }
        if (BMI <= 22.9) {
            return "Bình thường";
        }
        if (BMI <= 24.9) {
            return "Thừa cân";
        }
        return "Béo phì";
    }

    public static void main(String[] args){
        System.out.println((double) Math.round(27.128*100)/100);
    }
}

