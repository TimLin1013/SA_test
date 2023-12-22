package app;

import java.sql.Timestamp;
import org.json.JSONObject;

public class Announcement {
    private int announcement_id; // Unique identifier for the announcement
    private String title;
    private String announcement_content;
    private Timestamp announcement_time;
    private int admin_id; // Identifier for the admin who created the announcement

    // Constructors
    public Announcement(String announcement_content, String title, Timestamp announcement_time, int admin_id) {
        this.announcement_content = announcement_content;
        this.title = title;
        this.announcement_time = announcement_time;
        this.admin_id = admin_id;
    }

    public Announcement(int admin_id, String title, String announcement_content, Timestamp announcement_time) {
        this.announcement_content = announcement_content;
        this.title = title;
        this.announcement_time = announcement_time;
        this.admin_id = admin_id;
    }

    public Announcement(String title, String announcement_content, int admin_id) {
        this.announcement_content = announcement_content;
        this.title = title;
        this.admin_id = admin_id;
    }

    public Announcement(String title, int admin_id) {
        this.title = title;
        this.admin_id = admin_id;
    }

    // Method to return data to the frontend
    public JSONObject getData() {
        JSONObject jsonAnnouncement = new JSONObject();
        jsonAnnouncement.put("title", getTitle());
        jsonAnnouncement.put("announcement_content", getAnnouncementContent());
        jsonAnnouncement.put("announcement_time", getAnnouncementTime());
        jsonAnnouncement.put("admin_id", getAdminId());
        return jsonAnnouncement;
    }

    // Getter methods
    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.announcement_content;
    }

    public Timestamp getAnnouncementTime() {
        return this.announcement_time;
    }

    public int getAdminId() {
        return this.admin_id;
    }

    public String getAnnouncementContent() {
        return this.announcement_content;
    }
}