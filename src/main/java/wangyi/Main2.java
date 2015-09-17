package wangyi;

import java.util.Scanner;

/**
 * Created by yang on 2015/9/15.
 */
public class Main2 {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int cases = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < cases; i++) {
            String str = scanner.nextLine();
            Sloution sloution = new Sloution(str);
            System.out.println(sloution.readString());
        }
    }

    private static class Sloution{
        public String string;
        public int position;

        public Sloution(String string){
            this.string = string;
            this.position = 0;
        }

        public char peek(){
            return string.charAt(position + 1);
        }

        public void next(){
            position++;
        }

        public char now(){
            return string.charAt(position);
        }

        public boolean isEnd(){
           return position == string.length();
        }

        public int readString(){
            int ansLenght = 0;
            while(!isEnd()){
                char c = now();
                if(Character.isAlphabetic(c)){
                    next();
                    ansLenght ++;
                }else if(Character.isDigit(c)){
                    int num = readNum();
                    ansLenght = ansLenght + num - 1;
                }else if(c == '(') {
                    int length = readGroup();
                    int num = readNum();
                    ansLenght += num * length;
                }
            }

            return ansLenght;
        }

        public int readGroup(){
            int ansLenght =  0;
            next();         // for '('
            while(!isEnd()){
                char c = now();

                if(Character.isAlphabetic(c)){
                    next();
                    ansLenght ++;
                }else if(Character.isDigit(c)){
                    int num = readNum();
                    ansLenght = ansLenght + num - 1;
                }else if(c == '('){
                    int length = readGroup();
                    int num = readNum();
                    ansLenght += num * length;
                }else if(c == ')'){
                    next();
                    break;
                }
            }

            return ansLenght;
        }

        public int readNum(){
            StringBuffer buffer = new StringBuffer();
            while(!isEnd()){
                char c = now();
                if(Character.isDigit(c)){
                    buffer.append(c);
                    next();
                }else{
                    break;
                }
            }

            return Integer.parseInt(buffer.toString());
        }
    }
}
