package BDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonBDD {

	private static  Connection con=null ;
	
	private SingletonBDD() {}
	
public static Connection getConnexion() {
	
	if(con==null) {
		 try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ControleAerien", "root", "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	System.out.println("Database created successfully...");
	return con;
}
}
