package ddl;

import java.io.IOException;


public class test1 {

    public static void main (String[] args ) throws IOException{

        access_config_for_ddl obj = new access_config_for_ddl();
        System.out.println(obj.get_topics_list());
    }
    
}
