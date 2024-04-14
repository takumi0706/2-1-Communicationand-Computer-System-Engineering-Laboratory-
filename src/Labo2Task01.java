import java.nio.file.Path;
import java.util.Scanner;

public class Labo2Task01 {
    public static void main(String[] args) {
//        ファイル名を取得
        System.out.println("Please input file name.");
        String FileName = getFileName();


        SnowRecord[] records = new SnowRecord[10000];
        int index = 0;

        try(Scanner scanner_file = new Scanner(Path.of(FileName),"UTF-8")){
//            ファイルの中身を一行ずつ読み込む
//            続くまで取得
            while(scanner_file.hasNextLine()){
//                一行ずつ取得
                String date = scanner_file.next();
                int amount = scanner_file.nextInt();

                records[index] = new SnowRecord(date, amount);
                index++;
            }
        }catch (Exception e){
            System.out.println(e);
        }

        for (SnowRecord record : records){
                if(record != null){
                    if(record.match(2018, 1)) {
                        System.out.println(record);
                    }
                }else {
                    break;
                }
            }
        }



//    コンソールに入力したものを返す
    public static String getFileName(){
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        return str;
    }
//
    static class SnowRecord{
        public String date;
        public int amount;

        public SnowRecord(String date, int amount){
            this.date = date;
            this.amount = amount;
        }
//        戻り値が日付,積雪量の文字列
        public String toString(){
            return date + "," + amount;
        }

        public boolean match(int year, int month){
            return date.startsWith(year + "/" + month);
        }
    }
}


