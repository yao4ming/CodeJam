import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by yaoming on 4/14/16.
 */
public class Main {

    static boolean[] digitFound = new boolean[10];

    static String countSheep(String T) {

        if (T.equals("0")) return "Insomnia";

        //sleep when counter == 10
        int counter = 0;

        //integer val of T
        int Tval = Integer.parseInt(T);

        int N = 1;
        while (true) {

            T = Integer.toString(Tval * N);
            N++;

            //extract digits from T
            for (int i = 0; i < T.length(); i++) {
                int index = Character.getNumericValue(T.charAt(i));
                if (!digitFound[index]) {
                    digitFound[index] = true;
                    counter++;
                }
            }

            if (counter == 10) return T;
        }

    }

    static void reset() {
        for (int i = 0; i < digitFound.length; i++) {
            digitFound[i] = false;
        }
    }

    public static void main(String[] args) {
        try (Scanner in = new Scanner(new File(args[0]));
             PrintWriter writer = new PrintWriter("A-large-practice.out")) {
            int N = Integer.parseInt(in.nextLine());
            for (int i = 1; i <= N; i++) {
                String y = countSheep(in.nextLine());
                writer.println("Case #" + i + ": " + y);
                reset();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
