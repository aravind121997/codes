import com.google.gson.JsonArray;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ListToPayload {
    public JSONObject profilecolumn(Map<String,Object> row)
    {
        TableToList list = new TableToList();
        List<Map<String, Object>> data = list.list("select column_value from koshv2.column_frequency as cf " +
                "where table_id=" + row.get("table_id") +"and column_name =" + "'" + row.get("column_name") + "'" + "limit 10");
        //System.out.println(data);
        List<Object> sampledata = new ArrayList<>();
        for (int Row = 0; Row < data.size(); Row++) {
            sampledata.add(data.get(Row).get("column_value"));
        }
        //System.out.println(sampledata);
        //System.out.println(row);

        JSONObject assetidentifier = new JSONObject();
        JSONObject counts = new JSONObject();
        JSONObject samples = new JSONObject();
        JSONObject statistics = new JSONObject();
        JSONObject databasemetadata = new JSONObject();
        JSONObject categoricalmetadata = new JSONObject();
        JSONObject quantiles = new JSONObject();
        JSONObject distributions = new JSONObject();
        JSONObject columnobject = new JSONObject();
        //JsonArray sample =new JsonArray();
        getting api =new getting();
        String id = api.getId(row.get("table_name").toString(),row.get("column_name").toString());
        assetidentifier.put("id",id);
        assetidentifier.put("assetName", row.get("table_name").toString() + ">" + row.get("column_name"));
        assetidentifier.put("domainId", "5b13175a-9d3b-42cd-8cc7-147dab2b83a9");
        assetidentifier.put("communityName", "Sandbox R&D Tech");
        assetidentifier.put("domainName", "Gene Domain");
        columnobject.put("assetIdentifier", assetidentifier);
        columnobject.put("columnName", row.get("column_name"));
        columnobject.put("technicalDataType", row.get("column_datatype"));
        if(!(row.get("has_date")==null))
        {
            if (row.get("has_date").toString() == "true") {
                columnobject.put("dataType", "Date");
            }
        }
        if(!(row.get("is_numeric")==null)) {

            if (row.get("is_numeric").toString() == "true") {
                columnobject.put("dataType", "Whole Number");
            }
        }
        columnobject.put("columnPosition", row.get("ordinal_position"));
        counts.put("rowCount", row.get("total_count"));
        counts.put("emptyValuesCount", row.get("null_count"));
        counts.put("distinctValuesCount", row.get("distinct_count"));
        counts.put("mode", "");
        columnobject.put("counts", counts);
        samples.put("samples", sampledata);
        columnobject.put("samples", samples);
        statistics.put("mean", "");
        statistics.put("variance", "");
        statistics.put("minimum", row.get("min"));
        statistics.put("maximum", row.get("max"));
        statistics.put("minimumTextLength", row.get("min_string_length"));
        statistics.put("maximumTextLength", row.get("max_string_length"));
        statistics.put("standardDeviation", row.get("standard_deviation"));
        columnobject.put("statistics", statistics);
        databasemetadata.put("defaultValue", "");
        databasemetadata.put("numberOfDecimalDigits", row.get("numeric_count"));
        databasemetadata.put("charOctetLength", "");
        databasemetadata.put("columnSize", row.get("non_null_count"));
        databasemetadata.put("primaryKeyName", "");
        databasemetadata.put("isNullable", "");
        databasemetadata.put("isAutoIncremented", "");
        databasemetadata.put("isGenerated", "");
        databasemetadata.put("isPrimaryKey", "");
        columnobject.put("databaseMetadata", databasemetadata);
        categoricalmetadata.put("categorical", "");
        JSONObject categoricalfrequencies = new JSONObject();
        categoricalmetadata.put("categoriesFrequencies", categoricalfrequencies);
        columnobject.put("categoricalmetadata", categoricalmetadata);
        quantiles.put("percentile1", "");
        quantiles.put("percentile5", "");
        quantiles.put("decile1", "");
        quantiles.put("quartile1", "");
        quantiles.put("median", row.get("median"));
        quantiles.put("quartile3", "");
        quantiles.put("decile9", "");
        quantiles.put("percentile95", "");
        quantiles.put("percentile99", "");
        columnobject.put("quantiles", quantiles);
        JSONArray distibutiondensityestimation = new JSONArray();
        JSONObject distrbutionlist =new JSONObject();
        distrbutionlist.put("x","");
        distrbutionlist.put("y","");
        distibutiondensityestimation.put(distrbutionlist);
        distributions.put("distributionDensityEstimation", distibutiondensityestimation);
        JSONObject histogram = new JSONObject();
        histogram.put("lowerBound", "");
        histogram.put("upperBound", "");
        histogram.put("frequency", "");
        histogram.put("lowerBoundInISODateFormat", "");
        histogram.put("upperBoundInISODateFormat", "");
        JSONArray Histogram = new JSONArray();
        Histogram.put(histogram);
        distributions.put("histogram", Histogram);
        columnobject.put("distributions", distributions);
        JSONArray columnarray = new JSONArray();
        columnarray.put(columnobject);
        JSONObject finalcolumn = new JSONObject();
        finalcolumn.put("columnProfiles", columnarray);
        return finalcolumn;
    }
}
