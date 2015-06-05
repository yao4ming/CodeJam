public class AlienLanguage{

     public static void main(String []args){
        String s = "(ab)(bc)(ca)"; 
        findPermutations(s);
     }
     
     public static void findPermutations(String s) {
         int parentheses = 0;
         int len = 3;
         int[] numPermutations = new int[len];
         
         for (int i = 0; i < s.length(); i++) {
             if (s.charAt(i) == '(') {
                 while (s.charAt(++i) != ')') numPermutations[parentheses]++;
                 parentheses++;
             }
         }
         
         int permutations = 1;
         for (int i = 0; i < len; i++) {
            permutations *= numPermutations[i];
         }
		 
		 System.out.println(permutations);
         
     }
}