import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.HashMap;
import java.util.Random;

public class Labo2Task07 {
    public static Random rand = new Random();
    static HashMap<Integer, Color> hm = new HashMap<>();

    public static void main(String[] args) {
        var image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);

        for(int x = 0; x < 1000; x++){
            for(int y = 0; y < 1000; y++){
                Complex c = Complex.getComplexAt(x, y);
                int n = Complex.mandelCount(c);
                Color color = nToColor(n);
                image.setRGB(x, y, color.getRGB());
            }
        }

        File file = new File("mandelbrot.png");
        try{
            ImageIO.write(image, "png", file);
            System.out.println("Success");
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public static Color nToColor(int n){
        if(n >= 300){
            return Color.BLACK;
        }else{
            if(!hm.containsKey(n)) {
                int r = rand.nextInt(256);
                int g = rand.nextInt(256);
                int b = rand.nextInt(256);

                Color c = new Color(r, g, b);
                hm.put(n, c);
            }
                return hm.get(n);
        }
    }
}

class Complex {
    private static final double realMin = -1.1;
    private static final double realMax = 0.000000002;
    private static final double imagMin = -1.3;
    private static final double imagMax = 0.0000000013;

    public double real;
    public double imag;

    public Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    //        絶対値を取得
    public double abs() {
        return Math.sqrt(real * real + imag * imag);
    }

    //        複素数の表示方を成型
    public String toString() {
        return "(" + real + "," + imag + ")";
    }

    //        足し算
    public Complex plus(Complex other) {
        return new Complex(real + other.real, imag + other.imag);
    }

    //        引き算
    public Complex minus(Complex other) {
        return new Complex(real - other.real, imag - other.imag);
    }

    //        掛け算
    public Complex mul(Complex other) {
        return new Complex(real * other.real - imag * other.imag, real * other.imag + imag * other.real);
    }

    //        割り算
    public Complex div(Complex other) {
        return new Complex((real * other.real + imag * other.imag) / (abs() * abs()), (imag * other.real - real * other.imag) / (abs() * abs()));
    }

    public static Complex getComplexAt(int x, int y) {
        double imag = imagMin + (imagMax - imagMin) * y / 999;
        double real = realMin + (realMax - realMin) * x / 999;
        return new Complex(real, imag);
    }

    public static int mandelCount(Complex c) {
        Complex z = new Complex(0, 0);
        for(int i = 2; i <= 300; i++){
            z = z.mul(z).plus(c);
            if(z.abs() > 2){
                return i;
            }
        }
        return 300;
    }
}