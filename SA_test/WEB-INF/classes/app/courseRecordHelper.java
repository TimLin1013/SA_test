package app;

import java.sql.*;
import org.json.*;
import util.DBMgr;
public class courseRecordHelper {
	private courseRecordHelper() {      
    }
	private static courseRecordHelper crh;
	private Connection conn = null;
	private PreparedStatement pres = null;
	public static courseRecordHelper getHelper() {
		if(crh == null) crh = new courseRecordHelper();
		return crh; 
	}
	public JSONObject createrecord(int member_id ,int course_id){
		String exexcute_sql = "";
		long start_time = System.nanoTime();
		int row = 0;
		try {
			/** 取得資料庫之連線 */
			conn = DBMgr.getConnection();
			
			String sql = "INSERT INTO `sa`.`tbl_course_registration_record`(`member_id`,`course_id`)"
	                   + " VALUES( ?, ?)";
			pres = conn.prepareStatement(sql);
			pres.setInt(1,member_id);
			pres.setInt(2,course_id );
			pres.executeUpdate();
			
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
	
	public JSONObject deleterecord(int member_id ,int course_id){
		String exexcute_sql = "";
		long start_time = System.nanoTime();
		int row = 0;
		try {
			/** 取得資料庫之連線 */
			conn = DBMgr.getConnection();
			
			String sql = "DELETE FROM `sa`.`tbl_course_registration_record` WHERE `member_id` = ? AND `course_id` = ? LIMIT 1";

			pres = conn.prepareStatement(sql);
			pres.setInt(1,member_id);
			pres.setInt(2,course_id );
			pres.executeUpdate();
			
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
}
