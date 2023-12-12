package app;

import org.json.JSONObject;

public class instrument {
	private int id;
	private String name;
	private int quantity;
	public instrument (int id, String name, int quantity) {
		this.id=id;
		this.name=name;
		this.quantity=quantity;
	}
	public JSONObject getData() {
        /** 透過JSONObject將該名會員所需之資料全部進行封裝*/ 
        JSONObject jso = new JSONObject();
        jso.put("id", getId());
        jso.put("name", getName());
        jso.put("Quantity", getQuantity());
        
        return jso;
    }
	public int getId()
	{	
		return this.id;
	}
	public String getName()
	{
		return this.name;
	}
	public int getQuantity()
	{
		return this.quantity;
	}
}
