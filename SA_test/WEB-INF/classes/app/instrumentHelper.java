package app;
import java.sql.*;
import org.json.*;
import util.DBMgr;
public class instrumentHelper {
	private instrumentHelper() {
        
    }
    private static instrumentHelper ih;
    private Connection conn = null;
    private PreparedStatement pres = null;
    private instrument i;
    public static instrumentHelper getHelper() {
        if(ih == null) ih = new instrumentHelper();
        return ih;
    }
    public JSONObject getAllinstrument() {
        instrument i = null;
        JSONArray jsa = new JSONArray();
        String exexcute_sql = "";
        long start_time = System.nanoTime();
        int row = 0;
        ResultSet rs = null;
        try {
            conn = DBMgr.getConnection();
            String sql = "SELECT * FROM `sa`.`tbl_instrument`";
            pres = conn.prepareStatement(sql);
            rs = pres.executeQuery();
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;
                int id = rs.getInt("instrument_id");
                String name = rs.getString("instrument_name");
                int quantity = rs.getInt("instrument_quantity");
                i = new instrument(id,name,quantity);
                /** 取出該名會員之資料並封裝至 JSONsonArray 內 */
                jsa.put(i.getData());
            }

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }
        
        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
        
        /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }
    public JSONObject deleteone(int id) {
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        int quantity=0;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "Update `sa`.`tbl_instrument` SET `instrument_quantity` = ? WHERE `instrument_id` = ? ";
            quantity=searchquantity(id);
            quantity-=1;
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, quantity);
            pres.setInt(2, id);
            exexcute_sql = pres.toString();
            row = pres.executeUpdate();
        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres,conn);
        }

        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
        
        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("time", duration);
        response.put("sql", exexcute_sql);
        response.put("row",row);
        return response;
    }
    public int searchquantity(int id ) {
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        int quantity=0;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            String sql = "SELECT `instrument_quantity` FROM `sa`.`tbl_instrument` WHERE `instrument_id` = ? LIMIT 1";
            /** SQL指令 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1,id);
            rs = pres.executeQuery();
            if(rs.next()) {
            	quantity=rs.getInt("instrument_quantity");
            }
            
        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres,conn);
    }
        
        return quantity;
}
}
