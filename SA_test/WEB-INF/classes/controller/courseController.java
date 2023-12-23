package controller;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;
import app.course;
import app.courseHelper;
import tools.JsonReader;

import javax.servlet.annotation.WebServlet;
@WebServlet("/api/course.do")
public class courseController extends HttpServlet{
  private static final long serialVersionUID = 1L;
  private courseHelper ch =  courseHelper.getHelper();
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
	  JSONObject query = ch.getAllcourse();
      JSONObject resp = new JSONObject();
      resp.put("status", "200");
      resp.put("message", "所有資料取得成功");
      resp.put("response", query);        
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(resp.toString());
      
  }
  public void doPut(HttpServletRequest request, HttpServletResponse response)//新增設課評價和繳交作業
	      throws ServletException, IOException {
	  String submitHomework= request.getParameter("Homework");	
	  int member_id = Integer.parseInt(request.getParameter("member_id"));
	  int course_id = Integer.parseInt(request.getParameter("course_id"));
	  int course_value = Integer.parseInt(request.getParameter("rating"));
	  String content=request.getParameter("content");
	  if(submitHomework.equals("Yes")) {
		  JSONObject data = ch.submitHomework(member_id,course_id,content);
		  JSONObject resp = new JSONObject();
			resp.put("status", "200");
			resp.put("message", "所有資料取得成功");
			resp.put("response", data);    
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(resp.toString());
	  }
	  else {
		
		JSONObject data = ch.createCourseRating(member_id,course_id,course_value);
		int Rating =ch.getRating(member_id,course_id);
		JSONObject resp = new JSONObject();
		resp.put("status", "200");
		resp.put("message", "所有資料取得成功");
		resp.put("response", data);    
		resp.put("Rating", Rating);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(resp.toString()); 
	  }
	  
	  	
	      
  }
	  
  
}
