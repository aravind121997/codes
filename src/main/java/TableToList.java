import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.*;
import java.util.*;

public class TableToList {
    public List<Map<String, Object>> list(String query) {
            String url = "jdbc:postgresql://us1sdlx00011:5432/usprdkosh01?currentSchema=koshv2";
        String user = "gyy12861";
        String password = "dveleE8991";
        List<Map<String, Object>> Res =new ArrayList<>();
        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            TableToList result = new TableToList();
             Res =result.resultSetToList(rs);
            return  Res;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Res;
    }
    private   List<Map<String, Object>> resultSetToList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        while (rs.next()){
            Map<String, Object> row = new HashMap<String, Object>(columns);
            for(int i = 1; i <= columns; ++i){
                row.put(md.getColumnName(i), rs.getObject(i));
            }
            rows.add(row);
        }
        return rows;
    }
}
