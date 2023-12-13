package controller;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	   String borrowtime = request.getParameter("borrow-time");
	   String instrument_id = request.getParameter("instrument_id");
	   String id = request.getParameter("id");
	   JSONObject resp = new JSONObject();
	  //2023-12-13T18:27 時間格式
	   try {
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
           java.util.Date parsedDate = dateFormat.parse(borrowtime);
           Timestamp borrow_time = new Timestamp(parsedDate.getTime());
           resp.put("borrowTime", borrow_time);
       } catch (ParseException e) {
           e.printStackTrace();
       }
      
      //resp.put("response",data);
      //resp.put("borrowTime", borrow_time);
      //resp.put("borrow_time", borrow_time);
      resp.put("id", id);
      resp.put("status", "0");
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(resp.toString());
      
  }
}
