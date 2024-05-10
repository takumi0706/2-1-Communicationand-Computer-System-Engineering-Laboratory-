
import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Labo2Task05 {

    public static void main(String[] args) {

        String fileName = args[0];

//            ファイルがあるか確認
        try (Scanner scanner_file = new Scanner(new File(fileName))) {
            Node root = new Node();
            //            ファイルの中身を取得
            while (scanner_file.hasNextLine()) {
                String statement = scanner_file.nextLine();
                root.makeTree(statement);
                root.calculate();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
    class Node {
        Node left;
        Node right;
        String function;

        public void makeTree(String statement) {
            int sindex = statement.indexOf("(");
            int eindex = statement.lastIndexOf(")");

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

        public MyVal calculate() {
            if (left == null || right == null) {
                if (function.startsWith("$")) {
                        return hm.get(function);
                } else {
                    return MyVal.readVal(function);
                }
            }else {
                if(function.equals("set")){
                    hm.put(left.function,right.calculate());
                    return right.calculate();
                }
                MyVal leftVal = left.calculate();
                MyVal rightVal = right.calculate();

                if (leftVal instanceof MyDouble || rightVal instanceof MyDouble) {
                    double leftValue = leftVal.toDouble();
                    double rightValue = rightVal.toDouble();

                    if (function.equals("plus")) {
                        return new MyDouble(leftValue + rightValue);

                    } else if (function.equals("minus")) {
                        return new MyDouble(leftValue - rightValue);

                    } else if (function.equals("mul")) {
                        return new MyDouble(leftValue * rightValue);

                    } else if (function.equals("div")) {
                        return new MyDouble(leftValue / rightValue);
                    }else if (function.equals("print")) {
                        System.out.println(leftVal.toString());
                        return leftVal;
                    }else if(function.equals("mod")) {
                        return new MyInt((int)(leftValue % rightValue));
                    }else if(function.equals("set")){
                        hm.put(left.function,rightVal);
                        return rightVal;
                    }else if(function.equals("join")){
                        return new MyString(""+leftValue + rightValue);
                    }
                }else if (leftVal instanceof MyInt && rightVal instanceof MyInt) {
                    int leftValue = leftVal.toInt();
                    int rightValue = rightVal.toInt();

                    if (function.equals("plus")) {
                        return new MyInt(leftValue + rightValue);

                    } else if (function.equals("minus")) {
                        return new MyInt(leftValue - rightValue);

                    } else if (function.equals("mul")) {
                        return new MyInt(leftValue * rightValue);

                    } else if (function.equals("div")) {
                        return new MyInt(leftValue / rightValue);
                    } else if (function.equals("print")) {
                        System.out.println(leftVal.toString());
                        return leftVal;
                    } else if (function.equals("mod")) {
                        return new MyInt(leftValue % rightValue);
                    } else if (function.equals("set")) {
                        hm.put(left.function, rightVal);
                        return rightVal;
                    } else if (function.equals("join")) {
                        return new MyString("" + (leftValue + rightValue));
                    }

                }else if (function.equals("print")) {
                    System.out.println(leftVal.toString());
                    return leftVal;
                }else if (function.equals("set")) {
                    hm.put(left.function, rightVal);
                    return rightVal;
                } else if(function.equals("join")){
                    String leftValue = leftVal.toString();
                    String rightValue = rightVal.toString();
                    return new MyString(leftValue + rightValue);
                }
            }
            return null;
        }

        private static HashMap<String, MyVal> hm = new HashMap<>();
    }

    abstract class MyVal{
        public abstract int toInt();

        public abstract String toString();

        public abstract double toDouble();

        public static MyVal readVal(String str){
            if(str.contains("\"")){
                return new MyString(str.replace("\"", ""));
            }else if(str.contains(".")){
                return new MyDouble(Double.parseDouble(str));

            }else{
                return new MyInt(Integer.parseInt(str));
            }
        }
    }

    class MyInt extends MyVal{
        private int v;
        public MyInt(int v){
            this.v = v;
        }
        @Override
        public int toInt(){
            return v;
        }
        @Override
        public String toString(){
            return Integer.toString(v);
        }
        @Override
        public double toDouble(){
            return v;
        }
    }

    class MyDouble extends MyVal{
        private double v;
        public MyDouble(double v){
            this.v = v;
        }
        @Override
        public int toInt(){
            return (int)v;
        }
        @Override
        public String toString(){
            return Double.toString(v);
        }
        @Override
        public double toDouble(){
            return v;
        }
    }

    class MyString extends MyVal{
        private String v;
        public MyString(String v){
            this.v = v;
        }
        @Override
        public int toInt(){
            return 0;
        }
        @Override
        public String toString(){
            return v;
        }
        @Override
        public double toDouble(){
            return 0;
        }
    }

