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
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	     throws ServletException, IOException {
			
	    	
	    	JSONObject data = ah.getArticle();//article_helper中有一個getArticle
	    	JSONObject resp = new JSONObject();
	           resp.put("status", "200");
	           resp.put("message", "article取得成功");
	           resp.put("response", data);
	           response.setContentType("application/json");
		       response.setCharacterEncoding("UTF-8");
		       response.getWriter().write(resp.toString());     
    }
	public void doDelete(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    // Extract article ID from the request
	    String idStr = request.getParameter("id");
	    int id = Integer.parseInt(idStr);

	    // Call the delete method from articleHelper
	    JSONObject data = ah.deleteByID(id);

	    // Prepare the response
	    JSONObject resp = new JSONObject();
	    if (data.getInt("row") > 0) {
	        resp.put("status", "200");
	        resp.put("message", "Article deleted successfully");
	    } else {
	        resp.put("status", "400");
	        resp.put("message", "Article deletion failed");
	    }

	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(resp.toString());
	}

}

