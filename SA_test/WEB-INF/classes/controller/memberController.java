package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

import app.member;
import app.memberHelper;
import tools.JsonReader;

import javax.servlet.annotation.WebServlet;

@WebServlet("/api/member.do")
public class memberController extends HttpServlet {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private memberHelper mh =  memberHelper.getHelper();
  
	public void doPut(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        String account = jso.getString("account");
        String password = jso.getString("password");
        String name = jso.getString("name");
        String phone= jso.getString("phone");
        String group = jso.getString("group");
        String id = request.getParameter("id");
        member m = new member(account,name,password,phone,group);
        
        //new
        String adminAction = request.getParameter("adminAction");
        if (adminAction != null && adminAction.equals("createSystemAdmin")) {
            String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
            jsr.response(resp, response);
        }
        else {
        
        if(account.isEmpty() || password.isEmpty() || name.isEmpty()|| phone.isEmpty()) {
            String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
            jsr.response(resp, response);
        }
        
        else if (!mh.checkDuplicate(m)) {
            JSONObject data = mh.create(m);//member_helper中有一個create

            JSONObject resp = new JSONObject();
            
            resp.put("status", "200");
            resp.put("message", "成功! 註冊會員資料...");
            resp.put("response", data);
            resp.put("id",mh.getId(account) );
            jsr.response(resp, response);
        }
        else {
            String resp = "{\"status\": \'400\', \"message\": \'新增帳號失敗，此E-Mail帳號重複！\', \'response\': \'\'}";
            jsr.response(resp, response);
        }}
        
    }
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		
		 // 獲取 URL query string 中的 account 和 password 參數
	    String account = request.getParameter("account");
	    String password = request.getParameter("password");
	    String login = request.getParameter("login");
	    String id = request.getParameter("id");
	    
	   if("yes".equals(login)) {
		   if (mh.checkPassword(account, password)) {
		        
		        JSONObject resp = new JSONObject();
		        resp.put("status", "1");
		        resp.put("identity", mh.getIdentity(account));
		        resp.put("message", "登入成功");
		        resp.put("id",mh.getId(account) );
		        
		        response.setContentType("application/json");
		        response.setCharacterEncoding("UTF-8");
		        response.getWriter().write(resp.toString());
		    } else {
		        // 參數為 null，表示未能成功獲取到
		        JSONObject resp = new JSONObject();
		        resp.put("status", "0");
		        resp.put("message", "登入失敗");
		        
		        response.setContentType("application/json");
		        response.setCharacterEncoding("UTF-8");
		        response.getWriter().write(resp.toString());
		    }
	   }else if (id==null) {
           /** 透過MemberHelper物件之getAll()方法取回所有會員之資料，回傳之資料為JSONObject物件 */
           JSONObject query = mh.getAllmember();
           
           /** 新建一個JSONObject用於將回傳之資料進行封裝 */
           JSONObject resp = new JSONObject();
           resp.put("status", "200");
           resp.put("message", "所有會員資料取得成功");
           resp.put("response", query);        
	       response.setContentType("application/json");
	       response.setCharacterEncoding("UTF-8");
	       response.getWriter().write(resp.toString());
       }else {
    	   JSONObject query = mh.getByID(id);
           
           /** 新建一個JSONObject用於將回傳之資料進行封裝 */
           JSONObject resp = new JSONObject();
           resp.put("status", "200");
           resp.put("message", "會員資料取得成功");
           resp.put("response", query);
           response.setContentType("application/json");
	       response.setCharacterEncoding("UTF-8");
	       response.getWriter().write(resp.toString());
       }
    }
	public void doDelete(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
	        JsonReader jsr = new JsonReader(request);
	        JSONObject jso = jsr.getObject();
	        
	        /** 取出經解析到JSONObject之Request參數 */
	        int id = jso.getInt("id");
	        
	        /** 透過MemberHelper物件的deleteByID()方法至資料庫刪除該名會員，回傳之資料為JSONObject物件 */
	        JSONObject query = mh.deleteByID(id);
	        
	        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
	        JSONObject resp = new JSONObject();
	        resp.put("status", "200");
	        resp.put("message", "會員移除成功！");
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
	        String phone = jso.getString("phone");
	        String password = jso.getString("password");
	        String name = jso.getString("name");
	        String identity = jso.getString("identity");
	        String group = jso.getString("group");

	        /** 透過傳入之參數，新建一個以這些參數之會員Member物件 */
	        member m = new member(id,name, password,phone,group,identity);
	        
	        /** 透過Member物件的update()方法至資料庫更新該名會員資料，回傳之資料為JSONObject物件 */
	        JSONObject data = m.update();
	        
	        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
	        JSONObject resp = new JSONObject();
	        resp.put("status", "200");
	        resp.put("message", "成功! 更新會員資料...");
	        resp.put("response", data);
	        
	        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
	        jsr.response(resp, response);
	    }
}