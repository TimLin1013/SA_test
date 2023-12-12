package controller;

import java.io.*;
import java.sql.Timestamp;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;
import app.borrowrecordHelper;
import app.borrowrecord;
import tools.JsonReader;
import javax.servlet.annotation.WebServlet;
@WebServlet("/api/borrowrecord.do")
public class borrowrecordController extends HttpServlet{
  private static final long serialVersionUID = 1L;
  private borrowrecordHelper br =  borrowrecordHelper.getHelper();
  public void doPut(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
      JsonReader jsr = new JsonReader(request);
      JSONObject jso = jsr.getObject();
      String borrow_time = jso.getString("borrow-time");
      int instrument_id=jso.getInt("id");
      int member_id=jso.getInt("instrument_id");
      Timestamp borrowTime = Timestamp.valueOf(borrow_time);
      borrowrecord b = new borrowrecord(borrowTime,instrument_id,member_id);
      
     /* else if (!mh.checkDuplicate(m)) {
          JSONObject data = mh.create(m);

          JSONObject resp = new JSONObject();
          
          resp.put("status", "200");
          resp.put("message", "成功! 註冊會員資料...");
          resp.put("response", data);
          jsr.response(resp, response);
      }
      else {
          String resp = "{\"status\": \'400\', \"message\": \'新增帳號失敗，此E-Mail帳號重複！\', \'response\': \'\'}";
          jsr.response(resp, response);
      }*/
      
  }
}
