import java.util.Stack;

class Solution {
    public static String reverse(String s, int low, int high) {
        String st = "";
        for (int i = low; i < high; i++) {
            char ch = s.charAt(i);
            st = ch + st;
        }
        String substring = s.substring(low, high);
        return s.replace(substring, st);
    }


    // Function to return the modified string
    static String reverseParentheses(String str) {
        Stack<Integer> st = new Stack();
        for (int i = 0; i < str.length(); i++) {

            // Push the index of the current
            // opening bracket
            if (str.charAt(i) == '(') {
                st.push(i);
            }

            // Reverse the substring starting
            else if (str.charAt(i) == ')') {
                String reverse = reverse(str, st.peek() + 1, i);

                str = String.copyValueOf(reverse.toCharArray());
            }
        }

        return str;

    }

    public static void main(String[] args) {
        //String str = "abd(jnb)asdf";
        String str = "dd(df)a(ghhh)";
        // String str = "abdjnbasdf";
        System.out.println("Reversed word: " + reverseParentheses(str)
        );

    }

}