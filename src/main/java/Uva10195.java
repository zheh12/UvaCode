import java.util.Scanner;

/**
 * Created by yang on 2015/9/10.
 */
public class Uva10195 {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        while(scanner.hasNext()){
            Triangle triangle = Triangle.readTriangle();
            double area = triangle.area();
            double c = triangle.cer();

            double ans = area*2/c;
            if(Double.isNaN(ans)){
                ans = 0;
            }
            System.out.println(String.format("The radius of the round table is: %.3f",ans));
        }
    }

    private static class Triangle{
        private double[] lines = new double[3];
        private double[] angles = new double[3];

        public Triangle(double[] lines){
            this.lines = lines;

            for(int i=0;i<3;i++){
                double line1 = lines[i];
                double line2 = lines[(i+1)%3];
                double line3 = lines[(i+2)%3];

                angles[i] = angle(line1,line2,line3);
            }
        }

        public double angle(double line1,double line2,double line3){
            double cosValue = (line1*line1 + line2*line2 - line3*line3) / (2*line1 * line2);

            return angle(cosValue);
        }

        public double angle(double cosValue){
            cosValue = Math.max(-1,cosValue);
            cosValue = Math.min(1,cosValue);

            return Math.acos(cosValue);
        }

        public double area(){
            return lines[0]*lines[1]*Math.sin(angles[0])/2;
        }

        public double cer(){
            return lines[0] + lines[1] + lines[2];
        }

        public static Triangle readTriangle(){
            return new Triangle(new double[]{scanner.nextDouble(),scanner.nextDouble(),scanner.nextDouble()});
        }
    }
}
