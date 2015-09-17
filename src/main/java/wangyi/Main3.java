package wangyi;

import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

/**
 * Created by yang on 2015/9/15.
 */
public class Main3 {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        int cases = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < cases; i++) {
            String string = scanner.nextLine();
            Solution solution = new Solution(string);

            long num = solution.checkExpression();
            if(!solution.isRight){
                System.out.println("invalid expression");
            }else{
                System.out.println(num);
            }
        }
    }

    private static class Solution{
        public String string;
        public boolean isRight;
        private int position;
        private Stack<String> stack;

        public boolean isOperator(char c){
            return c == '+' || c== '-' || c == '*';
        }

        public Solution(String string){
            this.string = string;
            position = 0;
            stack = new Stack<>();
            isRight = true;
        }

        public long checkExpression(){
            int i = 0;
            while(i < string.length()) {
                if(!isRight){
                    return Long.MAX_VALUE;
                }

                char c = string.charAt(i);

               //System.out.println(i + " " + c);
                if(c == '('){
                    stack.push(Character.toString(c));
                }else if(c == ' '){
                    i++;
                    continue;
                }else if(Character.isDigit(c)){         // read num
                    StringBuffer buffer = new StringBuffer();
                    for(int j =i;j<string.length();j++){
                        char tmp = string.charAt(j);
                        if(Character.isDigit(tmp)) {
                            buffer.append(string.charAt(j));
                        }else{
                            stack.push(buffer.toString());
                            buffer.delete(0,buffer.length());
                            i = j-1;
                            break;
                        }
                    }

                    if(buffer.length() > 0){
                        stack.push(buffer.toString());
                    }
                }else if(c == ')'){
                    long num = clearStack();
                    stack.push(Long.toString(num));
                }else{
                    stack.push(Character.toString(c));
                }

                i++;
               // System.out.println(stack);
            }

            if(stack.size() > 1){
                isRight = false;
                return -1;
            }else{
                return Long.parseLong(stack.pop());
            }
        }

        public long clearStack(){
            boolean findFlag = false;
            boolean isOperator = false;
            long ans = Long.MAX_VALUE;
            Vector<Long> nums = new Vector<>();
            while(!stack.empty()){
                String string = stack.pop();
                if(string.equals("(")){
                    findFlag = true;
                    break;
                }else if(string.equals("+")) {
                    if(nums.size() > 0){
                        ans = 0;
                        for(int i=0;i<nums.size();i++){
                            ans += nums.get(i);
                        }
                    }else{
                        isRight = false;
                    }

                    isOperator = true;
                }else if(string.equals("*")){
                    if(nums.size() > 0){
                        ans = 1;
                        for(int i=0;i<nums.size();i++){
                            ans *= nums.get(i);
                        }
                    }else{
                        isRight = false;
                    }
                    isOperator = true;
                }else if(string.equals("-")){
                    if(nums.size() == 1){
                        ans = -nums.get(0);
                    }else if(nums.size() == 2){
                        ans = nums.get(1) - nums.get(0);
                    }else{
                        isRight = false;
                    }

                    isOperator = true;
                }else{
                    nums.add(Long.parseLong(string));
                }

                if(isOperator){
                    String str = stack.pop();
                    if(!str.equals("(")){
                        isRight = false;
                    }else {
                        findFlag = true;
                    }

                    break;
                }
            }

            if(!findFlag || ans == Long.MAX_VALUE){
                isRight = false;
            }

            return ans;
        }
    }
}
