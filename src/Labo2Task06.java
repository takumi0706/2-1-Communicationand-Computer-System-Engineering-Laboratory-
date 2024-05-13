import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Labo2Task06 {

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
            return calculateForLeaf();
        }else {
            if (function.equals("set")) {
                hm.put(left.function, right.calculate());
                return right.calculate();
            }
            MyVal v_left = left.calculate();
            MyVal v_right = right.calculate();

            if (function.equals("plus")) {
                if (v_left instanceof MyDouble || v_right instanceof MyDouble) {
                    return new MyDouble(v_left.toDouble() + v_right.toDouble());
                } else {
                    return new MyInt(v_left.toInt() + v_right.toInt());
                }
            } else if (function.equals("minus")) {
                if (v_left instanceof MyDouble || v_right instanceof MyDouble) {
                    return new MyDouble(v_left.toDouble() - v_right.toDouble());
                } else {
                    return new MyInt(v_left.toInt() - v_right.toInt());
                }
            } else if (function.equals("mul")) {
                if (v_left instanceof MyDouble || v_right instanceof MyDouble) {
                    return new MyDouble(v_left.toDouble() * v_right.toDouble());
                } else {
                    return new MyInt(v_left.toInt() * v_right.toInt());
                }
            } else if (function.equals("div")) {
                if (v_left instanceof MyDouble || v_right instanceof MyDouble) {
                    return new MyDouble(v_left.toDouble() / v_right.toDouble());
                } else {
                    return new MyInt(v_left.toInt() / v_right.toInt());
                }
            } else if (function.equals("mod")) {
                return new MyInt(v_left.toInt() % v_right.toInt());
            } else if (function.equals("join")) {
                return new MyString(v_left.toString() + v_right.toString());
            } else if (function.equals("set")) {
                hm.put(left.function, v_right);
                return v_right;
            } else if (function.equals("print")) {
                System.out.println(v_left.toString());
                return v_left;
            }
        }
        return null;
    }

    private MyVal calculateForLeaf(){
        if(function.startsWith("$")){
            return hm.get(function);
        }else if(function.contains("\"")){
            return new MyString(function.replace("\"", ""));
        }else if(function.contains(".")){
            return new MyDouble(Double.parseDouble(function));
        }else{
            return new MyInt(Integer.parseInt(function));
        }
    }

    private static HashMap<String, MyVal> hm = new HashMap<>();
}

abstract class MyVal{
    public abstract int toInt();

    public abstract String toString();

    public abstract double toDouble();
}

class MyInt extends MyVal {
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

class MyDouble extends MyVal {
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

class MyString extends MyVal {
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

