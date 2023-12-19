package controller;
import app.article;
import app.articleHelper;
import app.member;
import tools.JsonReader;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
@WebServlet("/api/article.do")
public class articleController extends HttpServlet{
	private articleHelper ah = articleHelper.getHelper();
	
	 public void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	        JsonReader jsr = new JsonReader(request);
	        JSONObject jso = jsr.getObject();
	        String title = jso.getString("title");
	        String article_content = jso.getString("content");
	        int member_id = jso.getInt("member_id");
	        article a = new article(title, article_content, member_id);
	        JSONObject data = ah.create(a); // article_helper中有一個create

	        JSONObject resp = new JSONObject();

	        resp.put("status", "200");
	        resp.put("response", data);
	        jsr.response(resp, response);
	    }
	/*public void doPost(HttpServletRequest request,HttpServletResponse response)
		throws ServletException, IOException {
			JsonReader jsr = new JsonReader(request);
			JSONObject jso = jsr.getObject();
			String title = request.getParameter("title");//從網頁取得甚麼資訊
			//String article_id = request.getParameter("article_id");資料庫直接給
			String article_content = request.getParameter("article_content");
			//String article_time = request.getParameter("article_time");資料庫直接給
			String member_id = request.getParameter("member_id");
			int id = Integer.valueOf(member_id);
			article a = new article(title,article_content,id);
            JSONObject data = ah.create(a);//article_helper中有一個create

            JSONObject resp = new JSONObject();
            
            resp.put("status", "200");
            resp.put("response", data);
            jsr.response(resp, response);
	}*/
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	     throws ServletException, IOException {
			
			JsonReader jsr = new JsonReader(request);
			JSONObject jso = jsr.getObject();
	    	String title = request.getParameter("title");
	    	String article_id = request.getParameter("article_id");
	    	String member_id = request.getParameter("member_id");
			int id = Integer.valueOf(member_id);
	    	//String Identity = request.getParameter("identity");
	    	article a = new article(title,id);
	    	JSONObject data = ah.getArticle(a);//article_helper中有一個getArticle
	    	JSONObject resp = new JSONObject();
            
            resp.get("status");
            resp.get("response");
            jsr.response(resp, response);
            
    }
}

