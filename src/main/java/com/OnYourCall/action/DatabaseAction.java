package com.OnYourCall.action;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class DatabaseAction {
	
	Connection connection = getConnection();
	static String tbl_name=null;
	static Boolean flag=true;
	List<HashMap<String,String>> returnList=new ArrayList<HashMap<String,String>>();
	 
	public List<HashMap<String,String>> Save(HashMap<String,String> params){
		DatabaseMetaData md;
		try {
			tbl_name=params.get("tbl_name");
			connection.setAutoCommit(false);
			md = connection.getMetaData();
			 ResultSet rs = md.getTables(null, null,"%", null);
	         while(rs.next()){
				 if (tbl_name.equals(rs.getString(3))) {
		        	 Insert(params);
		        	 flag=false;
		        	 break;
		         }else{
		        	 flag=true;
		         }
	         }
	         if(flag){
	        	 Initiate(params);
	         }
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return returnList;
	}
	public void Insert(HashMap<String,String> params){
		
		
	         try{
	        	 Statement stmt;
	 			 stmt = connection.createStatement();
	 			
	 			 String equery="INSERT INTO "+tbl_name+"(name,mail,date,sequence) VALUES ";
	 			  String name=params.get("name");
	 			  String mail=params.get("mail");
	 			  String date=params.get("date");
	 			  		equery+="('"+name+"','"+mail+"','"+date+"',nextval('seq'))";
	 			  		System.out.println(equery);
	 			 //INSERT INTO ticks (tick) VALUES ('gee')
	 			 stmt.executeUpdate(equery);
	 			Get();
	 			connection.commit();
	 			
	         }
	         catch(Exception e){
	        	 e.printStackTrace(); 
	         }
		}
	   public void Delete(HashMap<String,String> params){
		   
		try {
			Statement stmt;
			stmt = connection.createStatement();
			 String equery="DROP TABLE "+tbl_name;
			 System.out.println(equery);
			stmt.executeUpdate(equery);
			connection.commit();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	         
	   }
	   public void Initiate(HashMap<String,String> params){
		   try {
				Statement stmt;
				stmt = connection.createStatement();
		
		String equery="CREATE TABLE "+tbl_name+" (name text,mail text,date text,sequence int)";
				 System.out.println(equery);
				stmt.executeUpdate(equery);
				
				equery="DROP SEQUENCE IF EXISTS seq";
				 System.out.println(equery);
				stmt.executeUpdate(equery);
				
				equery="CREATE SEQUENCE seq START 001";
				 System.out.println(equery);
				stmt.executeUpdate(equery);
				
				connection.commit();				  
				Insert(params);
			} 
		   catch (SQLException e) {
				e.printStackTrace();
			} 
	   }
	   public void Get(){  
		   try {
				Statement stmt;
				stmt = connection.createStatement();
				 String equery="SELECT name,mail,date,sequence FROM "+tbl_name+" WHERE sequence BETWEEN (currval('seq')-50) AND currval('seq')";
				 System.out.println(equery);
				 ResultSet rs = stmt.executeQuery(equery);
		         while (rs.next()) {
		        	 HashMap<String,String> returnMap=new HashMap<String,String>();
		        	 returnMap.put("Name", rs.getString("name"));
		             System.out.println("Read from DB: " + rs.getString("name"));
		             returnMap.put("Mail", rs.getString("mail"));
		             System.out.println("Read from DB: " + rs.getString("mail"));
		             returnMap.put("Date", rs.getString("date"));
		             System.out.println("Read from DB: " + rs.getString("date"));
		             returnMap.put("Sequence", rs.getString("sequence"));
		             System.out.println("Read from DB: " + rs.getString("sequence"));
		             returnList.add(returnMap);
		         }
		         
			} catch (SQLException e) {
				e.printStackTrace();
			} 
	   }
	   private static Connection getConnection()  {

			 Connection con = null;
			 URI dbUri;
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		 //Local
		 /*
				 try {
				       	Class.forName("org.postgresql.Driver");
				       	con = DriverManager.getConnection("jdbc:postgresql://localhost:8081/testdb","postgres", "root");
				      }catch (Exception e) {
				    	  e.printStackTrace();
		                  System.err.println(e.getClass().getName()+": "+e.getMessage());
		                  System.exit(0);
				      }
				          System.out.println("Opened database successfully");      
		         */
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		    //Env   	 
		     	 
		 		try {
		 			dbUri = new URI(System.getenv("DATABASE_URL"));
		 			String username = dbUri.getUserInfo().split(":")[0];
		 		    String password = dbUri.getUserInfo().split(":")[1];
		 		    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();
		 		    try {
		 				con=DriverManager.getConnection(dbUrl, username, password);
		 			} catch (SQLException e) {
		 				e.printStackTrace();
		 			}
		 		} catch (URISyntaxException e) {
		 			e.printStackTrace();
		 		}        
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		          return con;		     	
		     }
}
