import java.util.*;

/**
 * Created by yang on 2015/9/4.
 */
public class Uva10047 {
    private final static int colorNum = 5;
    private final static int directionNum = 4;
    private final static int[][] directions = {{-1,0},{0,1},{1,0},{0,-1}};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int cases = 0;

        while(true){
            int m = scanner.nextInt();
            int n = scanner.nextInt();

            if(m == 0 || n == 0){
                break;
            }

            scanner.nextLine();

            if(cases!=0){
                System.out.println();
            }

            cases ++;
            findWay(scanner,cases,m,n);
        }
    }

    private static void findWay(Scanner scanner,int cases,int m,int n){
        String[] map = new String[m];
        for(int i=0;i<m;i++){
            map[i] = scanner.nextLine();
        }


        // check start and end point
        Pos start = null;
        Pos target = new Pos(-1,-1);
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(map[i].charAt(j) == 'S'){
                    start = new Pos(i,j);
                }

                if(map[i].charAt(j) == 'T'){
                    target = new Pos(i,j);
                }
            }
        }


        // find all the way
        int num = findMinWay(m,n,map,start,target);
        System.out.println("Case #"+cases);
        if(num > 0){
            System.out.println("minimum time = "+ num +" sec");
        }else {
            System.out.println("destination not reachable");
        }
    }


    private static int findMinWay(int m,int n,String[] map,Pos start,Pos end){
        Set<State> visited = new HashSet<>();
        Queue<State> stateQueue = new ArrayDeque<>();

        State state = new State(start.x,start.y,0,0,0);
        stateQueue.add(state);

        while(!stateQueue.isEmpty()){
            state = stateQueue.poll();
            visited.add(state);

            int step = nextState(m, n, map, end, state, stateQueue,visited);

            if(step > 0){
                return step;
            }
        }

        return -1;
    }

    private static int nextState(int m,int n,String[] map,Pos end,State state,Queue<State> stateQueue,Set<State> visited){

        // try to move to the dir
        int[] dir = directions[state.direction];
        int nrow = state.row + dir[0];
        int ncol = state.col + dir[1];
        if(nrow>=0 && ncol>=0 && nrow < m && ncol < n && map[nrow].charAt(ncol) != '#'){
            State nState = new State(nrow,ncol,(state.color+1)%5,state.direction,state.step+1);

            if(!visited.contains(nState)){
                stateQueue.add(nState);

                if(nState.row == end.x && nState.col == end.y && nState.color == 0)
                    return nState.step;
            }
        }

        // try to change dir
        int d = 1;
        for(int i=0;i<2;i++){
            d = (-1)*d;
            State nState = new State(state.row,state.col,state.color,(state.direction + d+4)%4,state.step+1);
            if(!visited.contains(nState)){
                stateQueue.add(nState);
            }
        }

        return -1;
    }

    private static class Pos{
        public int x;
        public int y;

        public Pos(int x,int y){
            this.x = x;
            this.y = y;
        }
    }

    private static class State{
        public int row;
        public int col;
        public int color;
        public int direction;
        public int step;

        public State(int row,int col,int color,int direction,int step){
            this.row = row;
            this.col = col;
            this.color = color;
            this.direction = direction;
            this.step = step;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof State)) return false;

            State state = (State) o;

            if (row != state.row) return false;
            if (col != state.col) return false;
            if (color != state.color) return false;
            if (direction != state.direction) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = row;
            result = 31 * result + col;
            result = 31 * result + color;
            result = 31 * result + direction;
            return result;
        }

    }
}
