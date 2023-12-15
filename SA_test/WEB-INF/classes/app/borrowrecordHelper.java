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
  public JSONObject createrecord(borrowrecord b,int instid,int memid){
      String exexcute_sql = "";
      long start_time = System.nanoTime();
      int row = 0;
      boolean inspect=false;
      Timestamp check;
      String change="";
      try {
          /** 取得資料庫之連線 */
          conn = DBMgr.getConnection();
          String sqlcheck = "SELECT count(*) FROM `sa`.`tbl_borrow_record` WHERE `member_id` = ?";
          /** 將參數回填至SQL指令當中 */
          pres = conn.prepareStatement(sqlcheck);
          pres.setInt(1, memid);
          /** 執行查詢之SQL指令並記錄其回傳之資料 */
          ResultSet rs = pres.executeQuery();
          if(rs.next()) {
              row = rs.getInt("count(*)");
            }
          
          String sqlcheck2 = "SELECT `return_time` FROM `sa`.`tbl_borrow_record` WHERE `member_id` = ? ";
          pres = conn.prepareStatement(sqlcheck2);
          pres.setInt(1, memid);
          ResultSet rscheck = pres.executeQuery();
          while(rscheck.next()) {
              check = rscheck.getTimestamp("return_time");
              change=toString().valueOf(check);
              if(change.equals(null)) {
            	  inspect=true;
            	  break;
              }
          }
          if(inspect==false || row==0) {
          //新增借用紀錄
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
          pres.executeUpdate();
        //資料庫器材數量-1
          String sqlQuantity = "SELECT `instrument_quantity` FROM `sa`.`tbl_instrument` WHERE `instrument_id` = ? LIMIT 1";

          // Prepare and execute the query
          pres = conn.prepareStatement(sqlQuantity);
          pres.setInt(1, instid);
          ResultSet rsQuantity = pres.executeQuery();

          int currentQuantity = 0;
          if (rsQuantity.next()) {
              currentQuantity = rsQuantity.getInt("instrument_quantity");
          }
          if (currentQuantity > 0) {
              String sqlUpdate = "UPDATE `sa`.`tbl_instrument` SET `instrument_quantity` = ? WHERE `instrument_id` = ?";
              pres = conn.prepareStatement(sqlUpdate);
              pres.setInt(1, currentQuantity - 1);
              pres.setInt(2, instid);
              pres.executeUpdate();
          }
      }
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

      /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
      JSONObject response = new JSONObject();
      response.put("sql", exexcute_sql);

      return response;
  }
}
