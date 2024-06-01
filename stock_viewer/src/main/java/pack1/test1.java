package pack1;

import org.json.JSONArray;

public class test1 {
    static String message = "[{\"yearLow\":4525,\"identifier\":\"APOLLOHOSPEQN\",\"symbol\":\"APOLLOHOSP\",\"totalTradedVolume\":1382132,\"change\":78.55,\"dayLow\":5743,\"perChange30d\":-1.81,\"yearHigh\":6874.45,\"perChange365d\":21.29,\"previousClose\":5766.45,\"pChange\":1.36,\"totalTradedValue\":8050089620.799999,\"meta\":{\"companyName\":\"Apollo Hospitals Enterprise Limited\",\"industry\":\"MISCELLANEOUS\",\"isin\":\"INE437A01024\"},\"dayHigh\":5959.35,\"open\":5855.95,\"lastPrice\":5845,\"lastUpdateTime\":\"31-May-2024 15:58:43\"},{\"yearLow\":942.3,\"identifier\":\"CIPLAEQN\",\"symbol\":\"CIPLA\",\"totalTradedVolume\":3270743,\"change\":-17.6,\"dayLow\":1440,\"perChange30d\":3.37,\"yearHigh\":1519,\"perChange365d\":50.01,\"previousClose\":1466.6,\"pChange\":-1.2,\"totalTradedValue\":4740941978.5,\"meta\":{\"companyName\":\"Cipla Limited\",\"industry\":\"PHARMACEUTICALS\",\"isin\":\"INE059A01026\"},\"dayHigh\":1472.7,\"open\":1466.95,\"lastPrice\":1449,\"lastUpdateTime\":\"31-May-2024 15:58:47\"},{\"yearLow\":4480.95,\"identifier\":\"DRREDDYEQN\",\"symbol\":\"DRREDDY\",\"totalTradedVolume\":1029318,\"change\":-91.75,\"dayLow\":5752.85,\"perChange30d\":-6.65,\"yearHigh\":6505.9,\"perChange365d\":27.24,\"previousClose\":5873.75,\"pChange\":-1.56,\"totalTradedValue\":5986997267.46,\"meta\":{\"companyName\":\"Dr. Reddy's Laboratories Limited\",\"industry\":\"PHARMACEUTICALS\",\"isin\":\"INE089A08051\"},\"dayHigh\":5904.65,\"open\":5899.95,\"lastPrice\":5782,\"lastUpdateTime\":\"31-May-2024 15:58:42\"},{\"yearLow\":271.9,\"identifier\":\"FORTISEQN\",\"symbol\":\"FORTIS\",\"totalTradedVolume\":2379563,\"change\":18.8,\"dayLow\":451.1,\"perChange30d\":8.33,\"yearHigh\":484.9,\"perChange365d\":70.38,\"previousClose\":457.2,\"pChange\":4.11,\"totalTradedValue\":1118394610,\"meta\":{\"companyName\":\"Fortis Healthcare Limited\",\"industry\":\"MISCELLANEOUS\",\"isin\":\"INE061F13018\"},\"dayHigh\":484.9,\"open\":460,\"lastPrice\":476,\"lastUpdateTime\":\"31-May-2024 15:59:51\"},{\"yearLow\":885.25,\"identifier\":\"RAINBOWEQN\",\"symbol\":\"RAINBOW\",\"totalTradedVolume\":331147,\"change\":69.05,\"dayLow\":1222.1,\"perChange30d\":-5.42,\"yearHigh\":1649,\"perChange365d\":35.51,\"previousClose\":1230.95,\"pChange\":5.61,\"totalTradedValue\":426629925.97999996,\"meta\":{\"companyName\":\"Rainbow Childrens Medicare Limited\",\"industry\":null,\"isin\":\"INE961O01016\"},\"dayHigh\":1312.95,\"open\":1233.4,\"lastPrice\":1300,\"lastUpdateTime\":\"31-May-2024 15:56:44\"}]";


    public static void main(String [] args ) {
        
        JSONArray result_jsonarr = new JSONArray(message);

        if (result_jsonarr.length() != 0){ 
            for (Object element: result_jsonarr){
                System.out.println(element);

                System.out.println("*******************");
    
            }

        }
        else {System.out.println("no extract");}




 
    }
}

