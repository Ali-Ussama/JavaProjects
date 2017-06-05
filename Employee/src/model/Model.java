package model;

import java.sql.*;


public class Model {
	public static  Statement statement;
	public static  ResultSet myRes ;
	public  Connection connect;
	
	
	public Model(){
		try{
			//1. Get a connection to database.
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/college_db?autoReconnect=true&useSSL=false" , "root" , "");
			
			//2.Create a statement.
			statement = connect.createStatement();
			
			//3.Execute server Query.
//			ResultSet myRes = statement.executeQuery("SELECT * FROM `worker`");
			
			//4.Process the result set.
//			while(myRes.next()){
//				System.out.println(myRes.getInt("id") + ", " + myRes.getString("name")+ ", " + myRes.getString("address")+ ", " + myRes.getString("phone")
//								 + ", " + myRes.getString("age")+ ", " + myRes.getString("job_id")+ ", " + myRes.getString("dep_id"));
//				
//			}
			
		}catch(Exception ex){
			ex.getStackTrace();
		}
	}

}
