package controller;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

import app.borrowrecord;
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
	  String isHomework=request.getParameter("homework");
	  if ("yes".equals(isHomework)) {
		  String Str_teacher_id=request.getParameter("teacher_id");
		  int teacher_id=Integer.parseInt(Str_teacher_id);
		  JSONObject query=ch.getAllhomework(teacher_id);
		  JSONObject resp = new JSONObject();
	      resp.put("status", "200");
	      resp.put("message", "所有資料取得成功");
	      resp.put("response", query);        
	      response.setContentType("application/json");
	      response.setCharacterEncoding("UTF-8");
	      response.getWriter().write(resp.toString());
	  }else {
		  JSONObject query = ch.getAllcourse();
	      JSONObject resp = new JSONObject();
	      resp.put("status", "200");
	      resp.put("message", "所有資料取得成功");
	      resp.put("response", query);        
	      response.setContentType("application/json");
	      response.setCharacterEncoding("UTF-8");
	      response.getWriter().write(resp.toString());
	      
	  }
	  
  }
  public void doPut(HttpServletRequest request, HttpServletResponse response)//新增設課評價和繳交作業
	      throws ServletException, IOException {
	  String admin= request.getParameter("admin");
	    if ("yes".equals(admin)) {
	        JsonReader jsr = new JsonReader(request);
	        JSONObject jso = jsr.getObject();
	        String course_name = jso.getString("course_name");
	        String course_start_time = jso.getString("course_start_time");
	        String teacher_id = jso.getString("teacher_id");
	        int Teacher_id = Integer.parseInt(teacher_id);
	        String course_location = jso.getString("course_location");

	        try {
	        	SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	        	SimpleDateFormat outputDateFormat = new SimpleDateFormat("MM/dd EEEE HH:mm");

	        	java.util.Date parsedDate = inputDateFormat.parse(course_start_time);
	        	long adjustedTime = parsedDate.getTime() + (8 * 60 * 60 * 1000); // 加上8小時

	        	Timestamp Course_start_time = new Timestamp(adjustedTime);

	        	// 格式化時間
	        	String formattedTime = outputDateFormat.format(parsedDate);

	        	// 計算下一個時間點
	        	Calendar calendar = Calendar.getInstance();
	        	calendar.setTime(parsedDate);
	        	calendar.add(Calendar.HOUR, 2); // 加2小時
	        	java.util.Date nextTime = calendar.getTime();

	        	String formattedNextTime = outputDateFormat.format(nextTime);

	        	String time = formattedTime + " - " + formattedNextTime;

	        	JSONObject resp = new JSONObject();
	        	JSONObject data = ch.createCourse(course_name, Course_start_time, Teacher_id, course_location,time);
	        	resp.put("status", "200");
	        	resp.put("message", "所有資料取得成功");
	        	resp.put("response", data);

	        	response.setContentType("application/json");
	        	response.setCharacterEncoding("UTF-8");
	        	response.getWriter().write(resp.toString());

	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	    }
	  else {
		  String submitHomework= request.getParameter("Homework");	
		  int member_id = Integer.parseInt(request.getParameter("member_id"));
		  int course_id = Integer.parseInt(request.getParameter("course_id"));
		  String Str_course_value = request.getParameter("rating");
		  String content=request.getParameter("content");
		  
		  if("Yes".equals(submitHomework)) {
			  	JSONObject resp = new JSONObject();
			  	JSONObject data = ch.submitHomework(member_id,course_id,content);
			 
				resp.put("status", "200");
				resp.put("message", "所有資料取得成功");
				resp.put("response", data);    
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(resp.toString());
		  }
		  else {
			  JSONObject resp = new JSONObject();
			  int course_value=Integer.parseInt(Str_course_value);
			JSONObject data = ch.createCourseRating(member_id,course_id,course_value);
			int Rating =ch.getRating(member_id,course_id);
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
  public void doPost(HttpServletRequest request, HttpServletResponse response)
	      throws ServletException, IOException {
		  int score=Integer.parseInt(request.getParameter("new_score"));
		  int student_id=Integer.parseInt(request.getParameter("student_id"));
		  int course_id=Integer.parseInt(request.getParameter("course_id"));
		  	JSONObject query = ch.updateScore(student_id,course_id,score);
			  
		      JSONObject resp = new JSONObject();
		      resp.put("status", "200");
		      resp.put("message", "所有資料取得成功");
		      resp.put("response", query);        
		      response.setContentType("application/json");
		      response.setCharacterEncoding("UTF-8");
		      response.getWriter().write(resp.toString());
		      
		  
		  
	  }
  
}
