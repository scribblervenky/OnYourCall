package com.OnYourCall.action;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ClientRequestDAO {
	 JsonObject json = new JsonObject();
	    public  JsonElement getJson(String key) {
	        return json.get(key);
	    }
	    public void setJson(String key, JsonElement value) {
	    	json.add(key, value);
	    }
}
