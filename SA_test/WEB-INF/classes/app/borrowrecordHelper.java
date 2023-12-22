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
		JSONObject response = new JSONObject();
		try {
			/** 取得資料庫之連線 */
			conn = DBMgr.getConnection();
			
			//如果有人還沒歸還又借的判斷
			String sqlcheck2 = "SELECT `return_time` FROM `sa`.`tbl_borrow_record` WHERE `member_id` = ? ";
			pres = conn.prepareStatement(sqlcheck2);
			pres.setInt(1, memid);
			ResultSet rscheck = pres.executeQuery();
			while(rscheck.next()) {
				check = rscheck.getTimestamp("return_time");
				change=String.valueOf(check);
				//如果改社員的資料裡面找到null就跳出，代表他不能借用然後inspect設成true
				if(change=="null") {
					inspect=true;
					break;
				}
			}
			if(inspect==false) {
				//新增借用紀錄
				/** SQL指令 */
				String sql = "INSERT INTO `sa`.`tbl_borrow_record`(`borrow_time`, `instrument_id`, `member_id`)"
                   + " VALUES(?, ?, ?)";
          
				/** 取得所需之參數 */
				Timestamp borrow_time = b.getBorrow_time();
				long borrowTimeInMillis = borrow_time.getTime();
				long eightHours = borrowTimeInMillis + (8 * 60 * 60 * 1000);
				Timestamp eightHoursLater = new Timestamp(eightHours);

				int instrument_id = b.getInstrument_id();
				int member_id = b.getMember_id();  
          
				/** 將參數回填至SQL指令當中 */
				pres = conn.prepareStatement(sql);
				pres.setTimestamp(1, eightHoursLater);
				pres.setInt(2, instrument_id);
				pres.setInt(3, member_id);
				pres.executeUpdate();
				//資料庫器材數量-1
				String sqlQuantity = "SELECT `instrument_quantity` FROM `sa`.`tbl_instrument` WHERE `instrument_id` = ? LIMIT 1";

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
				response.put("check","canborrow");
			}
			else {
				response.put("check","can'tborrow");
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
		response.put("sql", exexcute_sql);
		return response;
  }
	public JSONObject getByID(int id) {
		borrowrecord b = null;
		/** 用於儲存所有檢索回之會員，以JSONArray方式儲存 */
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
			String sql = "SELECT b.borrow_record_id,m.member_name,i.instrument_name,b.borrow_time,b.return_time "+
					     "FROM `sa`.`tbl_borrow_record` b "+
					     "JOIN `sa`.`tbl_member` m ON b.member_id = m.member_id "+
					     "JOIN `sa`.`tbl_instrument` i ON b.instrument_id = i.instrument_id "+
					     "WHERE b.member_id = ? ";
    
			/** 將參數回填至SQL指令當中 */
			pres = conn.prepareStatement(sql);
			pres.setInt(1, id);
			/** 執行查詢之SQL指令並記錄其回傳之資料 */
			rs = pres.executeQuery();

			/** 紀錄真實執行的SQL指令，並印出 **/
			exexcute_sql = pres.toString();
			System.out.println(exexcute_sql);
			while(rs.next()) {
				row += 1;
				/** 將 ResultSet 之資料取出 */
				int borrowrecord_id = rs.getInt("borrow_record_id");
				String member_name=rs.getString("member_name");
				String instrument_name=rs.getString("instrument_name");
				Timestamp borrow_time=rs.getTimestamp("borrow_time");
				Timestamp return_time=rs.getTimestamp("return_time");
				JSONObject recordObject = new JSONObject();
				recordObject.put("borrow_record_id", borrowrecord_id);
			    recordObject.put("member_name", member_name);
				recordObject.put("instrument_name", instrument_name);
				recordObject.put("borrow_time",borrow_time);
				recordObject.put("return_time",return_time);
				jsa.put(recordObject);
				
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
	  public JSONObject deleteByID(int id) {
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
	            String sql = "DELETE FROM `sa`.`tbl_borrow_record` WHERE `borrow_record_id` = ? LIMIT 1";
	            
	            /** 將參數回填至SQL指令當中 */
	            pres = conn.prepareStatement(sql);
	            pres.setInt(1, id);
	            /** 執行刪除之SQL指令並記錄影響之行數 */
	            row = pres.executeUpdate();

	            /** 紀錄真實執行的SQL指令，並印出 **/
	            exexcute_sql = pres.toString();
	            System.out.println(exexcute_sql);
	            
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
	        
	        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
	        JSONObject response = new JSONObject();
	        response.put("sql", exexcute_sql);
	        response.put("row", row);
	        response.put("time", duration);
	        return response;
	    }
}
