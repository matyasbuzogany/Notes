package helpers;

import com.sun.net.httpserver.Headers;

import java.util.HashMap;
import java.util.Map;

public class NoteQueryHelper {

    public NoteQueryHelper() {
    }

    public  Map<String, String> queryToMap(String query){
        Map<String, String> result = new HashMap<>();

        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], pair[1]);
            }else{
                result.put(pair[0], "");
            }
        }
        return result;
    }

    public void addCorsHeaders(Headers headers) {
        headers.add("Access-Control-Allow-Headers","x-prototype-version,x-requested-with");
        headers.add("Access-Control-Allow-Methods","GET,POST");
        headers.add("Access-Control-Allow-Origin","*");
    }
}
