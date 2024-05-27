import java.util.Scanner;

public class Labo2Task02 {
    public static void main(String[] args) {
        complex z1;
        complex z2;

//        数値の入力
        System.out.println("Please input real and imaginary part of the first complex number. ex:2.0 3.0");
        Scanner scanner = new Scanner(System.in);
        double real1 = scanner.nextDouble();
        double imag1 = scanner.nextDouble();
        z1 = new complex(real1, imag1);
        System.out.println("Z1 = "+z1);

        System.out.println("Please input real and imaginary part of the second complex number. ex:2.0 3.0");
        double real2 = scanner.nextDouble();
        double imag2 = scanner.nextDouble();
        z2 = new complex(real2, imag2);
        System.out.println("Z2 = "+z2);
        System.out.println(z1+"+"+z2+"="+z1.plus(z2));
        System.out.println(z1+"-"+z2+"="+z1.minus(z2));
        System.out.println(z1+"*"+z2+"="+z1.mul(z2));
        System.out.println(z1+"/"+z2+"="+z1.div(z2));


    }
    static class complex{
        public double real;
        public double imag;

        public complex(double real, double imag){
            this.real = real;
            this.imag = imag;
        }
//        絶対値を取得
        public double abs(){
            return Math.sqrt(real*real + imag*imag);
        }
//        複素数の表示方を成型
        public String toString(){
            return "("+real+ "," + imag + ")";
        }
//        足し算
        public complex plus(complex other){
            return new complex(real + other.real, imag+other.imag);
        }
//        引き算
        public complex minus(complex other){
            return new complex(real - other.real, imag - other.imag);
        }
//        掛け算
        public complex mul(complex other){
            return new complex(real*other.real - imag*other.imag, real* other.imag + imag*other.real);
        }
//        割り算
        public complex div(complex other){
            return new complex((real*other.real + imag*other.imag)/(abs()*abs()), (imag*other.real -real*other.imag)/(abs()*abs()));
        }
    }
}
