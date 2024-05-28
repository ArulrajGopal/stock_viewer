package pack1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONObject;


public class config {

    public static void main(String[] args) throws IOException {

    String filePath = "stock_viewer/src/main/java/pack1/config.json"; 
    String content = new String(Files.readAllBytes(Paths.get(filePath)));
    JSONObject jsonObject = new JSONObject(content);

    System.out.println(jsonObject);
    }
}


