package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtils {
	
	private DBUtils() {}
	
	private static Connection conn;
	
	public static Connection getConnection() {
		if(conn ==null) {
			try {
				Properties prop= new Properties();
				File file= new File("src/prop/mysql.properties");
				String path= file.getPath();
				
				prop.load(new FileReader(file));
				String driver= prop.getProperty("driver");
				String url= prop.getProperty("url");
				//String user= prop.getProperty("user");
				//String password= prop.getProperty("password");
				Class.forName(driver);
				//conn= DriverManager.getConnection(url, user, password);
				conn= DriverManager.getConnection(url, prop);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return conn;
	}
	
	public static void close(AutoCloseable... closers) {
		for(AutoCloseable c: closers) {
			try {
				if(c != null) c.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
