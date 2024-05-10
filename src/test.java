//import java.io.File;
//import java.util.Scanner;
//import java.util.HashMap;
//
//public class test {
//    private static HashMap<String, MyVal> hm = new HashMap<>();
//
//    public static void main(String[] args) {
//        if (args.length < 1) {
//            System.out.println("Please provide a file name as a command line argument.");
//            return;
//        }
//
//        String fileName = args[0];
//
//        try (Scanner scanner_file = new Scanner(new File(fileName))) {
//            Node root = new Node();
//            while (scanner_file.hasNextLine()) {
//                String statement = scanner_file.nextLine();
//                root.makeTree(statement);
//                System.out.println(root.calculate().toString());
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//    }
//}
//        class Node {
//            private Node left;
//            private Node right;
//            private String function;
//
//            public void makeTree(String statement) {
//                int sindex = statement.indexOf("(");
//                int eindex = statement.lastIndexOf(")");
//
//                int mindex = 0;
//
//                if (sindex == -1 && eindex == -1) {
//                    function = statement;
//                    left = null;
//                    right = null;
//                } else {
//                    int counts = 0;
//                    for (int i = 0; i < statement.length(); i++) {
//                        if (statement.charAt(i) == '(') {
//                            counts++;
//                        } else if (statement.charAt(i) == ')' && counts == 1) {
//                            mindex = i;
//                            break;
//                        }
//                    }
//
//                    function = statement.substring(0, sindex).trim();
//                    left = new Node();
//                    right = new Node();
//
//                    left.makeTree(statement.substring(sindex + 1, mindex));
//                    right.makeTree(statement.substring(mindex + 1, eindex));
//                }
//            }
//
//            public  MyVal calculate() {
//                if (left == null && right == null) {
//                    return MyVal.readVal(function);
//                } else {
//                    MyVal leftVal = left.calculate();
//                    MyVal rightVal = right.calculate();
//
//                    switch (function) {
//                        case "plus":
//                            return new MyDouble(leftVal.toDouble() + rightVal.toDouble());
//                        case "minus":
//                            return new MyDouble(leftVal.toDouble() - rightVal.toDouble());
//                        case "mul":
//                            return new MyDouble(leftVal.toDouble() * rightVal.toDouble());
//                        case "div":
//                            if (rightVal.toDouble() != 0) {
//                                return new MyDouble(leftVal.toDouble() / rightVal.toDouble());
//                            } else {
//                                System.out.println("Division by zero error.");
//                                return new MyDouble(0);
//                            }
//                        case "mod":
//                            return new MyInt(leftVal.toInt() % rightVal.toInt());
//                        case "join":
//                            return new MyString(leftVal.toString() + rightVal.toString());
//                        case "print":
//                            System.out.println(leftVal.toString());
//                            return leftVal;
//                        default:
//                            System.out.println("Function not recognized.");
//                            return new MyInt(0);
//                    }
//                }
//            }
//        }
//
//        abstract class MyVal {
//            public abstract int toInt();
//
//            public abstract double toDouble();
//
//            public abstract String toString();
//
//            public static MyVal readVal(String str) {
//                if (str.contains("\"")) {
//                    return new MyString(str.replace("\"", ""));
//                } else if (str.contains(".")) {
//                    return new MyDouble(Double.parseDouble(str));
//                } else {
//                    return new MyInt(Integer.parseInt(str));
//                }
//            }
//        }
//
//        class MyInt extends MyVal {
//            private int value;
//
//            public MyInt(int value) {
//                this.value = value;
//            }
//
//            @Override
//            public int toInt() {
//                return value;
//            }
//
//            @Override
//            public double toDouble() {
//                return (double) value;
//            }
//            @Override
//            public String toString() {
//                return Integer.toString(value);
//            }
//        }
//
//        class MyDouble extends MyVal {
//            private double value;
//
//            public MyDouble(double value) {
//                this.value = value;
//            }
//            @Override
//            public int toInt() {
//                return (int) value;
//            }
//            @Override
//            public double toDouble() {
//                return value;
//            }
//            @Override
//            public String toString() {
//                return Double.toString(value);
//            }
//        }
//        class MyString extends MyVal {
//            private String value;
//
//            public MyString(String value) {
//                this.value = value;
//            }
//
//            @Override
//            public int toInt() {
//                return 0;
//            }
//
//            @Override
//            public double toDouble() {
//                return 0.0;
//            }
//
//            @Override
//            public String toString() {
//                return value;
//            }
//
//        }
//
//
