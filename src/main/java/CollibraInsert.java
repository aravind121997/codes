import com.google.gson.JsonArray;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CollibraInsert {
    public static void main(String[] args) {

        CollibraInsert operation= new CollibraInsert();
//        try {
//            operation.MyGETRequest("t_tx_unified_project_ref","rdip_ingest_ts");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            operation.POSTRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

        public static void MyGETRequest (String table,String column) throws IOException {
            URL urlForGetRequest = new URL("https://datacatalog-uat.gsk.com/rest/2.0/assets?offset=0&limit=0&name="+table+"%20%3E%20"+column+"&nameMatchMode=ANYWHERE&domainId=5b13175a-9d3b-42cd-8cc7-147dab2b83a9&typeInheritance=true&excludeMeta=true&sortField=NAME&sortOrder=ASC");
            String readLine = null;

            HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
            conection.setRequestMethod("GET");
            //conection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample here
            String userpass = "testuser" + ":" + "Testing";
            String tempstring ="Basic "+ new String(Base64.getEncoder().encode(userpass.getBytes(StandardCharsets.UTF_8)));
            conection.setRequestProperty("Content-Type", "application/json");
            conection.setRequestProperty("Authorization",tempstring);
            int responseCode = conection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conection.getInputStream()));
                StringBuffer response = new StringBuffer();
                while ((readLine = in.readLine()) != null) {
                    response.append(readLine);
                }
                in.close();
                // print result
                System.out.println("JSON String Result " + response.toString());
                //GetAndPost.POSTRequest(response.toString());
                JSONObject jsonObj = new JSONObject(response.toString());

                String json =jsonObj.get("results").toString();

                HashMap<String, Object> retMap = new HashMap<String, Object>();
                if (json != null) {
                    JSONObject jsonObject = new JSONObject(json);
                    retMap = toMap(jsonObject);
                }
               // List<HashMap<String,Object>> temp =toList(retMap.get("results"));
                System.out.println(retMap);

                String tempres =jsonObj.get("results").toString();




            } else {
                System.out.println("GET NOT WORKED");
                System.out.println(responseCode);
            }
        }
        public static void POSTRequest () throws IOException {
            final  String PATCH_PARAMS = "";

            URL obj = new URL("https://datacatalog-uat.gsk.com/rest/catalog/1.0/profiling/columns");
            HttpURLConnection patchConnection = (HttpURLConnection) obj.openConnection();
            patchConnection.setRequestMethod("POST");
            patchConnection.setRequestProperty("X-HTTP-Method-Override", "PATCH");
           // patchConnection.setRequestMethod("PATCH");
            String userpass = "testuser" + ":" + "Testing";
            patchConnection.setRequestProperty("Content-Type", "application/json");
            String tempstring ="Basic "+ new String(Base64.getEncoder().encode(userpass.getBytes(StandardCharsets.UTF_8)));
            patchConnection.setRequestProperty("Authorization",tempstring);
            patchConnection.setDoOutput(true);
            OutputStream os = patchConnection.getOutputStream();
            os.write(PATCH_PARAMS.getBytes());
            os.flush();
            os.close();
            int responseCode = patchConnection.getResponseCode();
            System.out.println("POST Response Code :  " + responseCode);
            System.out.println("POST Response Message : " + patchConnection.getResponseMessage());
            if (responseCode == 200) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        patchConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println(response.toString());
            } else {
                System.out.println("POST NOT WORKED");
            }
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





}
