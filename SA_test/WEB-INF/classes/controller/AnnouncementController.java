package controller;

import app.Announcement;
import app.AnnouncementHelper;
import tools.JsonReader;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebServlet("/api/announcement.do")
public class AnnouncementController extends HttpServlet {
    private AnnouncementHelper announcementHelper = AnnouncementHelper.getHelper();

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonReader jsonReader = new JsonReader(request);
        JSONObject jsonObject = jsonReader.getObject();

        // Validate and sanitize user input

        // Get data from the request
        String announcementId = request.getParameter("announcement_id");
        String announcementContent = request.getParameter("announcement_content");

        // Create Announcement object
        int announcementIdInt = Integer.parseInt(announcementId); // 将字符串转换为整数
        Announcement announcement = new Announcement(announcementIdInt, announcementContent, null,null);


        // Perform the update operation
        JSONObject data = new JSONObject();

        // Construct the response
        JSONObject jsonResponse = createResponse(data);

        // Send the response to the client
        jsonReader.response(jsonResponse, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Similar changes as in doPut method
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Similar changes as in doPut method
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Similar changes as in doPut method
    }

    private JSONObject createResponse(JSONObject data) {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("status", "200");
        jsonResponse.put("response", data);
        return jsonResponse;
    }
}