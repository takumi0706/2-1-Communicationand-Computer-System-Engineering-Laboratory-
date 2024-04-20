import java.io.File;
import java.util.Scanner;

public class Labo2Task04 {
    public static void main(String[] args) {
//        ファイルネームを取得
        Scanner scanner = new Scanner(System.in);
//        取得したファイルネームを変数に代入
        String fileName = scanner.nextLine();
//        ファイルがあるか確認
        try(Scanner scanner_file = new Scanner(new File(fileName))){
//         ファイルの中身を取得
            while(scanner_file.hasNextLine()){
            String statement = scanner_file.nextLine();
            Node root = new Node();
            root.makeTree(statement);
            System.out.println(root.calculate());
            }
        }catch (Exception e){
            System.out.println(e);
        }

//
//        Node root = new Node();
//        root.makeTree("set(x2,2)");
////TODO:        calculateがintの関数だから小数をどう処理すればいいのかわからない。
//
//        System.out.println(root.calculate());
//        String test = "0123(456";
//        System.out.println(test.indexOf("("));
    }

    static class Node {
        Node left;
        Node right;
        String function;

        public void makeTree(String statement) {
            int sindex = statement.indexOf("(");
            int eindex = statement.lastIndexOf(")");

//            System.out.println(sindex+"+"+eindex);
            int mindex = 0;

            if (sindex == -1 && eindex == -1) {
                function = statement;
                left = null;
                right = null;

            } else {
                int counts = 0;
                for (int i = 0; i < statement.length(); i++) {
                    if (statement.charAt(i) == '(') {
                        counts++;
                    } else if (statement.charAt(i) == ')') {
                        counts--;
                    } else if (statement.charAt(i) == ',' && counts == 1) {
                        mindex = i;
                        break;
                    }
                }

                function = statement.substring(0, sindex);
                function = function.trim();

                left = new Node();
                right = new Node();
                left.makeTree(statement.substring(sindex + 1, mindex));
                right.makeTree(statement.substring(mindex + 1, eindex));
            }
        }

        public int calculate() {
            if (left == null && right == null) {
                if(function.startsWith("x")){
                    return x[Integer.parseInt(function.substring(1))];
                }else {
                    return Integer.parseInt(function);
                }
            } else {
                int leftValue = left.calculate();
                int rightValue = right.calculate();

                if (function.equals("plus")) {
                    return leftValue + rightValue;
                } else if (function.equals("minus")) {
                    return leftValue - rightValue;
                } else if (function.equals("mul")) {
                    return leftValue * rightValue;
                } else if (function.equals("div")) {
                    return leftValue / rightValue;
                } else if (function.equals("mod")) {
                    return leftValue % rightValue;
                } else if (function.equals("set")) {
//              leftValueにはx(数字)が入ってる。leftValueをｘを取り除いて数字だけにして配列用の数字にしなければならない。
                    return x[Integer.parseInt(left.function.substring(1))]=rightValue;
                }
            }
            return 0;
        }
        public static int[] x = new int[10000];
    }
}