package BDD;
	
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;




public class Main  {
	
	
	
	public static void main(String[] args) throws ParseException, SQLException {
		Services ser=new Services();

	
		ser.supprimer("air maroc", "avion1");
	}
}
