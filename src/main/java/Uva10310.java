import java.util.Scanner;
import java.util.Vector;

/**
 * Created by yang on 2015/9/7.
 */
public class Uva10310 {
	private static Scanner scanner;
	private static double eps = 0.00000001;
    public static void main(String[] args) {
    	scanner = new Scanner(System.in);
    	
    	while(scanner.hasNext()){
    		int num = scanner.nextInt();
    		Point gopherPoint = Point.readPoint();
    		Point dogPoint = Point.readPoint();
    		
    		Vector<Point> points = new Vector<>();
    		for(int i=0;i<num;i++){
    			points.add(Point.readPoint());
    		}

			func(gopherPoint,dogPoint,points);
    	}
	}

	private static void func(Point gopher,Point dog,Vector<Point> points){
		for(Point hole : points){
			double dogDistance = hole.distance(dog);
			double gopherDistance = hole.distance(gopher);


			if(dogDistance - gopherDistance*4 >= 0){
				System.out.println("The gopher can escape through the hole at " + hole + ".");
				return;
			}
		}

		System.out.println("The gopher cannot escape.");
	}
    
    private static class Point{
    	public float x;
    	public float y;
    	
    	public Point(float x,float y){
    		this.x = x;
    		this.y = y;
    	}
    	
    	public double distance(Point other){
			return (this.x - other.x)*(this.x - other.x) + (this.y - other.y)*(this.y - other.y);
    	}
    	
    	public static Point readPoint(){
    		return new Point(scanner.nextFloat(),scanner.nextFloat());
    	}

		public String toString(){
			return String.format("(%.3f,%.3f)",x,y);
		}
    }
}
