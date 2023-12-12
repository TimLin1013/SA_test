package app;

import java.sql.*;
import org.json.*;

import util.DBMgr;
public class borrowrecordHelper {
	private borrowrecordHelper() {
      
  }
  private static borrowrecordHelper br;
  private Connection conn = null;
  private PreparedStatement pres = null;
  public static borrowrecordHelper getHelper() {
      if(br == null) br = new borrowrecordHelper();
      return br;
  }
  public JSONObject createrecord(borrowrecord b){
      String exexcute_sql = "";
      long start_time = System.nanoTime();
      int row = 0;
      try {
          /** 取得資料庫之連線 */
          conn = DBMgr.getConnection();
          /** SQL指令 */
          String sql = "INSERT INTO `sa`.`tbl_borrow_record`(`borrow_time`, `instrument_id`, `member_id`)"
                  + " VALUES(?, ?, ?)";
          
          /** 取得所需之參數 */
          Timestamp borrow_time = b.getBorrow_time();
          int instrument_id = b.getInstrument_id();
          int member_id = b.getMember_id();
          
          /** 將參數回填至SQL指令當中 */
          pres = conn.prepareStatement(sql);
          pres.setTimestamp(1, borrow_time);
          pres.setInt(2, instrument_id);
          pres.setInt(3, member_id);
          
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
}
