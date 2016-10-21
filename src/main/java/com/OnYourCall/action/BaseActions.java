package com.OnYourCall.action;

import java.util.HashMap;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

public class BaseActions extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	public List<HashMap<String,String>> StorageInvoke(HashMap<String,String> params){
		DatabaseAction dataAction =new DatabaseAction();
		List<HashMap<String,String>> returnList=dataAction.Save(params);
		return returnList;
	}   

	
}
