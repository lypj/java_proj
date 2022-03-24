package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {
	Connection con = null;
	PreparedStatement insertStatement;
	PreparedStatement selectStatement;
	
	public DB()
	{
		
		try {
			con = DriverManager.getConnection("jdbc:sqlite:users.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String insertSQL = "Insert Into userinfo (username, pw) Values (?,?)";
		String selectSQL = "Select username from userinfo where username = ?";
		
		try {
			insertStatement = con.prepareStatement(insertSQL);
			selectStatement = con.prepareStatement(selectSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Database connected");
			
	}
	
	public void addUser(String username, String pw)
	{
		try {
			insertStatement.setString(1, username);
			insertStatement.setString(2, pw);
			insertStatement.execute();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean userExists(String username) 
	{
		ResultSet rs = null;
		boolean r = false;
		
		try {
			selectStatement.setString(1, username);
			rs = selectStatement.executeQuery();
			if(rs.next())
			{
				System.out.println(rs.getString("username"));
				r = true;
			}
			else
			{
				r = false;
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return r;	
	}
	
	public boolean checkpw(String username, String pw)
	{
		ResultSet rs = null;
		boolean r = false;
		String selectSQL = "Select pw from userinfo where username = ?";
		try {
			PreparedStatement statement = con.prepareStatement(selectSQL);
			statement.setString(1, username);
			rs = statement.executeQuery();
			if(rs.getString("pw").equals(pw))
			{	
				r = true;
			}
			else
			{
				r = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return r;
	}
	
	public static void main(String [] args)
	{
		DB db = new DB();
//		System.out.println(db.userExists("user1"));
//		System.out.println(db.checkpw("user1","nopaword"));
		db.addUser("user2","111111;");
	}
}
