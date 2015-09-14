import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by yang on 2015/9/10.
 */
public class Uva10180 {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int num = scanner.nextInt();
        for(int i=0;i<num;i++){
            Point point1 = Point.readPoint();
            Point point2 = Point.readPoint();
            double radius = scanner.nextDouble();

            func(point1,point2,radius);
        }
    }

    private static void func(Point p1,Point p2,double r){
        boolean cross = checkCross(p1, p2, r);

        Line line = new Line(p1,p2);

        if(cross){
            double line1 = new Line(p1,new Point(0,0)).length();
            double line2 = r;
            double line3 = Math.sqrt(line1*line1 - r*r);
            double angle1 = angle(r / line1);

            double line4 = new Line(p2,new Point(0,0)).length();
            double line5 = r;
            double line6 = Math.sqrt(line4*line4 - r*r);
            double angle2 = angle(r / line4);

            Triangle triangle = new Triangle(new double[]{line1,line4,line.length()});

            double angle3 = triangle.angles[0];

            double angle = Math.min(angle3 - angle1 - angle2,2*Math.PI - angle1 - angle2 - angle3);

            double ans = line3 + line6 + angle * r;
            System.out.println(String.format("%.3f",ans));
        }else{
            System.out.println(String.format("%.3f",line.length()));
        }
    }

    private static boolean checkCross(Point p1,Point p2,double r){
        Line line = new Line(p1,p2);
        double h = line.distance(new Point(0,0));

        if(h >= r){
            return false;
        }

        double length1 = line.length();
        double r1 = new Line(p1,new Point(0,0)).length();
        double r2 = new Line(p2,new Point(0,0)).length();

        double length2 = Math.sqrt(r1*r1 - h*h);
        double length3 = Math.sqrt(r2*r2 - h*h);

        double length4 = Math.abs(length1 - Math.abs(length2 - length3));
        double length5 = Math.abs(length1 - (length2 + length3));
        if(length4 > length5){
            return true;
        }

        return false;
    }

    private static double angle(double cosValue){
        cosValue = Math.max(-1, cosValue);
        cosValue = Math.min(1,cosValue);

        return Math.acos(cosValue);
    }

    private static class Point{
        public double x;
        public double y;

        public Point(double x,double y){
            this.x = x;
            this.y = y;
        }

        public static Point readPoint(){
            return new Point(scanner.nextDouble(),scanner.nextDouble());
        }
    }

    private static class Line{
        public Point[] points;

        public double a;
        public double b;
        public double c;

        public Line(Point p1,Point p2){
            points = new Point[]{p1,p2};
            a = (p2.y - p1.y);
            b = (p1.x - p2.x);
            c = - (a*p1.x + b*p1.y);

            assert(a*p1.x + b*p1.y == a*p2.x + b*p2.y);
        }

        public Line(double a,double b,double c){
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public double distance(Point p){
            double x = p.x;
            double y = p.y;

            return Math.abs(a*x + b*y +c)/Math.sqrt(a*a + b*b);
        }

        public double length(){
            double x = points[0].x - points[1].x;
            double y = points[0].y - points[1].y;

            return Math.sqrt(x*x+y*y);
        }
    }

    private static class Triangle{
        public Point[] points = new Point[3];
        public double[] lines = new double[3];
        public double[] angles = new double[3];

        public Triangle(Point[] points){
            this.points = points;
            for(int i=0;i<3;i++){
                Point p1 = points[i];
                Point p2 = points[(i+1)%3];

                lines[i] = new Line(p1,p2).length();
            }

            for(int i=0;i<3;i++){
                double line1 = lines[i];
                double line2 = lines[(i+1)%3];
                double line3 = lines[(i+2)%3];

                angles[i] = computeAngle(line1,line2,line3);
            }
        }

        public Triangle(double[] lines){
            this.lines = lines;

            for(int i=0;i<3;i++){
                double line1 = lines[i];
                double line2 = lines[(i+1)%3];
                double line3 = lines[(i+2)%3];

                angles[i] = computeAngle(line1,line2,line3);
            }
        }

        private double computeAngle(double line1,double line2,double line3){
            double cosValue =(-line3*line3 + line1*line1 + line2*line2)/(2*line1*line2);

            return angle(cosValue);
        }

        @Override
        public String toString() {
            return "Triangle{" +
                    "points=" + Arrays.toString(points) +
                    ", lines=" + Arrays.toString(lines) +
                    ", angles=" + Arrays.toString(angles) +
                    '}';
        }
    }
}
