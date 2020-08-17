import com.google.gson.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.Header;
import org.json.JSONException;
import org.json.JSONObject;
import sun.rmi.runtime.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Testquery {
    public static void main(String[] args) throws UnsupportedEncodingException {
   String temp ="{\"columnProfiles\":[{\"quantiles\":{\"percentile1\":\"\",\"percentile99\":\"\",\"median\":\"39538.0\",\"decile9\":\"\",\"quartile3\":\"\",\"percentile95\":\"\",\"quartile1\":\"\",\"percentile5\":\"\",\"decile1\":\"\"},\"technicalDataType\":\"INT\",\"counts\":{\"mode\":\"\",\"distinctValuesCount\":79078,\"rowCount\":79078,\"emptyValuesCount\":0},\"dataType\":\"Wholenumber\",\"categoricalmetadata\":{\"categorical\":\"\",\"categoriesFrequencies\":{}},\"columnPosition\":12,\"assetIdentifier\":{\"domainName\":\"Gene_Domain_Metadata_Dictionary\",\"assetName\":\"t_gene_arrayserver_t_blueprint_b37_transcriptannotation>transcriptindex\",\"communityName\":\"Sandbox R&D Tech\",\"id\":\"94abe53d-4a19-43bf-a9b4-a9f1245d7f60\",\"domainId\":\"5b13175a-9d3b-42cd-8cc7-147dab2b83a9\"},\"databaseMetadata\":{\"charOctetLength\":\"\",\"isGenerated\":\"\",\"isAutoIncremented\":\"\",\"defaultValue\":\"\",\"columnSize\":79078,\"isNullable\":\"\",\"numberOfDecimalDigits\":79078,\"isPrimaryKey\":\"\",\"primaryKeyName\":\"\"},\"samples\":{\"samples\":[\"3667\",\"3668\",\"3669\",\"3680\",\"3681\",\"3682\",\"3683\",\"3684\",\"3674\",\"3675\"]},\"distributions\":{\"histogram\":[{\"upperBound\":\"\",\"upperBoundInISODateFormat\":\"\",\"lowerBound\":\"\",\"lowerBoundInISODateFormat\":\"\",\"frequency\":\"\"}],\"distributionDensityEstimation\":[{\"x\":\"\",\"y\":\"\"}]},\"columnName\":\"transcriptindex\",\"statistics\":{\"variance\":\"\",\"mean\":\"\",\"maximum\":\"79077\",\"minimum\":\"0\",\"standardDeviation\":\"22827.85229166336\"}}]}";
//
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        JsonParser jp = new JsonParser();
//        JsonElement je = jp.parse(temp);
//        String prettyJsonString = gson.toJson(je);
//        System.out.println(prettyJsonString);
//        JsonParser parser = new JsonParser();
//        JsonObject json = parser.parse(temp).getAsJsonObject();
//
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String prettyJson = gson.toJson(json);
//        System.out.println(prettyJson);
//        StringEntity jstemp =new StringEntity(temp);
//        System.out.println(jstemp);
        //JSONUtils fin = new JSONUtils();
        //System.out.println(fin.isJSON(temp));



    }



    public static String executePost(String targetURL, String requestJSON) {
        HttpURLConnection connection = null;
        InputStream is = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("X-HTTP-Method-Override", "PATCH");
            // patchConnection.setRequestMethod("PATCH");
            String userpass = "testuser" + ":" + "Testing";
            connection.setRequestProperty("Content-Type", "application/json");
            //TODO may be prod or preprod api key
//            if (apikey.equals(Constants.APIKEY_PREPROD)) {
//                connection.setRequestProperty("Authorization", Constants.APIKEY_PREPROD);
//            }
//            if (apikey.equals(Constants.APIKEY_PROD)){
//                connection.setRequestProperty("Authorization", Constants.APIKEY_PROD);
//            }
            String tempstring ="Basic "+ new String(Base64.getEncoder().encode(userpass.getBytes(StandardCharsets.UTF_8)));
            connection.setRequestProperty("Authorization",tempstring);
            connection.setRequestProperty("Content-Length", Integer.toString(requestJSON.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            System.out.println(requestJSON);
            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream());
            wr.writeBytes(requestJSON);
            wr.close();

            //Get Response

            try {
                is = connection.getInputStream();
            } catch (IOException ioe) {
                if (connection instanceof HttpURLConnection) {
                    HttpURLConnection httpConn = (HttpURLConnection) connection;
                    int statusCode = httpConn.getResponseCode();
                    if (statusCode != 200) {
                        is = httpConn.getErrorStream();
                    }
                }
            }

            BufferedReader rd = new BufferedReader(new InputStreamReader(is));


            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
//        } finally {
//            if (connection != null) {
//                connection.disconnect();
//            }
        }


}
