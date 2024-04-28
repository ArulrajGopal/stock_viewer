package pack1;

public class test1 {
    public static void main(String[] args) {
        utils obj = new utils();
        String message = obj.fetchStockDetails("PHARMA");
        Object json_obj = obj.StrToJsonConvert(message);
        System.out.println(message);
        System.out.println(json_obj);
    }
    
}
