import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class test {

    public static void main(String[] args) {

        System.out.println("Please input file name.");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();

        try(Scanner scanner_file = new Scanner(new File(fileName))){
            while(scanner_file.hasNextLine()){
                String statement = scanner_file.nextLine();
                Node root = new Node();
                root.makeTree(statement);
                System.out.println(root.calculate());
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    static class Node {
        Node left;
        Node right;
        String function;

        private void makeTree(String statement) {
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

        private MyVal calculate() {
            MyVal leftVal = null;
            MyVal rightVal = null;

            if (left != null) {
                leftVal = left.calculate();
            }
            if (right != null) {
                rightVal = right.calculate();
            }

            if (leftVal == null && rightVal == null) {
                if (function.startsWith("$")) {
                    if (hm.containsKey(function)) {
                        return hm.get(function);
                    } else {
                        return null;
                    }
                } else {
                    return MyVal.readVal(function);
                }
            } else {
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

                    }else if (function.equals("set")) {
                        hm.put(left.function, new MyDouble(rightValue));
                        return new MyDouble(rightValue);
                    }
                }else if (leftVal instanceof MyInt || rightVal instanceof MyInt) {
                    int leftValue = leftVal.toInt();
                    int rightValue = rightVal.toInt();

                    if (function.equals("plus")) {
                        return new MyInt(leftValue + rightValue);

                    } else if (function.equals("minus")) {
                        return new MyInt (leftValue - rightValue);

                    } else if (function.equals("mul")) {
                        return new MyInt(leftValue * rightValue);

                    } else if (function.equals("div")) {
                        return new MyInt(leftValue / rightValue);

                    }else if (function.equals("set")) {
                        hm.put(left.function, new MyInt(rightValue));
                        return new MyInt(rightValue);
                    }
                }else if(function.equals("mod")){
                    int leftValue = leftVal.toInt();
                    int rightValue = rightVal.toInt();
                    return new MyInt(leftValue % rightValue);
                }else if(function.equals("join")){
                    String leftValue = leftVal.toString();
                    String rightValue = rightVal.toString();
                    return new MyString(leftValue + rightValue);
                }else if (function.equals("print")) {
                    System.out.println(leftVal.toString());
                    return leftVal;
                }
            }
            return null;
        }

        private static HashMap<String, MyVal> hm = new HashMap<>();
    }

    abstract static class MyVal{
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

    static class MyInt extends MyVal{
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
            return (double)v;
        }
    }

    static class MyDouble extends MyVal{
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

    static class MyString extends MyVal{
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

}
