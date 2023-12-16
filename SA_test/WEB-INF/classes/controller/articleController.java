package controller;
import app.article;
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
	//private articleHelper ah = articleHelper.getHelper();
	public void doput(HttpServletRequest request,HttpServletResponse response)
		throws ServletException, IOException {
			JsonReader jsr = new JsonReader(request);
			JSONObject jso = jsr.getObject();
			String title = request.getParameter("title");//從網頁取得甚麼資訊
			String article_id = request.getParameter("article_id");
			String article_content = request.getParameter("article_content");
			String article_time = request.getParameter("article_time");
			String member_id = request.getParameter("member_id");
			JSONObject resp = new JSONObject();//不知道這個在尬麻?
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		        java.util.Date parsedDate = dateFormat.parse(article_time);
		        Timestamp borrow_time = new Timestamp(parsedDate.getTime());
		        
			}catch (ParseException e) {
		           e.printStackTrace();
		       }
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	     throws ServletException, IOException {
	    	String title = request.getParameter("title");
	    	String article_content = request.getParameter("article_content");
	    	String article_id = request.getParameter("article_id");
    }
}

