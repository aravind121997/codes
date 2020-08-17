import org.json.JSONException;
import org.json.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.*;

public class getting {
    public String getId(String table, String column) {
        String url = "https://datacatalog-uat.gsk.com/rest/2.0/assets?offset=0&limit=0&name=" +
                table +
                "%20%3E%20" +
                column +
                "&nameMatchMode=ANYWHERE&domainId=5b13175a-9d3b-42cd-8cc7-147dab2b83a9&typeInheritance=true&excludeMeta=true&sortField=NAME&sortOrder=ASC";
        String userpass = "testuser" + ":" + "Testing";
        //System.out.println(url);
        String tempstring = new String(Base64.getEncoder().encode(userpass.getBytes(StandardCharsets.UTF_8)));
        String content = "application/json";
        HttpResponse<JsonNode> fin = getRequest(url, tempstring, content);
        //System.out.println(fin.getStatus());
        //System.out.println(fin);
        JSONObject temp = fin.getBody().getObject();
        JSONArray aray = new JSONArray(temp.get("results").toString());
       // System.out.println(temp.toString());
        String array = aray.get(0).toString();
        HashMap<String, Object> retMap = jsonToMap(array);
        //System.out.println(retMap);
        String id = retMap.get("id").toString();
       // System.out.println(" \n id \n" + id);
        //System.out.println(temp.toString());
        return id;

    }
    private  static HttpResponse<JsonNode> getRequest (String url, String basicAuth, String contentType){
        //makeClient();
        HttpResponse<JsonNode> responseDataGet = null;
        //String responseDataGet = null;
        try {
            responseDataGet = Unirest.get(url)
                    .header("authorization", "Basic " + basicAuth)
                    .header("content-type", contentType)
                    .asJson();
            JSONObject responseSourceCreate = responseDataGet.getBody().getObject();
            //logger.info("got the response for respective string  " + url);
        } catch (UnirestException e) {
            //logger.error("error in retrieving the getRequest for the string " + url + e.getMessage());
            e.printStackTrace();
        }
        return responseDataGet;
    }
    public static HashMap<String, Object> toMap(JSONObject object) throws JSONException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            if (value == JSONObject.NULL)
                map.put(key, null);
            else
                map.put(key, value);
        }
        return map;
    }
    public static ArrayList<Object> toList(JSONArray array) throws JSONException {
        ArrayList<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }
    public static HashMap<String, Object> jsonToMap(String json) throws JSONException {
        HashMap<String, Object> retMap = new HashMap<String, Object>();
        if (json != null) {
            JSONObject jsonObject = new JSONObject(json);
            retMap = toMap(jsonObject);
        }
        return retMap;
    }
}

