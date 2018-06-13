import java.sql.*;

public class WeatherDBClass
{
	
	static Connection conn = null;
	static Statement stmt = null;
	
	public static void init(){
		
		
		try{
			String userName = "root";
			String password = "1234";
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost/weatherdb";
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userName, password);
			System.out.println("Database connected");
			stmt = conn.createStatement();
			String query = "CREATE TABLE weather ( ID int  );";
			stmt.executeUpdate(query);
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try {	
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void addToDb(String city, String temp, String weatherType, String description, String humidity, String wind, String pressure){
		try{
			String userName = "root";
			String password = "1234";
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost/weatherdb";
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userName, password);
			//System.out.println("Database connected");
			stmt = conn.createStatement();
			String query = "INSERT INTO weather (city, temp, weatherType, description, humidity, wind, pressure) VALUES (\""+city+"\",\""+temp+"\",\""+weatherType+"\",\""+description+"\",\""+humidity+"\",\""+wind+"\",\""+pressure+"\");";
			stmt.executeUpdate(query);
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try {	
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	}
	
