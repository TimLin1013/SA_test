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
	public void doPut(HttpServletRequest request,HttpServletResponse response)
		throws ServletException, IOException {
			JsonReader jsr = new JsonReader(request);
			JSONObject jso = jsr.getObject();
			String title = request.getParameter("title");//從網頁取得甚麼資訊
			//String article_id = request.getParameter("article_id");資料庫直接給
			String article_content = request.getParameter("article_content");
			//String article_time = request.getParameter("article_time");資料庫直接給
			String member_id = request.getParameter("member_id");
			JSONObject resp = new JSONObject();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	     throws ServletException, IOException {
	    	String title = request.getParameter("title");
	    	String article_content = request.getParameter("article_content");
	    	String article_id = request.getParameter("article_id");
	    	String Identity = request.getParameter("identity");
    }
}

