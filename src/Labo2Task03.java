import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

public class Labo2Task03 {
    public static void main(String[] args) {
        System.out.println("Please input 2 file name.");
//        ファイル名を取得
        Scanner scanner = new Scanner(System.in);
        String fileName1 = scanner.nextLine();
        String fileName2 = scanner.nextLine();
//        ファイルがあるか確認
        try(Scanner scanner_file = new Scanner(new File(fileName1))){
//            画像を読み込む
            BufferedImage image1 = ImageIO.read(new File(fileName1));
            BufferedImage image2 = ImageIO.read(new File(fileName2));
//            画像のサイズを取得
            int width1 = image1.getWidth();
            int height1 = image1.getHeight();
            System.out.println("("+ width1 + "," + height1 + ")");

            int width2 = image2.getWidth();
            int height2 = image2.getHeight();
            System.out.println("("+ width2 + "," + height2 + ")");
            //        TODO: 画像の距離を出す
            ImageHistogram histogram1 = new ImageHistogram();
            ImageHistogram histogram2 = new ImageHistogram();

            histogram1.addImage(image1);
            histogram2.addImage(image2);

            double distance = histogram1.distance(histogram2);
            System.out.println("Distance: " + distance);

//            System.out.println();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    static class ImageHistogram{
        public int[] counts = new int[64];

        public void addRGB(int rgb){
            Color color = new Color(rgb);
            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();

            int index = 16* (red / 64) + 4 * (green / 64) + (blue / 64);
            counts[index]++;
        }

        public void addImage(BufferedImage image) {
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    int rgb = image.getRGB(x, y);
                    addRGB(rgb);
                }
            }
        }

        public double distance(ImageHistogram other){
            double sumcounts1 = 0;
            double sumcounts2 = 0;
            for (int i = 0; i < counts.length; i++){
                sumcounts1 += counts[i] ;
                sumcounts2 += other.counts[i];
            }
            double[] p = new double[64];
            double[] q = new double[64];

            double distance = 0;

            for (int i = 0; i < 64; i++){
                p[i] = counts[i] / sumcounts1;
                q[i] = other.counts[i] / sumcounts2;


                distance += Math.abs(p[i] - q[i]);
//                distance += p[i];
            }
            return distance;
        }
    }
}
