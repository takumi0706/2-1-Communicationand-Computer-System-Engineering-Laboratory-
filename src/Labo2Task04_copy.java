
import java.io.File;
import java.util.Scanner;

public class Labo2Task04_copy {

    public static void main(String[] args) {

//        Node root = new Node();
//        root.makeTree("plus(mul(4,5),minus(2,div(3,2)))");
//        System.out.println(root.caluculate());

//        Node root = new Node();
//        root.makeTree("set(x2,2)");
//        System.out.println(root.caluculate());

        System.out.println("Please input file name.");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();

//            ファイルがあるか確認
        try(Scanner scanner_file = new Scanner(new File(fileName))){
//            ファイルの中身を取得
            while(scanner_file.hasNextLine()){
                String statement = scanner_file.nextLine();
                Node root = new Node();
                root.makeTree(statement);
                System.out.println(root.caluculate());
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    static class Node {
        Node left;
        Node right;
        String function;

        public void makeTree(String statement) {
            int sindex = statement.indexOf("(");
            int eindex = statement.lastIndexOf(")");

//            System.out.println(sindex + "+" + eindex);

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

                function = statement.substring(0 ,sindex);
                function = function.trim();

                left = new Node();
                right = new Node();

                left.makeTree(statement.substring(sindex + 1, mindex));
                right.makeTree(statement.substring(mindex + 1, eindex));
            }
        }

        public int caluculate(){
            if(left == null && right == null){
                if(function.startsWith("x")){
                    return x[Integer.parseInt(function.substring(1))];
                }else {
                    return Integer.parseInt(function);
                }
            }else{
                int leftValue = left.caluculate();
                int rightValue = right.caluculate();

                if(function.equals("plus")){
                    return leftValue+ rightValue;

                }else if(function.equals("minus")){
                    return leftValue- rightValue;

                }else if(function.equals("mul")){
                    return leftValue * rightValue;

                }else if(function.equals("div")){
                    return leftValue / rightValue;

                }else  if(function.equals("mod")){
                    return leftValue % rightValue;

                }else if (function.equals("set")){
                    return x[Integer.parseInt(left.function.substring(1))] = rightValue;
                }
            }
            return 0;
        }

        public static int[] x = new int[10000];
    }
}
