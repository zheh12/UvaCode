
import java.util.Scanner;

/**
 * Created by yang on 2015/9/4.
 */
public class Uva10161 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            int num = Integer.parseInt(scanner.nextLine());
            if(num == 0)
                break;

            Position ans = position(num);
            System.out.println(ans.x+" "+ans.y);
        }
    }

    private static Position position(int time){
        Position ans = new Position(0,0);
        int n = (int)Math.floor(Math.sqrt(time));
        int remain = time - n*n;

        if(n % 2 == 0){
            ans = new Position(n,1);

            if(remain>0){
                if(remain < n+1){
                    ans.x ++;
                    ans.y = ans.y + remain - 1;
                }else{
                    ans.y = n+1;
                    ans.x = ans.x - (remain - n - 2);
                }
            }
        }else {
            ans = new Position(1,n);

            if(remain>0){
                if(remain < n+1){
                    ans.y ++;
                    ans.x = ans.x + remain - 1;
                }else{
                    ans.x = n+1;
                    ans.y = ans.y - (remain - n - 2);
                }
            }
        }

        return ans;
    }

    private static class Position {
        public int x;
        public int y;

        public Position(int x,int y){
            this.x = x;
            this.y = y;
        }
    }
}
