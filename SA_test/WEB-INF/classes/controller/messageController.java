package controller;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import app.article;
import app.articleHelper;
import app.message;
import app.messageHelper;
import tools.JsonReader;
@WebServlet("/api/message.do")
public class messageController extends HttpServlet{
	private messageHelper msh = messageHelper.getHelper();
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	    JsonReader jsr = new JsonReader(request);
	    JSONObject jso = jsr.getObject();
	    int article_id = jso.getInt("article_id");
	    String message_content = jso.getString("message_content");
	    String message_time= jso.getString("message_time");
	    Timestamp messageTime = null;

	    try {
	        // Parse the ISO 8601 string to a LocalDateTime
	        LocalDateTime dateTime = LocalDateTime.parse(message_time, DateTimeFormatter.ISO_DATE_TIME);
	        // Convert LocalDateTime to Timestamp
	        messageTime = Timestamp.valueOf(dateTime);
	    } catch (DateTimeParseException e) {
	        // Handle exception if the format is not parsable
	        e.printStackTrace();
	        // Send an error response back to client or set a default timestamp
	        messageTime = new Timestamp(System.currentTimeMillis());
	    }
	    
	    int member_id = jso.getInt("member_id");
	    message mes = new message(message_content, messageTime, article_id,member_id);
	    JSONObject data = msh.create(mes); // article_helper中有一個create

	    JSONObject resp = new JSONObject();

	    resp.put("status", "200");
	    resp.put("response", data);
	    jsr.response(resp, response);
	}

}
