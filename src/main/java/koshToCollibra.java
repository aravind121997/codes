import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class koshToCollibra {
    public static void main(String[] args) throws Exception {
        TableToList list = new TableToList();
        List<Map<String, Object>> Res = list.list("select cp.*,tm.table_name from koshv2.column_profile as cp join koshv2.table_metadata as tm on tm.datastore_id =cp.datastore_id and " +
                "cp.component_id = tm.component_id and cp.table_id = tm.table_id " +
                "where tm.table_name ='t_tx_unified_project_ref' and cp.valid_to_ts ='9999-12-31' limit 1");

        //System.out.println(Res);


        List<Map<String, Object>> final_list = new ArrayList<>();
        ListToPayload payload = new ListToPayload();
        for (int row = 0; row < Res.size(); row++) {
            String temp = payload.profilecolumn(Res.get(row)).toString();
            System.out.println(temp);
//            try {
//                String temp = payload.profilecolumn(Res.get(row)).toString();
//                System.out.println(temp);
//                patch(temp);
//            }
//            catch (Exception e)
//            {
//                System.out.println( "FAILED" + Res.get(row).get("column_name").toString());
//
//                continue;
//            }

        }
    }

    public static void patch(String temp) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPatch patch = new HttpPatch("https://datacatalog-uat.gsk.com/rest/catalog/1.0/profiling/columns");
        patch.addHeader("content-type", "application/json;charset=utf-8");
        String userpass = "testuser" + ":" + "Testing";
        patch.addHeader("Authorization", "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes(StandardCharsets.UTF_8))));


        //String json = null;
        HttpResponse response = null;
        try {
//            json = objectMapper.writeValue(temp);
            StringEntity se = new StringEntity(temp);
            //LOGGER.info("user api input json ==>   " + json);
            patch.setEntity(se);
            response = client.execute(patch);

            System.out.println(response.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}