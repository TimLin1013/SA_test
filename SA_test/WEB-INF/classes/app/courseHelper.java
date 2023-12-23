package app;

import java.sql.*;
import java.time.LocalDateTime;
import org.json.*;
import app.course;
import util.DBMgr;

public class courseHelper {
	private static courseHelper ch;
	private Connection conn = null;
	private PreparedStatement pres = null;
	public courseHelper(){
		
	}
	 public static courseHelper getHelper() {
        if(ch == null) ch = new courseHelper();
        return ch;
    }
	   public JSONObject getAllcourse() {
	        course c = null;

	        JSONArray jsa = new JSONArray();
	        /** 記錄實際執行之SQL指令 */
	        String exexcute_sql = "";
	        /** 紀錄程式開始執行時間 */
	        long start_time = System.nanoTime();
	        /** 紀錄SQL總行數 */
	        int row = 0;
	        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
	        ResultSet rs = null;
	        
	        try {
	            /** 取得資料庫之連線 */
	            conn = DBMgr.getConnection();
	            /** SQL指令 */
	            String sql = "SELECT * FROM `sa`.`tbl_course`";
	            
	            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
	            pres = conn.prepareStatement(sql);
	            /** 執行查詢之SQL指令並記錄其回傳之資料 */
	            rs = pres.executeQuery();

	            /** 紀錄真實執行的SQL指令，並印出 **/
	            exexcute_sql = pres.toString();
	            System.out.println(exexcute_sql);
	            
	            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
	            while(rs.next()) {
	                /** 每執行一次迴圈表示有一筆資料 */
	                row += 1;
	                
	                /** 將 ResultSet 之資料取出 */
	                int course_id = rs.getInt("course_id");
	                String course_name = rs.getString("course_name");
	                String  course_time= rs.getString("course_time");
	                int member_id = rs.getInt("member_id");
	                Timestamp course_start_time	= rs.getTimestamp("course_start_time");
	                String  course_location= rs.getString("course_location");
	                c = new course(course_id,course_name, course_start_time,member_id,course_time,course_location);
	                /** 取出該名會員之資料並封裝至 JSONsonArray 內 */
	                jsa.put(c.getData());
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
	   public JSONObject createCourseRating(int member_id,int course_id,int course_value) {
		   /** 記錄實際執行之SQL指令 */
	        String exexcute_sql = "";
	        /** 紀錄程式開始執行時間 */
	        long start_time = System.nanoTime();
	    	/** 紀錄SQL總行數 */
	        int row = 0;
	        
	        try {
	            /** 取得資料庫之連線 */
	            conn = DBMgr.getConnection();
	            /** SQL指令 */
	            String sql = "INSERT INTO `sa`.`tbl_course_value` (`member_id`, `course_id`, `course_value`) VALUES (?, ?, ?)";

	        
	            
	            /** 將參數回填至SQL指令當中 */
	            pres = conn.prepareStatement(sql);
	            pres.setInt(1, member_id);
	            pres.setInt(2, course_id);
	            pres.setInt(3, course_value );
	           
	            
	            /** 執行新增之SQL指令並記錄影響之行數 */
	            row = pres.executeUpdate();
	            
	        } catch (SQLException e) {
	            /** 印出JDBC SQL指令錯誤 **/
	            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
	        } catch (Exception e) {
	            /** 若錯誤則印出錯誤訊息 */
	            e.printStackTrace();
	        } finally {
	            /** 關閉連線並釋放所有資料庫相關之資源 **/
	            DBMgr.close(pres, conn);
	        }
	        exexcute_sql = pres.toString();
	        System.out.println(exexcute_sql);

	      
	        /** 紀錄程式結束執行時間 */
	        long end_time = System.nanoTime();
	        /** 紀錄程式執行時間 */
	        long duration = (end_time - start_time);

	        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
	        JSONObject response = new JSONObject();
	        response.put("sql", exexcute_sql);
	        response.put("time", duration);
	        response.put("row", row);
	        return response;
	    }
	   public int getRating(int member_id,int course_id) {
	    	int rating = 0; // 初始化為空值
	    	try {
	            /** 取得資料庫之連線 */
	            conn = DBMgr.getConnection();
	            /** SQL指令 */
	             String sql ="SELECT course_value FROM `sa`.`tbl_course_value` WHERE `member_id` = ? AND `course_id` = ? LIMIT 1";
	            /** 將參數回填至SQL指令當中 */
	            pres = conn.prepareStatement(sql);
	            pres.setInt(1, member_id);
	            pres.setInt(2, course_id);
	            /** 執行查詢 */
	            ResultSet rs = pres.executeQuery();

	            /** 檢查是否有查詢結果 */
	            if (rs.next()) {
	                rating = rs.getInt("course_value");
	            }
	           
	        } catch (SQLException e) {
	            /** 印出JDBC SQL指令錯誤 **/
	            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
	        } catch (Exception e) {
	            /** 若錯誤則印出錯誤訊息 */
	            e.printStackTrace();
	        } finally {
	            /** 關閉連線並釋放所有資料庫相關之資源 **/
	            DBMgr.close( pres, conn);
	        }
	    	return rating;
	    }
}
