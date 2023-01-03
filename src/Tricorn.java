import java.awt.geom.Rectangle2D;

public class Tricorn extends FractalGenerator{
    public static final int MAX_ITERATIONS = 2000;
    public void getInitialRange (Rectangle2D.Double a){
        a.x = -2;
        a.y = -2;
        a.width = 4;
        a.height = 4;
    }
    public int numIterations(double a, double b){
        int iter = 0;
        double zr = 0;
        double zi = 0;
        double zr2, zi2;
        while (iter<MAX_ITERATIONS && zr*zr+zi*zi<4){
            zr2 = zr * zr - zi*zi + a; // Z^2= Zre^2+2*Zre*Zim+Zim^2, real: Zre^2+(Zim*i)^2= Zre^2-Zim^2
            zi2 = -(2 * zr * zi) + b;     // imaginary: -2*Zre*Zim
            zr = zr2;
            zi = zi2;
            iter++;
        }
        if (iter == MAX_ITERATIONS){return -1;}
        return iter;
    }
}