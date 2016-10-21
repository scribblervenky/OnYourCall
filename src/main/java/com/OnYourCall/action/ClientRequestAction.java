package com.OnYourCall.action;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.opensymphony.xwork2.Action;

public class ClientRequestAction  implements Action,ServletRequestAware  {
	private String data;
	private Map<String,String> map = new LinkedHashMap<String,String>();
	public HttpServletRequest request;
	
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	@Override
	public void setServletRequest(HttpServletRequest request) {
		 this.request = request; 	
	}
	
	@SuppressWarnings("deprecation")
	public String execute() {
		JsonParser jsonParser = new JsonParser();
		ClientRequestDAO  clientDAO=new ClientRequestDAO();
    	JsonObject json = (JsonObject)jsonParser.parse(data);
    	Set<Entry<String, JsonElement>> jsonSet     = json.entrySet();
    	
    	 for(Map.Entry<String,JsonElement> entry : jsonSet){
    		 clientDAO.setJson(entry.getKey(), json.get(entry.getKey()));
           }
    	 
    	/*map.put("NAME", name);
    	map.put("Mail", mail);
    	map.put("Date", date);*/
    	BaseActions baseCall=new BaseActions();
        try {
        	HashMap<String,String> params=new HashMap<String,String>();
        	params.put("tbl_name", "onc_cust_det");
        	params.put("name", clientDAO.getJson("Name").getAsString());
        	params.put("mail", clientDAO.getJson("Mail").getAsString());
        	params.put("date", clientDAO.getJson("Date").getAsString());
        	
        	
        	List<HashMap<String,String>> selectReturn=baseCall.StorageInvoke(params);
        	Gson gson = new Gson();
        	String gsonJson = (String)gson.toJson(selectReturn);
        	String aroot=request.getRealPath("json.json");
			
				FileWriter file = new FileWriter(aroot);
				file.write(gsonJson);
				file.flush();
				file.close();

			

		} catch (IOException e) {
			e.printStackTrace();
		}
	        return SUCCESS;
	}
	
}
