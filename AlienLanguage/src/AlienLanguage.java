import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class AlienLanguage{

   static String[] words;

    public static void main(String []args) {

        File file = new File("A-small-practice.in");
        try (Scanner in = new Scanner(file); PrintWriter writer = new PrintWriter("A-small-practice.out")) {

            int L = in.nextInt();
            int D = in.nextInt();
            int N = in.nextInt();
            in.nextLine();
            words = new String[D];

            //store known words
            for (int i = 0; i < D; i++) {
                words[i] = in.nextLine();
            }

            //search known words for regex pattern
            for (int i = 0, testCase = 1; i < N; i++, testCase++) {

                int found = 0;

				//replace () with [] and use as regex to match known words
                String pattern = in.nextLine().replaceAll(Pattern.quote("("), "[").replaceAll(Pattern.quote(")"), "]");

                for (int j = 0; j < D; j++) {
                    if (words[j].matches(pattern)) found++;
                }
                writer.println("Case #" + testCase + ": " + found);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

