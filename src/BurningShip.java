import java.awt.geom.Rectangle2D;

public class BurningShip extends FractalGenerator{
    public static final int MAX_ITERATIONS = 2000;
    public void getInitialRange (Rectangle2D.Double a){
        a.x = -2;
        a.y = -2.5;
        a.width = 4;
        a.height = 4;
    }
    public int numIterations(double a, double b){
        int iter = 0;
        double zr = 0;
        double zi = 0;
        double zr2;
        while (iter<MAX_ITERATIONS && zr*zr+zi*zi<4){
            zr2 = zr * zr - zi*zi + a;
            zr = zr2;
            zi = Math.abs(2 * zr * zi) + b;
            iter++;
        }
        if (iter == MAX_ITERATIONS){return -1;}
        return iter;
    }
}