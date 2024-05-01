package pack1;

// variables = ["PHARMA","ENERGY","FMCG","FIN%20SERVICE"]

public class test1 {

    public static void main(String[] args) {
        utils obj = new utils();
        String message = obj.fetchStockDetails("FIN%20SERVICE");
        System.out.println(message);

    }

    
    
}
