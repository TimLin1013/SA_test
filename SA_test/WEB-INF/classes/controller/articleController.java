package controller;
import app.article;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
@WebServlet("/api/article.do")
public class articleController extends HttpServlet{
	public void doput(HttpServletRequest request,HttpServletResponse response)
		throws ServletException, IOException {
		   String title = request.getParameter("title");//從網頁取得甚麼資訊
		   String article_id = request.getParameter("article_id");
		   String article_content = request.getParameter("article_content");
		   JSONObject resp = new JSONObject();
	}
}

