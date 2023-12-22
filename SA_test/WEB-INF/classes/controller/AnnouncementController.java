package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

import app.AnnouncementHelper;
import tools.JsonReader;
import app.Announcement;

import javax.servlet.annotation.WebServlet;

@WebServlet("/api/member.do")
public class AnnouncementController extends HttpServlet {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private AnnouncementHelper ah =  AnnouncementHelper.getHelper();
  
	public void doPut(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        String title = jso.getString("title");
        String content = jso.getString("content");
        String id = request.getParameter("id");
        Announcement a = new Announcement(title,content, Integer.parseInt(id));

        
    }
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		
	      JsonReader jsr = new JsonReader(request);
	        JSONObject jso = jsr.getObject();
	    String title = request.getParameter("title");
	    String content = request.getParameter("content");
	    String id = request.getParameter("id");
	    

	    JSONObject query = ah.getAnnouncementByID(Integer.parseInt(id));
           
           /** 新建一個JSONObject用於將回傳之資料進行封裝 */
           JSONObject resp = new JSONObject();
           resp.put("status", "200");
           resp.put("message", "公告資料取得成功");
           resp.put("response", query);
           response.setContentType("application/json");
	       response.setCharacterEncoding("UTF-8");
	       response.getWriter().write(resp.toString());
       
    }
	public void doDelete(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
	        JsonReader jsr = new JsonReader(request);
	        JSONObject jso = jsr.getObject();
	        
	        /** 取出經解析到JSONObject之Request參數 */
	        int id = jso.getInt("id");
	        
	        /** 透過MemberHelper物件的deleteByID()方法至資料庫刪除該名會員，回傳之資料為JSONObject物件 */
	        JSONObject query = ah.deleteByID(id);
	        
	        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
	        JSONObject resp = new JSONObject();
	        resp.put("status", "200");
	        resp.put("message", "公告移除成功！");
	        resp.put("response", query);

	        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
	        jsr.response(resp, response);
    }
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
	        JsonReader jsr = new JsonReader(request);
	        JSONObject jso = jsr.getObject();
	        
	        /** 取出經解析到JSONObject之Request參數 */
	        int id = jso.getInt("id");
	        String title = jso.getString("title");
	        String content = jso.getString("content");

	        /** 透過傳入之參數，新建一個以這些參數之會員Member物件 */
	        Announcement a = new Announcement(title,content, id);
	        
	        /** 透過Member物件的update()方法至資料庫更新該名會員資料，回傳之資料為JSONObject物件 */
	        JSONObject data = ah.update(a);
	        
	        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
	        JSONObject resp = new JSONObject();
	        resp.put("status", "200");
	        resp.put("message", "成功! 更新公告...");
	        resp.put("response", data);
	        
	        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
	        jsr.response(resp, response);
	    }
}
